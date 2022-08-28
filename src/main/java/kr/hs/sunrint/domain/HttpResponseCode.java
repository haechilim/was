package kr.hs.sunrint.domain;

public enum HttpResponseCode {
    OK(200), NOT_FOUND(404);

    private int code;

    HttpResponseCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}