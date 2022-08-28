package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.MimeType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class HttpServer {
    private int responseCode = 200;
    private String contentsType = "text/html";

    private HttpStreamWriter streamWriter;
    private FileLoader fileLoader;

    public HttpServer() {
        streamWriter = new HttpStreamWriter();
        fileLoader = new FileLoader();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            System.out.println("Server is listening...");

            while (true) {
                Socket socket = serverSocket.accept();

                //HttpRequest request = streamReader.get();
                //File file = fileLoader.getFile(socket.getInputStream(), request.getPath());
                File file = fileLoader.getFile(socket.getInputStream(), getPath(socket.getInputStream()));

                if(file != null) streamWriter.write(socket.getOutputStream(), file, responseCode, contentsType);
                else streamWriter.write(socket.getOutputStream(), 404);
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
}
