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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.molisys.framework.base.BaseDaoImpl;

/**
 * @author diepdt
 *
 */
public class RecvnDaoImpl extends BaseDaoImpl<Recvn> implements RecvnDao {

	@Override
	public List<Recvn> getAvailableRecords(long handleFlag, int recPos,
			int numOfRecvn) {
		Session session = getSessionFactory().getCurrentSession();

		String hql = "select * from "
				+ "benh_an.chi_tiet"
				+ " c where c.HANDLE_FLAG = :handleFlag and c.WORD_COUNT > 1 order by c.WORD_COUNT desc";
		Query query = session.createSQLQuery(hql).addEntity(Recvn.class);
		query.setParameter("handleFlag", handleFlag);
		query.setFirstResult(recPos);
		query.setMaxResults(numOfRecvn);

		@SuppressWarnings("unchecked")
		List<Recvn> resultList = (List<Recvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Recvn>();
		}
		return resultList;
	}

	@Override
	public int countAnnotatedRecordByUser(String username) {
		int count = 0;
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery("countAnnotatedRecordByUser");
		query.setString("username", username);
		BigInteger result = (BigInteger) query.uniqueResult();
		if (result == null) {
			count = 0;
		} else {
			count = result.intValue();
		}
		return count;
	}

	@Override
	public List<Recvn> getListAnnotatedRecordByUser(String username) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery("getListAnnotatedRecordByUser");
		query.setString("username", username);

		@SuppressWarnings("unchecked")
		List<Recvn> resultList = (List<Recvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Recvn>();
		}
		return resultList;
	}

	@Override
	public List<String> getListUsers() {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery("getListUsers");

		@SuppressWarnings("unchecked")
		List<String> resultList = (List<String>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<String>();
		}
		return resultList;
	}

}
