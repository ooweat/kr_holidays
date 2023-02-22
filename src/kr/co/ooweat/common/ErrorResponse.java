package kr.co.ooweat.common;
public enum ErrorResponse {
    EMPTY_PARAM_BLANK_OR_NULL("6300", "Request Parameter 빈 값, NULL 또는 공백"),
    NOT_SUPPORT_DATE_FORMAT("6214", "지원하지 않는 날짜 형식"),
    NOT_SUPPORT_TIME_FORMAT("6215", "지원하지 않는 시간 형식");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
