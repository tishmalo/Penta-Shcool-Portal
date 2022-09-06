package com.example.pentaschoolportal.Model;

public class DowloadNotesModel {

    private String document, name, code;

    public DowloadNotesModel() {
    }

    public DowloadNotesModel(String document, String name, String code) {
        this.document = document;
        this.name = name;
        this.code = code;
    }

    public String getdocument() {
        return document;
    }

    public void setdocument(String document) {
        this.document = document;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }
}
