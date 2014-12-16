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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.word.Word;
import org.hcmut.emr.worvn.Worvn;
import org.hcmut.emr.worvn.WorvnDao;
import org.hcmut.file.ExportData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.molisys.framework.base.BaseServiceImpl;
import com.molisys.framework.utils.DateTimeHelper;

/**
 * @author diepdt
 *
 */
public class SenvnServiceImpl extends BaseServiceImpl<Senvn> implements
		SenvnService {
	private static final Logger logger = LoggerFactory
			.getLogger(SenvnServiceImpl.class);
	@Autowired
	private SenvnDao senvnDao;
	@Autowired
	private WorvnDao worvnDao;
	private int extractionMode = 0;

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
		this.baseDao = senvnDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Senvn> search(double value, String field, int handle) {
		return senvnDao.search(value, field, handle);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Senvn> searchDuplicateContents(String content, String section) {
		return senvnDao.searchDuplicateContents(content, section);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Senvn> getListAnnotatedSentences(double recordId) {
		return senvnDao.getListAnnotatedSentences(recordId);
	}

	@Override
	public List<Senvn> getAllAnnotatedSentences() {
		return senvnDao.getAllAnnotatedSentences();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean extractDataToCRFFormat(String filePath, float train) {
		boolean res = false;
		try {
			List<Senvn> listSentence = senvnDao.getAllAnnotatedSentences();
			int trainCount = Math.round(listSentence.size() * train);
			logger.info("Train/Test rate: " + trainCount + "/"
					+ (listSentence.size() - trainCount));
			// extract train file
			setExtractionMode(0);
			List<Senvn> listSenTrain = listSentence.subList(0, trainCount);
			res = extracListSentence(listSenTrain, filePath);
			// extract test file
			setExtractionMode(1);
			List<Senvn> listSenTest = listSentence.subList(trainCount,
					listSentence.size());
			res = extracListSentence(listSenTest, filePath);
		} catch (Exception e) {
			logger.error("Error on extract data to CRF format", e.getStackTrace().toString());
			res = false;
		}
		return res;
	}

	private boolean extracListSentence(List<Senvn> listSens, String filePath) {
		try {
			List<ExportData> exporters = createListOutWriter(filePath);
			for (Senvn sentence : listSens) {
				List<Worvn> listWord = worvnDao.search(sentence.getId(),
						"sentenceId", "=", 0, 10000);
				for (Worvn word : listWord) {
					if (word.getIboTag() != null) {
						word.setSection(sentence.getSection().trim());
						for (ExportData exporter : exporters) {
							exporter.writeLineVn(word);
						}
					}
				}
				for (ExportData exporter : exporters) {
					exporter.newLine();
				}
			}

			for (ExportData exporter : exporters) {
				exporter.finish();
			}
		} catch (IOException e) {
			logger.error("Write file error", e.getStackTrace().toString());
			logger.error("File path: " + filePath);
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param mode
	 * @return
	 * @throws IOException
	 */
	private List<ExportData> createListOutWriter(String filePath)
			throws IOException {
		ArrayList<ExportData> result = new ArrayList<ExportData>();
		String path = filePath;
		ExportData file = new ExportData(buildPath(path, "pos"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ convertIboTagVn(word.getIboTag());
					}
				});
		result.add(file);

		file = new ExportData(buildPath(path, "orth"),
				new ExportData.LineWriter() {

					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ convertIboTagVn(word.getIboTag());
					}
				});
		result.add(file);
		
		file = new ExportData(buildPath(path, "dic"),
				new ExportData.LineWriter() {

					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ word.getSection() + " "
								+ word.getDicTag() + " "
								+ convertIboTagVn(word.getIboTag());
					}
				});
		result.add(file);
		
		file = new ExportData(buildPath(path, "section"),
				new ExportData.LineWriter() {

					@Override
					public String write(Word word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return word.getContent() + " " + word.getPosTag() + " "
								+ word.getOrthTag() + " "
								+ word.getSection() + " "
								+ convertIboTagVn(word.getIboTag());
					}
				});
		result.add(file);

		file = new ExportData(buildPath(path, "baseline"),
				new ExportData.LineWriter() {
					@Override
					public String write(Word word) {
						return word.getContent() + " "
								+ convertIboTag(word.getIboTag());
					}

					@Override
					public String writeVn(Worvn word) {
						return word.getContent() + " "
								+ convertIboTagVn(word.getIboTag());
					}
				});
		result.add(file);
		return result;
	}

	private String buildFileName(String name) {
		DateTimeHelper dateHelper = DateTimeHelper.factory();
		String fileName = dateHelper.getCurrentDateOnString();
		fileName += "_" + name;
		if (getExtractionMode() == 0) {
			fileName += ".train";
		} else if (getExtractionMode() == 1) {
			fileName += ".test";
		}
		return fileName;
	}

	public String buildPath(String path, String name) {
		return path + "/" + buildFileName(name);
	}

	private String convertIboTag(Word.IBOTag tag) {
		if (tag == Word.IBOTag.B_TR) {
			return "B-TR";
		} else if (tag == Word.IBOTag.I_TR) {
			return "I-TR";
		} else if (tag == Word.IBOTag.I_PR) {
			return "I-PR";
		} else if (tag == Word.IBOTag.B_PR) {
			return "B-PR";
		} else if (tag == Word.IBOTag.B_TE) {
			return "B-TE";
		} else if (tag == Word.IBOTag.I_TE) {
			return "I-TE";
		}
		return "O";
	}

	private String convertIboTagVn(Worvn.IBOTag tag) {
		if (tag == Worvn.IBOTag.B_TR) {
			return "B-TR";
		} else if (tag == Worvn.IBOTag.I_TR) {
			return "I-TR";
		} else if (tag == Worvn.IBOTag.I_PR) {
			return "I-PR";
		} else if (tag == Worvn.IBOTag.B_PR) {
			return "B-PR";
		} else if (tag == Worvn.IBOTag.B_TE) {
			return "B-TE";
		} else if (tag == Worvn.IBOTag.I_TE) {
			return "I-TE";
		}
		return "O";
	}

	/**
	 * @return the extractionMode
	 */
	public int getExtractionMode() {
		return extractionMode;
	}

	/**
	 * @param extractionMode
	 *            the extractionMode to set
	 */
	public void setExtractionMode(int extractionMode) {
		this.extractionMode = extractionMode;
	}

}
