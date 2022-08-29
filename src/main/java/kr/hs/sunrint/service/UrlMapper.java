package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class UrlMapper {
    private Map<String, Handler> urlMap = new HashMap<>();

    public void register(String url, Handler handler) {
        urlMap.put(url, handler);
    }

    public boolean isRegister(String url) {
        return urlMap.containsKey(url);
    }

    public void runHandler(HttpRequest request, HttpStreamWriter streamWriter) {
        urlMap.get(request.getPath()).handle(request, streamWriter);
    }

    public interface Handler {
        void handle(HttpRequest request, HttpStreamWriter streamWriter);
    }
}
