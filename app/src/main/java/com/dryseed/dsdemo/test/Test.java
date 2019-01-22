package com.dryseed.dsdemo.test;


import java.util.Calendar;
import java.util.Date;

/**
 * @author caiminming
 */
public class Test {
    public static void main(String[] args) {
        long time1 = 1547999999000l;
        long time2 = 1547913601000l;
        long time3 = 1547978401000l;
        System.out.println(getWeekOfYear(time1));
        System.out.println(getWeekOfYear(time2));
        System.out.println(getWeekOfYear(time3));
    }

    public static int getWeekOfYear(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.clear();
        calendar.setTimeInMillis(time);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /*public static int getWeekOfYear(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(time);
        Date date = null;
        try {
            date = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }*/

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static int getDayFromDate(Date date) {
        int[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}
