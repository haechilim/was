package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpRequest;
import kr.hs.sunrint.domain.MimeType;
import kr.hs.sunrint.service.jsp.JspProcessor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static kr.hs.sunrint.domain.HttpResponseCode.OK;
import static kr.hs.sunrint.domain.HttpResponseCode.NOT_FOUND;

public class HttpServer {
    private HttpRequest request;
    private HttpStreamReader streamReader;
    private HttpStreamWriter streamWriter;
    private FileLoader fileLoader;
    private UrlMapper urlMapper;
    private JspProcessor jspProcessor;

    public HttpServer() {
        streamReader = new HttpStreamReader();
        streamWriter = new HttpStreamWriter();
        fileLoader = new FileLoader();
        urlMapper = new UrlMapper();
        jspProcessor = new JspProcessor();
    }

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);
            System.out.println("Server is listening...");

            while (true) {
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                request = streamReader.read(inputStream);

                //System.out.println(request.getMethod().name() + " " + request.getPath() + " " + request.getParameters() + " " + request.getBody());

                if(urlMapper.isRegister(request.getPath())) urlMapper.runHandler(request, outputStream);
                else {
                    String path = request.getPath();
                    File file = fileLoader.getFile(path);

                    if (file != null) {
                        if(request.getExtension().equals("jsp")) {
                            byte[] contents = jspProcessor.process(fileLoader.getFileToString(file)).getBytes();
                            streamWriter.write(outputStream, OK, MimeType.getContentsType(request.getExtension()), contents);
                        }
                        else streamWriter.write(outputStream, file, OK, MimeType.getContentsType(request.getExtension()));
                    }
                    else streamWriter.write(outputStream, NOT_FOUND);
                }

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
