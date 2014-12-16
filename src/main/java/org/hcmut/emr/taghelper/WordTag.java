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

import org.hcmut.emr.word.Word;

/**
 * @author sinhlk
 *
 */
public interface WordTag {
	/**
	 * Get tag of a word
	 * 
	 * @param word
	 *            to get
	 * @return tag
	 */
	public Tag getTag(String word);

	/**
	 * Update word below its tag
	 * 
	 * @param word
	 *            to update
	 */
	public void updateWord(Word word);
}
