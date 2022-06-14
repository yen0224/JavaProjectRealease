package com.team7.java_2022b;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class Register extends Commom {
    ObservableList<String> Groups = FXCollections.observableArrayList("學生", "老師", "職員", "社會人士");
    SQLMGR mgr = new SQLMGR();
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField confirmPassword;
    @FXML
    private ChoiceBox group;

    @FXML
    private void initialize() {
        group.setValue("請選擇");
        group.setItems(Groups);
    }

    @FXML
    public void register() throws SQLException {
        String username = this.username.getText();
        String password = this.password.getText();
        String confirmedPwd = this.confirmPassword.getText();
        String email = this.email.getText();
        if (username.isEmpty() || password.isEmpty() || confirmedPwd.isEmpty() || email.isEmpty()) {
            System.out.println("請填入所有欄位");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("請填入所有欄位");
            alert.showAndWait();
        } else if (!password.equals(confirmedPwd)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("密碼不相符");
            alert.showAndWait();
            System.out.println("Password does not match");
        } else if (group.getValue().equals("請選擇")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("請選擇身份組");
            alert.showAndWait();
        } else {
            String groupSelected = this.group.getValue().toString();
            int usergroup = 0;
            switch (groupSelected) {
                case "學生":
                    usergroup = 1;
                    break;
                case "老師":
                    usergroup = 2;
                    break;
                case "職員":
                    usergroup = 3;
                    break;
                case "社會人士":
                    usergroup = 4;
                    break;
            }

            if (mgr.checkUserExist(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("此帳號已被使用");
                alert.showAndWait();
            } else {
                int id = mgr.register(username, password, email, usergroup);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succeed!!");
                alert.setHeaderText("註冊成功, ID:");
                alert.setContentText("ID is" + id);
                alert.showAndWait();
                //System.out.println("Password does not match");
            }
        }
    }
}
