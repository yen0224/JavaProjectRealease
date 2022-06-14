package com.team7.java_2022b;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SignInController {
    public static int user_id;
    ObservableList<String> Methods = FXCollections.observableArrayList("以帳號密碼登入", "以借書證及檢碼登入");
    String username_str = "";
    String password_str = "";
    @FXML
    private ChoiceBox LogInMethod;
    @FXML
    private TextField AccountIdentifier;
    @FXML
    private TextField PassToken;
    SQLMGR mgr = new SQLMGR();
    @FXML
    public void login(ActionEvent event) throws IOException, SQLException {
        username_str = AccountIdentifier.getText();
        password_str = PassToken.getText();
        //int user_id = 0;
        Alert alert = new Alert(null);
        if (username_str.isEmpty() || password_str.isEmpty()) {
            //System.out.println("請填入所有欄位");
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("請填入所有欄位");
        } else if (username_str.isEmpty() && password_str.isEmpty()) {
            alert.setContentText("您並未填入任何欄位");
            alert.showAndWait();
        } else if (username_str.isEmpty()) {
            alert.setContentText("您並未填入帳號");
            alert.showAndWait();
        } else if (password_str.isEmpty()) {
            alert.setContentText("您並未填入密碼");
            alert.showAndWait();
        } else {

            user_id = mgr.userLogin(username_str, password_str);
            if (user_id > 0) {
                Main.user_id = user_id;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("登入成功");
                alert.showAndWait();
                Parent root = FXMLLoader.load(getClass().getResource("UserOP.fxml"));
                Scene scene = new Scene(root);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("登入失敗");
                alert.showAndWait();
            }
        }
    }

}
    /*protected void SignInByTk() {
        AccountIdentifier.setText("借書證ID");
        PassToken.setText("借書證密碼");
    }

    protected void SignInByPwd() {
        AccountIdentifier.setText("帳號");
        PassToken.setText("密碼");
    }*/


