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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.molisys.framework.base.BaseServiceImpl;

/**
 * @author sinhlk
 *
 */
public class WordServiceImpl extends BaseServiceImpl<Word> implements
		WordService {
	@Autowired
	private WordDao wordDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.word.WordService#getWordInSentence(long, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Word getWordInSentence(long sentenceId, long index) {
		return wordDao.getWordInSentence(sentenceId, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.word.WordService#search(long, long, long)
	 */
	@Override
	@Transactional(readOnly = true)
	public Word search(long recordId, long sentenceId, long index) {
		return wordDao.search(recordId, sentenceId, index);
	}

	/**
	 * @return the wordDao
	 */
	public WordDao getWordDao() {
		return wordDao;
	}

	/**
	 * @param wordDao
	 *            the wordDao to set
	 */
	public void setWordDao(WordDao wordDao) {
		this.wordDao = wordDao;
		this.baseDao = wordDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Word> getListWordBySentenceId(long sentenceId, String dataSet) {
		return wordDao.getListWordBySentenceId(sentenceId, dataSet);
	}

}
