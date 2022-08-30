package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpMethod;
import kr.hs.sunrint.domain.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser {
    public HttpRequest parseFirstPart(String string) {
        try {
            HttpRequest request = new HttpRequest();
            BufferedReader reader = new BufferedReader(new StringReader(string));
            String[] startLines = reader.readLine().split(" ");
            String path = parsePath(startLines);

            request.setMethod(HttpMethod.valueOf(parseMethod(startLines)));
            request.setPath(path);
            request.setFilename(parseFilename(path));
            request.setExtension(parseExtension(path));
            request.setParameters(parseParameters(startLines));
            request.setHeaders(parseHeaders(reader));

            return request;
        } catch (IOException e) {
            return null;
        }
    }

    private Map<String, String> parseHeaders(BufferedReader reader) throws IOException {
        Map<String, String> hashMap = new HashMap<>();

        String line = "";

        while ((line = reader.readLine()) != null) {
            if(!line.contains(":")) continue;

            String[] headers = line.split(": ");

            hashMap.put(headers[0], headers[1]);
        }

        return hashMap;
    }

    private String parseMethod(String[] elements) {
        if(elements.length <= 0) return null;

        return elements[0];
    }

    private String parsePath(String[] elements) {
        if(elements.length <= 1) return null;

        String url = elements[1];

        return url.contains("?") ? url.split("\\?")[0] : url;
    }

    private Map<String, String> parseParameters(String[] elements) {
        Map<String, String> hashMap = new HashMap<>();

        if(elements.length <= 1) return null;

        String url = elements[1];
        String parameter = url.contains("?") ? url.split("\\?")[1] : "";

        if(parameter.isEmpty()) return null;

        String[] parameters = parameter.split("&");

        for(int i = 0; i < parameters.length; i++) {
            String[] parameterData = parameters[i].split("=");

            if(parameterData.length < 2) continue;

            hashMap.put(parameterData[0], parameterData[1]);
        }

        return hashMap;
    }

    private String parseFilename(String path) {
        String[] directories = path.split("/");
        String[] elements = directories[directories.length - 1].split("\\.");

        if(elements.length <= 0) return null;

        return elements[0];
    }

    private String parseExtension(String path) {
        String[] elements = path.split("\\.");

        if(elements.length <= 1) return null;

        return elements[1];
    }
}
