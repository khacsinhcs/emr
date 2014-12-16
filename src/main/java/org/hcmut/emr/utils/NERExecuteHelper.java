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
package org.hcmut.emr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Random;

/**
 * @author sinhlk
 *
 */
public class NERExecuteHelper {
	private static String context;
	private static String basePath = "/home/emrie/crf/build";

	public String execute() {
		try {
			String fileInputName = writeInputFile(context);
			String shFilePath = writeShFile(fileInputName);
			String pathCmd = "sh " + shFilePath;
			String result = executeCommand(pathCmd);
			return readOutput(result);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		NERExecuteHelper demo = new NERExecuteHelper();
		demo.setContext("Blood type A positive , antibody negative , rubella immune , RPR nonreactive , hepatitis B surface antigen negative , group beta Strep status unknown .");
		demo.execute();
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context
	 *            the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	private String readOutput(String context) {
		context = context.replace("\t", "--");
		context = context.replace("\n", " ");
		context = context.replace("  ", "\n");
		System.out.println(context);
		return context;
	}

	/**
	 * @param fileInputName
	 * @return
	 * @throws IOException
	 */
	private String writeShFile(String fileInputName) throws IOException {
		String filepath = buildShFilePath();
		BufferedWriter out = createOutWriter(filepath);
		out.append("#!/bin/sh");
		out.newLine();
		out.append("cd /home/emrie/crf/build/shfile\n");
		out.append("../crf_test -m /home/emrie/crf/build/model/model "
				+ fileInputName);
		out.close();
		return filepath;
	}

	private String writeInputFile(String context) throws IOException {
		String filePath = buildInputFilePath();
		BufferedWriter out = createOutWriter(filePath);
		String[] sentences = context.split("\n");

		for (String sentence : sentences) {
			String[] words = sentence.split(" ");
			for (String word : words) {
				out.append(word);
				out.newLine();
			}
			out.newLine();
		}
		out.close();

		return filePath;
	}

	private String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

	private BufferedWriter createOutWriter(String filepath) throws IOException {
		File file = new File(filepath);
		System.out.println(filepath);
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fileWriter = new FileWriter(file, true);

		BufferedWriter out = new BufferedWriter(fileWriter);
		return out;
	}

	private String buildInputFilePath() {
		Date date = new Date();

		Random ran = new Random();
		int current = ran.nextInt();

		String fileName = basePath + "/data/" + date.getTime() + "-"
				+ current + ".data";
		return fileName;
	}

	private String buildShFilePath() {
		Date date = new Date();

		Random ran = new Random();
		int current = ran.nextInt();

		String fileName = basePath + "/shfile/" + date.getTime() + "-"
				+ current + ".sh";
		return fileName;
	}
}
