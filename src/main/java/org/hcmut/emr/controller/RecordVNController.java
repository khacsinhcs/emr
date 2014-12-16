package org.hcmut.emr.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hcmut.emr.recvn.RecvnService;
import org.hcmut.emr.request.Concept;
import org.hcmut.emr.request.Concept.ConcepType;
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.senvn.SenvnService;
import org.hcmut.emr.user.UserService;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.emr.worvn.WorvnService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.molisys.framework.base.BaseController;

/**
 * @author diepdt
 *
 */
@Controller
@RequestMapping(value = "/recordvn")
public class RecordVNController extends BaseController {
	private static final Logger logger = LoggerFactory
			.getLogger(PeopleToolHelperController.class);
	@Autowired
	private RecvnService recvnService;

	@Autowired
	private UserService userService;

	@Autowired
	private SenvnService senvnService;

	@Autowired
	private WorvnService worvnService;

	@RequestMapping(value = "/")
	public String index(ModelMap map) {
		map.addAttribute("listUsers", userService.getAll());
		return "recordvn";
	}

	@RequestMapping(value = "/getListRecord")
	public @ResponseBody Object getListRecord(
			@RequestParam("username") String username) {
		return recvnService.getListAnnotatedRecordByUser(username);
	}

	@RequestMapping(value = "/updateConcept", method = RequestMethod.POST)
	public @ResponseBody List<String> saveConcept(
			@RequestParam("listConcepts") String listConcepts,
			@RequestParam("recordId") double recordId,
			@RequestParam("userRec") String userRec) {
		ObjectMapper mapper = new ObjectMapper();
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
			List<Concept> concepts = mapper.readValue(listConcepts,
					new TypeReference<List<Concept>>() {
					});
			updateStatus = recvnService.annotateConceptForRecord(recordId,
					concepts, userRec, 1);
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
			@RequestParam("username") String username,
			@RequestParam("recordId") double recordId) {
		Map<String, Object> res = new HashMap<String, Object>();
		List<Senvn> listSentences = senvnService.search(recordId, "recordId",
				1);
		Iterator<Senvn> iterator = listSentences.iterator();
		while (iterator.hasNext()) {
			Senvn senvn = iterator.next();
			List<Worvn> listWords = worvnService.search(senvn.getId(),
					"sentenceId");
			Concept conceptObj = null;
			// remove duplicate sentence with word that have ibo tag is null
			if (listWords.get(0).getIboTag() == null) {
				iterator.remove();
				continue;
			}
			int countChar = 0;
			for (int i = 0; i < listWords.size(); i++) {
				Worvn worvn = listWords.get(i);
				switch (worvn.getIboTag()) {
				case B_TR:
					conceptObj = new Concept();
					conceptObj.addWordToContent(worvn.getContent());
					conceptObj.setType(ConcepType.TR);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(senvn.getId());
					conceptObj.setRecordId(recordId);
					break;
				case B_TE:
					conceptObj = new Concept();
					conceptObj.addWordToContent(worvn.getContent());
					conceptObj.setType(ConcepType.TE);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(senvn.getId());
					conceptObj.setRecordId(recordId);
					break;
				case B_PR:
					conceptObj = new Concept();
					conceptObj.addWordToContent(worvn.getContent());
					conceptObj.setType(ConcepType.PR);
					conceptObj.setFromWord(i + 1);
					conceptObj.setFromChar(countChar);
					conceptObj.setSentenceId(senvn.getId());
					conceptObj.setRecordId(recordId);
					break;
				case I_TR:
				case I_TE:
				case I_PR:
					if (conceptObj != null) {
						conceptObj.addWordToContent(worvn.getContent());
					} else {
						logger.debug("conceptObj is null at: ("
								+ worvn.getContent() + ", " + worvn.getIboTag()
								+ ")");
					}
					break;
				default:
					if (conceptObj != null) {
						senvn.getConcept().add(conceptObj);
						conceptObj = null;
					}
					break;
				}
				countChar += worvn.getContent().length() + 1;
			}
			// check if first words of the words list contain concept
			if (conceptObj != null) {
				senvn.getConcept().add(conceptObj);
			}
		}
		res.put("listSentences", listSentences);
		return res;
	}

}
