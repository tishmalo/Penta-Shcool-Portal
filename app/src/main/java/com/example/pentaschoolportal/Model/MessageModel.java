package com.example.pentaschoolportal.Model;

public class MessageModel {

    private String Sender, receiver, message, email;

    public MessageModel() {
    }

    public MessageModel(String sender, String receiver, String message, String email) {
        Sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.email = email;
    }

    public String getsender() {
        return Sender;
    }

    public void setsender(String sender) {
        Sender = sender;
    }

    public String getreceiver() {
        return receiver;
    }

    public void setreceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}
