package com.server;

import javax.swing.plaf.synth.Region;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;


public class MainThread implements Runnable {
    private String msg;
    private Selector selector;
    private SocketChannel socketChannel;
    public static String region;
    public static String upperlimit; public static String downlimit;
    public static String p; public static String q; public static String g; public static String h;
    public static String p_p; public static String p_q; public static String p_g; public static String p_h;

    public MainThread(String msg, Selector selector, SocketChannel socketChannel){
        this.msg = msg;
        this.selector = selector;
        this.socketChannel = socketChannel;
    }
    @Override
    public void run(){
        if ((msg.substring((msg.length())-5,msg.length())).equalsIgnoreCase("start")){
            System.out.println("<<<--- 您 已 进 入 计 算 模 式 --->>>");
        }
        if ((msg.substring(0,7)).equalsIgnoreCase("region:")){
            region = msg.substring(7, msg.length());
            System.out.println("<<<--- Region --->>>");
            //System.out.println(region);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("upperli")){
            upperlimit = msg.substring(11, msg.length());
            System.out.println("<<<--- Upper Limit --->>>");
            //System.out.println(upperlimit);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("downlim")){
            downlimit = msg.substring(10, msg.length());
            System.out.println("<<<--- Down Limit --->>>");
            //System.out.println(downlimit);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("valuep:")){
            p = msg.substring(7, msg.length());
            System.out.println("<<<--- VALUE P --->>>");
            //System.out.println(p);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("valueq:")){
            q = msg.substring(7, msg.length());
            System.out.println("<<<--- VALUE Q --->>>");
            //System.out.println(q);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("valueg:")){
            g = msg.substring(7, msg.length());
            System.out.println("<<<--- VALUE G --->>>");
            //System.out.println(g);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("valueh:")){
            h = msg.substring(7, msg.length());
            System.out.println("<<<--- VALUE H --->>>");
            //System.out.println(h);
        }

        /**
         * 以下是监听Prover的pqgh
         */

        else if ((msg.substring(0,7)).equalsIgnoreCase("Prov p:")){
            p_p = msg.substring(7, msg.length());
            System.out.println("<<<--- Prover's VALUE P --->>>");
            //System.out.println(p_p);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("Prov q:")){
            p_q = msg.substring(7, msg.length());
            System.out.println("<<<--- Prover's VALUE Q --->>>");
            //System.out.println(p_q);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("Prov g:")){
            p_g = msg.substring(7, msg.length());
            System.out.println("<<<--- Prover's VALUE G --->>>");
            //System.out.println(p_g);
        }
        else if ((msg.substring(0,7)).equalsIgnoreCase("Prov h:")){
            p_h = msg.substring(7, msg.length());
            System.out.println("<<<--- Prover's VALUE H --->>>");
            //System.out.println(p_h);
            System.out.println("<<<--- 请 输 入 ”Execute“ 来 继 续 执 行 --->>>");
            // 于此处阻塞
            Scanner scanner = new Scanner(System.in);
            if(scanner.hasNext()){
                String inputMsg = scanner.nextLine();
                if (inputMsg.equalsIgnoreCase("execute")){
                    // 比较两者的pqgh是否相同
                    //此时已收到p,q,g,h以及地区名(以及针对业务设计的特殊值
                    if (p.equals(p_p) && q.equals(p_q) && g.equals(p_g) && h.equals(p_h)){
                        System.out.println("<<<--- 双 方 公 开 锁 相 同 ！ --->>>");
                        System.out.println("<<<--- 将 处 理 "+ region +" 地 区 数 据 --->>>");
                        CalcuProcedure calcuProcedure = new CalcuProcedure(region);
                        String r = "r:"+String.valueOf(CalcuProcedure.no_mod_r);
                        String C = "C:"+String.valueOf(CalcuProcedure.C);
                        try {
                            sendToClient(r, selector, socketChannel);
                            Thread.sleep(10);
                            sendToClient(C, selector, socketChannel);
                            Thread.sleep(10);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        long StartCiTime = System.currentTimeMillis();

                        for (int i = 0; i < (CalcuProcedure.bigamount).intValue(); i++){
                            BigInteger big_r = CalcuProcedure.array_r.get(i);
                            int r0 = Integer.valueOf(big_r.toString());
                            BigInteger h = (new BigInteger(CalcuProcedure.h));
                            h = h.remainder(new BigInteger(CalcuProcedure.p));
                            BigInteger Ci_right = h.pow(r0);
                            Ci_right = Ci_right.remainder(new BigInteger(CalcuProcedure.p));
                            BigInteger g = new BigInteger(CalcuProcedure.g);
                            g = g.remainder(new BigInteger(CalcuProcedure.p));

                            String wi = CalcuProcedure.array_wi.get(i);
                            //todo 看一下 对不对 2023/05/31
                            //BigInteger Ci_left = g.pow(Integer.parseInt(wi));
                            BigInteger Ci_left = g.pow(Integer.parseInt(wi));
                            Ci_left = Ci_left.remainder(new BigInteger(CalcuProcedure.p));
                            BigInteger Ci = Ci_left.multiply(Ci_right);
                            Ci = Ci.remainder(new BigInteger(CalcuProcedure.p));
                            System.out.println("C[" + i + "]: " + Ci);
                            // 发送
                            try {
                                sendToClient("Ci:"+String.valueOf(Ci), selector, socketChannel);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        long EndCiTime = System.currentTimeMillis();

                        long StartFiTime = System.currentTimeMillis();

                        for (int i = 0; i < (CalcuProcedure.bigamount).intValue(); i++){
                            String fi = CalcuProcedure.array_fi.get(i);
                            System.out.println("f[" + i + "]: " + fi);
                            // 发送
                            try {
                                sendToClient("fi:"+fi, selector, socketChannel);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        long EndFiTime = System.currentTimeMillis();

                        System.out.println("Ci发送耗时：" + (EndCiTime - StartCiTime) + "ms");
                        System.out.println("fi发送耗时：" + (EndFiTime - StartFiTime) + "ms");

                        // =============================================================================================
                        // todo 计算count与发送
                        long StartCountTime = System.currentTimeMillis();

                        BigInteger count;
                        count = (new BigInteger(q)).pow(Integer.parseInt(String.valueOf(CalcuProcedure.bigamount)));

                        long EndCountTime = System.currentTimeMillis();

                        System.out.println("count:"+count);
                        System.out.println("count生成耗时："+(EndCountTime - StartCountTime)+"毫秒");

                        try {
                            sendToClient("count:"+String.valueOf(count), selector, socketChannel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        // =============================================================================================
                        // todo 计算average与发送
                        long StartAvgTime = System.currentTimeMillis();

                        // 发送
                        //BigInteger avg = avg_C(CalcuProcedure.g, CalcuProcedure.avg_f);
                        BigInteger sym = CalcuProcedure.temp_wi;
                        BigInteger avg;
                        avg = (new BigInteger(q)).pow((Integer.parseInt((sym.toString())))/(Integer.parseInt(String.valueOf(CalcuProcedure.bigamount))));
                        //BigInteger avg_right = (new BigInteger(h)).pow(Integer.parseInt((CalcuProcedure.no_mod_r).toString()));

                        long EndAvgTime = System.currentTimeMillis();

                        System.out.println("avg:"+avg);
                        //System.out.println("比较对象 后期删除 avg:"+(avg.remainder(new BigInteger("100"))));

                        System.out.println("avg生成耗时："+(EndAvgTime - StartAvgTime)+"毫秒");

                        try {
                            sendToClient("avg:"+String.valueOf(avg), selector, socketChannel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        // =============================================================================================
                        // todo 计算Standard Deviation(StD)与发送

                        long StartStDTime = System.currentTimeMillis();

                        BigInteger up_sum = new BigInteger("0");
                        for (int i = 0; i < (CalcuProcedure.bigamount).intValue(); i++){
                            if(i==0){
                                String wi_3 = CalcuProcedure.array_wi.get(i);
                                BigInteger temp_up_sum = ((new BigInteger(wi_3)).subtract(BigInteger.valueOf((Integer.parseInt((sym.toString())))/(Integer.parseInt(String.valueOf(CalcuProcedure.bigamount)))))).pow(2);
                                up_sum = temp_up_sum;
                            }else{
                                String wi_3 = CalcuProcedure.array_wi.get(i);
                                BigInteger temp_up_sum = ((new BigInteger(wi_3)).subtract(BigInteger.valueOf((Integer.parseInt((sym.toString())))/(Integer.parseInt(String.valueOf(CalcuProcedure.bigamount)))))).pow(2);
                                up_sum = up_sum.add(temp_up_sum);
                            }
                        }

                        BigDecimal StD;
                        //StD = (up_sum.divide(CalcuProcedure.bigamount)).pow(1/2);
                        //System.out.println("temp_avg:"+(Integer.parseInt((sym.toString())))/(Integer.parseInt(String.valueOf(CalcuProcedure.bigamount))));
                        //System.out.println("bigamount:"+CalcuProcedure.bigamount);
                        StD = (new BigDecimal(up_sum.toString()).divide(new BigDecimal(CalcuProcedure.bigamount),3,BigDecimal.ROUND_UP));

                        double x = StD.doubleValue();
                        Double d_std = Math.sqrt(x);
                        int int_std = (int) Math.round(d_std);
                        // TODO
                        StD = (new BigDecimal(q)).pow(int_std);
                        long EndStDTime = System.currentTimeMillis();

                        System.out.println("StD:"+StD);
                        System.out.println("StD生成耗时："+(EndStDTime - StartStDTime)+"毫秒");

                        /*
                        //todo 使用csv文件发送std
                        File writeFile = new File("C:\\Users\\G1\\OneDrive\\write.csv");
                        try{
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(writeFile));

                            bufferedWriter.newLine();
                            bufferedWriter.write(String.valueOf(count));
                            bufferedWriter.flush();

                            bufferedWriter.newLine();
                            bufferedWriter.write(String.valueOf(avg));
                            bufferedWriter.flush();

                            bufferedWriter.newLine();
                            bufferedWriter.write(String.valueOf(StD));
                            bufferedWriter.flush();

                            bufferedWriter.close();
                        }catch (IOException e){
                            System.out.println("文件读写出错");
                        }

                        try {
                            FileChannel fileChannel = FileChannel.open(Paths.get("C:\\Users\\G1\\OneDrive\\write.csv"));
                            transferFile(fileChannel, selector, socketChannel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                         */


                        try {
                            sendToClient("std:"+String.valueOf(StD), selector, socketChannel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        // =============================================================================================
                        try {
                            sendToClient("ready to continue", selector, socketChannel);
                            System.out.println("已发送完成的讯息");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        System.out.println("<<<--- 第 二 环 节 已 完 成 --->>>");

                    }else {
                        System.out.println("<<<--- 双 方 公 开 锁 不 同 ！ --->>>");
                    }
                }
            }
            scanner.close();
        }
    }
    // todo 这个不能放这里 要单独挑出来 并在comparethread里面调用

    private void sendToClient(String mmssgg, Selector selector, SocketChannel socketChannel) throws IOException {
        // 获取所有已经接入的客户端
        Set<SelectionKey> selectionKeySet = selector.keys();
        // 循环channel的广播
        for(SelectionKey selectionKey : selectionKeySet){
            // 获取里面的每个channel
            Channel targetChannel = selectionKey.channel();
            // 不需要发给自己
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                ((SocketChannel) targetChannel).write(StandardCharsets.UTF_8.encode(mmssgg));
            }
        }
    }
    private void transferFile(FileChannel fcnl, Selector selector, SocketChannel socketChannel) throws IOException {
        // 获取所有已经接入的客户端
        Set<SelectionKey> selectionKeySet = selector.keys();
        // 循环channel的广播
        for(SelectionKey selectionKey : selectionKeySet){
            // 获取里面的每个channel
            Channel targetChannel = selectionKey.channel();

            // 不需要发给自己
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel){
                fcnl.transferTo(0, fcnl.size(), socketChannel);
            }
        }
    }

    /*
    private BigInteger avg_C(String received_g, BigInteger received_f) {
        BigInteger C_average = (new BigInteger(received_g)).pow(Integer.valueOf(received_f.toString()));
        return C_average;
    }
     */
}
