package top.lzzzs.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String createdTime() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return ft.format(new Date());
    }
}
