/*******************************************************************************
 *  (C) Copyright 2009 Molisys Soluti.ons Co., Ltd. , All rights reserved       *
 *                                                                             *
 *  This source code and any compilation or derivative thereof is the sole     *
 *  property of Molisys Solutions Co., Ltd. and is provided pursuant to a      *
 *  Software License Agreement.  This code is the proprietary information      *
 *  of Molisys Solutions Co., Ltd and is confidential in nature.  Its use and  *
 *  dissemination by any party other than Molisys Solutions Co., Ltd is        *
 *  strictly limited by the confidential information provisions of the         *
 *  Agreement referenced above.                                                *
 ******************************************************************************/
package org.hcmut.emr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hcmut.emr.config.AppConfig;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.request.Concept;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.text.preprocess.ProcessENText;
import org.hcmut.emr.text.preprocess.ProcessText;
import org.hcmut.emr.text.preprocess.ProcessVNText;
import org.hcmut.emr.utils.IBOTagHelper;
import org.hcmut.emr.word.WordService;
import org.hcmut.emr.worvn.Worvn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcmut.emr.crf.CRFTagger;
import com.molisys.framework.base.BaseController;

/**
 * @author sinhlk
 *
 */
@Controller
@RequestMapping(value = "/ner")
public class NERController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(NERController.class);
	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private WordService wordService;

	@Autowired
	private AppConfig appConfig;

	@RequestMapping(value = "/")
	public String index() {
		return "ner";
	}

	@RequestMapping(value = "/annotation", method = RequestMethod.POST)
	public @ResponseBody Object run(@RequestParam("content") String content,
			@RequestParam("language") String language,
			@RequestParam("listFeatures") String listFeatures) {
		logger.info("Start concept annotation");
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> res = new HashMap<String, Object>();
		ProcessText processText = null;
		CRFTagger crfTagger = null;
		List<Senvn> resSens = new ArrayList<Senvn>();
		try {
			List<String> features = mapper.readValue(listFeatures,
					new TypeReference<List<String>>() {
					});
			String modelPath = IBOTagHelper.builModelFilePath(language,
					features, appConfig);
			if (StringUtils.equals(language, "vn")) {
				// Text language is Vietnamese
				processText = new ProcessVNText(
						appConfig.getResourcePathVNLib());
			} else if (StringUtils.equals(language, "en")) {
				// Text language is English
				processText = new ProcessENText();
			}
			crfTagger = new CRFTagger(modelPath, appConfig.getJavaCRFLibPath());
			if (processText == null || crfTagger == null) {
				return "FAILS";
			}
			// split sentence with tokens
			String[] listSens = processText.wordSegments(content);
			// init crf tagger
			for (int i = 0; i < listSens.length; i++) {
				String sen = listSens[i];
				if (StringUtils.isBlank(sen)) {
					continue;
				}
				// assign class for each tokens in sen
				List<String> listAnnotatedWords = crfTagger
						.assignClassForSen(sen);
				List<Worvn> listWords = new ArrayList<Worvn>();
				for (String word : listAnnotatedWords) {
					String[] wordElems = word.split("\\s");
					int tagIdx = wordElems.length - 1;
					Worvn w = new Worvn();
					w.setContent(wordElems[0]);
					w.setIboTag(IBOTagHelper
							.convertStrToIBOTag(wordElems[tagIdx]));
					listWords.add(w);
				}
				Senvn nSen = new Senvn();
				nSen.setId(i);
				nSen.setContent(sen);
				IBOTagHelper.buildConceptInfoForSen(nSen, listWords);
				resSens.add(nSen);
			}
			res.put("listSentences", resSens);
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}
		logger.info("End concept annotation");
		return res;
	}

	@RequestMapping(value = "/contributeNewContent")
	public @ResponseBody Object contribute(
			@RequestParam("record") String record,
			@RequestParam("language") String language,
			@RequestParam("listSentences") String listSentences,
			@RequestParam("listConcepts") String listConcepts) {
		ObjectMapper mapper = new ObjectMapper();
		boolean addStatus = true;
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			List<Concept> concepts = mapper.readValue(listConcepts,
					new TypeReference<List<Concept>>() {
					});
			List<Senvn> sentences = mapper.readValue(listSentences,
					new TypeReference<ArrayList<Senvn>>() {
					});
			String user = super.getLoggedUser();
			if (StringUtils.isBlank(user)) {
				user = "anonymous";
			}
			addStatus = recordService.saveContributedContent(user, language,
					record, sentences, concepts);
		} catch (Exception e) {
			logger.error("Contribute new content error", e);
			addStatus = false;
		}
		if (addStatus) {
			res.put("result", "success");
		} else {
			res.put("result", "fail");
		}
		return res;
	}
}
