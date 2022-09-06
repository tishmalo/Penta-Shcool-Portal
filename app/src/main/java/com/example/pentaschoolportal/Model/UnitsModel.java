package com.example.pentaschoolportal.Model;

public class UnitsModel {

    private String code, subject;

    public UnitsModel() {
    }

    public UnitsModel(String code, String subject) {
        this.code = code;
        this.subject = subject;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

    public String getsubject() {
        return subject;
    }

    public void setsubject(String subject) {
        this.subject = subject;
    }
}
