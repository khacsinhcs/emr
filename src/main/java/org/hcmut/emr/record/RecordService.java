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

import java.util.List;

import org.hcmut.emr.request.Concept;
import org.hcmut.emr.senvn.Senvn;

import com.molisys.framework.base.BaseService;

/**
 * @author sinhlk
 *
 */
public interface RecordService extends BaseService<Record> {
	List<Record> getListRecordByDataSet(String dataSet);
	
	boolean saveContributedContent(String user, String language, String record,
			List<Senvn> sentences, List<Concept> concepts);
}
