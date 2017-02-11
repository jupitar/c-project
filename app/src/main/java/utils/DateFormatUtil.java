package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/7.
 */

//格式化时间函数,返回当前时间对应的字符串

public class DateFormatUtil {
    public static String getCurrentTime(){
        long current_time = System.currentTimeMillis();
        Date date = new Date(current_time);

        SimpleDateFormat simpleformatter = new SimpleDateFormat(
                "yyyy/MM/dd HH:mm:ss");

        String formatStr = simpleformatter.format(date);
        return formatStr;

    }
}
