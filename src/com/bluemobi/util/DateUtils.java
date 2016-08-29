package com.bluemobi.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * <p>Title:DateUtils </p>
 * <p>Description: 日期处理工具类 </p>
 * <p>Company: </p> 
 * @author hezhanwei
 * @date 2016年8月26日 上午10:47:26
 */
public class DateUtils {
	
	private static  Logger log = Logger.getLogger(DateUtils.class);
	
	/** 时间格式化 格式 ：yyyy-MM-dd HH:mm:ss */
	public static final String FORMAT_DEFAULT_DATE = "yyyy-MM-dd HH:mm:ss";
	
	/** 时间格式化 格式 ：yyyy-MM-dd */
	public static final String FORMAT_YYMMDD_DATE = "yyyy-MM-dd";
	
	private static SimpleDateFormat dateformat1=new SimpleDateFormat(FORMAT_DEFAULT_DATE);
	
	private static SimpleDateFormat dateYYMMDD=new SimpleDateFormat(FORMAT_YYMMDD_DATE);
	
	private static SimpleDateFormat dateyymmdd = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 
	 * 功能说明:【获取时间】
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public static Date formatDate(String dateStr,String formatStr){
		DateFormat dd = new SimpleDateFormat(formatStr);
		try {
			return dd.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 描述:将数字时间转换为格式化时间<br/>
	 * @param time	时间
	 * @param formatStr 格式化
	 * @return
	 */
	public static String timestampToDate(Long time, String formatStr) {
		String str = null;
		if (null != time) {
			DateFormat dd = new SimpleDateFormat(formatStr);
			Date date = new Date(time);
			str = dd.format(date);
		}
		return str;
	}
	
	/**
	 * 描述:将时间格式化<br/>
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateFormat(Date date,String formatStr){
		if(null==date)return "";
		DateFormat dd = new SimpleDateFormat(formatStr);
		return dd.format(date);
	}

	/**
	 * 描述:获取当前服务器格式化时间<br/>
	 * @param formatStr
	 * @return
	 */
	public static String getDate(String formatStr) {
		DateFormat dd = new SimpleDateFormat(formatStr);
		return dd.format(new Date());
	}
	
	/**
	 * 描述:获取默认时间 
	 * @return yyyyMMddHHmmss
	 */
	public static String getDateDefaults() {
		return dateyymmdd.format(new Date());
	}
	
	/**
	 * 描述:获取默认时间 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getDateDefault() {
		return dateformat1.format(new Date());
	}
	
	/**
	 * 描述:获取默认时间 
	 * @return yyyy-MM-dd
	 */
	public static String getDateYYMMDD() {
		return dateYYMMDD.format(new Date());
	}
	
	/**
	 * 功能说明:【描述信息】
	 * @param format  格式化
	 * @return 返回毫秒数
	 */
	 public static String getCurrentDate(String format) {
        String tDate = new SimpleDateFormat(format).format(
        		new Date(System.currentTimeMillis()));
        return tDate;
	 }
	
	/**
	 * 
	 * <获取当前时间(Timestamp格式)>
	 * 
	 * @return
	 */
	public static Timestamp getCurrentDate2Timestamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**   
     * 计算两个日期之间相差的天数   
     * @param date1   
     * @param date2   
     * @return   
     */    
    public static int daysBetween(Date beforeDate,Date afterDate)
    {     
    	 long quot = 0;
    	  SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");
    	  try {
	    	   Date date1 = ft.parse(ft.format(beforeDate));
	    	   Date date2 = ft.parse(ft.format(afterDate));
	    	   quot = date2.getTime() - date1.getTime();
	    	   quot = quot / 1000 / 60 / 60 / 24;
    	  } catch (ParseException e) {
    		  log.error("beforeDate="+beforeDate+" |afterDate="+afterDate,e);
    	  }
    	  return Integer.parseInt(String.valueOf(quot));
    }
    
    /**
     * 描述: 时间加天
     * @param baseDate
     * @param plusDays
     * @return
     */
    public static String datePlus4Day(Date baseDate,Integer plusDays){
   	 Calendar cal = Calendar.getInstance();     
        cal.setTime(baseDate);
        cal.add(Calendar.DATE, plusDays);
        
        return dateYYMMDD.format(cal.getTime());
   }
    
    /**
     * 描述: 时间加星期
     * @param baseDate
     * @param plusDays
     * @return
     */
    public static Date datePlus4Week(Date baseDate,Integer plusDays){
    	 Calendar cal = Calendar.getInstance();     
         cal.setTime(baseDate);
         cal.add(Calendar.DAY_OF_WEEK, plusDays);
         
         return cal.getTime();
    }
    
    /**
     *  描述: 时间加月
     * @param baseDate
     * @param plusMonths
     * @return
     */
    public static Date datePlus4Month(Date baseDate,Integer plusMonths){
    	Calendar cal = Calendar.getInstance();     
        cal.setTime(baseDate);
        cal.add(Calendar.MONTH, plusMonths);
        
        return cal.getTime();
    }
    
    /**
     * 描述:将字符串转换为时间对象(默认方法)<br/>
     * @param str
     * @return yyyy-MM-dd HH:mm:ss
     * @throws ParseException 
     */
    public static Date parseStr(String str){
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	try {
			return dateformat1.parse(str);
		} catch (ParseException e) {
		}
    	return null;
    }
    
   /**
    * 
    * 功能说明:【将字符串转换为时间对象】
    * @param str
    * @param formatStr
    * @return
    */
    public static Date parseStr(String str,String formatStr){
    	DateFormat dd = new SimpleDateFormat(formatStr);
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	try {
			return dd.parse(str);
		} catch (ParseException e) {
		}
    	return null;
    }
    
    /**
     * 描述:短日期格式
     * @param str
     * @return yyyy-MM-dd
     * @throws ParseException
     */
    public static Date parseStrYY(String str){
    	
    	if(StringUtils.isBlank(str)){
    		return null;
    	}
    	
    	try {
			return dateYYMMDD.parse(str);
		} catch (ParseException e) {
		}
    	return null;
    }
    
    /**
     * 描述:将字符串转换为时间对象(默认方法)<br/>
     * @param str
     * @return
     * @throws ParseException 
     */
    public static String formatDate(Date date) throws ParseException{
    	return dateformat1.format(date);
    }
    
    /**
     * 描述:获取本年总共天数
     * @return
     */
    public static int getYearMaxDays(){
    	Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_YEAR, 1);
		date.roll(Calendar.DAY_OF_YEAR, -1);
		return date.get(Calendar.DAY_OF_YEAR);
    }
    
    /**
     * 描述:获取时间 + 月数
     * @param monthNum 几个月或几天
     * @param date 时间
     * @param type 类型：1 天，2 月
     * @return yyyy-MM-dd
     */
    public static String getDateAndMonth(int monthNum,Date date,int type){
		
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	Calendar calendar = Calendar.getInstance();
    	try {
    		Date now = sdf.parse(sdf.format(date));
    		calendar.setTime(now);
    		//calendar.add(Calendar.DATE,-1); //加一天
    		if(type == 1){
    			calendar.add(Calendar.DATE, monthNum - 1);
    		}else{
    			calendar.add(Calendar.MONTH, monthNum);
    		}
		} catch (Exception e) {
		}
		
		return dateYYMMDD.format(calendar.getTime());
    }
    
    /**
     * 功能说明:【加天数】
     * @param date
     * @param days
     * @return
     */
    public static Date addDaysNum(Date date,int days){
		
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE, days);
		
		return calendar.getTime();
    }
    
    /**
     * 功能说明:【加月份】
     * @param date
     * @param num
     * @return
     */
    public static Date addMonthNum(Date date,int num){
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH, num);
    	
    	return calendar.getTime();
    }
    
    /** 
     * 根据年 月 获取对应的月份 天数 
     * */  
    public static int getDaysByYearMonth(int year, int month) {  
          
        Calendar a = Calendar.getInstance();  
        a.set(Calendar.YEAR, year);  
        a.set(Calendar.MONTH, month - 1);  
        a.set(Calendar.DATE, 1);  
        a.roll(Calendar.DATE, -1);  
        int maxDate = a.get(Calendar.DATE);  
        return maxDate;  
    } 
    
    /**
     * 功能说明:【生成时间】
     * @param date 时间
     * @return 2015年12月12日
     */
    public static String getDateYYmmdd(Date date){
    	
    	if(null == date){
    		return "";
    	}
    	
    	StringBuffer dates = new StringBuffer();
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(date);
    	dates.append(cal.get(Calendar.YEAR));
    	dates.append("年");
    	dates.append(cal.get(Calendar.MONTH)+1);
    	dates.append("月");
    	dates.append(cal.get(Calendar.DAY_OF_MONTH));
    	dates.append("日");
    	return dates.toString();
    }
    
    /**
     * 功能说明:【获取时间中的日期】
     * @param date 时间
     * @return 返回 几日
     */
    public static int getCurrDay(Date date){
    	
    	Calendar cal = Calendar.getInstance();  
    	cal.setTime(date);
    	
    	return cal.get(Calendar.DAY_OF_MONTH);
    }
    
    
    /**
     * 某一个月第一天和最后一天
     * @param date
     * @return
     */
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
    	
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //calendar.add(Calendar.MONTH, -1); //如果是上一个月，则减一
        Date theDate = calendar.getTime();
        
        //本月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);

        //本月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天

        Map<String, String> map = new HashMap<String, String>();
        map.put("firstDay", dateFormat(gcLast.getTime(),FORMAT_YYMMDD_DATE));
        map.put("lastDay", dateFormat(calendar.getTime(),FORMAT_YYMMDD_DATE));
        
        return map;
    }
    
    /**
	 * 功能说明:【获取某一个星期时间】
	 * @return
	 */
	public static Map<String, String> getDateWeek(Date date){
		
		Map<String, String> map = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekday = calendar.get(7)-2;
		calendar.add(5,-weekday);
		map.put("firstDay", dateFormat(calendar.getTime(),FORMAT_YYMMDD_DATE));
		calendar.add(5,6);
		map.put("lastDay", dateFormat(calendar.getTime(),FORMAT_YYMMDD_DATE));
		  
		return map;
	}
    
    public static void main(String[] args) {
    	
    	Map<String, String> maps = getDateWeek(new Date());
    	System.out.println(maps.get("firstDay"));
    	System.out.println(maps.get("lastDay"));
    	
	}
    
}
