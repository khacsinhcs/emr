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
package org.hcmut.emr.taghelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.collections.map.HashedMap;
import org.hcmut.emr.word.Word;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

/**
 * @author sinhlk
 *
 */
public class PosBuilding implements SentenceTag {

	private static StanfordCoreNLP pipeline;

	public static StanfordCoreNLP getPipeline() {
		if (pipeline == null) {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos");
			pipeline = new StanfordCoreNLP(props);
		}
		return pipeline;
	}

	public static void main(String[] args) {
		PosBuilding pos = new PosBuilding();
		Map<String, Tag> map = pos
				.getTag("Ken Nansteel-Miller is the triplet "
						+ "#3 of a spontaneous triamniotic-trichorionic triplet pregnancy "
						+ "born to a 39-year-old G4 P1 spontaneous abortion 2 woman .");
		for (String word : map.keySet()) {
			System.out.println("------------------");
			System.out.println("WORD: " + word);
			System.out.println("POS: " + map.get(word).getTag());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.taghelper.SentenceTag#getTag(java.lang.String)
	 */
	@Override
	public Map<String, Tag> getTag(String sentence) {
		Map<String, Tag> result = new HashedMap();

		Annotation document = new Annotation(sentence);
		StanfordCoreNLP pipeline = getPipeline();
		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sen : sentences) {
			for (int index = 0; index < sen.get(TokensAnnotation.class).size(); index++) {
				CoreLabel token = sen.get(TokensAnnotation.class).get(index);
				String pos = token.get(PartOfSpeechAnnotation.class);
				Tag tag = new Tag();
				tag.setTag(pos);
				result.put(token.originalText(), tag);
			}
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.taghelper.SentenceTag#updateWords(java.util.List)
	 */
	@Override
	public void updateWords(String sentence, List<Word> words) {
		Map<String, Tag> map = getTag(sentence);
		for (Word word : words) {
			Tag tag = map.get(word.getContent());
			if (tag == null) {
				word.setPosTag("NONE");
			} else {
				word.setPosTag(tag.getTag());
			}
		}
	}

}
