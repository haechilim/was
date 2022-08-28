package kr.hs.sunrint.service;

import java.io.*;
import java.net.Socket;

public class HttpStreamWriter {
    public void write(OutputStream outputStream, int responseCode) {
        write(outputStream, responseCode, null, null);
    }

    public void write(OutputStream outputStream, File file, int responseCode, String contentsType) {
        try {
            if(file != null) {
                int length = (int) file.length();
                byte[] bytes = new byte[length];

                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(bytes);
                fileInputStream.close();

                write(outputStream, responseCode, contentsType, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(OutputStream outputStream, int responseCode, String contentsType, byte[] contents) {
        try {
            DataOutputStream stream = new DataOutputStream(outputStream);

            stream.writeBytes("HTTP/1.1 " + responseCode + " OK \r\n");
            if(contentsType != null) stream.writeBytes("Content-Type: " + contentsType + "\r\n");
            stream.writeBytes("\r\n");
            if(contents != null) stream.write(contents);

            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}