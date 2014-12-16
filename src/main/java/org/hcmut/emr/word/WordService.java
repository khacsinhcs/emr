/*******************************************************************************
N *  (C) Copyright 2009 Molisys Solutions Co., Ltd. , All rights reserved       *
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

import java.util.List;

import com.molisys.framework.base.BaseService;

/**
 * @author sinhlk
 *
 */
public interface WordService extends BaseService<Word> {
	/**
	 * Search for format i2b2 2010
	 * 
	 * @param recordId
	 *            record id to search
	 * @param sentenceIndex
	 *            sentence id to search
	 * @param index
	 *            of word
	 * @return the word has same info
	 */
	public Word search(long recordId, long sentenceIndex, long index);

	/**
	 * 
	 * @param sentenceId
	 *            id of sentence to search
	 * @param index
	 *            in sentence context
	 * @return
	 */
	public Word getWordInSentence(long sentenceId, long index);
	
	public List<Word> getListWordBySentenceId(long sentenceId, String dataSet);
}
