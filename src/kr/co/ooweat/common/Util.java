package kr.co.ooweat.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Util {
    public static StringUtils stringUtils(String param) {
        return new StringUtils(param);
    }

    public static class StringUtils {
        private String value;

        public StringUtils(String value) {
            this.value = value;
        }

        //SQL 의 그것과 같음 in
        public boolean in(String... values) {
            for (String v : values) {
                if (v.equals(value)) return true;
            }
            return false;
        }

        //SQL 의 그것과 같음 notIn
        public boolean notIn(String... values) {
            for (String v : values) {
                if (v.equals(value)) return false;
            }
            return true;
        }
    }

    public static DateUtils dateUtils() {
        return new DateUtils();
    }

    public static class DateUtils {

        public static String today() {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(date);
        }

        public static String dayofWeek(String yyyyMMdd) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, Integer.parseInt(yyyyMMdd.substring(0, 4)));
            calendar.set(Calendar.MONTH, Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1);
            calendar.set(Calendar.DATE, Integer.parseInt(yyyyMMdd.substring(6, 8)));
            return String.valueOf(calendar.get(Calendar.DAY_OF_WEEK));
        }
    }
}
