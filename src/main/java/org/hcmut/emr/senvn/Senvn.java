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

import java.util.ArrayList;
import java.util.List;

import org.hcmut.emr.request.Concept;

/**
 * @author diepdt
 *
 */
public class Senvn {
	private long id = 0;
	//private Recvn record;
	private double recordId = 0;
	private String content = "";
	private String section = "";
	private List<Concept> concept = new ArrayList<Concept>();
	private long handle = 0;

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

/*	*//**
	 * @return the record
	 *//*
	public Recvn getRecord() {
		return record;
	}

	*//**
	 * @param record
	 *            the record to set
	 *//*
	public void setRecord(Recvn record) {
		this.record = record;
	}*/

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
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * @param section
	 *            the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}

	/**
	 * @return the handle
	 */
	public long getHandle() {
		return handle;
	}

	/**
	 * @param handle
	 *            the handle to set
	 */
	public void setHandle(long handle) {
		this.handle = handle;
	}

	/**
	 * @return the concept
	 */
	public List<Concept> getConcept() {
		return concept;
	}

	/**
	 * @param concept
	 *            the concept to set
	 */
	public void setConcept(List<Concept> concept) {
		this.concept = concept;
	}

	/**
	 * @return the recordId
	 */
	public double getRecordId() {
		return recordId;
	}

	/**
	 * @param recordId the recordId to set
	 */
	public void setRecordId(double recordId) {
		this.recordId = recordId;
	}
}
