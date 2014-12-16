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
package org.hcmut.emr.utils;

/**
 * @author sinhlk
 *
 */
public class WordFixExtract {
	private int prefix;
	private int suffix;
	private static WordFixExtract wordfixInstanse;

	/**
	 * @param prefix
	 *            - length of prefix
	 * @param suffix
	 *            - length of suffix
	 * @param word
	 */
	public WordFixExtract(int prefix, int suffix) {
		super();
		this.prefix = prefix;
		this.suffix = suffix;
	}

	public static WordFixExtract factory() {
		if (wordfixInstanse == null) {
			wordfixInstanse = new WordFixExtract(3, 3);
		}
		return wordfixInstanse;
	}

	public String[] getFrefixAndSuffix(String word) {
		int resultLength = prefix + suffix;
		String[] result = new String[resultLength];
		// Add prefix
		for (int count = 0; count < prefix; count++) {
			if (count < word.length()) {
				result[count] = word.substring(0, count + 1);
			} else {
				result[count] = "nil";
			}
		}
		// Add suffix
		for (int count = 0; count < suffix; count++) {
			if (count < word.length()) {
				result[resultLength - count - 1] = word.substring(word.length()
						- count - 1, word.length());
			} else {
				result[resultLength - count - 1] = "nil";
			}
		}
		return result;
	}

	public String getFixsString(String word) {
		String[] allResult = getFrefixAndSuffix(word);
		StringBuilder result = new StringBuilder();

		for (String str : allResult) {
			result.append(str).append(" ");
		}
		return result.toString().substring(0, result.length() - 1);
	}

}
