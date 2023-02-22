package kr.co.ooweat.common;

import org.json.simple.JSONObject;

public class ExceptionHandler {
    public static class EmptyDateException extends Exception {
        public EmptyDateException() {
            errorParsing(ErrorResponse.EMPTY_PARAM_BLANK_OR_NULL);
        }
    }
    public static class NotSupportDateException extends Throwable {
        public NotSupportDateException() {
            errorParsing(ErrorResponse.NOT_SUPPORT_DATE_FORMAT);
        }
    }

    public static String errorParsing(ErrorResponse errorResponse) {
        JSONObject json = new JSONObject();
        json.put("code", errorResponse.getCode());
        json.put("message", errorResponse.getMessage());
        System.out.println(json.toJSONString());
        return json.toString();
    }
}
