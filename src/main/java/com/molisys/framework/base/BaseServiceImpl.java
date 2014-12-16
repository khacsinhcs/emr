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

import java.util.List;

import org.hibernate.validator.internal.xml.BeanType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author sinhlk
 *
 *         Create date Jun 14, 2014
 */
@SuppressWarnings("hiding")
public class BaseServiceImpl<BeanType> implements BaseService<BeanType> {

	protected BaseDao<BeanType> baseDao;

	protected static int paging = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#getById(long)
	 */
	@Override
	@Transactional(readOnly = true)
	public BeanType getById(long id) {
		return baseDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#getById(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public BeanType getById(String id) {
		return baseDao.getById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#delete(java.lang.Object)
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean delete(BeanType item) {
		return baseDao.delete(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#add(java.lang.Object)
	 */
	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean add(BeanType item) {
		if (!checkValidate(item)) {
			return false;
		}
		return baseDao.insert(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#update(java.lang.Object)
	 */
	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public boolean update(BeanType item) {
		if (!checkValidate(item)) {
			return false;
		}
		return baseDao.update(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#getAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> getAll() {
		return baseDao.getAll(0, 10000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#getAll(int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> getAll(int numOfPage) {
		return baseDao.getAll(startItem(numOfPage), paging);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#countAll()
	 */
	@Override
	public int countAll() {
		return baseDao.countAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(long,
	 * java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(long value, String field) {
		return baseDao.search(value, field, "=", 0, 10000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(long,
	 * java.lang.String, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(long value, String field, int page) {
		return baseDao.search(value, field, "=", startItem(page), paging);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(long value, String field, String condition) {
		return baseDao.search(value, field, condition, 0, 10000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(long,
	 * java.lang.String, java.lang.String, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(long value, String field, String condition,
			int page) {
		return baseDao.search(value, field, condition, startItem(page), paging);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#countWithCondition(long,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public int countWithCondition(long value, String field, String condition) {
		return baseDao.countWithCondition(value, field, condition);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(String value, String field) {
		return baseDao.search(value, field, 0, 10000);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.molisys.framework.base.BaseService#search(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	@Transactional(readOnly = true)
	public List<BeanType> search(String value, String field, int page) {
		return baseDao.search(value, field, startItem(page), paging);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.molisys.framework.base.BaseService#countWithCondition(java.lang.String
	 * , java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
	public int countWithCondition(String value, String field) {
		return baseDao.countWithCondition(value, field);
	}

	/**
	 * @return the baseDao
	 */
	public BaseDao<BeanType> getBaseDao() {
		return baseDao;
	}

	/**
	 * @param baseDao
	 *            the baseDao to set
	 */
	public void setBaseDao(BaseDao<BeanType> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @return the paging
	 */
	public static int getPaging() {
		return paging;
	}

	/**
	 * @param paging
	 *            the paging to set
	 */
	public static void setPaging(int paging) {
		BaseServiceImpl.paging = paging;
	}

	/**
	 * use in add and update item to database
	 * 
	 * @param item
	 *            item to validate
	 */
	protected boolean checkValidate(BeanType item) {
		return true;
	}

	/**
	 * @param page
	 *            in context
	 * @return start item in result
	 */
	private int startItem(int page) {
		return paging * (page - 1);
	}

}
