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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class ReturnController extends Commom {
    SQLMGR mgr = new SQLMGR();
    //get the item from the text field
    @FXML
    private TextField book_id;
    private Object Instant;

    //private int BookID = Integer.parseInt(book_id.getText());
    @FXML
    public void returnBook() throws SQLException, ParseException {
        int BookID = Integer.parseInt(book_id.getText());

        if (mgr.isbookAvailable(BookID)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("索書號錯誤");
            alert.setContentText("此書籍並未在借閱紀錄中，請直接投入還書箱");
            alert.showAndWait();
        } else {
            //get borrower data
            int userid = mgr.getBorrowerData(BookID);
            String borrowDate = mgr.getBorrowDate(BookID);
            //set return date to today's date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String returnDate = sdf.format(new Date());
            //calculate the days between borrow date and return date
            int days = getDaysBetween(borrowDate, returnDate);
            //calculate the fine
            //int days = 14;
            int fine = mgr.calculateFine(userid, days);

            if (days < 7) {
                //update the book status to available
                mgr.updateBookStatusToAvailable(BookID);
                //update the user's remained books
                mgr.incUserRemained(userid);
                //update the history table
                mgr.updateHistory(BookID);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("還書成功");
                alert.setContentText("此書籍已還回，請直接投入還書箱");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("書籍已逾期");
                alert.setHeaderText("書籍已逾期且超過緩衝期，請繳交罰款");
                alert.setContentText("罰款金額為" + fine + "元");
                alert.showAndWait();

            }
        }

    }

    public int getDaysBetween(String borrowDate, String returnDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date borrow = sdf.parse(borrowDate);
        Date returnDate1 = sdf.parse(returnDate);
        long diff = returnDate1.getTime() - borrow.getTime();
        int days = (int) (diff / (1000 * 60 * 60 * 24));
        return days;
    }
}

/*

    private String getTodayDate() {
        return Date.from(Instant.now()).toString();
    }
/*
    public int getuser(int user_id) throws SQLException {
        SQLMGR mgr = new SQLMGR();
        ResultSet UserData = mgr.findUserById(user_id);
        UserData.next();
        int borrower_roll = UserData.getInt("roll");
        return borrower_roll;
    }*/

