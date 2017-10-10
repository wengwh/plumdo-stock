package com.plumdo.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	public static final String FORMAT_DATE = "yyyy-MM-dd";
	public static final String FORMAT_TIME = "HH:mm:ss";
	public static final String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_DATETIME_CHINA = "yyyy年MM月dd日 HH:mm:ss";

	public static SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat(FORMAT_DATE);
	}

	public static SimpleDateFormat getTimeFmt() {
		return new SimpleDateFormat(FORMAT_TIME);
	}
	
	public static SimpleDateFormat getDateTimeFmt() {
		return new SimpleDateFormat(FORMAT_DATETIME);
	}
	
	public static SimpleDateFormat getDateTimeCHFmt() {
		return new SimpleDateFormat(FORMAT_DATETIME_CHINA);
	}
	
	public static SimpleDateFormat getTimestampFmt() {
		return new SimpleDateFormat(FORMAT_TIMESTAMP);
	}
	
	public static Date newDateByDay(int day) {
		return newDateByDay(newDate(), day);
	}

	public static Date newDateByDay(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	public static Date newDateBySecond(int second) {
		return newDateBySecond(newDate(), second);
	}

	public static Date newDateBySecond(Date d, int second) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
		return now.getTime();
	}

	public static Date newDate() {
		return new Date();
	}

	public static Long currentTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public static Timestamp currentTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public static Date getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
	public static Date getYesterdayOutWeek() {
		if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) != 2) {
			return newDateByDay(getCurrentDay(), -1);
		} else {
			return newDateByDay(getCurrentDay(), -3);
		}
	}

	public static long getTimeConsume(long begintime) {
		return System.currentTimeMillis() - begintime;
	}

	public static String currentDateTimeCHStr() {
		return getDateTimeCHFmt().format(currentTimestamp());
	}
	
	public static String currentDateTimeStr() {
		return getDateTimeFmt().format(currentTimestamp());
	}
	
	public static String currentTimestampStr() {
		return getTimestampFmt().format(currentTimestamp());
	}

	public static String currentTimeStr() {
		return getTimeFmt().format(currentTimestamp());
	}

	public static String formatTimestamp(Date date) {
		if (date == null) {
			return null;
		}
		return getTimestampFmt().format(date);
	}
	
	public static String formatDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return getDateTimeFmt().format(date);
	}
	
	public static String formatCHDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return getDateTimeCHFmt().format(date);
	}

	public static Date parseTime(String date) {
		try {
			if (date != null) {
				return getTimeFmt().parse(date);
			}
		} catch (ParseException e) {
		}
		return null;
	}

	
	public static Date parseDateTime(String date) {
		return parseDateTime(date, null);
	}

	public static Date parseDateTime(String date, Date defaultDate) {
		try {
			if (date != null) {
				return getDateTimeFmt().parse(date);
			}
		} catch (ParseException e) {
		}
		return defaultDate;
	}
	public static Timestamp parseTimestamp(String date) {
		return parseTimestamp(date, null);
	}
	
	public static Timestamp parseTimestamp(String date, Timestamp defaultDate) {
		try {
			if (date != null) {
				return Timestamp.valueOf(date);
			}
		} catch (Exception e) {
		}
		return defaultDate;
	}

	public static String hourToTime(int hour) {
		return String.format("%02d:00:00", hour);
	}

	public static long minuteDifference(Date date1, Date date2) {
		return timeDifference(date1, date2) / (60);
	}

	public static long timeDifference(Date date1, Date date2) {
		long diff = date1.getTime() - date2.getTime();
		long second = diff / 1000;
		return second;
	}

	public static boolean betweenTimes(String compareTime, String beginTimeStr, String endTimeStr) {
		try {
			Date nowTime = DateUtils.parseTime(compareTime);
			Date beginTime = null;
			Date endTime = null;
			if (ObjectUtils.isNotEmpty(beginTimeStr)) {
				beginTime = DateUtils.parseTime(beginTimeStr);
			}
			if (ObjectUtils.isNotEmpty(endTimeStr)) {
				endTime = DateUtils.parseTime(endTimeStr);
				// 结束时间和开始时间做个比较（19.00.00 —— 2.00.00，结束时间需要加上1天 ）
				if (beginTime != null && endTime != null && endTime.before(beginTime)) {
					endTime = DateUtils.newDateByDay(endTime, 1);
				}
			}

			if (beginTime != null && nowTime.before(beginTime)) {
				return false;
			}
			if (endTime != null && nowTime.after(endTime)) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
