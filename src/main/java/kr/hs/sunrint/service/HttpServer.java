package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static kr.hs.sunrint.domain.HttpResponseCode.OK;
import static kr.hs.sunrint.domain.HttpResponseCode.NOT_FOUND;

public class HttpServer {
    private String contentsType = "text/html";

    private HttpStreamReader streamReader;
    private HttpStreamWriter streamWriter;
    private FileLoader fileLoader;

    public HttpServer() {
        streamReader = new HttpStreamReader();
        streamWriter = new HttpStreamWriter();
        fileLoader = new FileLoader();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            System.out.println("Server is listening...");

            while (true) {
                Socket socket = serverSocket.accept();
                HttpRequest request = streamReader.read(socket.getInputStream());

                System.out.println(request.getMethod().name() + " " + request.getPath() + " " + request.getBody());

                File file = fileLoader.getFile(socket.getInputStream(), request.getPath());

                if(file != null) streamWriter.write(socket.getOutputStream(), file, OK, contentsType);
                else streamWriter.write(socket.getOutputStream(), NOT_FOUND);

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
