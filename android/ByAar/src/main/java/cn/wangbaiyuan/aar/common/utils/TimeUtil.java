package cn.wangbaiyuan.aar.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by BrainWang on 2017-03-03.
 */

public class TimeUtil {
    public static String TimeToString(String time,String format) {
        String timeString = time;
        try {
            SimpleDateFormat dateFormat;

            dateFormat = new SimpleDateFormat(format);
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormat.setLenient(false);
            Date timeDate = dateFormat.parse(time);
//            Long timeStamp = timeDate.getTime();
//            Long currentStamp = new Date().getTime();
//            Long timetogo = currentStamp - timeStamp;
            Calendar calDateA = Calendar.getInstance();
            Calendar calDateB = Calendar.getInstance();
            calDateB.setTime(timeDate);
            int year = calDateA.get(Calendar.YEAR);
            int mon = calDateA.get(Calendar.MONTH);
            int day = calDateA.get(Calendar.DATE);
            int currentyear = calDateB.get(Calendar.YEAR);
            int currentmon = calDateB.get(Calendar.MONTH);
            int currentday = calDateB.get(Calendar.DATE);
            if (year == currentyear && mon == currentmon && day == currentday) {
                timeString = "今天" + dateFormat2.format(timeDate);
            } else if (year == currentyear && mon == currentmon && (day - currentday) == 1) {
                timeString = "昨天" + dateFormat2.format(timeDate);
            } else if (year == currentyear && mon == currentmon && (day - currentday) == -1) {
                timeString = "明天" + dateFormat2.format(timeDate);
            } else {
                timeString = dateFormat3.format(timeDate);
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// util类型

        return timeString;
    }
}
