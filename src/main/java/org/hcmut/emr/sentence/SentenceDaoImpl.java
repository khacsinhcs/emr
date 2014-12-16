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
package org.hcmut.emr.sentence;

import java.math.BigInteger;
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
public class SentenceDaoImpl extends BaseDaoImpl<Sentence> implements
		SentenceDao {
	private static final Logger logger = LoggerFactory
			.getLogger(SentenceDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Sentence> getListSenByRecordId(long recordId, String dataSet) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		if (StringUtils.equals(dataSet, "train")) {
			query = session.getNamedQuery("getListSentenceTrain");
		} else if (StringUtils.equals(dataSet, "test")) {
			query = session.getNamedQuery("getListSentenceTest");
		}
		query.setParameter("recordId", recordId);
		List<Sentence> resultList = null;
		try {
			resultList = (List<Sentence>) query.list();
		} catch (Exception e) {
			logger.error("Something error", e.getStackTrace());
		}

		if (resultList == null) {
			resultList = new ArrayList<Sentence>();
		}
		return resultList;
	}

	@Override
	public int addContributedSentence(Sentence newSen) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		// insert new record
		query = session.getNamedQuery("addContributedSentence");
		query.setParameter("record_id", newSen.getRecord().getId());
		query.setParameter("content", newSen.getContent());
		query.setParameter("index", newSen.getIndex());
		query.executeUpdate();
		// get last insert id
		query = session.getNamedQuery("getLastInsertId");
		@SuppressWarnings("unchecked")
		List<BigInteger> lastInsertId = (List<BigInteger>) query.list();
		return lastInsertId.get(0).intValue();
	}
}
