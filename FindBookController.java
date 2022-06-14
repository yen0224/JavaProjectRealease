package com.team7.java_2022b;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FindBookController extends Commom {
    SQLMGR mgr = new SQLMGR();
    @FXML
    private CheckBox sbn = new CheckBox();
    @FXML
    private CheckBox sa = new CheckBox();
    @FXML
    private CheckBox sp = new CheckBox();
    @FXML
    private TextField searchText = new TextField();
    @FXML
    private TableColumn<BookTableData, String> idTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookNameTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookAuthorTableColumn;
    @FXML
    private TableColumn<BookTableData, String> bookPublisherTableColumn;

    @FXML
    private TableColumn<BookTableData, String> bookStatusTableColumn;
    @FXML
    private TableView<BookTableData> bookTableView;

    public void initialize() throws SQLException {
        SQLMGR mgr = new SQLMGR();
        setBookTableViewData(bookTableView, getAllBooks(), idTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookPublisherTableColumn, bookStatusTableColumn);

    }

    public void setBookTableViewData(TableView tableView, ObservableList data, TableColumn<BookTableData, String> idColumn, TableColumn<BookTableData, String> nameColumn, TableColumn<BookTableData, String> authorColumn, TableColumn<BookTableData, String> publisherColumn, TableColumn<BookTableData, String> statusColumn) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().bookAuthorProperty());
        publisherColumn.setCellValueFactory(cellData -> cellData.getValue().bookPublisherProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().bookStatusProperty());
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


    public void search() throws SQLException {
        SQLMGR mgr = new SQLMGR();
        boolean searchByName = sbn.isSelected();
        boolean searchByAuthor = sa.isSelected();
        boolean searchByPublisher = sp.isSelected();
        List list = mgr.searchBooks(searchText.getText(), searchByName, searchByAuthor, searchByPublisher);
        ObservableList<BookTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            Book r = (Book) list.get(i);
            BookTableData td = new BookTableData(String.valueOf(r.getId()), r.getName(), r.getAuthor(), r.getPublisher(), String.valueOf(r.getPrice()), "123", String.valueOf(r.getStatus()));
            data.add(td);
        }
        setBookTableViewData(bookTableView, data, idTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookPublisherTableColumn, bookStatusTableColumn);
    }
}
