package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpHeader;
import kr.hs.sunrint.domain.HttpMethod;
import kr.hs.sunrint.domain.HttpRequest;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpStreamReader {
    public HttpStreamReader() {}

    public HttpRequest read(InputStream stream) {
        try {
            HttpRequest request = new HttpRequest();
            parseFirstPart(request, readFirstPart(stream));
            readBodyPart(request, stream);

            return request;
        } catch (IOException e) {
            return null;
        }
    }

    private String readFirstPart(InputStream stream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int length = 0;

        while (true) {
            int ch = stream.read();
            byteArrayOutputStream.write(ch);
            length++;

            if (ch == '\n') {
                if (length <= 2) break;
                length = 0;
            }
        }

        return byteArrayOutputStream.toString();
    }

    private void parseFirstPart(HttpRequest request, String string) {
        try {
            BufferedReader reader = new BufferedReader(new StringReader(string));

            String[] startLines = reader.readLine().split(" ");

            request.setMethod(getMethod(startLines));
            request.setPath(getPath(startLines));
            request.setParameters(getParameter(startLines));

            String path = getPath(startLines);

            request.setFilename(getFilename(path));
            request.setExtension(getExtension(path));

            String line = "";

            while ((line = reader.readLine()) != null) {
                if(!line.contains(":")) continue;

                String[] headers = line.split(": ");

                request.addHeader(headers[0], headers[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readBodyPart(HttpRequest request, InputStream stream) {
        try {
            String contentType = request.getHeader(HttpHeader.CONTENT_LENGTH);

            if(contentType.isEmpty()) return;

            int contentLength = Integer.parseInt(contentType);

            if(contentLength == 0) return;

            byte[] content = new byte[contentLength];

            stream.read(content, 0, contentLength);

            request.setBody(new String(content));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HttpMethod getMethod(String[] elements) {
        if(elements.length <= 0) return null;

        return HttpMethod.valueOf(elements[0]);
    }

    private String getPath(String[] elements) {
        if(elements.length <= 1) return null;

        String url = elements[1];

        return url.contains("?") ? url.split("\\?")[0] : url;
    }

    private Map<String, String> getParameter(String[] elements) {
        Map<String, String> hashMap = new HashMap<>();

        if(elements.length <= 1) return null;

        String url = elements[1];
        String parameter = url.contains("?") ? url.split("\\?")[1] : "";

        if(parameter.isEmpty()) return null;

        String[] parameters = parameter.split("&");

        for(int i = 0; i < parameters.length; i++) {
            hashMap.put(parameters[i].split("=")[0], parameters[i].split("=")[1]);
        }

        return hashMap;
    }

    private String getFilename(String path) {
        String[] directories = path.split("/");
        String[] elements = directories[directories.length - 1].split("\\.");

        if(elements.length <= 0) return null;

        return elements[0];
    }

    private String getExtension(String path) {
        String[] elements = path.split("\\.");

        if(elements.length <= 1) return null;

        return elements[1];
    }
}
