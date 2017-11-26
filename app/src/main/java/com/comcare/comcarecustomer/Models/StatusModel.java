package com.comcare.comcarecustomer.Models;

public class StatusModel {

    private String name, date, status,type;

    public StatusModel() {
        this.name = "";
        this.date = "";
        this.status = "";
        this.type = "";
    }

    public StatusModel(String name, String date, String status, String type) {
        this.name = name;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

