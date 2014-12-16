package org.hcmut.emr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.sentence.Sentence;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.utils.WordFixExtract;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordService;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.file.ExportData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.molisys.framework.utils.DateTimeHelper;

/**
 * @author sinhlk
 *
 */
@Controller
@RequestMapping(value = "/extract")
public class ExtractionController {
	private static final String trainingPath = "/home/silver/Documents/THESIS/Data/NewEN/Train";
	private static final String testingPath = "/home/silver/Documents/THESIS/Data/NewEN/Test";

	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private WordService wordService;

	@RequestMapping(value = "/fileall")
	public @ResponseBody Object extractAll(@RequestParam("mode") String mode) {
		try {

			List<ExportData> exporters = createListOutWriter(mode);

			List<Sentence> listSentence = sentenceService.getAll();

			for (Sentence sentence : listSentence) {
				List<Word> listWord = wordService.search(sentence.getId(),
						"sentence.id");
				for (Word word : listWord) {
					for (ExportData exporter : exporters) {
						exporter.writeLine(word);
					}
				}
				for (ExportData exporter : exporters) {
					exporter.newLine();
				}
			}

			for (ExportData exporter : exporters) {
				exporter.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILS";
		}
		return "SUCCESS";
	}

	/**
	 * @param mode
	 * @return
	 * @throws IOException
	 */
	private List<ExportData> createListOutWriter(String mode)
			throws IOException {

		ArrayList<ExportData> result = new ArrayList<ExportData>();
		String path = "";
		if (mode.equals("trainning")) {
			path = trainingPath;
		} else {
			path = testingPath;
		}

		ExportData file = new ExportData(buildPath(path, "orth"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						WordFixExtract fixHelper = WordFixExtract.factory();
						
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ convertIboTag(word.getIboTag()) + " "
								+ fixHelper.getFixsString(word.getContent());
					}

					@Override
					public String writeVn(Worvn word) {
						return null;
					}
				});
		result.add(file);

		file = new ExportData(buildPath(path, "umls"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " " + word.getUmlsTag()
								+ " " + convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return null;
					}
				});
		result.add(file);

		file = new ExportData(buildPath(path, "pos"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return null;
					}
				});
		result.add(file);

		file = new ExportData(buildPath(path, "baseline"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						return word.getContent() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return null;
					}
				});
		result.add(file);
		return result;
	}

	@RequestMapping(value = "/record")
	public Object extractOneRecord(ModelMap model,
			@RequestParam(value = "id") long id) {

		Record record = recordService.getById(id);

		List<Sentence> listSentence = sentenceService.search(record.getId(),
				"record.id");

		ArrayList<List<Word>> result = new ArrayList<List<Word>>();

		for (Sentence sentence : listSentence) {
			List<Word> listWord = wordService.search(sentence.getId(),
					"sentence.id");
			result.add(listWord);
		}
		model.addAttribute("result", result);
		return "extract";
	}

	@RequestMapping(value = "/")
	public Object listRecord(ModelMap model) {
		List<Record> listRecord = recordService.getAll();
		model.addAttribute("listRecord", listRecord);
		return "recordsinfo";
	}

	private String buildFileName(String name) {
		DateTimeHelper dateHelper = DateTimeHelper.factory();
		String fileName = dateHelper.getCurrentDateOnString();
		fileName += "_" + name;
		fileName += ".emrie";
		return fileName;
	}

	public String buildPath(String path, String name) {
		return path + "/" + buildFileName(name);
	}

	private String convertIboTag(Word.IBOTag tag) {
		if (tag == Word.IBOTag.B_TR) {
			return "B-TR";
		} else if (tag == Word.IBOTag.I_TR) {
			return "I-TR";
		} else if (tag == Word.IBOTag.I_PR) {
			return "I-PR";
		} else if (tag == Word.IBOTag.B_PR) {
			return "B-PR";
		} else if (tag == Word.IBOTag.B_TE) {
			return "B-TE";
		} else if (tag == Word.IBOTag.I_TE) {
			return "I-TE";
		}
		return "O";
	}
}
