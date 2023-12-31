package com.server;

import java.math.BigInteger;
import java.util.ArrayList;

public class CalcuProcedure {
    public static BigInteger no_mod_r;
    public static BigInteger C;
    public static BigInteger w;
    public static BigInteger temp_wi;
    public static BigInteger f;
    public static BigInteger bigamount;
    public static ArrayList<BigInteger> array_r;
    public static ArrayList<String> array_fi;
    public static ArrayList<String> array_wi;
    public static String p;
    public static String q;
    public static String g;
    public static String h;
    Runtime runtime = Runtime.getRuntime();
    public CalcuProcedure(String x){
        /**
         * 读取数据、赋值于变量并进行部分计算
         */

        CSVRead csvRead = new CSVRead(x);
        array_wi = csvRead.array_wi;
        array_fi = csvRead.Deliver_fi();

        // todo 用户数
        int amount = csvRead.Deliver_amount();
        System.out.println("用户数:" + amount);

        long StartfTime = System.currentTimeMillis();

        // todo f值 所有ID的值总和
        f = csvRead.Deliver_f();
        System.out.println("ID值总和:" + f);

        long EndfTime = System.currentTimeMillis();

        System.out.println("f生成耗时："+(EndfTime-StartfTime)+"毫秒");

        temp_wi = new BigInteger("0");
        for (int i=0; i < array_wi.size(); i++){
            temp_wi = temp_wi.add(new BigInteger(array_wi.get(i)));
        }
        System.out.println("信用分总和:" + temp_wi);

        // todo 信用分平均值
        bigamount = BigInteger.valueOf(amount);
        //avg = w.divide(bigamount);
        //System.out.println("平均值:" + avg);

        long StartrTime = System.currentTimeMillis();

        // todo r值
        // todo r = 累加fi*ri
        no_mod_r = csvRead.Deliver_NoMod_r();
        System.out.println("r值（取余前）:" + no_mod_r);

        long EndrTime = System.currentTimeMillis();

        System.out.println("r生成耗时："+(EndrTime-StartrTime)+"毫秒");

        long StartCTime = System.currentTimeMillis();

        // todo 为计算C而赋值
        array_r = csvRead.Deliver_array_r();
        p = MainThread.p;
        q = MainThread.q;
        g = MainThread.g;
        h = MainThread.h;

        // todo C(mod p)值

        long startfree = runtime.freeMemory();
        long starttotal = runtime.totalMemory();
        C = CalcuC(p, g, h, f, no_mod_r);
        long endfree = runtime.freeMemory();
        long endtotal = runtime.totalMemory();
        System.out.println("JVM消耗内存："+((endtotal-endfree)-(starttotal-startfree))/1024/1024+"MB");

        System.out.println("C值: "+ C);

        long EndCTime = System.currentTimeMillis();
        System.out.println("C生成耗时："+(EndCTime-StartCTime)+"毫秒");
    }
    private BigInteger CalcuC(String received_p, String received_g, String received_h, BigInteger received_f, BigInteger received_r){

        // todo 另一个分治算法的案例
        BigInteger thisC = ((new BigInteger(received_g)).remainder(new BigInteger(received_p)).pow(Integer.valueOf(received_f.toString())).remainder(new BigInteger(received_p))).multiply(
                (new BigInteger(received_h)).remainder(new BigInteger(received_p)).pow(Integer.valueOf(received_r.toString())).remainder(new BigInteger(received_p))).remainder(
                new BigInteger(received_p));

        //BigInteger thisC = ((new BigInteger(received_g)).pow(Integer.valueOf(received_f.toString()))).multiply((new BigInteger(received_h)).pow(Integer.valueOf(received_r.toString()))).remainder(new BigInteger(received_p));

        return thisC;
    }
}
