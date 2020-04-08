package com.Group6.checkup.Entities;

public class OnlineHelpReply extends  Message{

    //Constructors
    public OnlineHelpReply(int ID, String messageTitle, String message, long dateTime, int doctorID) {
        super(ID,messageTitle,message,dateTime,doctorID);
        this.isReply = true;
    }

    public OnlineHelpReply(String messageTitle, String message, long dateTime, int doctorID) {
        super(messageTitle,message,dateTime,doctorID);
        this.isReply = true;
    }

    //Getters & Setters
    public int getID() {
        return id;
    }

    public void setID(int ID) {
        this.id = ID;
    }

    public String getMessageTitle() {
        return subject;
    }

    public void setMessageTitle(String messageTitle) {
        this.subject = messageTitle;
    }

    public String getMessageContent() {
        return body;
    }

    public void setMessageContent(String messageContent) {
        this.body = messageContent;
    }

    public long getDateTime() {
        return timestamp;
    }

    public void setDateTime(long dateTime) {
        this.timestamp = dateTime;
    }

    public int getDoctorID() {
        return senderID;
    }

    public void setDoctorID(int doctorID) {
        this.senderID = doctorID;
    }
}
