package org.hcmut.emr.text.preprocess;

import jvntextpro.data.Sentence;

import org.hcmut.emr.constant.Constants;
import org.hcmut.emr.process.text.POSTagService;
import org.hcmut.emr.process.text.VNTokenizerWordServiceImpl;
import org.hcmut.emr.process.text.WordService;

import vn.hus.nlp.tokenizer.IConstants;

public class ProcessVNText implements ProcessText {

	public ProcessVNText(String resourcePath) {
		IConstants.setResourcePath(resourcePath);
		Constants.setResourcePath(resourcePath);
		if (wordService == null) {
			wordService = new VNTokenizerWordServiceImpl();
		}
		if (postTagService == null) {
			postTagService = null;
		}
	}

	// init word segmentation service
	private static WordService wordService = null;
	// init pos tagging service
	private static POSTagService postTagService = null;

	@Override
	public String wordSegment(String text) {
		return wordService.wordSegment(text);
	}

	@Override
	public String[] wordSegments(String text) {
		return wordService.wordSegments(text);
	}

	@Override
	public String posTagging(String text) {
		return postTagService.posTagging(text);
	}

	@Override
	public Sentence posTagging2(String text) {
		return postTagService.posTagging2(text);
	}
}
