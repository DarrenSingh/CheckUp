package com.Group6.checkup.Entities;

public class OnlineHelp extends Message{

    //Attributes
    private int onlineHelpReplyID;

    //Constructors
    public OnlineHelp(int ID, String messageTitle, String message, long sentDateTime, int patientID, int doctorID, int onlineHelpReplyID) {
        super(ID,messageTitle,message,sentDateTime,patientID,doctorID);
        this.onlineHelpReplyID = onlineHelpReplyID;
        this.isReply = false;
    }

    public OnlineHelp(String messageTitle, String message, long sentDateTime, int patientID, int doctorID, int onlineHelpReplyID) {
        super(messageTitle,message,sentDateTime,patientID,doctorID);
        this.onlineHelpReplyID = onlineHelpReplyID;
        this.isReply = false;
    }

    public OnlineHelp(String messageTitle, String message, long sentDateTime, int patientID, int doctorID) {
        super(messageTitle,message,sentDateTime,patientID,doctorID);
        this.isReply = false;
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

    public String getMessage() {
        return body;
    }

    public void setMessage(String message) {
        this.body = message;
    }

    public long getSentDateTime() {
        return timestamp;
    }

    public void setSentDateTime(long sentDateTime) {
        this.timestamp = sentDateTime;
    }

    public int getPatientID() {
        return senderID;
    }

    public void setPatientID(int patientID) {
        this.senderID = patientID;
    }

    public int getDoctorID() {
        return recipientID;
    }

    public void setDoctorID(int doctorID) {
        this.recipientID = doctorID;
    }

    public int getOnlineHelpReplyID() {
        return onlineHelpReplyID;
    }

    public void setOnlineHelpReplyID(int onlineHelpReplyID) {
        this.onlineHelpReplyID = onlineHelpReplyID;
    }

}
