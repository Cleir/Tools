package cn.cleir.home.until;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author lianghuining
 * @date 2018/11/13
 * @description 计算并(或)拼接日期,目前只能计算减;
 */
public class DateSpliceUntil {
    /**
     * 日期拼接
     * @param year
     * @param month
     * @param day
     * @param yearOffset
     * @param monthOffset
     * @param dayOffset
     * @return String yyyy-MM-dd
     */
    public static String spliceDate(String year,String month,String day,int yearOffset,int monthOffset,int dayOffset){
        int yearNum = Integer.parseInt(year);
        int monthNum = Integer.parseInt(month);
        int dayNum = Integer.parseInt(day);
        if (!isDateStyle(yearNum,monthNum,dayNum))return null;
        yearNum += yearOffset;
        monthNum += monthOffset;
        dayNum += dayOffset;
        while (dayNum <= 0){
            if (monthNum-- <= 0){
                yearNum--;
                monthNum += 12;
            }
            int maxDay = getMaxDayForMonth(yearNum,monthNum);
            dayNum += maxDay;
        }
        if (monthOffset < 0){
            while (monthNum <= 0){
                yearNum--;
                monthNum += 12;
            }
            dayNum = dayNum > getMaxDayForMonth(yearNum,monthNum) ? getMaxDayForMonth(yearNum,monthNum) : dayNum;
        }
        return isDateStyle(yearNum,monthNum,dayNum) ? spliceDateStyle(yearNum,monthNum,dayNum) : null;
    }

    public static String spliceDate(String date,int yearOffset,int monthOffset,int dayOffset){
        String year = date.split("-")[0];
        String month = date.split("-")[1];
        String day = date.split("-")[2];
        return spliceDate(year, month, day, yearOffset, monthOffset, dayOffset);
    }

    //日期格式拼接
    public static String spliceDateStyle(int year, int month, int day){
        String monthDate = "01";
        String dayDate = "01";
        if (month < 10) {
            monthDate = "0" + month;
        }else{
            monthDate = "" + month;
        }
        if (day<10) {
            dayDate = "0" + day;
        }else{
            dayDate = "" + day;
        }
        return year + "-" + monthDate + "-" + dayDate;
    }

    //判断是否闰年
    public static boolean isLeaYear(int year){
        if ( year % 4 == 0 && year % 400 != 0){
            return true;
        }
        return false;
    }

    //判断是否在数值数组内
    public static boolean inArray(int i,int[] array){
        for (int j :
                array) {
            if (j == i)return true;
        }
        return false;
    }

    //获取指定日期在当周第几日
    public static int getDayOfWeek(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(date));
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            dayOfWeek = 7;
        }else{
            dayOfWeek --;
        }
        return dayOfWeek;
    }

    //获取去年同周同周几的日期
    public static String getLastYearWeekDate(String date) throws ParseException {
        final Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        cal.setTime(sdf.parse(date));
        cal.setWeekDate(2017, cal.get(Calendar.WEEK_OF_YEAR), cal.get(Calendar.DAY_OF_WEEK));
        return sdf.format(cal.getTime().getTime());
    }

    //获取该季度的第一个月
    public static String getStartMonth(String date){
        int year = Integer.parseInt(date.split("-")[0]);
        int month = Integer.parseInt(date.split("-")[1]);
        int startMonth = 1;
        if(month % 3 == 0){
            startMonth = month - 2;
        }else{
            startMonth = month / 3 * 3 + 1;
        }
        return spliceDateStyle(year,startMonth,1);
    }

    //获取该月份的最大日期
    public static int getMaxDayForMonth(int yearNum,int monthNum){
        int[] bigMonth = {1,3,5,7,8,10,12};
        int[] smallMonth = {4,6,9,11};
        int maxDay = 28;
        if (inArray(monthNum,bigMonth)) maxDay = 31;
        else if (inArray(monthNum,smallMonth)) maxDay = 30;
        else if (monthNum==2 && isLeaYear(yearNum)) maxDay = 29;
        return maxDay;
    }

    //判断日期是否符合格式
    public static boolean isDateStyle(int yearNum, int monthNum, int dayNum){
        boolean dayTrue = !(dayNum > getMaxDayForMonth(yearNum,monthNum));
        if (yearNum > 3000 || yearNum <= 1970 || monthNum > 12 || monthNum <= 0 || dayNum <= 0 || !dayTrue) return false;
        return true;
    }

}
