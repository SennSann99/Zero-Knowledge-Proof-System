package com.clientproverfx;

import com.prover.ClientProver;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ProverApplication extends Application {
    public static TextField fxState = new TextField();
    public static TextField fxP = new TextField();
    public static TextField fxQ = new TextField();
    public static TextField fxG = new TextField();
    public static TextField fxH = new TextField();
    private Button fxButton1 = new Button("Enter");
    private Button fxStart = new Button("启动服务");
    private Button fxSend = new Button("发送密钥");
    @Override
    public void start(Stage stage) throws IOException {

        // 添加图标
        stage.getIcons().add(new Image("C:\\Users\\G1\\OneDrive\\图标.png"));
        //==============================================================================================================
        /*
        //todo 开头
        GridPane pane = new GridPane();

        pane.setHgap(20);
        pane.setVgap(20);

        Text scenetitle1 = new Text("Welcome, Prover");
        scenetitle1.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        pane.add(scenetitle1, 0, 0);
        pane.add(fxButton1,0,1);

        pane.setAlignment(Pos.CENTER);
        fxButton1.setAlignment(Pos.CENTER);
        fxButton1.setPrefSize(240,30);

        pane.setHalignment(fxButton1, HPos.CENTER);
        Scene pre_scene = new Scene(pane, 610, 530);

         */

        //==============================================================================================================

        GridPane gridPane = new GridPane();

        gridPane.setHgap(20);
        gridPane.setVgap(20);

        Text scenetitle1 = new Text("Welcome, Prover");
        scenetitle1.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane.add(scenetitle1, 0, 0);

        Text scenetitle2 = new Text("欢迎使用数据交易系统");
        scenetitle2.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        gridPane.add(scenetitle2, 0, 1);

        Text zhuangtai = new Text("提示您，当前状态为：");
        zhuangtai.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 20));
        gridPane.add(zhuangtai,0,2);
        gridPane.add(fxState, 1, 2);

        Text ValueP = new Text("第一密钥  >>");
        ValueP.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(ValueP, 0, 4);
        gridPane.add(fxP, 1, 4);

        Text ValueQ = new Text("第二密钥  >>");
        ValueQ.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(ValueQ, 0, 5);
        gridPane.add(fxQ, 1, 5);

        Text ValueG = new Text("第三密钥  >>");
        ValueG.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(ValueG, 0, 6);
        gridPane.add(fxG, 1, 6);

        Text ValueH = new Text("第四密钥  >>");
        ValueH.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        gridPane.add(ValueH, 0, 7);
        gridPane.add(fxH, 1, 7);

        gridPane.add(fxStart, 0, 8);
        fxStart.setPrefSize(240,30);
        gridPane.add(fxSend, 1, 8);
        fxSend.setPrefSize(240,30);

        gridPane.setAlignment(Pos.CENTER);

        fxState.setAlignment(Pos.CENTER);
        fxP.setAlignment(Pos.CENTER);
        fxQ.setAlignment(Pos.CENTER);
        fxG.setAlignment(Pos.CENTER);
        fxH.setAlignment(Pos.CENTER);
        //gridPane.setHalignment(fxStart, HPos.CENTER);
        //gridPane.setHalignment(fxSend, HPos.CENTER);

        fxState.setText("未启动");

        fxStart.setOnAction(e -> startProcess());

        fxSend.setOnAction(e -> sendProcess());

        Scene scene = new Scene(gridPane, 660, 660);
        stage.setTitle("Qrypt (Prover Ver.)");

        stage.setScene(scene);
        //stage.setScene(pre_scene);
        stage.show();

        // 设定按钮功能
        fxButton1.setOnAction(event -> {
            stage.setScene(scene);
        });

    }

    private void startProcess() {
        try{

            new ClientProver().startClient("证明方");
            fxState.setText("请等待");

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendProcess(){
        try{

            ClientProver.socketChannel.write(StandardCharsets.UTF_8.encode("Prov p:"+fxP.getText()));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientProver.socketChannel.write(StandardCharsets.UTF_8.encode("Prov q:"+fxQ.getText()));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientProver.socketChannel.write(StandardCharsets.UTF_8.encode("Prov g:"+fxG.getText()));
            try{Thread.sleep(100);}
            catch (Exception e){}
            ClientProver.socketChannel.write(StandardCharsets.UTF_8.encode("Prov h:"+fxH.getText()));
            try{Thread.sleep(100);}
            catch (Exception e){}

            fxState.setText("已发送");

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}