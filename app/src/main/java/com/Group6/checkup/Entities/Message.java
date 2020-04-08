package com.Group6.checkup.Entities;

public class Message {

    int id;
    String subject;
    String body;
    long timestamp;
    int senderID;
    int recipientID;
    boolean isReply;

    public Message(){

    }

    public Message(int id, String subject, String body, long timestamp, int senderID, int recipientID) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.senderID = senderID;
        this.recipientID = recipientID;
    }

    public Message(int id, String subject, String body, long timestamp, int senderID) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.senderID = senderID;
        this.recipientID = recipientID;
    }

    public Message(String subject, String body, long timestamp, int senderID, int recipientID) {
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.senderID = senderID;
        this.recipientID = recipientID;
    }

    public Message(String subject, String body, long timestamp, int senderID) {
        this.subject = subject;
        this.body = body;
        this.timestamp = timestamp;
        this.senderID = senderID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
    }

    public boolean isReply() {
        return isReply;
    }

    public void setReply(boolean reply) {
        isReply = reply;
    }
}
