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
package org.hcmut.emr.worvn;

import org.apache.commons.lang3.StringUtils;


/**
 * @author diepdt
 *
 */
public class Worvn {
	private long id;
	//private Senvn sentence;
	private long sentenceId;
	private String content;
	private IBOTag iboTag;
	private String posTag;
	private String orthTag;
	private int dicTag = 0;
	private int section = 0;

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
	 * @return the sentence
	 *//*
	public Senvn getSentence() {
		return sentence;
	}

	*//**
	 * @param sentence
	 *            the sentence to set
	 *//*
	public void setSentence(Senvn sentence) {
		this.sentence = sentence;
	}*/

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
	 * @return the sentenceId
	 */
	public long getSentenceId() {
		return sentenceId;
	}

	/**
	 * @param sentenceId the sentenceId to set
	 */
	public void setSentenceId(long sentenceId) {
		this.sentenceId = sentenceId;
	}

	/**
	 * @return the section
	 */
	public int getSection() {
		return section;
	}

	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		if (StringUtils.equals("HB_BENHLY", section)) {
			this.section = 1;
		} else if (StringUtils.equals("KB_TOMTAT", section)) {
			this.section = 2;
		} else if (StringUtils.equals("DIEUTRI", section)) {
			this.section = 3;
		} else if (StringUtils.equals("TK_PHUONGPHAP", section)) {
			this.section = 4;
		} else if (StringUtils.equals("TK_BENHLY", section)) {
			this.section = 5;
		} else if (StringUtils.equals("LYDO", section)) {
			this.section = 6;
		} else if (StringUtils.equals("HB_BANTHAN", section)) {
			this.section = 7;
		} else {
			this.section = 0;
		}
	}

	/**
	 * @return the dicTag
	 */
	public int getDicTag() {
		return dicTag;
	}

	/**
	 * @param dicTag the dicTag to set
	 */
	public void setDicTag(int dicTag) {
		this.dicTag = dicTag;
	}

	public enum IBOTag {
		O, B_TE, I_TE, B_TR, I_TR, B_PR, I_PR
	}
}
