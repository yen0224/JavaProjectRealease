package com.team7.java_2022b;

import javafx.beans.property.SimpleStringProperty;

public class UserTableData {
    private SimpleStringProperty userid;
    private SimpleStringProperty username;
    private SimpleStringProperty userEmail;
    private SimpleStringProperty userPassword;
    private SimpleStringProperty userStatus;
    private SimpleStringProperty userRole;
    private SimpleStringProperty Allowed;
    private SimpleStringProperty Remained;
    private SimpleStringProperty fineRate;

    public UserTableData(String userid, String username, String userEmail, String userRole, String userStatus, String Allowed, String Remained, String fineRate) {
        this.userid = new SimpleStringProperty(userid);
        this.username = new SimpleStringProperty(username);
        this.userEmail = new SimpleStringProperty(userEmail);
        this.userStatus = new SimpleStringProperty(userStatus);
        this.userRole = new SimpleStringProperty(userRole);
        this.Allowed = new SimpleStringProperty(Allowed);
        this.Remained = new SimpleStringProperty(Remained);
        this.fineRate = new SimpleStringProperty(fineRate);
    }

    public SimpleStringProperty userIdProperty() {
        return userid;
    }

    public SimpleStringProperty userNameProperty() {
        return username;
    }

    public SimpleStringProperty userEmailProperty() {
        return userEmail;
    }

    public SimpleStringProperty userRoleProperty() {
        return userRole;
    }

    public SimpleStringProperty userStatusProperty() {
        return userStatus;
    }

    public SimpleStringProperty userAllowedProperty() {
        return Allowed;
    }

    public SimpleStringProperty userRemainedProperty() {
        return Remained;
    }

    public SimpleStringProperty userFineRateProperty() {
        return fineRate;
    }


    public String getUserid() {
        return userid.get();
    }

    public String getUserName() {
        return username.get();
    }

    public String getUserRole() {
        return userRole.get();
    }

    public String getUserStatus() {
        return userStatus.get();
    }

    public String getUserAllowed() {
        return Allowed.get();
    }

    public String getUserRemained() {
        return Remained.get();
    }

    public String getUserFineRate() {
        return fineRate.get();
    }
}
