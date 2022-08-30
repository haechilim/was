package kr.hs.sunrint.service;

import java.io.*;
import java.net.URL;

public class FileLoader {
    public File getFile(String path) {
        URL url = getUrl(path);

        return url != null ? new File(url.getFile()) : null;
    }

    public String getFileToString(File file) {
        try {
            FileInputStream stream = new FileInputStream(file);
            int length = (int) file.length();
            byte[] bytes = new byte[length];

            stream.read(bytes, 0, length);

            return new String(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static File getFile(String name, URL url, String responseCode, String contentsType) {
        if(url == null) {
            responseCode = "404";
            return null;
        }

        responseCode = "200";
        return new File(url.getFile());
    }

    private URL getUrl(String path) {
        return getClass().getResource("/static" + path);
    }
}
