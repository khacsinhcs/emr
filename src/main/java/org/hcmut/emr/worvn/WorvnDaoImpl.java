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

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.molisys.framework.base.BaseDaoImpl;

/**
 * @author sinhlk
 *
 */
public class WorvnDaoImpl extends BaseDaoImpl<Worvn> implements WorvnDao {

	@Override
	public int findInDictionary(String content) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		query = session.getNamedQuery("findInDictionary");
		// search like
		query.setParameter("content", "%" + content + "%");
		@SuppressWarnings("unchecked")
		List<BigInteger> res = (List<BigInteger>) query.list();
		return res.get(0).intValue();
	}

	@Override
	public void updateDicTag(long id, int tagValue) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = null;
		// update dic_tag for word
		query = session.getNamedQuery("updateDicTag");
		query.setParameter("tagValue", tagValue);
		query.setParameter("id", id);
		query.executeUpdate();
	}

}
