package com.ntwarispringboot.mail.models;

import org.springframework.core.io.FileSystemResource;

import java.io.File;

public class MessageAttachment {
    private String recieverEmail;
    private String subject;
    private String content;
    private File attachment;

    public MessageAttachment(String recieverEmail, String subject, String content, File attachment) {
        this.recieverEmail = recieverEmail;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
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

    public File getAttachment() {
        return attachment;
    }

    public void setAttachment(File attachment) {
        this.attachment = attachment;
    }
}
