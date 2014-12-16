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

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.Sentence;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.taghelper.OrthorBuilding;
import org.hcmut.emr.taghelper.WordTag;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sinhlk
 *
 */
@Controller
@RequestMapping(value = "/tag/word")
public class WordTagController {
	private static final Logger logger = LoggerFactory
			.getLogger(WordTagController.class);
	@Autowired
	private WordService wordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private RecordService recordService;

	@RequestMapping(value = "/record")
	public @ResponseBody Object setTagForRecord(
			@RequestParam(value = "id") long id) {
		Record record = recordService.getById(id);

		if (record == null) {
			return "INVALID_RECORD";
		}

		List<Sentence> sentences = sentenceService.search(id, "record.id");

		
		
		updateWordTagOnListSentence(sentences);
		return "SUCCESS";
	}

	@RequestMapping(value = "/all")
	public @ResponseBody Object setTagForAllRecord() {
		int numberOfThread = 300;
		int size = sentenceService.countAll();
		int limit = size / numberOfThread;
		for (int count = 0; count < numberOfThread; count++) {
			Runnable run = new Runnable() {
				@Override
				public void run() {
				}
			};
		}
		
		List<Sentence> sentences = sentenceService.getAll();
		updateWordTagOnListSentence(sentences);
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/from")
	public @ResponseBody Object setTagForFrom(@RequestParam("sentence") long id) {

		List<Sentence> sentences = sentenceService.search(id, "id", ">");

		updateWordTagOnListSentence(sentences);
		return "SUCCESS";
	}

	private void updateWordTagOnListSentence(List<Sentence> sentences) {
		WordTag tag = new OrthorBuilding();
		for (Sentence sentence : sentences) {
			List<Word> words = wordService.search(sentence.getId(),
					"sentence.id");
			for (Word word : words) {
				tag.updateWord(word);
				logger.info(word.getOrthTag());
				wordService.update(word);
			}
		}
	}
}
