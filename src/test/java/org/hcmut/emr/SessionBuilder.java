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
package org.hcmut.emr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sinhlk
 *
 */
public class SessionBuilder {
	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"/home/sinhlk/myspace/emr/src/main/resources/patern"))) {
			ObjectMapper jsonMapper = new ObjectMapper();
			String line = br.readLine();
			Map<String, String> result = new HashMap<String, String>();
			List<NameValuePair> list = new ArrayList<>();

			while (line != null) {
				if (line != null && line != "") {
					list.add(new BasicNameValuePair(line.trim().toLowerCase(), SessionBuilder
							.buildValue(line)));
					result.put(line.trim().toLowerCase(), SessionBuilder.buildValue(line));
					line = br.readLine();
				}
			}
			System.out.println(jsonMapper.writeValueAsString(list));
			File file = new File(
					"/home/sinhlk/myspace/emr/src/main/resources/session.js");
			jsonMapper.writeValue(file, list);
		}

	}

	public static String buildValue(String content) {
		content = content.trim();
		String[] str = content.split(" ");
		if (str.length == 2) {
			return str[0].substring(0, 3);
		} else {
			String result = "";
			for (int i = 0; i < str.length - 1; i++) {
				String current = (str[i].length() > 2) ? str[i].trim()
						.substring(0, 2) : str[i].trim().substring(0, 1);
				result = result + current;
			}
			return result;
		}
	}
}
