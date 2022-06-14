package com.team7.java_2022b;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class BorrowController extends Commom {
    SQLMGR mgr = new SQLMGR();
    @FXML
    private TextField book_id;
    @FXML
    private TextField user_id;

    //completed
    public void borrowBook() throws SQLException {
        String rBookID = book_id.getText();
        String rUserID = user_id.getText();
        if (rBookID.isEmpty() || rUserID.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("缺少必要資料");
            if (rBookID.isEmpty() && rUserID.isEmpty()) {
                alert.setContentText("請填入書籍編號及使用者編號");
            } else if (rBookID.isEmpty()) {
                alert.setContentText("書籍編號不可為空");
            } else if (rUserID.isEmpty()) {
                alert.setContentText("使用者編號不可為空");
            }
            alert.showAndWait();
        } else {
            int BookID = Integer.parseInt(rBookID);
            int UserID = Integer.parseInt(rUserID);

            //TODO 將使用者資料狀態回傳值改為狀態碼而非布林
            if (mgr.isbookAvailable(BookID)) {
                System.out.println("book id: " + BookID + " is available");
                if (mgr.canUserBorrow(UserID)) {
                    mgr.writeHistory(UserID, BookID);
                    mgr.decrUserRemained(UserID);
                    mgr.updateBookStatusToUnavailable(BookID);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succeed!!");
                    alert.setHeaderText("成功借書");
                    alert.setContentText("記得要準時歸還喔!!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("您已達到借閱上限，請先歸還書籍再借書");
                    alert.showAndWait();
                    System.out.println("user id: " + UserID + " can't borrow more books");
                }
                //System.out.println("book id: " + BookID + " was borrowed by" + UserID);
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error");
                alert.setHeaderText("書籍已被借出，或查無資料");
                alert.setContentText("請確認索書號是否輸入錯誤，或洽管理員確認");
                alert.showAndWait();
                System.out.println("book id: " + BookID + " is not available");
            }
        }
    }
    /*public void backtoUserPan(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserPan.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }*/
}



