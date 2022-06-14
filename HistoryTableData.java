package com.team7.java_2022b;

import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;

public class HistoryTableData {
    private SimpleStringProperty tx;
    private SimpleStringProperty userid;
    private SimpleStringProperty bookid;
    private SimpleStringProperty borrow_date;
    private SimpleStringProperty return_date;
    private SimpleStringProperty bookname;

    public HistoryTableData(String tx, String userid, String bookid, String borrow_date, String return_date) throws SQLException {
        this.tx = new SimpleStringProperty(tx);
        this.userid = new SimpleStringProperty(userid);
        this.bookid = new SimpleStringProperty(bookid);
        this.borrow_date = new SimpleStringProperty(borrow_date);
        this.return_date = new SimpleStringProperty(return_date);
        SQLMGR mgr = new SQLMGR();
        this.bookname = new SimpleStringProperty(mgr.getBookName(Integer.parseInt(bookid)));
    }

    public SimpleStringProperty txProperty() {
        return tx;
    }

    public SimpleStringProperty useridProperty() {
        return userid;
    }

    public SimpleStringProperty bookidProperty() {
        return bookid;
    }

    public SimpleStringProperty borrow_dateProperty() {
        return borrow_date;
    }

    public SimpleStringProperty return_dateProperty() {
        return return_date;
    }

    public SimpleStringProperty booknameProperty() {
        return bookname;
    }
}
