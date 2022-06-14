package com.team7.java_2022b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController extends Commom {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button startBtn;

    public void switchToUserPan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserPan.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
        /*stage = new Stage();
        root = FXMLLoader.load(getClass().getResource("UserPan.fxml"));
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }
/*
    @FXML
    protected void onHelloButtonClick() {
        //load SignIn.fxml
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("UserPan.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("User Options");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
/*
 * 借書 / Borrow Book(s)
 * 還書 / Return Book(s)
 * 會員資料管理 / Member Dashboard
 * 成為會員 / Register
 * 重設密碼 / Reset Password
 * 管理員專區/ADMIN ONLY
 * */