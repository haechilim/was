package kr.hs.sunrint.domain;

import java.util.HashMap;
import java.util.Map;

public class MimeType {
    private static Map<String, String> mime = new HashMap<String, String>();

    static {
        mime.put("html", "text/html");
        mime.put("htm", "text/html");
        mime.put("jsp", "text/html");
        mime.put("css", "text/css");
        mime.put("js", "text/javascript");
        mime.put("json", "application/json");
        mime.put("xml", "application/xml");
        mime.put("txt", "text/plain");
        mime.put("ico", "image/x-icon");
        mime.put("woff", "application/x-font-woff");
        mime.put("gif", "image/gif");
        mime.put("jpg", "image/jpeg");
        mime.put("jpeg", "image/jpeg");
        mime.put("svg", "image/svg+xml");
        mime.put("mpeg", "video/mpeg");
        mime.put("avi", "video/x-msvideo");
    }

    public static String getContentsType(String key) {
        return mime.getOrDefault(key, "text/plain");
    }
}