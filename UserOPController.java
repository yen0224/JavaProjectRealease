package com.team7.java_2022b;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

//TODO 處理參數傳遞問題
public class UserOPController extends Commom {
    //int user_id = SignInController.user_id;
    @FXML
    private TableColumn<HistoryTableData, String> historyId;
    @FXML
    private TableColumn<HistoryTableData, String> BookId;
    @FXML
    private TableColumn<HistoryTableData, String> BookName;
    @FXML
    private TableColumn<HistoryTableData, String> BorrowDate;
    @FXML
    private TableColumn<HistoryTableData, String> ReturnDate;
    @FXML
    private TableView<HistoryTableData> historyTable;
    @FXML
    private Label name = new Label();

    @FXML
    private Label roll = new Label();

    @FXML
    private Label borrowed = new Label();
    @FXML
    private Label allowed = new Label();
    @FXML
    private Label remained = new Label();
    @FXML
    private Label dued = new Label();
    SQLMGR mgr = new SQLMGR();
    public void initialize() throws SQLException {

        User user = mgr.getUserInfo(Main.user_id);
        System.out.println(Main.user_id);
        name.setText(user.getName());
        switch (user.getRole()) {
            case 0:
                roll.setText("管理員");
                break;
            case 1:
                roll.setText("學生");
                break;
            case 2:
                roll.setText("教師");
                break;
            case 3:
                roll.setText("職員");
                break;
            case 4:
                roll.setText("社會人士");
                break;
        }
        remained.setText(String.valueOf(user.getRemained()));
        allowed.setText(String.valueOf(user.getAllowed()));
        //borrowed.setText(String.valueOf(user.getBorrowed()));
        borrowed.setText(String.valueOf(user.getBorrowed()));
        setHistoryTableViewData(historyTable, getAllBooks(), historyId, BookId, BookName, BorrowDate, ReturnDate);
    }

    public void setHistoryTableViewData(TableView tableView,
                                        ObservableList data,
                                        TableColumn<HistoryTableData, String> historyId,
                                        TableColumn<HistoryTableData, String> BookId,
                                        TableColumn<HistoryTableData, String> BookName,
                                        TableColumn<HistoryTableData, String> BorrowDate,
                                        TableColumn<HistoryTableData, String> ReturnDate) {

        historyId.setCellValueFactory(cellData -> cellData.getValue().txProperty());
        BookId.setCellValueFactory(cellData -> cellData.getValue().bookidProperty());
        BookName.setCellValueFactory(cellData -> cellData.getValue().booknameProperty());
        BorrowDate.setCellValueFactory(cellData -> cellData.getValue().borrow_dateProperty());
        ReturnDate.setCellValueFactory(cellData -> cellData.getValue().return_dateProperty());
        tableView.setItems(data);
    }

    public ObservableList getAllBooks() throws SQLException {

        List list = mgr.getHistoryofUser(Main.user_id);
        ObservableList<HistoryTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            History r = (History) list.get(i);
            HistoryTableData td = new HistoryTableData(String.valueOf(r.getTx()), String.valueOf(r.getUserid()), String.valueOf(r.getBookid()), String.valueOf(r.getBorrow_date()), String.valueOf(r.getReturn_date()));
            data.add(td);
        }
        return data;
    }
}
