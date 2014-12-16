package org.hcmut.emr.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpSession;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.Sentence;
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
@RequestMapping(value = "/i2b2/record")
public class I2b2RecordController {
	private static final Logger logger = LoggerFactory
			.getLogger(I2b2RecordController.class);
	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private WordService wordService;

	/**
	 * Load view page
	 */
	@RequestMapping(value = "/")
	public String index() {
		return "i2b2record";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Object readRecordFile(@RequestParam("record") MultipartFile record) {
		try {
			String fileName = record.getOriginalFilename().split("\\.")[0];

			if (recordService.search(fileName, "name").size() == 0) {
				multipartFileRecordHandling(record);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "i2b2record";
	}

	@RequestMapping(value = "/uploads", method = RequestMethod.POST)
	public Object readRecordFiles(@RequestParam("record") MultipartFile record,
			@RequestParam("record1") MultipartFile record1,
			@RequestParam("record2") MultipartFile record2,
			@RequestParam("record3") MultipartFile record3,
			@RequestParam("record4") MultipartFile record4, HttpSession session) {
		readRecordFile(record);
		readRecordFile(record1);
		readRecordFile(record2);
		readRecordFile(record3);
		readRecordFile(record4);
		return "i2b2record";
	}

	private void multipartFileRecordHandling(MultipartFile record)
			throws IOException {
		Record item = new Record();
		item.setName(record.getOriginalFilename().split("\\.")[0]);
		recordService.add(item);

		InputStream stream = record.getInputStream();

		BufferedReader in = new BufferedReader(new InputStreamReader(stream));

		StringBuilder contentBuilder = new StringBuilder();
		String line = "";
		long index = 0;
		while (in.ready()) {
			line = in.readLine();
			index++;
			contentBuilder.append(line + "\n");
			lineRecordHandling(line, item, index);
		}

		item.setText(contentBuilder.toString());
		recordService.update(item);
	}
	private void fileRecordHandling(File file) throws IOException {
		Record item = new Record();
		item.setName(file.getName().split("\\.")[0]);
		recordService.add(item);


		BufferedReader in = new BufferedReader(new FileReader(file));

		StringBuilder contentBuilder = new StringBuilder();
		String line = "";
		long index = 0;
		while (in.ready()) {
			line = in.readLine();
			index++;
			contentBuilder.append(line + "\n");
			lineRecordHandling(line, item, index);
		}

		item.setText(contentBuilder.toString());
		recordService.update(item);
	}

	private void lineRecordHandling(String line, Record record, long index) {
		Sentence sentence = new Sentence();
		sentence.setContent(line);
		sentence.setRecord(record);
		sentence.setIndex(index);
		sentence.setIsHandle(0L);

		sentenceService.add(sentence);

		String[] listWord = line.split(" ");
		for (int count = 0; count < listWord.length; count++) {
			wordRecordHandling(listWord[count], count, sentence);
		}
	}

	private void wordRecordHandling(String content, long index,
			Sentence sentence) {
		Word word = new Word();
		word.setContent(content);
		word.setIndex(index);
		word.setSentence(sentence);
		word.setIboTag(Word.IBOTag.O);

		wordService.add(word);
	}

	public static void main(String[] args) {
		String file = "text-13.txt";
		String[] slip = file.split("\\.");

		for (int i = 0; i < slip.length; i++) {
			System.out.println(slip[i]);
		}
	}
	@RequestMapping(value = "/beth")
	public @ResponseBody String readAllBethFile() {
		try {
			readManyFile("file/concept_assertion_relation_training_data/beth/txt");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}
	@RequestMapping(value = "/partners")
	public @ResponseBody String readAllPartnersFile() {
		try {
			readManyFile("file/concept_assertion_relation_training_data/partners/txt");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}
	@RequestMapping(value = "/test")
	public @ResponseBody String readAllTestFile() {
		try {
			readManyFile("file/test_data");
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}

		return "SUCCESS";
	}

	private void readManyFile(String path) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		File folder = new File(classLoader.getResource(path).getFile());
		logger.info(folder.getName());
		
		File[] files = folder.listFiles();

		for (File file : files) {
			logger.info(file.getName());
			String fileName = file.getName().split("\\.")[0];
			
			if (recordService.search(fileName, "name").size() == 0) {
				fileRecordHandling(file);
			}
		}
	}
}
