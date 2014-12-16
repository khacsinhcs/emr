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
package org.hcmut.emr.worvn;

import java.util.List;

import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.senvn.SenvnDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.molisys.framework.base.BaseServiceImpl;

/**
 * @author sinhlk
 *
 */
public class WorvnServiceImpl extends BaseServiceImpl<Worvn> implements
		WorvnService {
	private static final Logger logger = LoggerFactory
			.getLogger(WorvnServiceImpl.class);
	@Autowired
	private WorvnDao worvnDao;
	@Autowired
	private SenvnDao senvnDao;

	/**
	 * @return the worvnDao
	 */
	public WorvnDao getWorvnDao() {
		return worvnDao;
	}

	/**
	 * @param worvnDao
	 *            the worvnDao to set
	 */
	public void setWorvnDao(WorvnDao worvnDao) {
		this.worvnDao = worvnDao;
		this.baseDao = worvnDao;
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean updateDicTag() {
		try {
			List<Senvn> listSentence = senvnDao.getAllAnnotatedSentences();
			for (Senvn sentence : listSentence) {
				List<Worvn> listWord = worvnDao.search(sentence.getId(),
						"sentenceId", "=", 0, 10000);
				for (Worvn worvn : listWord) {
					int countMatches = worvnDao
							.findInDictionary(worvn.getContent());
					if (countMatches > 0) {
						worvnDao.updateDicTag(worvn.getId(), 1);
					}
				}
			}
		} catch (Exception e) {
			logger.error("update Dic tag error", e);
			return false;
		}

		return true;
	}

}
