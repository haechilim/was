package kr.hs.sunrint.service;

import kr.hs.sunrint.domain.MimeType;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class FileLoader {
    private static File getFile(String name, URL url, String responseCode, String contentsType) {
        if(url == null) {
            responseCode = "404";
            return null;
        }

        responseCode = "200";
        return new File(url.getFile());
    }

    public static String getPath(InputStream inputStream, String contentsType) {
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
