package kr.hs.sunrint.service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class HttpServer {
    private int responseCode = 200;

    public void start() {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            while (true) {
                Socket socket = serverSocket.accept();

                File file = getFile("/static" + getPath(socket.getInputStream()));

                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeBytes("HTTP/1.1 " + responseCode + " OK \r\n");
                dataOutputStream.writeBytes("Content-Type: text/html\r\n");
                dataOutputStream.writeBytes("\r\n");

                if(file != null) {
                    int fileLength = (int) file.length();
                    FileInputStream fileInputStream = new FileInputStream(file);
                    byte[] bytes = new byte[fileLength];
                    fileInputStream.read(bytes);
                    fileInputStream.close();

                    dataOutputStream.write(bytes, 0, fileLength);
                }

                dataOutputStream.flush();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getPath(InputStream inputStream) {
        String startLine = getStartLine(inputStream);

        if(startLine != null) {
            String[] str = startLine.split(" ");

            if (str.length >= 2) return str[1];
        }

        return "";
    }

    private String getStartLine(InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            return null;
        }
    }

    private File getFile(String name) {
        URL url = getClass().getResource(name);

        if(url == null) {
            responseCode = 404;
            return null;
        }

        responseCode = 200;
        return new File(url.getFile());
    }
}
