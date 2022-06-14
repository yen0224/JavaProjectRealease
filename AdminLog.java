package com.team7.java_2022b;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class AdminLog {
    String username_str = "";
    String password_str = "";
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    SQLMGR mgr = new SQLMGR();
    @FXML
    public void logon() throws IOException, SQLException {
        username_str = username.getText();
        password_str = password.getText();
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

            if (mgr.adminLogin(username_str, password_str)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("登入成功");
                alert.showAndWait();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminPan.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                Stage stage = new Stage();
                stage.setTitle("Admin Panel");
                stage.setScene(scene);
                stage.show();
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("登入失敗");
                alert.showAndWait();
            }
        }
    }
}
