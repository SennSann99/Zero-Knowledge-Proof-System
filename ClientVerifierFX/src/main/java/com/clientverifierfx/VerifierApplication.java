package com.clientverifierfx;

import com.client.*;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class VerifierApplication extends Application {
    private TextField fxRegion = new TextField();
    private TextField fxCountUpLimit = new TextField();
    private TextField fxCountDownLimit = new TextField();
    private TextField fxAvgUpLimit = new TextField();
    private TextField fxAvgDownLimit = new TextField();
    private TextField fxStdUpLimit = new TextField();
    private TextField fxStdDownLimit = new TextField();
    public static TextField fxState = new TextField();
    private TextField fxTrust = new TextField();
    private TextField fxVerify1 = new TextField();
    private TextField fxVerify2 = new TextField();
    private TextField fxVerify3 = new TextField();
    private TextField fxVerify4 = new TextField();
    private TextField fxVerify5 = new TextField();
    private TextField fxVerify6 = new TextField();
    private Button fxStart = new Button("启动服务");
    private Button fxResult = new Button("获取结果");
    public static String fxRegionText;
    public static String fxCountUpLimitText;
    public static String fxCountDownLimitText;
    public static String fxAvgUpLimitText;
    public static String fxAvgDownLimitText;
    public static String fxStdUpLimitText;
    public static String fxStdDownLimitText;
    public static BigInteger p, q, g, h;
    @Override
    public void start(Stage primaryStage) throws IOException {
        GridPane gridPane = new GridPane();
        //gridPane.setStyle("-fx-background-color: White;");
        gridPane.setHgap(20);
        gridPane.setVgap(20);

        // todo 添加图标
        primaryStage.getIcons().add(new Image("C:\\Users\\G1\\OneDrive\\图标.png"));

        Text scenetitle1 = new Text("Welcome, Verifier");
        scenetitle1.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane.add(scenetitle1, 0, 0);
        Text scenetitle2 = new Text("欢迎使用数据交易系统");
        scenetitle2.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane.add(scenetitle2, 0, 1);
        Text scenetitle3 = new Text("请输入以下信息并启动服务");
        scenetitle3.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(scenetitle3, 0, 2);

        Text region = new Text("地区名称  >>");
        region.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(region,0,4);
        gridPane.add(fxRegion, 1, 4);

        Text upper_count = new Text("预测最大总数  >>");
        upper_count.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(upper_count,0,5);
        gridPane.add(fxCountUpLimit, 1, 5);

        Text down_count = new Text("预测最小总数  >>");
        down_count.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(down_count,0,6);
        gridPane.add(fxCountDownLimit, 1, 6);

        Text upper_average = new Text("预测最大平均分  >>");
        upper_average.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(upper_average,0,7);
        gridPane.add(fxAvgUpLimit, 1, 7);

        Text down_average = new Text("预测最小平均分  >>");
        down_average.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(down_average,0,8);
        gridPane.add(fxAvgDownLimit, 1, 8);

        Text upper_std = new Text("预测最大标准差  >>");
        upper_std.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(upper_std,0,9);
        gridPane.add(fxStdUpLimit, 1, 9);

        Text down_std = new Text("预测最小标准差  >>");
        down_std.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(down_std,0,10);
        gridPane.add(fxStdDownLimit, 1, 10);

        gridPane.add(fxStart, 1, 2);
        fxStart.setPrefSize(240,30);

        // Set properties for UI
        gridPane.setAlignment(Pos.CENTER);
        fxRegion.setAlignment(Pos.BOTTOM_CENTER);
        fxCountUpLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxCountDownLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxAvgUpLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxAvgDownLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxStdUpLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxStdDownLimit.setAlignment(Pos.BOTTOM_CENTER);
        fxTrust.setAlignment(Pos.BOTTOM_CENTER);
        GridPane.setHalignment(fxStart, HPos.CENTER);
        GridPane.setHalignment(fxResult, HPos.CENTER);

        // Create a scene and place it in the stage
        Scene scene = new Scene(gridPane, 660, 660);
        primaryStage.setTitle("数据交易系统——验证方");
        primaryStage.setScene(scene);
        //primaryStage.show();

        GridPane gridPane1 = new GridPane();
        //gridPane1.setStyle("-fx-background-color: White;");
        gridPane1.setHgap(20);
        gridPane1.setVgap(20);

        Text scenetitle4 = new Text("验证阶段");
        scenetitle4.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane1.add(scenetitle4, 0, 1);
        Text scenetitle5 = new Text("Validation Phase");
        scenetitle5.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane1.add(scenetitle5, 0, 2);

        Text zhuangtai = new Text("提示您，当前状态为：");
        zhuangtai.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(zhuangtai,0,4);
        gridPane1.add(fxState, 1, 4);

        Text trust = new Text("互信结果：");
        trust.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(trust,0,5);
        gridPane1.add(fxTrust, 1, 5);

        Text v1 = new Text("总数是否小于上界:");
        v1.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v1,0,6);
        gridPane1.add(fxVerify1, 1, 6);

        Text v2 = new Text("总数是否大于下界:");
        v2.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v2,0,7);
        gridPane1.add(fxVerify2, 1, 7);

        Text v3 = new Text("平均值是否小于上界:");
        v3.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v3,0,8);
        gridPane1.add(fxVerify3, 1, 8);

        Text v4 = new Text("平均值是否大于下界:");
        v4.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v4,0,9);
        gridPane1.add(fxVerify4, 1, 9);

        Text v5 = new Text("标准差是否小于上界:");
        v5.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v5,0,10);
        gridPane1.add(fxVerify5, 1, 10);

        Text v6 = new Text("标准差是否大于下界:");
        v6.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane1.add(v6,0,11);
        gridPane1.add(fxVerify6, 1, 11);

        gridPane1.add(fxResult, 1, 12);
        fxResult.setPrefSize(240,30);

        gridPane1.setAlignment(Pos.CENTER);
        fxState.setAlignment(Pos.BOTTOM_CENTER);
        fxTrust.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify1.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify2.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify3.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify4.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify5.setAlignment(Pos.BOTTOM_CENTER);
        fxVerify6.setAlignment(Pos.BOTTOM_CENTER);
        GridPane.setHalignment(fxResult, HPos.CENTER);

        fxState.setText("请您等待");

        Scene scene1 = new Scene(gridPane1, 660, 660);
        primaryStage.setTitle("Qrypt (Verifier Ver.)");
        primaryStage.show();

        // Process events
        fxStart.setOnAction(event -> {
            primaryStage.setScene(scene1);
            startProcess();
        });
        fxResult.setOnAction(e -> getResult());

    }

    private void startProcess() {
        try{
            new ClientVerifier().startClient("验证方");
            fxRegionText = fxRegion.getText();
            fxCountUpLimitText = fxCountUpLimit.getText();
            fxCountDownLimitText = fxCountDownLimit.getText();
            fxAvgUpLimitText = fxAvgUpLimit.getText();
            fxAvgDownLimitText = fxAvgDownLimit.getText();
            fxStdUpLimitText = fxStdUpLimit.getText();
            fxStdDownLimitText = fxStdDownLimit.getText();

            /**
             * 前置信息的获取、发送
             */
            System.out.println("您的前置信息如下所示：\t" +
                    "地区：\t" + fxRegionText +
                    "期望上界：\t" + fxAvgUpLimitText +
                    "期望下界：\t" + fxAvgDownLimitText);

            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode("Region:" +fxRegionText));
            try{Thread.sleep(100);}
            catch (Exception e){}


             //调取GetPrimeEngine.java，获得g h p q, 随后发送给prover
            // (10000, 5000, 5001);
            // (1000000, 500000, 500001);
            GetPrimeNum GP = new GetPrimeNum(10000,5000, 5001);
            p = GP.Deliver_q();
            //todo
            //p = new BigInteger("11");
            // (15000, 5000, 10001);
            // (1500000, 500000, 1000001);
            GetPrimeNum GGP = new GetPrimeNum(15000, 5000, 10001);
            q = GGP.Deliver_q();
            //todo
            //q = new BigInteger("13");
            System.out.println("The value of prime number p is:" + p + ", the value of number q is:" + q);

            // (1500, 500, 1001);
            // (150000, 50000, 100001);
            GetPrimeNum NGP = new GetPrimeNum(1500, 500, 1001);
            g = NGP.Deliver_q();
            //todo
            //g = new BigInteger("3");
            // (2000, 500, 1501);
            // (200000, 50000, 150001);
            GetPrimeNum NGPH = new GetPrimeNum(2000, 500, 1501);
            h = NGPH.Deliver_q();
            //todo
            //h = new BigInteger("5");
            System.out.println("The value of g is:" + g + ", the value of h is:" + h);

            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode("ValueP:" +p));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode("ValueQ:" +q));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode("ValueG:" +g));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode("ValueH:" +h));
            try{Thread.sleep(100);}
            catch (Exception e){}

            ClientVerifier.socketChannel.write(StandardCharsets.UTF_8.encode( "验证方第一环节已完成"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getResult() {

        long StartVeriTime = System.currentTimeMillis();

        int subtr = (ClientVerifierThread.myC).compareTo(ClientVerifierThread.otherC);
        if(subtr == 0){
            fxState.setText("验算中");
            System.out.println("<<<--- 恭 喜 您，达 成 了 互 信 --->>>\n<<<--- 进 入 比 较 环 节 --->>>");
            fxTrust.setText("达成互信");

            //==========================================================================================================
            // todo count值验证
            BigInteger count = new BigInteger(ClientVerifierThread.received_count);
            System.out.println("received_count:"+ClientVerifierThread.received_count);

            Count_maxmin cmn = new Count_maxmin(VerifierApplication.q,
                    VerifierApplication.fxCountUpLimitText,
                    VerifierApplication.fxCountDownLimitText);
            int new_subtr_1 = count.compareTo(cmn.max);
            int new_subtr_2 = (cmn.min).compareTo(count);

            if(new_subtr_1 == -1){
                System.out.println("<<<--- 元 素 总 数 小 于 上 界 --->>>");
                fxVerify1.setText("是");
            } else{
                System.out.println("<<<--- 元 素 总 数 大 于 上 界 --->>>");
                fxVerify1.setText("否");
            }
            if(new_subtr_2 == -1){
                System.out.println("<<<--- 元 素 总 数 大 于 下 界 --->>>");
                fxVerify2.setText("是");
                fxState.setText("验算结束");
            } else{
                System.out.println("<<<--- 元 素 总 数 小 于 下 界 --->>>");
                fxVerify2.setText("否");
                fxState.setText("验算结束");
            }
            //==========================================================================================================
            // todo average值验证
            BigInteger avg_C = new BigInteger(ClientVerifierThread.received_avg);

            Avg_maxmin amn = new Avg_maxmin(VerifierApplication.q,
                    VerifierApplication.fxAvgUpLimitText,
                    VerifierApplication.fxAvgDownLimitText);
            int new_subtr_3 = avg_C.compareTo(amn.max); //-1
            int new_subtr_4 = (amn.min).compareTo(avg_C); //-1

            if(new_subtr_3 == -1){
                System.out.println("<<<--- 平 均 值 小 于 上 界 --->>>");
                fxVerify3.setText("是");
            } else{
                System.out.println("<<<--- 平 均 值 大 于 上 界 --->>>");
                fxVerify3.setText("否");
            }
            if(new_subtr_4 == -1){
                System.out.println("<<<--- 平 均 值 大 于 下 界 --->>>");
                fxVerify4.setText("是");
                fxState.setText("验算结束");
            } else{
                System.out.println("<<<--- 平 均 值 小 于 下 界 --->>>");
                fxVerify4.setText("否");
                fxState.setText("验算结束");
            }

            //==========================================================================================================
            // todo StD值验证
            BigInteger StD = new BigInteger(ClientVerifierThread.received_StD);

            System.out.println("STD:"+ClientVerifierThread.received_StD+"\nfxStdUpLimitText"+VerifierApplication.fxStdUpLimitText+"\nfxStdDownLimitText"+VerifierApplication.fxStdDownLimitText);
            StD_maxmin smn  = new StD_maxmin(VerifierApplication.q,
                    VerifierApplication.fxStdUpLimitText,
                    VerifierApplication.fxStdDownLimitText);
            int new_subtr_5 = StD.compareTo(smn.max); //-1
            int new_subtr_6 = (smn.min).compareTo(StD); //-1

            if(new_subtr_5 == -1){
                System.out.println("<<<--- 方 差 小 于 上 界 --->>>");
                fxVerify5.setText("是");
            } else{
                System.out.println("<<<--- 方 差 大 于 上 界 --->>>");
                fxVerify5.setText("否");
            }
            if(new_subtr_6 == -1){
                System.out.println("<<<--- 方 差 大 于 下 界 --->>>");
                fxVerify6.setText("是");
                fxState.setText("验算结束");
            } else{
                System.out.println("<<<--- 方 差 小 于 下 界 --->>>");
                fxVerify6.setText("否");
                fxState.setText("验算结束");
            }

            //==========================================================================================================
        }else {
            System.out.println("<<<--- 抱 歉，您 未 达 成 互 信 --->>>");
            fxState.setText("验算结束");
            fxTrust.setText("未能互信");
        }

        long EndVeriTime = System.currentTimeMillis();

        System.out.println("验证耗时："+(EndVeriTime-StartVeriTime)+"毫秒");

    }

    public static void main(String[] args) {
        launch();
    }
}