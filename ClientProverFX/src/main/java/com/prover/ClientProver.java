package com.prover;

/**
 * 零知识证明——证明者
 * 提供知识的一方
 */

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
public class ClientProver{
    public static SocketChannel socketChannel;
    public void startClient(String name) throws IOException {
        // 连接服务端
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost",2222));

        // 接收服务端响应数据
        Selector selector = Selector.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 创建响应线程
        new Thread(new ClientProverThread(selector)).start();
    }
}