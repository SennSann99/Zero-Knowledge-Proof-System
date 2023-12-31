package com.prover;

import com.clientproverfx.ProverApplication;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class ClientProverThread implements Runnable {
    private Selector selector;

    // static静态变量 到ClientProver引用
    public ClientProverThread(Selector selector){
        this.selector = selector;
    }
    @Override
    public void run(){
        try{
            // 循环等待链接
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
                    // 如果可读状态
                    if (selectionKey.isReadable()){
                        readOperator(selector, selectionKey);
                    }
                }
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
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
        }
        // 将channel再次注册到selector上, 监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 把客户端发来的信息，广播到其他客户端
        if (msg.length() > 0
                && !(msg.substring(0,10)).equalsIgnoreCase("验证方第一环节已完成")
                && !(msg.substring(0,7)).equalsIgnoreCase("valuep:")
                && !(msg.substring(0,7)).equalsIgnoreCase("valueq:")
                && !(msg.substring(0,7)).equalsIgnoreCase("valueg:")
                && !(msg.substring(0,7)).equalsIgnoreCase("valueh:")){
            // 打印
            System.out.println(msg);

        }else if((msg.substring(0,10)).equalsIgnoreCase("验证方第一环节已完成")){
            ProverApplication.fxState.setText("已接收");
            System.out.println("已接受验证方第一环节已完成信息");
        }else if((msg.substring(0,7)).equalsIgnoreCase("valuep:")){
            ProverApplication.fxP.setText(msg.substring(7,msg.length()));
            System.out.println("Value P:"+msg.substring(7,msg.length()));
        }else if((msg.substring(0,7)).equalsIgnoreCase("valueq:")){
            ProverApplication.fxQ.setText(msg.substring(7,msg.length()));
            System.out.println("Value Q:"+msg.substring(7,msg.length()));
        }else if((msg.substring(0,7)).equalsIgnoreCase("valueg:")){
            ProverApplication.fxG.setText(msg.substring(7,msg.length()));
            System.out.println("Value G:"+msg.substring(7,msg.length()));
        }else if((msg.substring(0,7)).equalsIgnoreCase("valueh:")){
            ProverApplication.fxH.setText(msg.substring(7,msg.length()));
            System.out.println("Value H:"+msg.substring(7,msg.length()));
        }
    }
}
