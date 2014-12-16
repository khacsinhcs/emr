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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sinhlk
 *
 */
@Controller
@RequestMapping(value = "/i2b2/ibo")
public class I2b2IBOController {
	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private WordService wordService;

	private static final Logger logger = LoggerFactory
			.getLogger(I2b2IBOController.class);

	/**
	 * Load view page
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "i2b2ibo";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadOneFile(@RequestParam("ibofile") MultipartFile iboFile) {
		try {
			String filename = iboFile.getOriginalFilename();
			filename = filename.split("\\.")[0];
			Record record = recordService.search(filename, "name").get(0);
			multipartFileIBOHandling(iboFile, record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "i2b2ibo";
	}

	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public String iboUpload(@RequestParam("ibofile") MultipartFile iboFile,
			@RequestParam("ibofile1") MultipartFile iboFile1,
			@RequestParam("ibofile2") MultipartFile iboFile2,
			@RequestParam("ibofile3") MultipartFile iboFile3,
			@RequestParam("ibofile4") MultipartFile iboFile4) {
		try {
			uploadOneFile(iboFile);
			uploadOneFile(iboFile1);
			uploadOneFile(iboFile2);
			uploadOneFile(iboFile3);
			uploadOneFile(iboFile4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "i2b2ibo";
	}

	/**
	 * @param record
	 */
	private void multipartFileIBOHandling(MultipartFile iboFile, Record record) {
		try {
			InputStream stream = iboFile.getInputStream();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(stream));

			String line = "";
			while (in.ready()) {
				line = in.readLine();
				lineIBOFileHandling(line, record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fileIBOHandling(File iboFile, Record record)
			throws IOException {
		try {
			BufferedReader in = new BufferedReader(new FileReader(iboFile));

			String line = "";
			while (in.ready()) {
				line = in.readLine();
				lineIBOFileHandling(line, record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void lineIBOFileHandling(String line, Record record) {
		try {
			String[] strContain = line.split("t=\"");

			int sentenceIndex = getSentenceIndex(strContain[0]);
			List<Integer> wordIds = getWordIds(strContain[0]);

			for (int index = 0; index < wordIds.size(); index++) {
				String lable = getLable(strContain[1]);
				logger.info("Line: " + line);
				logger.info(record.getId() + "");
				logger.info(sentenceIndex + "");
				logger.info(wordIds.get(index) + "");
				Word word = wordService.search(record.getId(), sentenceIndex,
						wordIds.get(index));

				// Set up ibo tag. After that, update to database
				setUpIBOLable(lable, word, index);
				wordService.update(word);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getSentenceIndex(String line) {
		logger.info(line);
		line = line.split("\" ")[1];
		line = line.split(":")[0];
		return Integer.parseInt(line);
	}

	private List<Integer> getWordIds(String line) {
		line = line.split("\" ")[1].trim();
		line = line.substring(0, line.length() - 2);

		String[] lineContainer = line.split(" ");
		int begin = Integer.parseInt(lineContainer[0].split(":")[1].trim());
		int end = Integer.parseInt(lineContainer[1].split(":")[1].trim());

		ArrayList<Integer> result = new ArrayList<Integer>();

		for (int count = begin; count <= end; count++) {
			result.add(count);
		}
		return result;
	}

	private String getLable(String line) {
		line = line.trim();
		return line.split("\"")[0];
	}

	private void setUpIBOLable(String lable, Word word, int index) {
		if (lable.equals("problem")) {
			if (index == 0) {
				word.setIboTag(Word.IBOTag.B_PR);
			} else {
				word.setIboTag(Word.IBOTag.I_PR);
			}
		} else if (lable.equals("treatment")) {
			if (index == 0) {
				word.setIboTag(Word.IBOTag.B_TR);
			} else {
				word.setIboTag(Word.IBOTag.I_TR);
			}
		} else if (lable.equals("test")) {
			if (index == 0) {
				word.setIboTag(Word.IBOTag.B_TE);
			} else {
				word.setIboTag(Word.IBOTag.I_TE);
			}
		}
	}

	@RequestMapping(value = "/beth")
	public @ResponseBody String readAllBethFile() {
		try {
			handleManyFile("file/concept_assertion_relation_training_data/beth/concept");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}
	@RequestMapping(value = "/partners")
	public @ResponseBody String readAllPartnersFile() {
		try {
			handleManyFile("file/concept_assertion_relation_training_data/partners/concept");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}
	@RequestMapping(value = "/test")
	public @ResponseBody String readAllTestFile() {
		try {
			handleManyFile("file/reference_standard_for_test_data/concepts");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}

	private void handleManyFile(String path) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File folder = new File(classLoader.getResource(path).getFile());
		logger.info(folder.getName());
		File[] files = folder.listFiles();

		for (File file : files) {
			String filename = file.getName();
			filename = filename.split("\\.")[0];
			Record record = recordService.search(filename, "name").get(0);
			if (record != null) {
				fileIBOHandling(file, record);
			}
		}
	}
}
