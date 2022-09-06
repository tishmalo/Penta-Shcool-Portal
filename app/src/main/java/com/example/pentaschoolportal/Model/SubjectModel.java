package com.example.pentaschoolportal.Model;

public class SubjectModel {

    private String code, username,userid,year;

    public SubjectModel() {
    }

    public SubjectModel(String code, String username, String userid, String year) {
        this.code = code;
        this.username = username;
        this.userid = userid;
        this.year = year;
    }

    public String getyear() {
        return year;
    }

    public void setyear(String year) {
        this.year = year;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid) {
        this.userid = userid;
    }
}
