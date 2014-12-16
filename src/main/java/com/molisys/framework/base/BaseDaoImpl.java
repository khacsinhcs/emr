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
package com.molisys.framework.base;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author sinhlk
 *
 *         Create date Jun 14, 2014
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<BeanType> implements BaseDao<BeanType> {
	// extent class must initial values
	private String tableHibernate;
	private String keyHibernate;

	private SessionFactory sessionFactory;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.famework.base.BaseDao#getById(java.lang.String)
	 */
	@Override
	public BeanType getById(String id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from " + tableHibernate + " t where t." + keyHibernate
				+ " = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);

		List<BeanType> resultList = (List<BeanType>) query.list();
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	@Override
	public BeanType getById(long id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from " + tableHibernate + " t where t." + keyHibernate
				+ " = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);

		List<BeanType> resultList = (List<BeanType>) query.list();
		if (resultList == null || resultList.size() == 0) {
			return null;
		}
		return resultList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.famework.base.BaseDao#delete(java.lang.Object)
	 */
	@Override
	public boolean delete(BeanType item) {
		sessionFactory.getCurrentSession().delete(item);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.famework.base.BaseDao#insert(java.lang.Object)
	 */
	@Override
	public boolean insert(BeanType item) {
		sessionFactory.getCurrentSession().save(item);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.famework.base.BaseDao#update(java.lang.Object)
	 */
	@Override
	public boolean update(BeanType item) {
		sessionFactory.getCurrentSession().update(item);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.famework.base.BaseDao#getAll(int, int)
	 */
	@Override
	public List<BeanType> getAll(int start, int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from " + tableHibernate;
		Query query = session.createQuery(hql);
		query.setFirstResult(start);

		List<BeanType> resultList = (List<BeanType>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<BeanType>();
		}
		return resultList;
	}

	@Override
	public List<BeanType> search(String value, String field, int start,
			int limit) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from " + tableHibernate + " t where t." + field
				+ " like :value";
		Query query = session.createQuery(hql);
		query.setParameter("value", "%" + value + "%");
		query.setFirstResult(start);

		List<BeanType> resultList = (List<BeanType>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<BeanType>();
		}
		return resultList;
	}

	@Override
	public List<BeanType> search(long value, String field, String condition,
			int start, int limit) {
		Session session = sessionFactory.getCurrentSession();

		String hql = "from " + tableHibernate + " t where t." + field + " "
				+ condition + " :value";
		Query query = session.createQuery(hql);
		query.setParameter("value", value);
		query.setFirstResult(start);

		List<BeanType> resultList = (List<BeanType>) query.list();

		if (resultList == null) {
			resultList = new ArrayList<BeanType>();
		}
		return resultList;
	}

	@Override
	public int countWithCondition(String value, String field) {
		return this.search(value, field, 0, 10000).size();
	}

	@Override
	public int countWithCondition(long value, String field, String condition) {
		return this.search(value, field, condition, 0, 10000).size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseDao#countAll()
	 */
	@Override
	public int countAll() {
		return this.getAll(0, 10000).size();
	}

	/**
	 * @return the tableHibernate
	 */
	public String getTableHibernate() {
		return tableHibernate;
	}

	/**
	 * @param tableHibernate
	 *            the tableHibernate to set
	 */
	public void setTableHibernate(String tableHibernate) {
		this.tableHibernate = tableHibernate;
	}

	/**
	 * @return the keyHibernate
	 */
	public String getKeyHibernate() {
		return keyHibernate;
	}

	/**
	 * @param keyHibernate
	 *            the keyHibernate to set
	 */
	public void setKeyHibernate(String keyHibernate) {
		this.keyHibernate = keyHibernate;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @param sessionFactory
	 *            the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
