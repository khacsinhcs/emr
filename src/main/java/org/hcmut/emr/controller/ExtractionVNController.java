package org.hcmut.emr.controller;

import org.hcmut.emr.config.AppConfig;
import org.hcmut.emr.senvn.SenvnService;
import org.hcmut.emr.worvn.WorvnService;
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
@RequestMapping(value = "/extractvn")
public class ExtractionVNController {
	@Autowired
	private SenvnService sentenceService;

	@Autowired
	private WorvnService wordService;

	@Autowired
	private AppConfig appConfig;

	@RequestMapping(value = "/fileall")
	public @ResponseBody Object extractAll(@RequestParam("train") float train) {
		boolean res = false;
		try {
			res = sentenceService.extractDataToCRFFormat(
					appConfig.getExtractDataPath(), train);
		} catch (Exception e) {
			e.printStackTrace();
			res = false;
		}
		if (res) {
			return "SUCCESS";
		}
		return "FAILS";
	}
}
