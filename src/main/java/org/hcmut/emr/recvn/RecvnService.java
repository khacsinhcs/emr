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

import java.util.List;

import org.hcmut.emr.request.Concept;

import com.molisys.framework.base.BaseService;

/**
 * @author diepdt
 *
 */
public interface RecvnService extends BaseService<Recvn> {
	/**
	 * Get available record to be assigned class
	 * 
	 * @param handleFlag
	 *            handle flag
	 * @param recPos
	 *            the position want to get record
	 * @param numOfRecvn
	 *            number of records want to get
	 * @return list of records
	 * @author dao.diep
	 */
	List<Recvn> getAvailableRecords(long handleFlag, int recPos, int numOfRecvn);

	int countAnnotatedRecordByUser(String username);

	List<Recvn> getListAnnotatedRecordByUser(String username);

	List<String> getListUsers();

	boolean annotateConceptForRecord(double recordId, List<Concept> listConcepts, String pic, long senHandle);
}
