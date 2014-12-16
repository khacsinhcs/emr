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
public interface BaseDao<BeanType> {

	/**
	 * @param id
	 * @param keyType
	 * @return Object has key equal id
	 */
	BeanType getById(String id);

	/**
	 * @param id
	 * @return Object has key equal id
	 */
	BeanType getById(long id);

	/**
	 * @param item
	 *            item to delete
	 * @return true if delete successfully
	 */
	boolean delete(BeanType item);

	/**
	 * @param item
	 *            item to insert
	 * @return true if insert successfully
	 */
	boolean insert(BeanType item);

	/**
	 * @param item
	 *            item to update
	 * @return true if update successfully
	 */
	boolean update(BeanType item);

	/**
	 * @param start
	 *            start item
	 * @param limit
	 *            max size of result set
	 * @return get all data in database
	 */
	List<BeanType> getAll(int start, int limit);

	/**
	 * @return number of items in dataset
	 */
	int countAll();

	/**
	 * search field when type of this field in database is String
	 * 
	 * @param value
	 *            value is keyword to search
	 * @param field
	 *            field to search in
	 * @param start
	 *            start item
	 * @param limit
	 *            max size of result set
	 * @return list items have field value contain search key
	 */
	List<BeanType> search(String value, String field, int start, int limit);

	/**
	 * @param value
	 * @param field
	 * @return number of items has field value contain search value
	 */
	int countWithCondition(String value, String field);

	/**
	 * search field when type of this field in database is Int/Long
	 * 
	 * @param value
	 *            value is keyword to search
	 * @param field
	 *            field to search in
	 * @param condition
	 *            condition to search. Include ">", ">=", "<", "<=", "="
	 * @param start
	 *            start item
	 * @param limit
	 *            max size of result set
	 * @return list items have field value contain search key
	 */
	List<BeanType> search(long value, String field, String condition,
			int start, int limit);

	/**
	 * @param value
	 *            value to count
	 * @param field
	 *            field to count
	 * @param condition
	 *            condition to count. Include ">", ">=", "<", "<=", "="
	 * @return number of items have field value equal search value
	 */
	int countWithCondition(long value, String field, String condition);

}
