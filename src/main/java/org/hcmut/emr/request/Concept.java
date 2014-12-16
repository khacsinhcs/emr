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
package org.hcmut.emr.request;

import org.apache.commons.lang.StringUtils;

/**
 * @author sinhlk
 *
 */
public class Concept {
	private String content = null;
	private ConcepType type = null;
	private long sentenceId = 0;
	private double recordId = 0;
	private int fromWord = -1;
	private int toWord = -1;
	private int fromChar = -1;
	private int toChar = -1;

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the sentenceId
	 */
	public long getSentenceId() {
		return sentenceId;
	}

	/**
	 * @param sentenceId
	 *            the sentenceId to set
	 */
	public void setSentenceId(long sentenceId) {
		this.sentenceId = sentenceId;
	}

	/**
	 * @return the recordId
	 */
	public double getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId
	 *            the recordId to set
	 */
	public void setRecordId(double recordId) {
		this.recordId = recordId;
	}

	/**
	 * Add a word to content
	 * 
	 * @param word
	 *            the word to be added to content
	 * @author dao.diep
	 */
	public void addWordToContent(String word) {
		if (StringUtils.isBlank(this.content)) {
			this.content = word;
		} else {
			this.content += " " + word;
		}
	}

	/**
	 * @return the fromWord
	 */
	public int getFromWord() {
		return fromWord;
	}

	/**
	 * @param fromWord
	 *            the fromWord to set
	 */
	public void setFromWord(int fromWord) {
		this.fromWord = fromWord;
	}

	/**
	 * @return the toWord
	 */
	public int getToWord() {
		if (this.toWord == -1) {
			this.toWord = this.fromWord + this.content.split(" ").length - 1;
		}
		return toWord;
	}

	/**
	 * @param toWord
	 *            the toWord to set
	 */
	public void setToWord(int toWord) {
		this.toWord = toWord;
	}

	/**
	 * @return the type
	 */
	public ConcepType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ConcepType type) {
		this.type = type;
	}

	/**
	 * @return the fromChar
	 */
	public int getFromChar() {
		return fromChar;
	}

	/**
	 * @param fromChar the fromChar to set
	 */
	public void setFromChar(int fromChar) {
		this.fromChar = fromChar;
	}

	/**
	 * @return the toChar
	 */
	public int getToChar() {
		if (this.toChar == -1) {
			this.toChar = this.getFromChar() + this.getContent().length();
		}
		return toChar;
	}

	/**
	 * @param toChar the toChar to set
	 */
	public void setToChar(int toChar) {
		this.toChar = toChar;
	}

	public enum ConcepType {
		PR, TE, TR;
	}
}
