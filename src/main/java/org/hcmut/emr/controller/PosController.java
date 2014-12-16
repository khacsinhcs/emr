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

import java.util.List;
import java.util.Properties;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.Sentence;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.taghelper.PosBuilding;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author sinhlk
 *
 */
@Controller
@RequestMapping(value = "/pos")
public class PosController {

	@Autowired
	SentenceService sentenceService;

	@Autowired
	WordService wordService;

	@Autowired
	RecordService recordService;

	private void handleOneSentence(Sentence sentence) {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		String text = sentence.getContent();
		Annotation document = new Annotation(text);
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sen : sentences) {
			for (int index = 0; index < sen.get(TokensAnnotation.class).size(); index++) {
				CoreLabel token = sen.get(TokensAnnotation.class).get(index);
				String pos = token.get(PartOfSpeechAnnotation.class);

				Word word = wordService.getWordInSentence(sentence.getId(),
						index);
				if (word != null) {
					word.setPosTag(pos);
					wordService.update(word);
				}

			}
		}
	}

	@RequestMapping(value = "/updateall")
	public String updateAllPosTag() {
		PosBuilding posBuilding = new PosBuilding();

		List<Sentence> sentences = sentenceService.getAll();
		for (Sentence sentence : sentences) {
			List<Word> listWord = wordService.search(sentence.getId(),
					"sentence.id");
			posBuilding.updateWords(sentence.getContent(), listWord);

			for (Word word : listWord) {
				wordService.update(word);
			}
		}
		return "SUCCESS";
	}

	@RequestMapping(value = "/record")
	public String updateRecord(@RequestParam("id") long id) {
		PosBuilding posBuilding = new PosBuilding();

		Record record = recordService.getById(id);
		if (record == null) {
			return "INVALID_RECORD";
		}

		List<Sentence> sentences = sentenceService.search(id, "record.id");
		for (Sentence sentence : sentences) {
			List<Word> listWord = wordService.search(sentence.getId(),
					"sentence.id");
			posBuilding.updateWords(sentence.getContent(), listWord);

			for (Word word : listWord) {
				wordService.update(word);
			}
		}
		return "SUCCESS";
	}

}
