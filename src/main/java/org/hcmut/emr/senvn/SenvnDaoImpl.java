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
package org.hcmut.emr.senvn;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.molisys.framework.base.BaseDaoImpl;

/**
 * @author diepdt
 *
 */
public class SenvnDaoImpl extends BaseDaoImpl<Senvn> implements SenvnDao {

	@Override
	public List<Senvn> search(double value, String field, long handle) {
		Session session = getSessionFactory().getCurrentSession();

		String hql = "from " + getTableHibernate() + " t where t." + field
				+ " " + "=" + " :value and t.handle = :handle and t.section IN ('LYDO', 'HB_BANTHAN', 'HB_BENHLY', 'KB_TOMTAT', 'DIEUTRI', 'TK_PHUONGPHAP', 'TK_BENHLY')";
		Query query = session.createQuery(hql);
		query.setParameter("value", value);
		query.setParameter("handle", handle);

		@SuppressWarnings("unchecked")
		List<Senvn> resultList = (List<Senvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Senvn>();
		}
		return resultList;
	}

	@Override
	public List<Senvn> searchDuplicateContents(String content, String section) {
		Session session = getSessionFactory().getCurrentSession();

		String hql = "from " + getTableHibernate() + " t where t.content = :contentValue and t.section = :sectionValue";
		Query query = session.createQuery(hql);
		query.setParameter("contentValue", content);
		query.setParameter("sectionValue", section);

		@SuppressWarnings("unchecked")
		List<Senvn> resultList = (List<Senvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Senvn>();
		}
		return resultList;
	}

	@Override
	public List<Senvn> getListAnnotatedSentences(double recordId) {
		Session session = getSessionFactory().getCurrentSession();

		String hql = "from " + getTableHibernate() + " t where t." + "recordId"
				+ " " + "=" + " :recordId and t.handle = 1";
		Query query = session.createQuery(hql);
		query.setParameter("recordId", recordId);

		@SuppressWarnings("unchecked")
		List<Senvn> resultList = (List<Senvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Senvn>();
		}
		return resultList;
	}

	@Override
	public List<Senvn> getAllAnnotatedSentences() {
		Session session = getSessionFactory().getCurrentSession();

		String hql = "from " + getTableHibernate() + " t where t.handle = 1";
		Query query = session.createQuery(hql);
		//query.setParameter("section", "TK_BENHLY");

		@SuppressWarnings("unchecked")
		List<Senvn> resultList = (List<Senvn>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<Senvn>();
		}
		return resultList;
	}

}
