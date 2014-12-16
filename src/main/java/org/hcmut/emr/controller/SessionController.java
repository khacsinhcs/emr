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

import java.io.File;
import java.util.List;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sinhlk
 *
 */

@Controller
@RequestMapping("/session")
public class SessionController {

	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@RequestMapping("/lable/{id}")
	public @ResponseBody String lableForOneRecord(
			@PathVariable("id") int recordId) {
		System.out.println("Welcome to session");
		Record record = recordService.getById(recordId);
		if (record != null) {
			sentenceService.lableForOneRecord(record.getId());
		}
		return "finish";
	}

	@RequestMapping("/lable/all")
	public @ResponseBody String lableAll() {
		List<Record> records = recordService.getAll();
		for (Record record : records) {
			sentenceService.lableForOneRecord(record.getId());
		}
		return "finish";
	}
}
