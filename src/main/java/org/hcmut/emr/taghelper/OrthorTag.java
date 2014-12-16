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

/**
 * @author sinhlk
 *
 */
public class OrthorTag extends Tag {

	private String allCapPattern = "[A-Z]+";
	private String lowerCase = "[a-z]+";
	private String capsAndHyphen = "([A-Z]+-[A-Z]+)+";
	private String capsAndPeriod = "[A-Z\\.]*";
	private String capsAndDegits = "[A-Z0-9]*";
	private String lettersAndDegits = "[a-zA-Z0-9]+";
	private String initCapPattern = "[A-Z].*";
	private String initCapAndDegits = "[A-Z][0-9]+";
	private String initCapAndPeriod = "[A-Z](.*)\\.(.*)";
	private String initCapAndHyphen = "[A-Z](.*)-(.*)";
	private String openParen = "\\(";
	private String closeParen = "\\)";
	private String brace = "\"|\\[|\\]|\\{|\\}";
	private String number = "[0-9]+(\\.[0-9]+)?";
	private String mark = ":|\\?|!|\\.";
	private String percent = "%";
	private String hephen = "-";
	private String slash = "/";
	
	private String date = "\\d(\\d)?-\\d(\\d)?(-\\d\\d(\\d\\d)?)?|\\d(\\d)?/\\d(\\d)?(/\\d\\d(\\d\\d)?)?|"
			+ "(\\d\\d(\\d\\d)?-)?\\d(\\d)?-\\d(\\d)?|(\\d\\d(\\d\\d)?/)?\\d(\\d)?/\\d(\\d)?";
	private String time = "(\\d(\\d)?:\\d(\\d)?|\\d(\\d)?h\\d(\\d)?')(am|pm)?";

	public OrthorTag(String word) {
		if (word.matches(slash)) {
			this.setTag("1"); 
		} else if (word.matches(hephen)) {
			this.setTag("2");
		} else if (word.matches(openParen)) {
			this.setTag("3");
		} else if (word.matches(closeParen)) {
			this.setTag("4");
		} else if (word.matches(percent)) {
			this.setTag("5");
		} else if (word.matches(mark)) {
			this.setTag("6");
		} else if (word.matches(number)) {
			this.setTag("7");
		} else if (word.matches(brace)) {
			this.setTag("8");
		} else if (word.matches(time)) {
			this.setTag("9");
		} else if (word.matches(date)) {
			this.setTag("10");
		} else if (word.matches(allCapPattern)) {
			this.setTag("11");
		} else if (word.matches(capsAndDegits)) {
			this.setTag("12");
		} else if (word.matches(capsAndHyphen)) {
			this.setTag("13");
		} else if (word.matches(capsAndPeriod)) {
			this.setTag("14");
		} else if (word.matches(lowerCase)) {
			this.setTag("15");
		} else if (word.matches(lettersAndDegits)) {
			this.setTag("16");
		} else if (word.matches(initCapAndDegits)) {
			this.setTag("17");
		} else if (word.matches(initCapAndHyphen)) {
			this.setTag("18");
		} else if (word.matches(initCapAndPeriod)) {
			this.setTag("19");
		} else if (word.matches(initCapPattern)) {
			this.setTag("20");
		} else {
			this.setTag("21");
		}
	}

	public static void main(String[] args) {
		OrthorTag tag = new OrthorTag("");
		String str1 = "21/10/1221";
		tag.check(str1, tag.date);
	}

	public void check(String text, String pattern) {
		if (text.matches(pattern)) {
			System.out.println("Match");
		} else {
			System.out.println("None");
		}
	}
}
