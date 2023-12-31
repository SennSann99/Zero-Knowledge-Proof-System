package com.client;

import com.clientverifierfx.VerifierApplication;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class ClientVerifierThread implements Runnable {

    private Selector selector;
    public static String received_r;
    public static String received_C;
    public static String received_f;
    public static String received_count;
    public static String received_avg;
    public static String received_StD;
    public static ArrayList<String> array_Ci = new ArrayList<String>();
    public static ArrayList<String> array_fi = new ArrayList<String>();
    BigInteger result;
    public static BigInteger myC, otherC;
    Runtime runtime = Runtime.getRuntime();
    public ClientVerifierThread(Selector selector){
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
        }catch (Exception e){
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
                && !(msg.substring(0,2)).equalsIgnoreCase("r:")
                && !(msg.substring(0,2)).equalsIgnoreCase("C:")
                && !(msg.substring(0,3)).equalsIgnoreCase("Ci:")
                && !(msg.substring(0,3)).equalsIgnoreCase("fi:")
                && !(msg.substring(0,6)).equalsIgnoreCase("count:")
                && !(msg.substring(0,4)).equalsIgnoreCase("avg:")
                && !(msg.substring(0,4)).equalsIgnoreCase("std:")
                && !(msg.substring(0,5)).equalsIgnoreCase("ready")) {
            // 打印
            System.out.println(msg);
        } else if ((msg.substring(0,2)).equalsIgnoreCase("r:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            received_r = msg.substring(2, msg.length());
        }else if((msg.substring(0,2)).equalsIgnoreCase("C:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            received_C = msg.substring(2, msg.length());
        }else if((msg.substring(0,3)).equalsIgnoreCase("Ci:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            String received_Ci = msg.substring(3, msg.length());
            array_Ci.add(received_Ci);
        }else if((msg.substring(0,3)).equalsIgnoreCase("fi:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            String received_fi = msg.substring(3, msg.length());
            array_fi.add(received_fi);
        }else if((msg.substring(0,6)).equalsIgnoreCase("count:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            received_count = msg.substring(6, msg.length());
        }else if((msg.substring(0,4)).equalsIgnoreCase("avg:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            received_avg = msg.substring(4, msg.length());
        }else if((msg.substring(0,4)).equalsIgnoreCase("std:")){
            System.out.println("<<<--- 已 收 到 "+msg+" --->>>");
            received_StD = msg.substring(4, msg.length());
            //System.out.println("比较对象 后期删除 avg:"+((new BigInteger(received_avg)).remainder(new BigInteger("100"))));
            // 注意 只能对每个msg的substring做判定 若不用substring的话 可能因为传输过程设计\n的断尾符 而无法识别
        }else if((msg.substring(0,5)).equalsIgnoreCase("ready")){
            long starttotal = runtime.totalMemory();
            long startfree = runtime.freeMemory();

            System.out.println("<<<--- 开 始 验 算 --->>>");
            String str_fi, str_fi1;
            BigInteger temp_result, temp_result1;
            BigInteger original = new BigInteger("1");

            long startCTime = System.currentTimeMillis();

            // 为了运算速度 代入模运算公式 new_p为模
            for (int i = 0; i < array_Ci.size(); i++){

                str_fi = ClientVerifierThread.array_fi.get(i);
                int fi = Integer.parseInt(str_fi);
                BigInteger temporary_Ci = new BigInteger(array_Ci.get(i));

                // todo 分治算法下
                temporary_Ci = temporary_Ci.remainder(VerifierApplication.p);
                temp_result = temporary_Ci.pow(fi);
                temp_result = temp_result.remainder(VerifierApplication.p);

                //todo 常规算法下
                //temp_result = temporary_Ci.pow(fi);

                //当前相邻两个项结果，算到最后，result为答案，需要再进行mod p
                result = original.multiply(temp_result);

                //original 为上次结果的值，初始值为1
                original = result;

                System.out.println("<<<--- 计 算 中，第 ["+i+"] 轮 --->>>");
                //System.out.println("测试用：result为" + result);

            }

            //todo 23/02/06修改
            myC = result.remainder(VerifierApplication.p);

            long endfree = runtime.freeMemory();
            long endtotal = runtime.totalMemory();
            System.out.println("JVM消耗内存："+((endtotal-endfree)-(starttotal-startfree))/1024+"KB");
            System.out.println("JVM消耗内存："+((endtotal-endfree)-(starttotal-startfree))/1024/1024+"MB");

            long endCTime = System.currentTimeMillis();

            System.out.println("C值计算耗时： " + (endCTime-startCTime) + "毫秒");

            otherC = new BigInteger(ClientVerifierThread.received_C);
            System.out.println("<<<--- 您 计 算 的 C 值 为: " +myC+" --->>>");
            System.out.println("<<<--- 证 明 者 C 值 为: " +otherC+" --->>>");
            VerifierApplication.fxState.setText("接收完毕");

        }
    }
}
