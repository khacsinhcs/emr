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
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.senvn.SenvnDao;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.emr.worvn.WorvnDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.molisys.framework.base.BaseServiceImpl;

/**
 * @author diepdt
 *
 */
public class RecvnServiceImpl extends BaseServiceImpl<Recvn> implements
		RecvnService {
	private static final Logger logger = LoggerFactory
			.getLogger(RecvnServiceImpl.class);
	@Autowired
	private RecvnDao recvnDao;
	@Autowired
	private WorvnDao worvnDao;
	@Autowired
	private SenvnDao senvnDao;

	/**
	 * @return the recvnDao
	 */
	public RecvnDao getRecvnDao() {
		return recvnDao;
	}

	/**
	 * @param recvnDao
	 *            the recvnDao to set
	 */
	public void setRecvnDao(RecvnDao recvnDao) {
		this.recvnDao = recvnDao;
		this.baseDao = recvnDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recvn> getAvailableRecords(long handleFlag, int recPos,
			int numOfRecvn) {
		return recvnDao.getAvailableRecords(handleFlag, recPos, numOfRecvn);
	}

	@Override
	@Transactional(readOnly = true)
	public int countAnnotatedRecordByUser(String username) {
		return recvnDao.countAnnotatedRecordByUser(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Recvn> getListAnnotatedRecordByUser(String username) {
		return recvnDao.getListAnnotatedRecordByUser(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> getListUsers() {
		return recvnDao.getListUsers();
	}

	@Override
	@Transactional(isolation = Isolation.REPEATABLE_READ)
	public boolean annotateConceptForRecord(double recordId,
			List<Concept> listConcepts, String pic, long senHandle) {
		// check all word in a record
		List<Senvn> sentences = senvnDao
				.search(recordId, "recordId", senHandle);
		for (Senvn senvn : sentences) {
			List<Worvn> words = worvnDao.search(senvn.getId(), "sentenceId",
					"=", 0, 10000);
			// reset all word
			for (Worvn word : words) {
				word.setIboTag(Worvn.IBOTag.O);
			}
			// set IBO tag for each concept
			for (Concept concept : listConcepts) {
				if (concept.getSentenceId() == senvn.getId()) {
					setIboTagForAConcept(concept, words);
				}
			}

			for (Worvn word : words) {
				// update word
				logger.info("Word tagging: (" + word.getContent() + ", "
						+ word.getIboTag() + ")");
				worvnDao.update(word);
			}

			// mark this sentence handled
			senvn.setHandle(1);
			senvnDao.update(senvn);

			// search for other duplicate sentence
			List<Senvn> listDupSens = senvnDao.searchDuplicateContents(
					senvn.getContent(), senvn.getSection());
			if (listDupSens != null && !listDupSens.isEmpty()) {
				for (Senvn senvnDup : listDupSens) {
					List<Worvn> listWordDups = worvnDao.search(
							senvnDup.getId(), "sentenceId", "=", 0, 10000);
					for (int idx = 0; idx < listWordDups.size(); idx++) {
						Worvn wordDup = listWordDups.get(idx);
						wordDup.setIboTag(words.get(idx).getIboTag());
						worvnDao.update(wordDup);
					}
					// mark this duplicate sentence handled
					senvnDup.setHandle(1);
					senvnDao.update(senvnDup);
				}
			}
		}
		// mark this record handled
		if (sentences != null && !sentences.isEmpty()) {
			Recvn record = new Recvn();
			record.setId(recordId);
			record.setHandle(2);
			record.setPic(pic);
			return recvnDao.update(record);
		}
		return false;
	}

	/**
	 * @param concept
	 *            concept to be assigned to words
	 * @param listWords
	 */
	private void setIboTagForAConcept(Concept concept, List<Worvn> listWords) {
		int fromIdx = concept.getFromWord() - 1;
		int toIdx = concept.getToWord();
		try {
			for (int i = fromIdx; i < toIdx; i++) {
				Worvn word = listWords.get(i);
				switch (concept.getType()) {
				case PR:
					if (i == fromIdx) {
						word.setIboTag(Worvn.IBOTag.B_PR);
					} else {
						word.setIboTag(Worvn.IBOTag.I_PR);
					}
					break;
				case TE:
					if (i == fromIdx) {
						word.setIboTag(Worvn.IBOTag.B_TE);
					} else {
						word.setIboTag(Worvn.IBOTag.I_TE);
					}
					break;
				case TR:
					if (i == fromIdx) {
						word.setIboTag(Worvn.IBOTag.B_TR);
					} else {
						word.setIboTag(Worvn.IBOTag.I_TR);
					}
					break;
				default:
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			logger.error(
					"Array out of index: "
							+ concept.getContent()
							+ ("(" + concept.getFromWord() + ", "
									+ concept.getToWord() + ")"),
					e.getMessage());
			return;
		}
	}

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
	}

	/**
	 * @return the senvnDao
	 */
	public SenvnDao getSenvnDao() {
		return senvnDao;
	}

	/**
	 * @param senvnDao
	 *            the senvnDao to set
	 */
	public void setSenvnDao(SenvnDao senvnDao) {
		this.senvnDao = senvnDao;
	}
}
