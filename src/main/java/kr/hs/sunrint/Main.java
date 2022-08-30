package kr.hs.sunrint;

import kr.hs.sunrint.domain.MimeType;
import kr.hs.sunrint.service.HttpServer;
import kr.hs.sunrint.service.HttpStreamWriter;

import java.util.Arrays;
import java.util.List;

import static kr.hs.sunrint.domain.HttpResponseCode.OK;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer httpServer = new HttpServer();
        HttpStreamWriter streamWriter = httpServer.getStreamWriter();

        httpServer.getUrlMapper().register("/api/unique", (request, outputStream) -> {
            List<String> ids = Arrays.asList("haechi", "yellow");

            String id = request.getParameter("id");

            if(ids.contains(id)) {
                streamWriter.write(outputStream, OK, MimeType.getContentsType("json"), "{\"code\": \"DUPLICATED\"}".getBytes());
                return;
            }

            streamWriter.write(outputStream, OK, MimeType.getContentsType("json"), "{\"code\": \"OK\"}".getBytes());
        });

        httpServer.start();
    }
}
