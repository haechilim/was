package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.HttpResponseCode;

import java.io.*;
import java.net.Socket;

public class HttpStreamWriter {
    public void write(OutputStream outputStream, HttpResponseCode responseCode) {
        write(outputStream, responseCode, null, null);
    }

    public void write(OutputStream outputStream, File file, HttpResponseCode responseCode, String contentsType) {
        try {
            if(file != null) {
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];

                fileInputStream.read(bytes);
                fileInputStream.close();

                write(outputStream, responseCode, contentsType, bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(OutputStream outputStream, HttpResponseCode responseCode, String contentsType, byte[] contents) {
        try {
            DataOutputStream stream = new DataOutputStream(outputStream);

            stream.writeBytes("HTTP/1.1 " + responseCode.getCode() + " " + responseCode.name() + " \r\n");
            if(contentsType != null) stream.writeBytes("Content-Type: " + contentsType + "\r\n");
            stream.writeBytes("\r\n");
            if(contents != null) stream.write(contents);

            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}