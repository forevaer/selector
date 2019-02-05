package com.godme.selector;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioMain {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(8989));
        while(true){
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            int readLength = -1;
            byte[] bs = new byte[1024];
            while((readLength = inputStream.read(bs))!=-1){
                outputStream.write(bs, 0 , readLength);
            }
            inputStream.close();
            outputStream.close();
        }
    }
}
