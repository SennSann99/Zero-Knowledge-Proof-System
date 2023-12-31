package com.client;

import java.math.BigInteger;
import java.util.ArrayList;

public class Avg_maxmin {
    public static BigInteger max, min;
    public Avg_maxmin(BigInteger q, String m, String n) {

        int amount = ClientVerifierThread.array_fi.size();

        // 加密后期望最大、最小值计算
        BigInteger temp_f_max = new BigInteger("0");
        BigInteger f_max = new BigInteger("0");
        for (int i = 0; i < amount; i++){
            temp_f_max = new BigInteger(m);
            f_max = f_max.add(temp_f_max);
        }
        BigInteger f_pow_max = f_max.divide(BigInteger.valueOf(amount));
        max = q.pow(Integer.parseInt(f_pow_max.toString()));

        BigInteger temp_f_min = new BigInteger("0");
        BigInteger f_min = new BigInteger("0");
        for (int i = 0; i < amount; i++){
            temp_f_min = new BigInteger(n);
            f_min = f_min.add(temp_f_min);
        }
        BigInteger f_pow_min = f_min.divide(BigInteger.valueOf(amount));
        min = q.pow(Integer.parseInt(f_pow_min.toString()));
    }
}