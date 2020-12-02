package com.ntwarispringboot.mail.models;

public class Message {
    private String recieverEmail;
    private String subject;
    private String content;

    public Message(String recieverEmail, String subject, String content) {
        this.recieverEmail = recieverEmail;
        this.subject = subject;
        this.content = content;
    }

    public String getRecieverEmail() {
        return recieverEmail;
    }

    public void setRecieverEmail(String recieverEmail) {
        this.recieverEmail = recieverEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
