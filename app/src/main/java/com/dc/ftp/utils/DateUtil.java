package com.dc.ftp.utils;

import android.text.format.DateUtils;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 用于将String转化为时间
 * 
 */
public class DateUtil {
    private static SimpleDateFormat sfd;

    /**
     * 转换日期格式到用户体验好的时间格式
     *
     * @param time 2015-04-11 12:45:06
     * @return
     */
    public static String second2Date(long time) {
        String text = "";
        if (sfd == null) {
            sfd = new SimpleDateFormat("yyyyMMdd");
        }
        long created = time * 1000;
        long now = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(now));

        //获取传入的日期
        int currentdate = Integer.parseInt(sfd.format(created));
        //获取今年第一天的日期
        int thisyear = calendar.get(Calendar.YEAR) * 10000 + 101;
        //获取昨天的日期
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int yesterday = Integer.parseInt(sfd.format(calendar.getTime()));
        //获取2天前的日期
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int twoDayAgo = Integer.parseInt(sfd.format(calendar.getTime()));
        //获取3天前的日期
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        int threeDayAgo = Integer.parseInt(sfd.format(calendar.getTime()));
        Log.i("threeDayAgo", threeDayAgo + "");
        Log.i("twoDayAgo", twoDayAgo + "");
        Log.i("oneDayAgo", yesterday + "");
        Log.i("currentDate", currentdate + "");
        if (currentdate >= threeDayAgo) {
            if (currentdate == twoDayAgo) {
                text = "2天前";
            } else if (currentdate == yesterday) {
                text = "1天前";
            } else if (currentdate == threeDayAgo) {
                text = "3天前";
            } else {
                long difference = now - created;
                text = (/*difference >= 0 && */difference <= DateUtils.MINUTE_IN_MILLIS) ?
                        "刚刚" : DateUtils.getRelativeTimeSpanString(
                        created,
                        now,
                        DateUtils.MINUTE_IN_MILLIS,
                        DateUtils.FORMAT_ABBREV_RELATIVE).toString();
            }
        } else if (currentdate >= thisyear) {
            text = (currentdate + "").substring(4, 6) + "月" + (currentdate + "").substring(6) + "日";
        } else {
            text = (currentdate + "").substring(0, 4) + "年" + (currentdate + "").substring(4, 6) + "月" + (currentdate + "").substring(6) + "日";
        }
        return text;
    }

    public static String second2FormatDate(long time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time * 1000));
    }

    ///////////将时间戳字符串转化成日期
    public static String getStrTime(String cc_time,String fomat) {
        String re_StrTime = null;////"yyyy年MM月dd日HH时mm分"
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    ///////////将时间戳字符串转化成日期2
    public static String getStrTimeFromStack(String cc_time,String fomat) {
        String re_StrTime = null;////"yyyy年MM月dd日HH时mm分"
        SimpleDateFormat sdf = new SimpleDateFormat(fomat);
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;
    }
    
    
    
    /////将20151129分解拿到2015,11,29
    public static String[] getEveryLevel(String time){
        String []level=new String[3];
        try {
            long timeL=Long.parseLong(time);
            long level2=timeL%10000;
            long year=timeL/10000;
            long day=level2%100;
            long month=level2/100;
            level[0]=String.valueOf(year);
            level[1]=String.valueOf(month);
            level[2]=String.valueOf(day);
            return level;
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 得到当前时间
     * @param dateFormat 时间格式
     * @return 转换后的时间格式
     */
    public static String getStringToday(String dateFormat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将字符串型日期转换成日期
     * @param dateStr 字符串型日期
     * @param dateFormat 日期格式
     * @return
     */
    public static Date stringToDate(String dateStr, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 日期转字符串
     * @param date
     * @param dateFormat
     * @return
     */
    public static String dateToString(Date date, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }




    /**
     * 根据时间返回指定术语，自娱自乐，可自行调整
     * @param hourday 小时
     * @return
     */
    public static String showTimeView(int hourday) {
        if(hourday >= 22 && hourday <= 24){
            return "晚上";
        }else if(hourday >= 0 && hourday <= 6 ){
            return  "凌晨";
        }else if(hourday > 6 && hourday <= 12){
            return "上午";
        }else if(hourday >12 && hourday < 22){
            return "下午";
        }
        return null;
    }
    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /////////根据出生日期获得年龄
    public static int getAge(Date dateOfBirth) {
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                throw new IllegalArgumentException(
                        "Can't be born in the future");
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                age -= 1;
            }
        }
        return age;
    }
    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param timeStr   时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t*1000);
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}
