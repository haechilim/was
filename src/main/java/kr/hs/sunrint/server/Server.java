package kr.hs.sunrint.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args ) {
        try {
            ServerSocket serverSocket = new ServerSocket(9000);

            while (true) {
                Socket socket = serverSocket.accept();

                OutputStream outputStream = socket.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

                dataOutputStream.writeBytes("HTTP/1.1 200 OK \r\n");
                dataOutputStream.writeBytes("Content-Type: text/html\r\n");
                dataOutputStream.writeBytes("\r\n");
                dataOutputStream.writeBytes("<html lang=\"ko\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Document</title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<h1>hello world</h1>\n" +
                        "</body>\n" +
                        "</html>");
                dataOutputStream.flush();


                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
