package com.ndquangr.qatv.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public final class DateUtil {

    /**
     * 현재시간을 포맷에 따라 반환 
     * @param format -  String ("yyyy-MM-dd HH:mm:ss")
     * @return String 
     * ex) String today = DateUtil.getToday("yyyy-MM-dd");
     *     today -> "2012-03-16"
     */
	public static String getToday(String format){
		if(format == null || format.equals("")) format = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
    /**
     * 현재시간을 배열로 반환 
     * @return String[] 
     * ex) String[] today = DateUtil.getToday();
     *     today[0] -> 년도("2012")
     *     today[1] -> 월("03")
     *     today[2] -> 일("16")
     */	
	public static String[] getToday(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String today = sdf.format(new Date());
		return CommonUtil.toArray(today, "-");
	}
	
	//현재년도(yyyy)
	public static String getYear(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		return sdf.format(new Date());
	}
	//현재월(MM)
	public static String getMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		return sdf.format(new Date());
	}
	//현재일(dd)
	public static String getDay(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		return sdf.format(new Date());
	}
	
    /**
     * 특정년/월의 마지막 날자(문자열)를 반환(28~31);
     * @param year - String : 년도(yyyy)
     * @param month - String : 월(MM)
     * @return String - 마지막날
     */	
	public static String getLastDay(String year, String month){
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(year), Integer.parseInt(month), 1);//다음달 1일
		cal.add(Calendar.DATE, -1);
		int last = cal.get(Calendar.DATE);
		return last+"";
	}
	
    /**
     * 특정일에 대해 년/월/일을 더하거나 빼서 계산
     * @param date - String : 일자(yyyyMMdd,yyyy-MM-dd형태 - 20120316,2012-03-16) 
     *             - CommonUtil.stripExp를 이용 regex를 제거한 값
     * @param format - String : 반환날짜의 구분자 
     *             : "-" -> "2012-03-16"
     *             : ""  -> "20120316"
     * @param field - String : 년/월/일구분(YEAR|MONTH|DAY)
     * @param amount - int : 증/감분
     * @return String - 계산된 날짜
     */		
	public static String addDate(String date, String format, String field, int amount){
		String newDate = "";
		int yy = 0;
		int mm = 0;
		int dd = 0;
		if(date != null && (date.trim().length() == 8 || date.trim().length() == 10)){
			//기준일자
			if(date.trim().length() == 8){
				yy = Integer.parseInt(date.substring(0, 4));
				mm = Integer.parseInt(date.substring(4, 6)) - 1;//calendar의 월은 (0 ~ 11)
				dd = Integer.parseInt(date.substring(6, 8));
			}else if(date.trim().length() == 10){
				yy = Integer.parseInt(date.substring(0,4));
				mm = Integer.parseInt(date.substring(5, 7)) - 1;//calendar의 월은 (0 ~ 11)
				dd = Integer.parseInt(date.substring(8, 10));
			}
			Calendar cal = Calendar.getInstance();
			cal.set(yy, mm, dd);
			//계산
			if(field.equals("YEAR")){
				cal.add(Calendar.YEAR, amount);
			}else if(field.equals("MONTH")){
				cal.add(Calendar.MONTH, amount);
			}else if(field.equals("DAY")){
				cal.add(Calendar.DATE, amount);
			}
			//계산된일자
			yy = cal.get(Calendar.YEAR);
			mm = (cal.get(Calendar.MONTH) + 1);
			dd = cal.get(Calendar.DATE);
			newDate = yy + format + ((mm < 10)?"0"+mm:mm) + format + ((dd < 10)?"0"+dd:dd);
		}
		return newDate;
	}
	
	  public static String[][] getDayOfWeek(int year, int month, int day)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, day);
	    int weekPos = cal.get(7);
	    cal.add(5, -(weekPos - 1));
	    String[][] weeks = new String[7][4];
	    int mm = 0;
	    int dd = 0;
	    for (int i = 0; i < 7; i++) {
	      cal.add(5, i);
	      mm = cal.get(2) + 1;
	      dd = cal.get(5);
	      weeks[i][0] = cal.get(1)+"";
	      weeks[i][1] = String.format("%02d", mm);
	      weeks[i][2] = String.format("%02d", dd);
	      weeks[i][3] = cal.get(7)+"";
	      cal.add(5, -i);
	    }
	    return weeks;
	  }
	  
	  public static String[][] getOutDayOfWeek(int year, int month, int day)
	  {
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, day);
	    int weekPos = cal.get(7);

	    String[][] weeks = new String[2][4];

	    cal.add(5, -(weekPos + 1));
	    int mm = cal.get(2) + 1;
	    int dd = cal.get(5);
	    weeks[0][0] = cal.get(1)+"";
	    weeks[0][1] = mm+"";
	    weeks[0][2] = dd+"";
	    weeks[0][3] = cal.get(7)+"";
	    cal.add(5, weekPos + 1);

	    cal.add(5, 7 - weekPos + 6);
	    mm = cal.get(2) + 1;
	    dd = cal.get(5);
	    weeks[1][0] = cal.get(1)+"";
	    weeks[1][1] = mm+"";
	    weeks[1][2] = dd+"";
	    weeks[1][3] = cal.get(7)+"";

	    return weeks;
	  }
	  
	  public static String[] getWeek(int year, int month, int day)
	  {
	    String[] weekDate = new String[4];
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, day);

	    int weekPos = cal.get(7);
	    cal.add(5, 6 - weekPos);

	    weekDate[0] = cal.get(1)+"";
	    weekDate[1] = (cal.get(2) + 1)+"";
	    weekDate[2] = cal.get(5)+"";
	    weekDate[3] = cal.get(4)+"";
	    return weekDate;
	  }
	  
	  public static List getWeeks(int year, int month)
	  {
	    List weeks = new ArrayList();
	    Map week = null;
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, 1);
	    int lastDay = cal.getMaximum(5);
	    int weekPos = -1;
	    int mm = 0;
	    int dd = 0;
	    for (int i = 1; i <= lastDay; i += 7) {
	      cal.set(year, month - 1, i);
	      weekPos = cal.get(7);
	      cal.add(5, 6 - weekPos);
	      if (month == cal.get(2) + 1) {
	        mm = cal.get(2) + 1;
	        dd = cal.get(5);
	        week = new HashMap();
	        week.put("YEAR", cal.get(1));
	        //week.put("MONTH", mm);
	        week.put("MONTH", String.format("%02d", mm));
	        //week.put("DATE", dd);
	        week.put("DATE", String.format("%02d", dd));
	        week.put("WEEK", cal.get(4));
	        weeks.add(week);
	      }
	    }

	    return weeks;
	  }
	  
	  public static String[] getSEDayOfWeek(int year, int month, int day)
	  {
	    String[] weeks = new String[2];
	    int yy = 0;
	    int mm = 0;
	    int dd = 0;
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, day);
	    int weekPos = cal.get(7);
	    cal.add(5, -(weekPos - 1));
	    yy = cal.get(1);
	    mm = cal.get(2) + 1;
	    dd = cal.get(5);
	    weeks[0] = (yy + (mm < 10 ? "0" + mm : new StringBuilder().append(mm).toString()) + (dd < 10 ? "0" + dd : new StringBuilder().append(dd).toString()));
	    cal.add(5, 6);
	    yy = cal.get(1);
	    mm = cal.get(2) + 1;
	    dd = cal.get(5);
	    weeks[1] = (yy + (mm < 10 ? "0" + mm : new StringBuilder().append(mm).toString()) + (dd < 10 ? "0" + dd : new StringBuilder().append(dd).toString()));
	    return weeks;
	  }

	  public static String[] getSEDayOfWeek2(int year, int month, int day)
	  {
	    String[] weeks = new String[2];
	    int yy = 0;
	    int mm = 0;
	    int dd = 0;
	    Calendar cal = Calendar.getInstance();
	    cal.set(year, month - 1, day);
	    int weekPos = cal.get(7);
	    cal.add(5, -(weekPos + 1));
	    yy = cal.get(1);
	    mm = cal.get(2) + 1;
	    dd = cal.get(5);
	    weeks[0] = (yy + (mm < 10 ? "0" + mm : new StringBuilder().append(mm).toString()) + (dd < 10 ? "0" + dd : new StringBuilder().append(dd).toString()));
	    cal.add(5, 6);
	    yy = cal.get(1);
	    mm = cal.get(2) + 1;
	    dd = cal.get(5);
	    weeks[1] = (yy + (mm < 10 ? "0" + mm : new StringBuilder().append(mm).toString()) + (dd < 10 ? "0" + dd : new StringBuilder().append(dd).toString()));
	    return weeks;
	  }
	  
	  
}
