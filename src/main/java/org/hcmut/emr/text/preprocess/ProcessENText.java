package org.hcmut.emr.text.preprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import jvntextpro.data.Sentence;

import org.apache.commons.lang.StringUtils;

import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class ProcessENText implements ProcessText {

	@Override
	public String wordSegment(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] wordSegments(String text) {
		String[] listTokenSens = null;
		List<String> listSens = new ArrayList<String>();
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization,
		// NER, parsing, and coreference resolution
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// create an empty Annotation just with the given text
		Annotation document = new Annotation(text);

		// run all Annotators on this text
		pipeline.annotate(document);

		// these are all the sentences in this document
		// a CoreMap is essentially a Map that uses class objects as keys and
		// has values with custom types
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			List<String> listWord = new ArrayList<String>();
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				listWord.add(word);
				// this is the POS tag of the token
				// String pos = token.get(PartOfSpeechAnnotation.class);
			}
			listSens.add(StringUtils.join(listWord, " "));
		}
		listTokenSens = new String[listSens.size()];
		listTokenSens = listSens.toArray(listTokenSens);
		return listTokenSens;
	}

	@Override
	public String posTagging(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sentence posTagging2(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {
		ProcessText processText = new ProcessENText();
		String[] listSens = processText
				.wordSegments("The backbone of the CoreNLP package is formed by two classes: Annotation and Annotator. Annotations are the data structure which hold the results of annotators. Annotations are basically maps, from keys to bits of the annotation, such as the parse, the part-of-speech tags, or named entity tags.");
		System.out.println(listSens.toString());
	}
}
