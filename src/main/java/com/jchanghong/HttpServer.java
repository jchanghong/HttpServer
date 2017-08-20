package com.jchanghong;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: jiang
 * \* Date: 2017/8/20 0020
 * \* Time: 9:02
 * \
 */
public class HttpServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println(socket.getPort());
            handle(socket);
        }
    }

    private static void handle(final Socket socket) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Scanner scanner = new Scanner(socket.getInputStream());
                    PrintWriter writer = new PrintWriter(socket.getOutputStream());
                    StringBuilder builder = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        System.out.println(line);
                        if (line.length() < 1) {
                            break;
                        }
                    }

                    System.out.println("end---------");

                    builder.append("HTTP/1.1 200 OK\r\n");
//                    builder.append("\n");
                    builder.append("Content-Type:  text/html;charset=utf-8\r\n");
                    builder.append("Server:Apache Tomcat/5.0.12\r\n");
                    builder.append("Date:Mon,6Oct2003 13:23:42 GMT\r\n");
                    String body = "<html><body>helokdddddddddddddddddddddd<body><html>";
                    builder.append("Content-Length:" + body.length()+"\r\n\n");
                    builder.append(body);
                    System.out.println(builder.toString());

//                    String body1 = "HTTP/1.1 200 OK\n\n<html><body>" +
//                            "木木彬的singleThreadWebServer: </body></html>";

                   socket.getOutputStream().write(builder.toString().getBytes());
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
