package org.yood.commons.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 农历生成器，根据公元日期生成农历的日期
 * 
 * @author 袁慎建
 * @version 1.0
 * @email ysjian_pingcx@126.com
 * @QQ 646633781
 * @telephone 18192235667
 * @csdnBlog http://blog.csdn.net/ysjian_pingcx
 * @createTime 2013-10-24 上午8:44:04
 * @copyRight 西安美林电子有限责任公司
 */
public class LunarUtils {

	/**
	 * 农历信息
	 */
	private static final long[] LUNAR_INFO = new long[] { 0x04bd8, 0x04ae0,
			0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
			0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
			0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
			0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
			0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
			0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
			0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
			0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
			0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
			0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
			0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
			0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
			0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
			0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
			0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
			0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
			0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
			0x0ada0 };

	/**
	 * 一周的天数，"日", "一", "二", "三", "四", "五", "六"
	 */
	private static final List<String> WEEKDAYS = new ArrayList<String>();

	/**
	 * 农历月份，"正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
	 */
	private static final List<String> LUNAR_MONTH = new ArrayList<String>();

	/**
	 * 阿拉伯数字对应的汉语小写，"零","一", "二", "三", "四", "五", "六", "七", "八", "九"
	 */
	private static final List<String> NUMBERS = new ArrayList<String>();

	/**
	 * 阳历月份，"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"
	 */
	private static final List<String> SOLAR_MONTH = new ArrayList<String>();

	/**
	 * 十二生肖，"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"
	 */
	private static final List<String> ANIMALS = new ArrayList<String>();

	/**
	 * 十天干，"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"
	 */
	private static final List<String> GAN = new ArrayList<String>();

	/**
	 * 十二地支，"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"
	 */
	private static final List<String> ZHI = new ArrayList<String>();

	/**
	 * 农历没十天一个的单位表示，"初", "十", "廿", "卅"
	 */
	private static final List<String> DAY_DECADES = new ArrayList<String>();

	static {
		Collections.addAll(WEEKDAYS, "", "\u65E5", "\u4E00", "\u4E8C",
				"\u4E09", "\u56DB", "\u4E94", "\u516D");
		Collections.addAll(LUNAR_MONTH, "", "\u6B63", "\u4E8C", "\u4E09",
				"\u56DB", "\u4E94", "\u516D", "\u4E03", "\u516B", "\u4E5D",
				"\u5341", "\u51AC", "\u814A");
		Collections.addAll(SOLAR_MONTH, "", "\u4E00", "\u4E8C", "\u4E09",
				"\u56DB", "\u4E94", "\u516D", "\u4E03", "\u516B", "\u4E5D",
				"\u5341", "\u5341\u4E00", "\u5341\u4E8C");
		Collections.addAll(ANIMALS, "\u9F20", "\u725B", "\u864E", "\u5154",
				"\u9F99", "\u86C7", "\u9A6C", "\u7F8A", "\u7334", "\u9E21",
				"\u72D7", "\u732A");
		Collections.addAll(GAN, "\u7532", "\u4E59", "\u4E19", "\u4E01",
				"\u620A", "\u5DF1", "\u5E9A", "\u8F9B", "\u58EC", "\u7678");
		Collections.addAll(ZHI, "\u5B50", "\u4E11", "\u5BC5", "\u536F",
				"\u8FB0", "\u5DF3", "\u5348", "\u672A", "\u7533", "\u9149",
				"\u620C", "\u4EA5");
		Collections.addAll(DAY_DECADES, "\u521D", "\u5341", "\u5EFF", "\u5345");
		Collections.addAll(NUMBERS, "\u96F6", "\u4E00", "\u4E8C", "\u4E09",
				"\u56DB", "\u4E94", "\u516D", "\u4E03", "\u516B", "\u4E5D");
	}

	/**
	 * Private constructor,prevent to create instance
	 */
	private LunarUtils() {

	}

	/**
	 * Return the lunar year of the year
	 * 
	 * @param year
	 *            the year
	 * @return <code>year</code>the lunar year
	 */
	public static final int daysOfLunarYear(int year) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((LUNAR_INFO[year - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + daysOfLeapMonth(year));
	}

	/**
	 * Return the days of the leap month of the year
	 * 
	 * @param year
	 *            the year
	 * @return <code>year</code>the days of the leap month
	 */
	public static int daysOfLeapMonth(int year) {
		if (leapMonthOfLunarYear(year) != 0) {
			if ((LUNAR_INFO[year - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	/**
	 * Return the lunar date of current date
	 * 
	 * @return the lunar date
	 */
	public static String currentLunarDate() {
		Calendar c = Calendar.getInstance();
		return getLunarDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DATE));
	}

	public static String currentLunarYear() {
		return getLunarYear((int) currentLunarElement()[0]);
	}

	public static String currentLunarMonth() {
		return LUNAR_MONTH.get((int) currentLunarElement()[1]);
	}

	public static String currentLunarDay() {
		return getLunarDay((int) (currentLunarElement()[2]));
	}

	/**
	 * Return the lunar year of the year
	 * 
	 * @param year
	 *            the year
	 * @return the lunar year
	 */
	public static String getLunarYear(int year) {
		StringBuilder sb = new StringBuilder();
		for (char ch : String.valueOf(year).toCharArray()) {
			sb.append(NUMBERS.get(Integer.parseInt(String.valueOf(ch))));
		}
		return sb.toString();
	}

	/**
	 * Return the leap month of the year
	 * 
	 * @param year
	 *            the year
	 * @return if the <code>year</code>has leap month then return the month,else
	 *         return zero
	 */
	public static int leapMonthOfLunarYear(int year) {
		return (int) (LUNAR_INFO[year - 1900] & 0xf);
	}

	/**
	 * Return the days of the month in the year
	 * 
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @return the days
	 */
	public static int daysOfMonth(int year, int month) {
		return 0 == (LUNAR_INFO[year - 1900] & (0x10000 >> month)) ? 29 : 30;
	}

	/**
	 * Return the representing animal of the month
	 * 
	 * @param year
	 *            the year
	 * @return an animal of the twelve animals
	 */
	public static String animalOfYear(int year) {
		return ANIMALS.get((year - 4) % 12);
	}

	public static String currentAnimal() {
		return animalOfYear(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * Return the Chinese era
	 * 
	 * @param the
	 *            year
	 * @return the Chinese ear of the year
	 */
	public static String getChineseEar(int year) {
		int num = year - 1900 + 36;
		return (GAN.get(num % 10) + ZHI.get(num % 12));
	}

	public static String currentChineseEar() {
		return getChineseEar(Calendar.getInstance().get(Calendar.YEAR));
	}

	/**
	 * Return the lunar year,month and day of the year,month and day
	 * 
	 * @param year
	 *            the year
	 * @param month
	 *            the month
	 * @param day
	 *            day
	 * @return the lunar year,month and day
	 */
	@SuppressWarnings("deprecation")
	public static long[] getLunarElement(int year, int month, int day) {
		long[] nongDate = new long[7];
		int i = 0, temp = 0, leap = 0;
		Date baseDate = new Date(0, 0, 31);
		Date objDate = new Date(year - 1900, month - 1, day);
		long offset = (objDate.getTime() - baseDate.getTime()) / 86400000L;
		nongDate[5] = offset + 40;
		nongDate[4] = 14;
		for (i = 1900; i < 2050 && offset > 0; i++) {
			temp = daysOfLunarYear(i);
			offset -= temp;
			nongDate[4] += 12;
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4] -= 12;
		}
		nongDate[0] = i;
		nongDate[3] = i - 1864;
		leap = leapMonthOfLunarYear(i);
		nongDate[6] = 0;
		for (i = 1; i < 13 && offset > 0; i++) {
			if (leap > 0 && i == (leap + 1) && nongDate[6] == 0) {
				i--;
				nongDate[6] = 1;
				temp = daysOfLeapMonth((int) nongDate[0]);
			} else {
				temp = daysOfMonth((int) nongDate[0], i);
			}
			if (nongDate[6] == 1 && i == (leap + 1))
				nongDate[6] = 0;
			offset -= temp;
			if (nongDate[6] == 0)
				nongDate[4]++;
		}
		if (offset == 0 && leap > 0 && i == leap + 1) {
			if (nongDate[6] == 1) {
				nongDate[6] = 0;
			} else {
				nongDate[6] = 1;
				i--;
				nongDate[4]--;
			}
		}
		if (offset < 0) {
			offset += temp;
			i--;
			nongDate[4]--;
		}
		nongDate[1] = i;
		nongDate[2] = offset + 1;
		return nongDate;
	}

	public static long[] currentLunarElement() {
		Calendar c = Calendar.getInstance();
		return getLunarElement(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1,
				c.get(Calendar.DATE));
	}

	/**
	 * Return the lunar month of the month
	 * 
	 * @param month
	 *            the month
	 * @return the lunar month
	 * 
	 * @exception IllegalArgumentException
	 *                if the month below zero or month above twelve
	 */
	public static String getLunarMonth(int month) {
		if (month < 0 || month > 12) {
			throw new IllegalArgumentException(
					"the month is an invalid argument-->" + month);
		}
		return LUNAR_MONTH.get(month);
	}

	/**
	 * Return the lunar day of the day
	 * 
	 * @param the
	 *            day
	 * @return the lunar day
	 */
	public static String getLunarDay(int day) {
		String firstChar = null;
		if (10 == day) {
			return "\u521D\u5341";
		}
		int decade = (int) ((day) / 10);
		firstChar = DAY_DECADES.get(decade);
		int unit = (int) (day % 10);
		firstChar += SOLAR_MONTH.get(unit);
		return firstChar;
	}

	/**
	 * Return the lunar date according solar calendar
	 * 
	 * @param year
	 *            solar calendar year
	 * @param month
	 *            solar calendar month
	 * @param day
	 *            solar calendarday
	 * @return the lunar date
	 */
	public static String getLunarDate(int year, int month, int day) {
		long[] lunarDate = getLunarElement(year, month, day);
		String lunarYear = getLunarYear((int) (lunarDate[0]));
		String lunarMonth = LUNAR_MONTH.get((int) (lunarDate[1]));
		String lunarDay = getLunarDay((int) (lunarDate[2]));
		return lunarYear + "\u5E74" + lunarMonth + "\u6708" + lunarDay;
	}
}
