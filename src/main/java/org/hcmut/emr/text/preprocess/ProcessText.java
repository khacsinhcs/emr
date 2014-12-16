package org.hcmut.emr.text.preprocess;

import jvntextpro.data.Sentence;

public interface ProcessText {
	String wordSegment(String text);

	String[] wordSegments(String text);

	String posTagging(String text);

	Sentence posTagging2(String text);
}
