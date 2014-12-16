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
package com.molisys.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author sinhlk
 *
 */
public class DateTimeHelper {

	private static DateTimeHelper instant;
	private String format = "yyyy-MM-dd";
	private SimpleDateFormat formatter;
	private SimpleDateFormat customerFormatter;

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
		formatter.applyPattern(format);
	}

	private DateTimeHelper() {
		customerFormatter = new SimpleDateFormat(format, Locale.US);
		formatter = new SimpleDateFormat(format, Locale.US);
	}

	/**
	 * @return the simpleFormat
	 */
	public SimpleDateFormat getFormatter() {
		if (formatter != null) {
			formatter = new SimpleDateFormat(format, Locale.US);
		}
		return formatter;
	}

	public static DateTimeHelper factory() {
		if (instant == null) {
			instant = new DateTimeHelper();
		}
		return instant;
	}

	/**
	 * parse date object to string in format
	 * 
	 * @param date
	 *            to parse
	 * @return
	 */
	public String parse(Date date) {
		if (date == null) {
			return "";
		}
		return this.formatter.format(date);
	}

	/**
	 * parse string to date
	 * 
	 * @param time
	 *            to parse
	 * @param format
	 *            current time format
	 * @return
	 * @throws ParseException
	 *             if time is incorrect format
	 */
	public Date parse(String time, String format) throws ParseException {
		return convertDateFormat(time, format);
	}

	/**
	 * parse string to date
	 * 
	 * @param time
	 *            to parse
	 * @return
	 * @throws ParseException
	 *             if time is incorrect format
	 */
	public Date parse(String time) throws ParseException {
		return this.getFormatter().parse(time);
	}

	/**
	 * parse a string have many date like "2014/11/23 - 2013/1/22"
	 * 
	 * @param time
	 *            to parse
	 * @param format
	 *            format of each date in time
	 * @param separation
	 *            in this case, it is "-"
	 * @return list date
	 * @throws ParseException
	 */
	public List<Date> parseListDate(String time, String format,
			String separation) throws ParseException {
		ArrayList<Date> list = new ArrayList<Date>();
		String[] dates = time.split(separation);

		for (String date : dates) {
			list.add(this.parse(date.concat(date), format));
		}
		return list;
	}

	/**
	 * parse a string have many date like "2014/11/23 - 2013/1/22"
	 * 
	 * @param time
	 *            to parse
	 * @param format
	 *            format of each date in time
	 * @param separation
	 *            in this case, it is "-"
	 * @return list date
	 * @throws ParseException
	 */
	public List<Date> parseListDate(String time, String separation)
			throws ParseException {
		return parseListDate(time, this.format, separation);
	}

	/**
	 * Change current format to system format
	 * 
	 * @param time
	 *            time to cover
	 * @param format
	 *            datetime format
	 * @throws ParseException
	 */
	private Date convertDateFormat(String time, String format)
			throws ParseException {
		customerFormatter.applyPattern(format);
		Date date = customerFormatter.parse(time);
		return date;
	}
	
	public String getCurrentDateOnString() {
		Date date = new Date();
		return this.parse(date);
	}
}
