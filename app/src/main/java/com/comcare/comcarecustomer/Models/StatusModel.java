package com.comcare.comcarecustomer.Models;

public class StatusModel {

    private String problem1, date, status, type, time, user_id;

    public StatusModel() {
        this.problem1 = "";
        this.date = "";
        this.status = "";
        this.type = "";
        this.time = "";
        this.user_id = "";
    }

    public StatusModel(String problem1, String date, String status, String type, String time, String user_id) {
        this.problem1 = problem1;
        this.date = date;
        this.status = status;
        this.type = type;
        this.time = time;
        this.user_id = user_id;
    }

    public String getproblem1() {
        return problem1;
    }

    public void setproblem1(String problem1) {
        this.problem1 = problem1;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}

