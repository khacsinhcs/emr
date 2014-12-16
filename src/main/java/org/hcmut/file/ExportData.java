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
package org.hcmut.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.hcmut.emr.word.Word;
import org.hcmut.emr.worvn.Worvn;

/**
 * @author sinhlk
 *
 */
public class ExportData {
	private BufferedWriter out;
	private FileWriter fileWriter;
	private LineWriter writer;

	public ExportData(String path, LineWriter writer) throws IOException {
		super();
		createOutWriter(path);
		this.writer = writer;
	}

	public void writeLine(Word word) throws IOException {
		out.append(writer.write(word));
		out.newLine();
	}

	public void writeLineVn(Worvn word) throws IOException {
		out.append(writer.writeVn(word));
		out.newLine();
	}

	public void newLine() throws IOException {
		out.newLine();
	}

	public void finish() throws IOException {
		out.close();
		fileWriter.close();
	}

	private void createOutWriter(String path) throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}

		fileWriter = new FileWriter(file);
		out = new BufferedWriter(fileWriter);
		out.write("");

		fileWriter.close();
		out.close();

		fileWriter = new FileWriter(file, true);
		out = new BufferedWriter(fileWriter);
	}

	public interface LineWriter {
		public String write(Word word);

		public String writeVn(Worvn word);
	}
}
