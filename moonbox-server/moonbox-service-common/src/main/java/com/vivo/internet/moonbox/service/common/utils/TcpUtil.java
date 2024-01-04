package com.vivo.internet.moonbox.service.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TcpUtil {

    public static void main(String[] args) {
        tcpStart("10.197.6.149",5555,"ls");
    }
    public static String tcpStart(String host,Integer port,String command) {
        // Moonbox Server的主机名和端口号
        String serverHost = host;
        int serverPort = port; // 替换为实际的端口号
        Socket socket = null;

        try {
            // 创建与Moonbox Server的Socket连接
            socket = new Socket(serverHost, serverPort);

            // 获取输入流和输出流
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // 向Moonbox Server发送数据
            String requestData = command;
            System.out.println("requestData = " + requestData);
            outputStream.write(requestData.getBytes());

            // 读取Moonbox Server的响应
            byte[] buffer = new byte[1024];
            int bytesRead;
            StringBuilder responseBuilder = new StringBuilder();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                responseBuilder.append(new String(buffer, 0, bytesRead));
            }
            String serverResponse = responseBuilder.toString();
            System.out.println("Received response from Moonbox Server: " + serverResponse);


            // 在这里处理Moonbox Server的响应
            // 您可以根据需要执行命令或处理其他操作
            // 向Moonbox Server发送数据
          /*  while (true) {
                System.out.println("发送数据 = " + requestData);
                outputStream.write(requestData.getBytes());
                Thread.sleep(2000);
            }*/

            // 关闭连接
            socket.close();
            return serverResponse;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}