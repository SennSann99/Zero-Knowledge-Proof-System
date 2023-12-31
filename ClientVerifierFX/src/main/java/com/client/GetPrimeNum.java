package com.client;

import java.math.BigInteger;
import java.util.Random;

public class GetPrimeNum {
    int num_size = 0;
    boolean sign;
    int n, n1, n2;
    //int n3;
    BigInteger q;

    public GetPrimeNum(int n, int n1, int n2){

        long StartPrimeTime = System.currentTimeMillis();

        this.n = n;
        this.n1 = n1;
        this.n2 = n2;
        //this.n3 = n3;
        Random r = new Random();

        int r0 = r.nextInt(n1) + n2;
        //int r1 = r.nextInt(n3);

        for (int i = r0; i < n; i++){
            //先排除能被2整除的
            if(i % 2 == 0 && i != 2) continue;
            sign = true;
            for (int j = 2; j<i; j++){
                //再排除能被任意大于2的数整除的
                if(i%j == 0){
                    sign = false;
                    break;
                }
            }

            //对于满足条件的i
            if (sign){
                num_size++;
                //System.out.println("Checked");

                //define q
                q = new BigInteger(String.valueOf(i));
                //p = q * Big_r1 + 1;
                break;
            }
        }

        long EndPrimeTime = System.currentTimeMillis();

        System.out.println("素数生成耗时："+(EndPrimeTime - StartPrimeTime)+"毫秒");

    }
    public BigInteger Deliver_q(){
        q = this.q;
        return q;
    }
}