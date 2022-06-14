package com.team7.java_2022b;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Commom {

    public void forgetpw(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ForgetPw.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Forget Password");
        stage.setScene(scene);
        stage.show();
    }

    public void AdminPan(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("AdminLogIn.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Admin Log In");
        stage.setScene(scene);
        stage.show();
    }

    public void Register(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Register.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Admin Panel");
        stage.setScene(scene);
        stage.show();
    }

    public void backtoUserPan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserPan.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void BorrowPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Borrow.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void ReturnPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Return.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void FindPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FindBook.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void UserOP(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Sign-In.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
