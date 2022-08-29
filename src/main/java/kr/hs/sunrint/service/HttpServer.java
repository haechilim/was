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

    private HttpRequest request;
    private HttpStreamReader streamReader;
    private HttpStreamWriter streamWriter;
    private FileLoader fileLoader;
    private UrlMapper urlMapper;

    public HttpServer() {
        streamReader = new HttpStreamReader();
        streamWriter = new HttpStreamWriter();
        fileLoader = new FileLoader();
        urlMapper = new UrlMapper();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            System.out.println("Server is listening...");

            while (true) {
                Socket socket = serverSocket.accept();
                request = streamReader.read(socket.getInputStream());

                System.out.println(request.getMethod().name() + " " + request.getPath() + " " + request.getParameters() + " " + request.getBody());

                if(urlMapper.isRegister(request.getPath())) urlMapper.runHandler(request, streamWriter);

                File file = fileLoader.getFile(socket.getInputStream(), request.getPath());

                if(file != null) streamWriter.write(socket.getOutputStream(), file, OK, contentsType);
                else streamWriter.write(socket.getOutputStream(), NOT_FOUND);

                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HttpRequest getRequest() {
        return request;
    }

    public void setRequest(HttpRequest request) {
        this.request = request;
    }

    public HttpStreamReader getStreamReader() {
        return streamReader;
    }

    public void setStreamReader(HttpStreamReader streamReader) {
        this.streamReader = streamReader;
    }

    public HttpStreamWriter getStreamWriter() {
        return streamWriter;
    }

    public void setStreamWriter(HttpStreamWriter streamWriter) {
        this.streamWriter = streamWriter;
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }

    public void setFileLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public UrlMapper getUrlMapper() {
        return urlMapper;
    }

    public void setUrlMapper(UrlMapper urlMapper) {
        this.urlMapper = urlMapper;
    }
}
