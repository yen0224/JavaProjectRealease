package com.team7.java_2022b;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class adminPane {
    private SQLMGR mgr = new SQLMGR();
    @FXML
    private TextField bookid;
    @FXML
    private TextField bookname;
    @FXML
    private TextField bookauthor;
    @FXML
    private TextField bookpublisher;
    @FXML
    private TextField bookprice;
    @FXML
    private CheckBox onboard;
    @FXML
    private TextField userid;
    @FXML
    private TextField username;
    @FXML
    private TextField usergroup;
    @FXML
    private TextField userstatus;
    @FXML
    private TextField userallowed;
    @FXML
    private TextField userremained;
    @FXML
    private TextField userfinerate;
    @FXML
    private TableView<BookTableData> bookManageTableView;
    @FXML
    private TableColumn<BookTableData, String> idTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookNameTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookAuthorTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookPublisherTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookPriceTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookImagePathColumn;
    @FXML
    private TableColumn<BookTableData, String> bookStatusTableColumn;
    @FXML
    private TableColumn<UserTableData, String> userIdTableCol;
    @FXML
    private TableColumn<UserTableData, String> userNameTableCol;
    @FXML
    private TableColumn<UserTableData, String> userRoleTableCol;
    @FXML
    private TableColumn<UserTableData, String> userStatusTableCol;
    @FXML
    private TableColumn<UserTableData, String> userAllowedTableCol;
    @FXML
    private TableColumn<UserTableData, String> userBorrowedTableCol;
    @FXML
    private TableColumn<UserTableData, String> userFineRateTableCol;

    @FXML
    private TableView<UserTableData> userManageTableView;

    @FXML
    private TableColumn<HistoryTableData, String> txTableColumnOfHis;
    @FXML
    private TableColumn<HistoryTableData, String> useridTableColumnOfHis;
    @FXML
    private TableColumn<HistoryTableData, String> bookidTableColumnOfHis;
    @FXML
    private TableColumn<HistoryTableData, String> borrow_dateTableColumnOfHis;
    @FXML
    private TableColumn<HistoryTableData, String> return_dateTableColumnOfHis;
    @FXML
    private TableView<HistoryTableData> historyTableView;


    public void initialize() throws SQLException {
        userid.setDisable(true);
        bookid.setDisable(true);
        setBookTableViewData(bookManageTableView, getAllBooks(), idTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookPublisherTableColumn, bookPriceTableColumn, bookStatusTableColumn);
        setUserTableViewData(userManageTableView, getAllUsers(), userIdTableCol, userNameTableCol, userRoleTableCol, userStatusTableCol, userAllowedTableCol, userBorrowedTableCol, userFineRateTableCol);
        setHistoryTableViewData(historyTableView, getAllHistory(), txTableColumnOfHis, useridTableColumnOfHis, bookidTableColumnOfHis, borrow_dateTableColumnOfHis, return_dateTableColumnOfHis);

        bookManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails(newValue));
        userManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showUserDetails(newValue));
    }

    public void refreshTable() throws SQLException {
        setBookTableViewData(bookManageTableView, getAllBooks(), idTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookPublisherTableColumn, bookPriceTableColumn, bookStatusTableColumn);
        setUserTableViewData(userManageTableView, getAllUsers(), userIdTableCol, userNameTableCol, userRoleTableCol, userStatusTableCol, userAllowedTableCol, userBorrowedTableCol, userFineRateTableCol);
        setHistoryTableViewData(historyTableView, getAllHistory(), txTableColumnOfHis, useridTableColumnOfHis, bookidTableColumnOfHis, borrow_dateTableColumnOfHis, return_dateTableColumnOfHis);
    }

    public void setBookTableViewData(TableView tableView, ObservableList data, TableColumn<BookTableData, String> idColumn, TableColumn<BookTableData, String> nameColumn, TableColumn<BookTableData, String> authorColumn, TableColumn<BookTableData, String> publisherColumn, TableColumn<BookTableData, String> priceColumn, TableColumn<BookTableData, String> statusColumn) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().bookAuthorProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().bookPriceProperty());
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().bookPublisherProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().bookStatusProperty());
        tableView.setItems(data);
    }

    public void setUserTableViewData(TableView tableView, ObservableList data, TableColumn<UserTableData, String> userIdTableCol, TableColumn<UserTableData, String> userNameTableCol, TableColumn<UserTableData, String> userRoleTableCol, TableColumn<UserTableData, String> userStatusTableCol, TableColumn<UserTableData, String> userAllowedTableCol, TableColumn<UserTableData, String> userBorrowedTableCol, TableColumn<UserTableData, String> userFineRateTableCol) {
        userIdTableCol.setCellValueFactory(cellData -> cellData.getValue().userIdProperty());
        userNameTableCol.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
        userRoleTableCol.setCellValueFactory(cellData -> cellData.getValue().userRoleProperty());
        userStatusTableCol.setCellValueFactory(cellData -> cellData.getValue().userStatusProperty());
        userAllowedTableCol.setCellValueFactory(cellData -> cellData.getValue().userAllowedProperty());
        userBorrowedTableCol.setCellValueFactory(cellData -> cellData.getValue().userRemainedProperty());
        userFineRateTableCol.setCellValueFactory(cellData -> cellData.getValue().userFineRateProperty());
        tableView.setItems(data);
    }

    public void setHistoryTableViewData(TableView tableView, ObservableList data, TableColumn<HistoryTableData, String> txTableColumnOfHis, TableColumn<HistoryTableData, String> useridTableColumnOfHis, TableColumn<HistoryTableData, String> bookidTableColumnOfHis, TableColumn<HistoryTableData, String> borrow_dateTableColumnOfHis, TableColumn<HistoryTableData, String> return_dateTableColumnOfHis) {
        txTableColumnOfHis.setCellValueFactory(cellData -> cellData.getValue().txProperty());
        useridTableColumnOfHis.setCellValueFactory(cellData -> cellData.getValue().useridProperty());
        bookidTableColumnOfHis.setCellValueFactory(cellData -> cellData.getValue().bookidProperty());
        borrow_dateTableColumnOfHis.setCellValueFactory(cellData -> cellData.getValue().borrow_dateProperty());
        return_dateTableColumnOfHis.setCellValueFactory(cellData -> cellData.getValue().return_dateProperty());
        tableView.setItems(data);
    }


    public ObservableList getAllBooks() throws SQLException {
        List list = mgr.getAllBooks();
        ObservableList<BookTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            Book r = (Book) list.get(i);
            BookTableData td = new BookTableData(String.valueOf(r.getId()), r.getName(), r.getAuthor(), r.getPublisher(), String.valueOf(r.getPrice()), "123", String.valueOf(r.getStatus()));
            data.add(td);
        }
        return data;
    }

    public ObservableList getAllUsers() throws SQLException {
        List list = mgr.getAllUsers();
        ObservableList<UserTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            User r = (User) list.get(i);
            UserTableData td = new UserTableData(String.valueOf(r.getId()), r.getName(), r.getEmail(), String.valueOf(r.getRole()), String.valueOf(r.getStatus()), String.valueOf(r.getAllowed()), String.valueOf(r.getRemained()), String.valueOf(r.getFineRate()));
            data.add(td);
        }
        return data;
    }

    public ObservableList getAllHistory() throws SQLException {
        List list = mgr.getAllHistory();
        ObservableList<HistoryTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            History r = (History) list.get(i);
            HistoryTableData td = new HistoryTableData(String.valueOf(r.getTx()), String.valueOf(r.getUserid()), String.valueOf(r.getBookid()), String.valueOf(r.getBorrow_date()), String.valueOf(r.getReturn_date()));
            data.add(td);
        }
        return data;
    }

    public void showBookDetails(BookTableData bookTableData) {
        // 判断用户是否选中表格中的某一行
        if (bookManageTableView.getSelectionModel().getSelectedIndex() < 0) {
            return;
        } else {
            // 如果选中表格中的某一行，则将选中行的数据显示在下面的文本框中
            bookid.setText(bookTableData.getBookId());
            bookname.setText(bookTableData.getBookName());
            bookauthor.setText(bookTableData.getBookAuthor());
            bookpublisher.setText(bookTableData.getBookPublisher());
            bookprice.setText(bookTableData.getBookPrice());
            if (bookTableData.getBookStatus() == 1) {
                onboard.setSelected(true);
            } else {
                onboard.setSelected(false);
            }
        }
    }

    public void showUserDetails(UserTableData userTableData) {
        // 判断用户是否选中表格中的某一行
        if (userManageTableView.getSelectionModel().getSelectedIndex() < 0) {
            return;
        } else {
            // 如果选中表格中的某一行，则将选中行的数据显示在下面的文本框中
            userid.setText(userTableData.getUserid());
            username.setText(userTableData.getUserName());
            usergroup.setText(userTableData.getUserRole());
            userstatus.setText(userTableData.getUserStatus());
            userallowed.setText(userTableData.getUserAllowed());
            userremained.setText(userTableData.getUserRemained());
            userfinerate.setText(userTableData.getUserFineRate());
        }
    }

    @FXML
    public void addBook() throws SQLException {
        //String bookid = this.bookid.getText();
        String bookname = this.bookname.getText();
        String bookauthor = this.bookauthor.getText();
        String bookpublisher = this.bookpublisher.getText();
        int bookprice = Integer.parseInt(this.bookprice.getText());
        int bookstatus;
        if (onboard.isSelected()) {
            bookstatus = 1;
        } else {
            bookstatus = 0;
        }
        mgr.addBook(bookname, bookauthor, bookpublisher, bookprice, bookstatus);
        bookManageTableView.setItems(getAllBooks());
    }

    @FXML
    public void editbook() throws SQLException {
        int bookid = Integer.parseInt(this.bookid.getText());
        String bookname = this.bookname.getText();
        String bookauthor = this.bookauthor.getText();
        String bookpublisher = this.bookpublisher.getText();
        int bookprice = Integer.parseInt(this.bookprice.getText());
        int bookstatus;
        if (onboard.isSelected()) {
            bookstatus = 1;
        } else {
            bookstatus = 0;
        }
        mgr.editBook(bookid, bookname, bookauthor, bookpublisher, bookprice, bookstatus);
        bookManageTableView.setItems(getAllBooks());
    }

    @FXML
    public void deletebook() throws SQLException {
        int bookid = Integer.parseInt(this.bookid.getText());
        mgr.deleteBook(bookid);
        bookManageTableView.setItems(getAllBooks());
    }

    @FXML
    public void resetpassword() throws SQLException {
        int userid = Integer.parseInt(this.userid.getText());
        mgr.resetPassword(userid);
        userManageTableView.setItems(getAllUsers());
    }

    @FXML
    public void editUser() throws SQLException {
        int userid = Integer.parseInt(this.userid.getText());
        String username = this.username.getText();
        int usergroup = Integer.parseInt(this.usergroup.getText());
        int userstatus = Integer.parseInt(this.userstatus.getText());
        int userallowed = Integer.parseInt(this.userallowed.getText());
        int userremained = Integer.parseInt(this.userremained.getText());
        int userfinerate = Integer.parseInt(this.userfinerate.getText());
        mgr.editUser(userid, username, usergroup, userstatus, userallowed, userremained, userfinerate);
        userManageTableView.setItems(getAllUsers());
    }

    @FXML
    public void disableUser() throws SQLException {
        int userid = Integer.parseInt(this.userid.getText());
        mgr.disableUser(userid);
        userManageTableView.setItems(getAllUsers());
    }

}

