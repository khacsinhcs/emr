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

/**
 * @author sinhlk
 *
 *         Create date Jun 14, 2014
 */
public interface BaseService<BeanType> {

	/**
	 * @param id
	 * @return item has key value equal id
	 */
	public BeanType getById(long id);

	/**
	 * @param id
	 * @return item has key value equal id
	 */
	public BeanType getById(String id);

	/**
	 * @param item
	 *            item to delete
	 * @return true if delete successful
	 */
	public boolean delete(BeanType item);

	/**
	 * @param item
	 *            item to add
	 * @return true if add successful
	 */
	public boolean add(BeanType item);

	/**
	 * @param item
	 *            item to update
	 * @return true if update successful
	 */
	public boolean update(BeanType item);

	/**
	 * @return All items in database
	 */
	public List<BeanType> getAll();

	/**
	 * @param numOfPage
	 *            is number of page in context
	 * @return
	 */
	public List<BeanType> getAll(int numOfPage);

	/**
	 * @return number of record
	 */
	public int countAll();

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @return all items have value in search field equal search value
	 */
	public List<BeanType> search(long value, String field);

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @param page
	 *            page in context
	 * @return all items have value in search field equal search value
	 */
	public List<BeanType> search(long value, String field, int page);

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @param condition
	 *            condition to search. Including >, <, >=, <=, =
	 * @return all items have value in search field match condition with search
	 *         value
	 */
	public List<BeanType> search(long value, String field, String condition);

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @param condition
	 *            condition to search. Including ">", "<", ">=", "<=", "="
	 * @param page
	 *            page is number of page in context
	 * @return all items have value in search field match condition with search
	 *         value
	 */
	public List<BeanType> search(long value, String field, String condition,
			int page);

	/**
	 * @param value
	 *            value to count
	 * @param field
	 *            field to count
	 * @param condition
	 *            condition to search. Including >, <, >=, <=, =
	 * @return number of all items have value in search field match condition
	 *         with search value
	 */
	public int countWithCondition(long value, String field, String condition);

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @return all items have value in search field contain search value
	 */
	public List<BeanType> search(String value, String field);

	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @param page
	 *            number of page in context
	 * @return items in page number
	 */
	public List<BeanType> search(String value, String field, int page);

	/**
	 * @param value
	 *            value to count
	 * @param field
	 *            field to count
	 * @return number of all items have value in search field contain search
	 *         value
	 */
	public int countWithCondition(String value, String field);
}
