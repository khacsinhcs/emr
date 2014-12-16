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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.molisys.framework.base.BaseDaoImpl;

/**
 * @author sinhlk
 *
 */
public class WordDaoImpl extends BaseDaoImpl<Word> implements WordDao {
	private static final Logger logger = LoggerFactory
			.getLogger(WordDaoImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.word.WordDao#getWordInSentence(long, long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Word getWordInSentence(long sentenceId, long index) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "from Word w where w.index = :index "
				+ "and w.sentence.index = :sentenceId ";
		Query query = session.createQuery(hql);
		query.setParameter("index", index);
		query.setParameter("sentenceId", sentenceId);
		List<Word> resultList = (List<Word>) query.list();
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hcmut.emr.word.WordDao#search(long, long, long)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Word search(long recordId, long sentenceIndex, long index) {
		Session session = this.getSessionFactory().getCurrentSession();
		String hql = "from Word w where w.index = :index "
				+ "and w.sentence.index = :sentenceIndex "
				+ "and w.sentence.record.id = :recordId";
		Query query = session.createQuery(hql);
		query.setParameter("index", index);
		query.setParameter("sentenceIndex", sentenceIndex);
		query.setParameter("recordId", recordId);
		List<Word> resultList = (List<Word>) query.list();
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Word> getListWordBySentenceId(long sentenceId, String dataSet) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		if (StringUtils.equals(dataSet, "train")) {
			query = session.getNamedQuery("getListWordTrain");
		} else if (StringUtils.equals(dataSet, "test")) {
			query = session.getNamedQuery("getListWordTest");
		}
		query.setParameter("sentenceId", sentenceId);
		List<Word> resultList = null;
		try {
			resultList = (List<Word>) query.list();
		} catch (Exception e) {
			logger.error("Something error", e.getStackTrace());
		}

		if (resultList == null) {
			resultList = new ArrayList<Word>();
		}
		return resultList;
	}

	@Override
	public void addContributedWord(Word word) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		// insert new record
		query = session.getNamedQuery("addContributedWord");
		query.setParameter("sentence_id", word.getSentence().getId());
		query.setParameter("content", word.getContent());
		query.setParameter("index", word.getIndex());
		query.setParameter("ibo_tag", word.getIboTag().toString());
		query.executeUpdate();
	}

}
