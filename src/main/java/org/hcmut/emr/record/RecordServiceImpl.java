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

import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.request.Concept;
import org.hcmut.emr.sentence.Sentence;
import org.hcmut.emr.sentence.SentenceDao;
import org.hcmut.emr.senvn.Senvn;
import org.hcmut.emr.word.Word;
import org.hcmut.emr.word.WordDao;
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
public class RecordServiceImpl extends BaseServiceImpl<Record> implements
		RecordService {
	private static final Logger logger = LoggerFactory
			.getLogger(RecordServiceImpl.class);
	@Autowired
	private RecordDao recordDao;

	@Autowired
	private SentenceDao sentenceDao;

	@Autowired
	private WordDao wordDao;

	/**
	 * @return the recordDao
	 */
	public RecordDao getRecordDao() {
		return recordDao;
	}

	/**
	 * @param recordDao
	 *            the recordDao to set
	 */
	public void setRecordDao(RecordDao recordDao) {
		this.recordDao = recordDao;
		this.baseDao = recordDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Record> getListRecordByDataSet(String dataSet) {
		return recordDao.getListRecordByDataSet(dataSet);
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean saveContributedContent(String user, String language,
			String record, List<Senvn> listSentences, List<Concept> listConcepts) {
		try {
			// insert record
			Record rec = new Record();
			rec.setUserId(user);
			rec.setText(record);
			rec.setLanguage(language);
			int recordId = recordDao.addContributedRecord(rec);
			rec.setId(recordId);
			// insert sentence
			int senPos = 0;
			for (Senvn sen : listSentences) {
				// annotated id
				long annotatedId = sen.getId();
				// insert sentence
				Sentence newSen = new Sentence();
				newSen.setContent(sen.getContent().trim());
				newSen.setIndex(senPos);
				newSen.setRecord(rec);
				int senId = sentenceDao.addContributedSentence(newSen);
				newSen.setId(senId);
				// insert word
				String[] wordArr = sen.getContent().trim().split("\\s");
				List<Word> listWords = new ArrayList<Word>();
				int wordPos = 0;
				for (String wordStr : wordArr) {
					Word word = new Word();
					word.setContent(wordStr.trim());
					word.setIndex(wordPos);
					word.setSentence(newSen);
					word.setIboTag(Word.IBOTag.O);
					listWords.add(word);
					wordPos++;
				}
				senPos++;
				// set ibo tag for word
				for (Concept concept : listConcepts) {
					if (concept.getSentenceId() == annotatedId) {
						setIboTagForAConcept(concept, listWords);
					}
				}
				// insert each word in sen to database
				for (Word word : listWords) {
					wordDao.addContributedWord(word);
				}
			}
		} catch (Exception e) {
			logger.error("Add contributed record error", e);
			return false;
		}

		return true;
	}

	/**
	 * @param concept
	 *            concept to be assigned to words
	 * @param listWords
	 */
	private void setIboTagForAConcept(Concept concept, List<Word> listWords) {
		int fromIdx = concept.getFromWord() - 1;
		int toIdx = concept.getToWord();
		try {
			for (int i = fromIdx; i < toIdx; i++) {
				Word word = listWords.get(i);
				switch (concept.getType()) {
				case PR:
					if (i == fromIdx) {
						word.setIboTag(Word.IBOTag.B_PR);
					} else {
						word.setIboTag(Word.IBOTag.I_PR);
					}
					break;
				case TE:
					if (i == fromIdx) {
						word.setIboTag(Word.IBOTag.B_TE);
					} else {
						word.setIboTag(Word.IBOTag.I_TE);
					}
					break;
				case TR:
					if (i == fromIdx) {
						word.setIboTag(Word.IBOTag.B_TR);
					} else {
						word.setIboTag(Word.IBOTag.I_TR);
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
									+ concept.getToWord() + ")"), e);
			return;
		}
	}

}
