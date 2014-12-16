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

import java.util.List;

import com.molisys.framework.base.BaseService;

/**
 * @author diepdt
 *
 */
public interface SenvnService extends BaseService<Senvn> {
	/**
	 * @param value
	 *            value to search
	 * @param field
	 *            field to search
	 * @return all items have value in search field equal search value
	 */
	public List<Senvn> search(double value, String field, int handle);
	
	/**
	 * @param content
	 *            content to search
	 * @param section
	 *            section to search
	 * @return list duplicate contents
	 */
	public List<Senvn> searchDuplicateContents(String content, String section);
	
	List<Senvn> getListAnnotatedSentences(double recordId);
	
	List<Senvn> getAllAnnotatedSentences();
	
	boolean extractDataToCRFFormat(String filePath, float train);
}
