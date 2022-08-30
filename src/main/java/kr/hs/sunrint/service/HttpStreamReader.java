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
            HttpRequest request;
            HttpRequestParser parser = new HttpRequestParser();

            request = parser.parseFirstPart(readFirstPart(stream));
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

        byteArrayOutputStream.close();

        return byteArrayOutputStream.toString();
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
}
