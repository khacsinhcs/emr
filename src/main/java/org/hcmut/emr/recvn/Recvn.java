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
package org.hcmut.emr.recvn;

/**
 * @author dao.diep
 *
 */
public class Recvn {
	private double id;
	private long handle;
	//private long wordCount;
	private String pic;

	/**
	 * @return the id
	 */
	public double getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(double id) {
		this.id = id;
	}

	/**
	 * @return the handle
	 */
	public long getHandle() {
		return handle;
	}

	/**
	 * @param handle the handle to set
	 */
	public void setHandle(long handle) {
		this.handle = handle;
	}

	/**
	 * @return the wordCount
	 *//*
	public long getWordCount() {
		return wordCount;
	}

	*//**
	 * @param wordCount the wordCount to set
	 *//*
	public void setWordCount(long wordCount) {
		this.wordCount = wordCount;
	}*/

	/**
	 * @return the pic
	 */
	public String getPic() {
		return pic;
	}

	/**
	 * @param pic the pic to set
	 */
	public void setPic(String pic) {
		this.pic = pic;
	}
}
