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
package org.hcmut.emr.record;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.molisys.framework.base.BaseDaoImpl;

/**
 * @author sinhlk
 *
 */
public class RecordDaoImpl extends BaseDaoImpl<Record> implements RecordDao {

	@Override
	public List<Record> getListRecordByDataSet(String dataSet) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		if (StringUtils.equals(dataSet, "train")) {
			query = session.getNamedQuery("getListRecordTrain");
		} else if (StringUtils.equals(dataSet, "test")) {
			query = session.getNamedQuery("getListRecordTest");
		}

		@SuppressWarnings("unchecked")
		List<Record> resultList = (List<Record>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Record>();
		}
		return resultList;
	}

	@Override
	public int addContributedRecord(Record rec) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		// insert new record
		query = session.getNamedQuery("addContributedRecord");
		query.setParameter("user_id", rec.getUserId());
		query.setParameter("text", rec.getText());
		query.setParameter("language", rec.getLanguage());
		query.executeUpdate();
		// get last insert id
		query = session.getNamedQuery("getLastInsertId");
		List<BigInteger> lastInsertId = (List<BigInteger>) query.list();
		return lastInsertId.get(0).intValue();
	}

}
