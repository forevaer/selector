package com.godme.selector;

import com.sun.security.ntlm.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Set;

public class NioMain {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost", 8989));
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while(true){
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            selectedKeys.forEach(selectedKey->{
               try{
                  if(selectedKey.isAcceptable()){
                      ServerSocketChannel server = (ServerSocketChannel) selectedKey.channel();
                      SocketChannel socketChannel = server.accept();
                      socketChannel.configureBlocking(false);
                      socketChannel.register(selector, SelectionKey.OP_READ);
                  }else if(selectedKey.isReadable()){
                      SocketChannel client = (SocketChannel) selectedKey.channel();
                      ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                      client.configureBlocking(false);
                      client.read(byteBuffer);
                      byteBuffer.flip();
                      client.write(byteBuffer);
                  }
               }catch (Exception e){
                   e.printStackTrace();
               }
            });
            selectedKeys.clear();
        }
    }
}
