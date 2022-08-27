package kr.hs.sunrint.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpStreamReader {
    public static String getStartLine(InputStream inputStream) {
        try {
            return new BufferedReader(new InputStreamReader(inputStream)).readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
