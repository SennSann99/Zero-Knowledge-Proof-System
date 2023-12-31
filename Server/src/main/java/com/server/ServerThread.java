package com.server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ServerThread implements Runnable {
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    public ServerThread(Selector selector, ServerSocketChannel serverSocketChannel) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
    }
    @Override
    public void run(){
        try{
            for(;;){
                int readChannels = selector.select();
                if (readChannels == 0){
                    continue;
                }
                // 获取可用Channel
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                // 迭代器遍历
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                    // 防止重复 移出
                    iterator.remove();
                    // 根据2种就绪状态，实现操作
                    if (selectionKey.isAcceptable()){
                        acceptOperator(serverSocketChannel, selector);
                    }
                    if (selectionKey.isReadable()){
                        readOperator(selector, selectionKey);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    //处理接收状态
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        // 接入状态下，创建socketChannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 非阻塞模式
        socketChannel.configureBlocking(false);
        // 把channel注册到selector
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 客户端回复信息
        socketChannel.write(StandardCharsets.UTF_8.encode("欢迎！您已成功连接！"));
    }

    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException{
        // 从SelectionKey获取已经就绪的通道
        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
        // 创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024*1024);
        // 循环读取客户端发来的信息
        int readLength = socketChannel.read(byteBuffer);
        String msg = "";
        if (readLength > 0){
            //切换读模式
            byteBuffer.flip();
            //读取内容
            msg += StandardCharsets.UTF_8.decode(byteBuffer);
        }else if(readLength == -1){
            System.out.println("<<<--- 出 现 服 务 断 开  --->>>");
        }
        // 将channel再次注册到selector上, 监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 接收并赋值的线程
        new Thread(new MainThread(msg, selector, socketChannel)).start();
        // 把客户端发来的信息，广播到其他客户端
        if (msg.length() > 0){
            // 广播给其他客户端
            System.out.println(msg);
            castToClient(msg, selector, socketChannel);
        }
    }

    private void castToClient(String msg, Selector selector, SocketChannel socketChannel) throws IOException{
        // 获取所有已经接入的客户端
        Set<SelectionKey> selectionKeySet = selector.keys();
        // 循环channel的广播
        for(SelectionKey selectionKey : selectionKeySet){
            //System.out.println(selectionKey);
            // 获取里面的每个channel
            Channel targetChannel = selectionKey.channel();
            // 不需要发给自己
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                ((SocketChannel) targetChannel).write(StandardCharsets.UTF_8.encode(msg));
            }
        }
    }
}
