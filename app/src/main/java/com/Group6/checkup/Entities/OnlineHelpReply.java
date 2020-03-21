package com.Group6.checkup.Entities;

public class OnlineHelpReply {

    //Attributes
    private int ID;
    private String messageTitle;
    private String messageContent;
    private long dateTime;
    private int doctorID;

    //Constructors
    public OnlineHelpReply(int ID, String messageTitle, String message, long dateTime, int doctorID) {
        this.ID = ID;
        this.messageTitle = messageTitle;
        this.messageContent = message;
        this.dateTime = dateTime;
        this.doctorID = doctorID;
    }

    public OnlineHelpReply(String messageTitle, String message, long dateTime, int doctorID) {
        this.messageTitle = messageTitle;
        this.messageContent = message;
        this.dateTime = dateTime;
        this.doctorID = doctorID;
    }

    //Getters & Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
