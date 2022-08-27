package kr.hs.sunrint.service;

import java.io.*;
import java.net.Socket;

public class HttpStreamWriter {
    public static void write(Socket socket, File file, String responseCode, String contentsType) {
        try {
            OutputStream outputStream = socket.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeBytes("HTTP/1.1 " + responseCode + " OK \r\n");
            dataOutputStream.writeBytes("Content-Type: " + contentsType + "\r\n");
            dataOutputStream.writeBytes("\r\n");

            if(file != null) {
                int fileLength = (int) file.length();
                byte[] bytes = new byte[fileLength];

                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
                fileInputStream.close();

                dataOutputStream.write(bytes, 0, fileLength);
            }

            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
