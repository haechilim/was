package kr.hs.sunrint;

import kr.hs.sunrint.service.HttpServer;

public class Main {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.start();
    }
}
