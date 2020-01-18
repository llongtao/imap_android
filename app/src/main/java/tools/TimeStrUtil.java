package tools;

import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Created by Mac on 2019/4/25.
 */

public class TimeStrUtil {
    public static String getTimeStr(Long t) {
        Long ct=System.currentTimeMillis();
        Long t1=ct - t; //时间差
        //3个月前的直接显示时间
        if (t1 >= 90 * 24 * 60 * 60 * 1000L) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(t));
            /*return workShow.getWork_time().getYear()+"-"+
                    workShow.getWork_time().getMonth()+"-"+
                    workShow.getWork_time().getDay();*/
        } else if (t1 >= 30 * 24 * 60 * 60 * 1000L) {
            return t1 / (30 * 24 * 60 * 60 * 1000L) + "月前";
        } else if (t1 > 24 * 60 * 60 * 1000L) {
            return t1 / (24 * 60 * 60 * 1000L) + "天前";
        } else if (t1 > 60 * 60 * 1000L) {
            return t1 / (60 * 60 * 1000L) + "小时前";
        } else if (t1 > 60 * 1000L) {
            return t1 / (60 * 1000L) + "分钟前";
        } else if (t1 > 1000L) {
            return t1 / 1000L + "秒前";
        } else {
            return "刚刚";
        }
    }
}
