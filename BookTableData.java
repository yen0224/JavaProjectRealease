package com.team7.java_2022b;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;

public class BookTableData {
    private SimpleStringProperty ImgPath;
    private SimpleStringProperty publisher;
    private SimpleStringProperty bookId;
    private SimpleStringProperty bookName;
    private SimpleStringProperty bookAuthor;
    private SimpleStringProperty bookAuthorSex;
    private SimpleStringProperty bookPrice;
    private SimpleStringProperty bookDescription;
    private SimpleStringProperty bookType;
    private SimpleStringProperty status;


    public BookTableData() {
    }

    public BookTableData(String bookId, String bookName, String bookAuthor, String publisher, String bookPrice, String ImgPath, String status) {
        this.bookId = new SimpleStringProperty(bookId);
        this.bookName = new SimpleStringProperty(bookName);
        this.bookAuthor = new SimpleStringProperty(bookAuthor);
        this.publisher = new SimpleStringProperty(publisher);
        this.bookPrice = new SimpleStringProperty(bookPrice);
        this.ImgPath = new SimpleStringProperty(ImgPath);
        this.status = new SimpleStringProperty(status);

    }

    public String getBookId() {
        return bookId.get();
    }

    public void setBookId(String bookId) {
        this.bookId.set(bookId);
    }

    public SimpleStringProperty bookIdProperty() {
        return bookId;
    }

    public String getBookName() {
        return bookName.get();
    }

    public void setBookName(String bookName) {
        this.bookName.set(bookName);
    }

    public SimpleStringProperty bookNameProperty() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor.get();
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor.set(bookAuthor);
    }

    public SimpleStringProperty bookAuthorProperty() {
        return bookAuthor;
    }

    public int getBookStatus() {
        return Integer.parseInt(status.get());
    }

    public SimpleStringProperty bookStatusProperty() {
        return status;


    }

    public String getBookAuthorSex() {
        return bookAuthorSex.get();
    }

    public void setBookAuthorSex(String bookAuthorSex) {
        this.bookAuthorSex.set(bookAuthorSex);
    }

    public SimpleStringProperty bookAuthorSexProperty() {
        return bookAuthorSex;
    }

    public String getBookPrice() {
        return bookPrice.get();
    }

    public void setBookPrice(String bookPrice) {
        this.bookPrice.set(bookPrice);
    }

    public SimpleStringProperty bookPriceProperty() {
        return bookPrice;
    }

    public String getBookDescription() {
        return bookDescription.get();
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription.set(bookDescription);
    }

    public SimpleStringProperty bookDescriptionProperty() {
        return bookDescription;
    }

    public String getBookType() {
        return bookType.get();
    }

    public void setBookType(String bookType) {
        this.bookType.set(bookType);
    }

    public SimpleStringProperty bookTypeProperty() {
        return bookType;
    }

    public SimpleStringProperty bookPublisherProperty() {
        return publisher;
    }

    public String getBookPublisher() {
        return publisher.get();
    }
}
