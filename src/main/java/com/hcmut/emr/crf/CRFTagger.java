package com.hcmut.emr.crf;

import java.util.ArrayList;
import java.util.List;

import org.chasen.crfpp.Tagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CRFTagger {
	private static final Logger logger = LoggerFactory
			.getLogger(CRFTagger.class);
	private static Tagger tagger = null;

	public CRFTagger(String modelPath, String javaCRFLibPath) {
		loadJavaCRFLibrary(javaCRFLibPath);
		tagger = new Tagger("-m " + modelPath);
	}

	public CRFTagger() {
	}

	private static void loadJavaCRFLibrary(String javaCRFLibPath) {
		try {
			System.load(javaCRFLibPath);
		} catch (UnsatisfiedLinkError e) {
			logger.error("Cannot load the example native code.\nMake sure your LD_LIBRARY_PATH contains \'.\'\n"
					+ e);
		}
	}

	public List<String> assignClass(String[] listWords) {
		List<String> listAnnotatedWords = new ArrayList<String>();
		tagger.clear();
		// add content
		for (String word : listWords) {
			tagger.add(word);
		}
		logger.info("column size: " + tagger.xsize());
		logger.info("token size: " + tagger.size());
		logger.info("tag size: " + tagger.ysize());

		System.out.println("tagset information:");
		for (int i = 0; i < tagger.ysize(); ++i) {
			logger.info("tag " + i + " " + tagger.yname(i));
		}

		// parse and change internal stated as 'parsed'
		if (!tagger.parse())
			return listAnnotatedWords;

		logger.info("conditional prob=" + tagger.prob() + " log(Z)="
				+ tagger.Z());

		for (int i = 0; i < tagger.size(); ++i) {
			StringBuilder word = new StringBuilder();
			for (int j = 0; j < tagger.xsize(); j++) {
				logger.info(tagger.x(i, j));
				word.append(tagger.x(i, j) + " ");
			}
			logger.info(tagger.y2(i));
			word.append(tagger.y2(i));
			listAnnotatedWords.add(word.toString());
		}
		logger.info("Done");
		return listAnnotatedWords;
	}

	public List<String> assignClassForSen(String sentence) {
		return assignClass(sentence.split("\\s"));
	}
}
