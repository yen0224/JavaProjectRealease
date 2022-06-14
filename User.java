package com.team7.java_2022b;

public class User {
    private int id;
    private String name;
    private String Email;
    private String password;
    private int status;
    private int allowed;
    private int remained;
    private int borrowed;
    private int fine;
    private int role;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAllowed() {
        return allowed;
    }

    public void setAllowed(int allowed) {
        this.allowed = allowed;
    }

    public int getRemained() {
        return remained;
    }

    public void setRemained(int remained) {
        this.remained = remained;
    }

    public void setFine(int fine) {
        this.fine = fine;
    }

    public int getFineRate() {
        return fine;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getBorrowed() {
        return allowed - remained;

    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

}
