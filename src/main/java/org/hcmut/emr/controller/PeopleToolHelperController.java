/*******************************************************************************
 *  (C) Copyright 2009 Molisys Solutions Co., Ltd. , All rights reserved       *
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

import org.hcmut.emr.config.AppConfig;
import org.hcmut.emr.recvn.Recvn;
import org.hcmut.emr.recvn.RecvnService;
import org.hcmut.emr.request.Concept;
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.senvn.SenvnService;
import org.hcmut.emr.utils.IBOTagHelper;
import org.hcmut.emr.utils.NumberHelper;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.emr.worvn.WorvnService;
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
 * @author diepdt
 *
 */
@Controller
@RequestMapping(value = "/tool")
public class PeopleToolHelperController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(PeopleToolHelperController.class);
	@Autowired
	private RecvnService recvnService;

	@Autowired
	private SenvnService senvnService;

	@Autowired
	private WorvnService worvnService;

	@Autowired
	private AppConfig appConfig;

	@RequestMapping(value = "/")
	public String index() {
		return "tool";
	}

	@RequestMapping(value = "/getRecord", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> getRecord(
			@RequestParam("helper") boolean helper,
			@RequestParam("dataSet") String dataSet) {
		logger.info("Start get record");
		Map<String, Object> res = new HashMap<String, Object>();
		// get total record
		int recordCount = appConfig.getRecordPos();
		// get a random record position for synchronize when many people use
		// this tool
		int randRecPos = NumberHelper.randInt(0, recordCount - 1);
		List<Recvn> listRecvn = new ArrayList<Recvn>();
		List<Senvn> listSentences = new ArrayList<Senvn>();
		while (listRecvn.isEmpty() || listSentences.isEmpty()) {
			// if list record is empty, get another position
			listRecvn = recvnService.getAvailableRecords(1, randRecPos, 1);
			if (!listRecvn.isEmpty()) {
				listSentences = senvnService.search(listRecvn.get(0).getId(),
						"recordId", 0);
			}
			randRecPos++;
		}

		// helper process
		if (helper) {
			// helper is enabled
			helperPreProcess(listSentences, dataSet);
		}
		res.put("listSentences", listSentences);
		res.put("annotatedRecordCount",
				recvnService.countAnnotatedRecordByUser(getLoggedUser()));
		logger.info("End get record");
		return res;
	}

	/**
	 * Helper user to annotated concept
	 * 
	 * @return list sentence with concepts
	 * @author dao.diep
	 */
	private List<Senvn> helperPreProcess(List<Senvn> listSentences,
			String language) {
		CRFTagger crfTagger = null;
		// list sentence
		String modelPath = IBOTagHelper.builModelFilePath(language, null,
				appConfig);
		// init crf tagger
		crfTagger = new CRFTagger(modelPath, appConfig.getJavaCRFLibPath());
		for (int i = 0; i < listSentences.size(); i++) {
			Senvn sen = listSentences.get(i);
			// assign class for each tokens in sen
			List<String> listAnnotatedWords = crfTagger.assignClassForSen(sen
					.getContent());
			List<Worvn> listWords = new ArrayList<Worvn>();
			for (String word : listAnnotatedWords) {
				String[] wordElems = word.split("\\s");
				int tagIdx = wordElems.length - 1;
				Worvn w = new Worvn();
				w.setContent(wordElems[0]);
				w.setIboTag(IBOTagHelper.convertStrToIBOTag(wordElems[tagIdx]));
				listWords.add(w);
			}
			IBOTagHelper.buildConceptInfoForSen(sen, listWords);
		}
		return listSentences;
	}

	@RequestMapping(value = "/saveConcept", method = RequestMethod.POST)
	public @ResponseBody List<String> saveConcept(
			@RequestParam("listConcepts") String listConcepts,
			@RequestParam("recordId") double recordId) {
		ObjectMapper mapper = new ObjectMapper();
		boolean updateStatus = true;
		List<String> res = new ArrayList<String>();
		try {
			List<Concept> concepts = mapper.readValue(listConcepts,
					new TypeReference<List<Concept>>() {
					});
			updateStatus = recvnService.annotateConceptForRecord(recordId,
					concepts, getLoggedUser(), 0);
		} catch (Exception e) {
			e.printStackTrace();
			updateStatus = false;
		}
		if (updateStatus) {
			res.add("success");
		} else {
			res.add("fail");
		}
		return res;
	}
	
	@RequestMapping(value = "/updateDicTag")
	public @ResponseBody Object updateDicTag() {
		try {
			worvnService.updateDicTag();
		} catch (Exception e) {
			logger.error("update dic tag error", e);
			return "FAILS";
		}
		return "SUCCESS";
	}

}
