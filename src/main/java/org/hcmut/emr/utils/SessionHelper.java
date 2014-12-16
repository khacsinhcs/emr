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
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.hcmut.emr.SessionBuilder;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author sinhlk
 *
 */
public class SessionHelper {

	public static List<NameValuePair> getListSession()
			throws JsonParseException, JsonMappingException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(
				"/home/sinhlk/myspace/emr/src/main/resources/patern"))) {
			String line = br.readLine();
			List<NameValuePair> list = new ArrayList<>();

			while (line != null) {
				if (line != null && line != "") {
					list.add(new BasicNameValuePair(line.trim().toLowerCase(),
							buildValue(line)));
					line = br.readLine();
				}
			}
			return list;
		} catch (Exception e) {
			return new ArrayList<>();
		}

	}

	private static String buildValue(String content) {
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
