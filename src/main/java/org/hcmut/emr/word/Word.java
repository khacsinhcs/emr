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
package org.hcmut.emr.word;

import org.hcmut.emr.sentence.Sentence;

/**
 * @author sinhlk
 *
 */
public class Word {
	private long id;
	private long index;
	private Sentence sentence;
	private String content;
	private IBOTag iboTag;
	private String posTag;
	private String orthTag;
	private String sessionTag;
	private int umlsTag;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the index
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(long index) {
		this.index = index;
	}

	/**
	 * @return the sentence
	 */
	public Sentence getSentence() {
		return sentence;
	}

	/**
	 * @param sentence
	 *            the sentence to set
	 */
	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

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
	 * @return the iboTag
	 */
	public IBOTag getIboTag() {
		return iboTag;
	}

	/**
	 * @param iboTag
	 *            the iboTag to set
	 */
	public void setIboTag(IBOTag iboTag) {
		this.iboTag = iboTag;
	}

	/**
	 * @return the posTag
	 */
	public String getPosTag() {
		return posTag;
	}

	/**
	 * @param posTag
	 *            the posTag to set
	 */
	public void setPosTag(String posTag) {
		this.posTag = posTag;
	}

	/**
	 * @return the orthTag
	 */
	public String getOrthTag() {
		return orthTag;
	}

	/**
	 * @param orthTag
	 *            the orthTag to set
	 */
	public void setOrthTag(String orthTag) {
		this.orthTag = orthTag;
	}

	/**
	 * @return the umlsTag
	 */
	public int getUmlsTag() {
		return umlsTag;
	}

	/**
	 * @param umlsTag
	 *            the umlsTag to set
	 */
	public void setUmlsTag(int umlsTag) {
		this.umlsTag = umlsTag;
	}

	/**
	 * @return the sessionTag
	 */
	public String getSessionTag() {
		return sessionTag;
	}

	/**
	 * @param sessionTag
	 *            the sessionTag to set
	 */
	public void setSessionTag(String sessionTag) {
		this.sessionTag = sessionTag;
	}

	public enum IBOTag {
		O, B_TE, I_TE, B_TR, I_TR, B_PR, I_PR
	}
}
