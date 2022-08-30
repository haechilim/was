package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpRequest;

import java.io.OutputStream;
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

    public void runHandler(HttpRequest request, OutputStream outputStream) {
        urlMap.get(request.getPath()).handle(request, outputStream);
    }

    public interface Handler {
        void handle(HttpRequest request, OutputStream outputStream);
    }
}
