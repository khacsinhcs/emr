package org.hcmut.emr.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hcmut.emr.config.AppConfig;
import org.hcmut.emr.request.Concept;
import org.hcmut.emr.request.Concept.ConcepType;
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.emr.worvn.Worvn.IBOTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IBO tag helper
 * 
 * @author dao.diep
 *
 */
public class IBOTagHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(IBOTagHelper.class);

	public static String builModelFilePath(String language, List<String> features,
			AppConfig appConfig) {
		// buil file path
		String filePath = "";
		String fileName = buildModelFileName(features);
		if (StringUtils.equals(language, "vn")) {
			filePath = appConfig.getModelVNPath() + "/" + fileName;
		} else if (StringUtils.equals(language, "en")) {
			filePath = appConfig.getModelENPath() + "/" + fileName;
		}
		return filePath;
	}

	public static String buildModelFileName(List<String> features) {
		// buil file name
		String fileName = "baseline";
		if (features != null && !features.isEmpty()) {
			for (String feature : features) {
				fileName += "_" + feature;
			}
		}
		fileName += ".model";
		return fileName;
	}

	public static IBOTag convertStrToIBOTag(String tagStr) {
		if (StringUtils.equals("B-PR", tagStr.trim())) {
			return IBOTag.B_PR;
		}
		if (StringUtils.equals("B-TE", tagStr.trim())) {
			return IBOTag.B_TE;
		}
		if (StringUtils.equals("B-TR", tagStr.trim())) {
			return IBOTag.B_TR;
		}
		if (StringUtils.equals("I-PR", tagStr.trim())) {
			return IBOTag.I_PR;
		}
		if (StringUtils.equals("I-TE", tagStr.trim())) {
			return IBOTag.I_TE;
		}
		if (StringUtils.equals("I-TR", tagStr.trim())) {
			return IBOTag.I_TR;
		}
		return IBOTag.O;
	}

	public static Senvn buildConceptInfoForSen(Senvn senvn, List<Worvn> listWords) {
		int countChar = 0;
		Concept conceptObj = null;
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
				break;
			case B_TE:
				conceptObj = new Concept();
				conceptObj.addWordToContent(worvn.getContent());
				conceptObj.setType(ConcepType.TE);
				conceptObj.setFromWord(i + 1);
				conceptObj.setFromChar(countChar);
				conceptObj.setSentenceId(senvn.getId());
				break;
			case B_PR:
				conceptObj = new Concept();
				conceptObj.addWordToContent(worvn.getContent());
				conceptObj.setType(ConcepType.PR);
				conceptObj.setFromWord(i + 1);
				conceptObj.setFromChar(countChar);
				conceptObj.setSentenceId(senvn.getId());
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
		return senvn;
	}
}
