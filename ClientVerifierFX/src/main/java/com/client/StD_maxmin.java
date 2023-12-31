package com.client;

import java.math.BigInteger;

public class StD_maxmin {
    public static BigInteger max, min;
    public StD_maxmin(BigInteger q, String m, String n){
        max = q.pow(Integer.parseInt(m));

        min = q.pow(Integer.parseInt(n));
    }
}
