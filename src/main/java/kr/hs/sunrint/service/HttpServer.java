package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.MimeType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class HttpServer {
    private String responseCode = "200";
    private String contentsType = "text/html";

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            while (true) {
                Socket socket = serverSocket.accept();

                File file = getFile("/static" + getPath(socket.getInputStream()));

                HttpStreamWriter.write(socket, file, responseCode, contentsType);
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath(InputStream inputStream) {
        String startLine = HttpStreamReader.getStartLine(inputStream);

        if(startLine != null) {
            String[] str = startLine.split(" ");

            if (str.length >= 2) {
                if(str[1].contains(".")) contentsType = MimeType.getContentsType(str[1].split("\\.")[1]);
                return str[1];
            }
        }

        return "";
    }

    private File getFile(String name) {
        URL url = getClass().getResource(name);

        if(url == null) {
            responseCode = "404";
            return null;
        }

        responseCode = "200";
        return new File(url.getFile());
    }
}
