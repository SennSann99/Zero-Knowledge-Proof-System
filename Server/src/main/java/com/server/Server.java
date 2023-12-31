package com.server;

/**
 * 第三方——计算者、监督者
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;

public class Server{
    public void startServer() throws IOException {
        // 创建Selector
        Selector selector = Selector.open();
        // 创建ServerSocketChannel通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听端口9
        serverSocketChannel.bind(new InetSocketAddress(2222));
        // 非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 将channel注册至selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 响应线程
        System.out.println("<<<--- 服 务 启 动 --->>>");
        new Thread(new ServerThread(selector, serverSocketChannel)).start();
    }

    // 主程序入口
    public static void main(String[] args){
        try{
            new Server().startServer();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}