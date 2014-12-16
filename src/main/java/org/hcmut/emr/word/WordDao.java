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

import java.util.List;

import com.molisys.framework.base.BaseDao;

/**
 * @author sinhlk
 *
 */
public interface WordDao extends BaseDao<Word> {

	/**
	 * @param recordId
	 * @param sentenceId
	 * @param index
	 * @return
	 */
	public Word search(long recordId, long sentenceId, long index);

	/**
	 * @param sentenceId
	 * @param index
	 * @return
	 */
	public Word getWordInSentence(long sentenceId, long index);

	public List<Word> getListWordBySentenceId(long sentenceId, String dataSet);

	public void addContributedWord(Word word);

}
