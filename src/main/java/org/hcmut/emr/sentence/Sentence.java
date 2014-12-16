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
package org.hcmut.emr.sentence;

import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.record.Record;
import org.hcmut.emr.request.Concept;

/**
 * @author sinhlk
 *
 */
public class Sentence {
	private long id;
	private Long index;
	private Record record;
	private String content;
	private Long includePattern;
	private List<Concept> concept = new ArrayList<Concept>();
	private Long isHandle;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the index
	 */
	public long getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(long index) {
		this.index = index;
	}

	/**
	 * @return the record
	 */
	public Record getRecord() {
		return record;
	}

	/**
	 * @param record
	 *            the record to set
	 */
	public void setRecord(Record record) {
		this.record = record;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the includePattern
	 */
	public Long getIncludePattern() {
		return includePattern;
	}

	/**
	 * @param includePattern the includePattern to set
	 */
	public void setIncludePattern(Long includePattern) {
		this.includePattern = includePattern;
	}

	/**
	 * @return the isHandle
	 */
	public Long getIsHandle() {
		return isHandle;
	}

	/**
	 * @param isHandle the isHandle to set
	 */
	public void setIsHandle(Long isHandle) {
		this.isHandle = isHandle;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Long index) {
		this.index = index;
	}

	/**
	 * @return the concept
	 */
	public List<Concept> getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(List<Concept> concept) {
		this.concept = concept;
	}

}
