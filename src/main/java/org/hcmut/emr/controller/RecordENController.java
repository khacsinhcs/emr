package org.hcmut.emr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hcmut.emr.record.RecordService;
import org.hcmut.emr.request.Concept;
import org.hcmut.emr.request.Concept.ConcepType;
import org.hcmut.emr.sentence.Sentence;
import org.hcmut.emr.sentence.SentenceService;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.molisys.framework.base.BaseController;

/**
 * @author diepdt
 *
 */
@Controller
@RequestMapping(value = "/recorden")
public class RecordENController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(PeopleToolHelperController.class);
	@Autowired
	private RecordService recordService;

	@Autowired
	private SentenceService sentenceService;

	@Autowired
	private WordService wordService;

	@RequestMapping(value = "/")
	public String index(ModelMap map) {
		return "recorden";
	}

	@RequestMapping(value = "/getListRecord")
	public @ResponseBody Object getListRecord(
			@RequestParam("dataSet") String dataSet) {
		return recordService.getListRecordByDataSet(dataSet);
	}

	@RequestMapping(value = "/updateConcept", method = RequestMethod.POST)
	public @ResponseBody List<String> saveConcept(
			@RequestParam("listConcepts") String listConcepts,
			@RequestParam("recordId") double recordId,
			@RequestParam("userRec") String userRec) {
		List<String> res = new ArrayList<String>();
		// must logged to update concept
		if (getLoggedUser() == null) {
			res.add("login");
			return res;
		}
		// only owner can change
		if (!hasRole("ROLE_ADMIN")) {
			if (!StringUtils.equals(getLoggedUser().trim(), userRec.trim())) {
				res.add("autho");
				return res;
			}
		}
		boolean updateStatus = true;
		try {
			/*List<Concept> concepts = mapper.readValue(listConcepts,
					new TypeReference<List<Concept>>() {
					});
			updateStatus = recordService.annotateConceptForRecord(recordId,
					concepts, userRec, 1);*/
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

	@RequestMapping(value = "/getRecordContent")
	public @ResponseBody Object getRecordContent(
			@RequestParam("dataSet") String dataSet,
			@RequestParam("recordId") long recordId) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Sentence> listSentences = sentenceService.getListSenByRecordId(recordId, dataSet);
		Iterator<Sentence> iterator = listSentences.iterator();
		while (iterator.hasNext()) {
			Sentence sentence = iterator.next();
			List<Word> listWords = wordService.getListWordBySentenceId(sentence.getId(), dataSet);
			Concept conceptObj = null;
			// remove duplicate sentence with word that have ibo tag is null
			if (listWords.isEmpty() || listWords.get(0).getIboTag() == null) {
				iterator.remove();
				continue;
			}
			int countChar = 0;
			for (int i = 0; i < listWords.size(); i++) {
				Word word = listWords.get(i);
				switch (word.getIboTag()) {
				case B_TR:
					conceptObj = new Concept();
					conceptObj.addWordToContent(word.getContent());
					conceptObj.setType(ConcepType.TR);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(sentence.getId());
					conceptObj.setRecordId(recordId);
					break;
				case B_TE:
					conceptObj = new Concept();
					conceptObj.addWordToContent(word.getContent());
					conceptObj.setType(ConcepType.TE);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(sentence.getId());
					conceptObj.setRecordId(recordId);
					break;
				case B_PR:
					conceptObj = new Concept();
					conceptObj.addWordToContent(word.getContent());
					conceptObj.setType(ConcepType.PR);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(sentence.getId());
					conceptObj.setRecordId(recordId);
					break;
				case I_TR:
				case I_TE:
				case I_PR:
					if (conceptObj != null) {
						conceptObj.addWordToContent(word.getContent());
					} else {
						logger.debug("conceptObj is null at: ("
								+ word.getContent() + ", " + word.getIboTag()
								+ ")");
					}
					break;
				default:
					if (conceptObj != null) {
						sentence.getConcept().add(conceptObj);
						conceptObj = null;
					}
					break;
				}
				countChar += word.getContent().length() + 1;
			}
			// check if first words of the words list contain concept
			if (conceptObj != null) {
				sentence.getConcept().add(conceptObj);
			}
		}
		res.put("listSentences", listSentences);
		return res;
	}

}
