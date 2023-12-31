package com.server;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

/**
 * 对Churn Modeling.csv中地区名与信用分的读取
 *
 */

public class CSVRead {
    BigInteger result = new BigInteger("0");
    BigInteger R = new BigInteger("0");
    ArrayList<String> array_wi = new ArrayList<>();
    ArrayList<String> array_fi = new ArrayList<>();
    ArrayList<BigInteger> array_r = new ArrayList<>();
    int amount = 0;
    public CSVRead(String NationName) {

        Random r = new Random();
        BigInteger each_R;

        long DBStart = System.currentTimeMillis();

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase mongoDatabase = mongoClient.getDatabase("database");
        MongoCollection mongoCollection = mongoDatabase.getCollection("collection mini");

        //查询指定文档
        Bson bson_filter = Filters.eq("Geography",NationName);
        FindIterable findIterable = mongoCollection.find(bson_filter);
        MongoCursor mongoCursor = findIterable.iterator();

        while(mongoCursor.hasNext()){
            Document document = (Document) mongoCursor.next();
            //System.out.println("已检索： "+document.get("CreditScore"));
            array_wi.add(document.get("CreditScore").toString());
            array_fi.add(document.get("Age").toString());

            BigInteger each_value = new BigInteger(document.get("CreditScore").toString());
            BigInteger each_ID = new BigInteger(document.get("Age").toString());
            BigInteger multiplied = each_value.multiply(each_ID);
            result = result.add(multiplied);

            int r0 = r.nextInt(3) + 1;
            String rr0 = String.valueOf(r0);
            BigInteger R0 = new BigInteger(rr0);
            array_r.add(R0);

            each_R = each_ID.multiply(R0);
            R = R.add(each_R);
            amount ++;
        }

        long DBStop = System.currentTimeMillis();
        System.out.println("MongoDB耗时："+(DBStop-DBStart)+"毫秒");


        /*

        String csvFile = "C:\\Users\\G1\\OneDrive\\Churn Modeling.csv";
        BufferedReader br = null;
        String line = "";
        String csvSplitComma = ",";

        try {
            long CsvStart = System.currentTimeMillis();

            br = new BufferedReader(new FileReader(csvFile));
            Random r = new Random();
            BigInteger each_R;

            while ((line = br.readLine()) != null) {
                String[] Churn = line.split(csvSplitComma);
                if (NationName.equalsIgnoreCase(Churn[4])){
                    //System.out.println(Churn[4] + ", " + Churn[3]);

                    String each_valu = String.valueOf(Churn[3]);
                    array_fi.add(each_valu);

                    BigInteger each_value = new BigInteger(each_valu);
                    result = result.add(each_value);

                    int r0 = r.nextInt(3) + 1;
                    String rr0 = String.valueOf(r0);
                    BigInteger R0 = new BigInteger(rr0);
                    array_r.add(R0);

                    each_R = each_value.multiply(R0);
                    R = R.add(each_R);
                    amount ++;
                }else{
                    ;
                }
            }

            long CsvStop = System.currentTimeMillis();
            System.out.println("csv读取耗时"+(CsvStop-CsvStart)+"毫秒");
            //System.out.println("f:" + result);
            //System.out.println("Without mod, r is:" + R);

        }catch(IOException e){
            e.printStackTrace();
        }


         */

    }
    public ArrayList<BigInteger> Deliver_array_r(){
        // 随机数的数组
        array_r = this.array_r;
        return array_r;
    }
    public ArrayList<String> Deliver_wi(){
        // Churn Modeling.csv中的信用分组成的数组
        array_wi = this.array_wi;
        return array_wi;
    }
    public ArrayList<String> Deliver_fi(){
        // Churn Modeling.csv中的ID号组成的数组
        array_fi = this.array_fi;
        return array_fi;
    }
    public BigInteger Deliver_f(){
        // 所有ID值的总和
        result = this.result;
        return result;
    }
    public int Deliver_amount(){
        // 用户数
        amount = this.amount;
        return amount;
    }
    public BigInteger Deliver_NoMod_r(){
        // 加密值的和（未取余
        R = this.R;
        return R;
    }
}