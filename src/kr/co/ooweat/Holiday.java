package kr.co.ooweat;

import com.ibm.icu.util.ChineseCalendar;
import kr.co.ooweat.api.ApiExplorer;
import kr.co.ooweat.common.ExceptionHandler;
import kr.co.ooweat.common.Util;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Holiday {

    //HINT: INPUT 값이 없으면
    public static boolean isHoliday() throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        return blReturn(Util.dateUtils().today());
    }

    //HINT: INPUT 값이 없으면
    public static boolean isWorkday() throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        return Util.stringUtils(Util.dateUtils().dayofWeek(Util.dateUtils().today())).notIn("1", "7") ?
                !blReturn(Util.dateUtils().today()) : false;
    }

    //HINT: INPUT 값이 있으면
    public static boolean isHoliday(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        return blReturn(yyyyMMdd);
    }

    //HINT: INPUT 값이 있으면
    public static boolean isWorkday(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        return Util.stringUtils(Util.dateUtils().dayofWeek(yyyyMMdd)).notIn("1", "7") ? !blReturn(yyyyMMdd) : false;
    }

    private static boolean blReturn(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        //NOTE: 유효성 검사
        checkValid(yyyyMMdd);
        boolean result = false;
        try {
            result = ApiExplorer.holidayList
                    .stream()
                    .filter(x -> String.valueOf(x.get("locdate")).equals(yyyyMMdd))
                    .findAny().get().size() > 0 ? true : false;
        } catch (NoSuchElementException e) {

        }
        return result;
    }

    public static Map<String, String> whatHoliday(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        //NOTE: 유효성 검사
        checkValid(yyyyMMdd);
        try {
            //HINT: #Type1
            Map<String, String> result = ApiExplorer.holidayList.stream().filter(x -> String.valueOf(x.get("locdate")).equals(yyyyMMdd)).findAny().get();

            //HINT: #Type2
           /*
            Map<String, String> result = null;
            Iterator<Map<String, String>> itr = ApiExplorer.holidayList.stream().filter(x -> String.valueOf(x.get("locdate")).equals(yyyyMMdd)).iterator();

            while (itr.hasNext()) {
                result = itr.next();
                System.out.println("result: {} " + result);
            }
            */
            return result;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    //NOTE: 확장성 - 음력 계산을 위함.
    public static String convertLuna(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        ChineseCalendar cc = new ChineseCalendar();
        checkValid(yyyyMMdd);

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(yyyyMMdd.substring(0, 4)) + 2637);   // 년, year + 2637
        cc.set(ChineseCalendar.MONTH, Integer.parseInt(yyyyMMdd.substring(4, 6)) - 1);              // 월, month -1
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(yyyyMMdd.substring(6)));              // 일

        LocalDate solar = Instant.ofEpochMilli(cc.getTimeInMillis()).atZone(ZoneId.of("UTC")).toLocalDate();
        int y = solar.getYear();
        int m = solar.getMonth().getValue();
        int d = solar.getDayOfMonth();

        StringBuilder ret = new StringBuilder();
        ret.append(String.format("%04d", y));
        ret.append(String.format("%02d", m));
        ret.append(String.format("%02d", d));

        return ret.toString();
    }

    //NOTE: 유효성 검사
    private static void checkValid(String yyyyMMdd) throws ExceptionHandler.EmptyDateException, ExceptionHandler.NotSupportDateException {
        if (yyyyMMdd.isEmpty()) {
            throw new ExceptionHandler.EmptyDateException();
        } else if (yyyyMMdd.length() != 8) {
            throw new ExceptionHandler.NotSupportDateException();
        } else if (Integer.parseInt(yyyyMMdd.substring(4, 6)) > 13) {
            throw new ExceptionHandler.NotSupportDateException();
        } else if (Integer.parseInt(yyyyMMdd.substring(6, 8)) > 31) {
            throw new ExceptionHandler.NotSupportDateException();
        }
    }

    private static void staticRangeSet() throws IOException {
        ApiExplorer apiExplorer = new ApiExplorer();
        //NOTE: API 만료기간인 2025년 까지만 설정
        List<String> years = Arrays.asList("2023", "2024", "2025");
        List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        for (String i : years) {
            for (String j : months) {
                apiExplorer.call(i, j);
            }
        }
    }

    static void initSet() throws IOException {
        ApiExplorer apiExplorer = new ApiExplorer();
        apiExplorer.call(Util.dateUtils().today().substring(0, 4), Util.dateUtils().today().substring(4, 6));
    }

    static void initSet(String yyyyMMdd) throws IOException {
        ApiExplorer apiExplorer = new ApiExplorer();
        apiExplorer.call(yyyyMMdd.substring(0, 4), yyyyMMdd.substring(4, 6));
    }
}
