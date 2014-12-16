package org.hcmut.emr.config;

public class AppConfig {
	private int recordPos = 0;
	private String extractDataPath = null;
	private String modelVNPath = null;
	private String modelENPath = null;
	private String javaCRFLibPath = null;
	private String resourcePathVNLib = null;

	/**
	 * @return the recordPos
	 */
	public int getRecordPos() {
		return recordPos;
	}

	/**
	 * @param recordPos the recordPos to set
	 */
	public void setRecordPos(int recordPos) {
		this.recordPos = recordPos;
	}

	/**
	 * @return the extractDataPath
	 */
	public String getExtractDataPath() {
		return extractDataPath;
	}

	/**
	 * @param extractDataPath the extractDataPath to set
	 */
	public void setExtractDataPath(String extractDataPath) {
		this.extractDataPath = extractDataPath;
	}

	/**
	 * @return the modelVNPath
	 */
	public String getModelVNPath() {
		return modelVNPath;
	}

	/**
	 * @param modelVNPath the modelVNPath to set
	 */
	public void setModelVNPath(String modelVNPath) {
		this.modelVNPath = modelVNPath;
	}

	/**
	 * @return the modelENPath
	 */
	public String getModelENPath() {
		return modelENPath;
	}

	/**
	 * @param modelENPath the modelENPath to set
	 */
	public void setModelENPath(String modelENPath) {
		this.modelENPath = modelENPath;
	}

	/**
	 * @return the resourcePathVNLib
	 */
	public String getResourcePathVNLib() {
		return resourcePathVNLib;
	}

	/**
	 * @param resourcePathVNLib the resourcePathVNLib to set
	 */
	public void setResourcePathVNLib(String resourcePathVNLib) {
		this.resourcePathVNLib = resourcePathVNLib;
	}

	/**
	 * @return the javaCRFLibPath
	 */
	public String getJavaCRFLibPath() {
		return javaCRFLibPath;
	}

	/**
	 * @param javaCRFLibPath the javaCRFLibPath to set
	 */
	public void setJavaCRFLibPath(String javaCRFLibPath) {
		this.javaCRFLibPath = javaCRFLibPath;
	}
}
