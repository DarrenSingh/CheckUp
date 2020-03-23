package com.Group6.checkup.Entities;

public class OnlineHelp {

    //Attributes
    private int ID;
    private String messageTitle;
    private String message;
    private long sentDateTime;
    private int patientID;
    private int doctorID;
    private int onlineHelpReplyID;

    //Constructors
    public OnlineHelp(int ID, String messageTitle, String message, long sentDateTime, int patientID, int doctorID, int onlineHelpReplyID) {
        this.ID = ID;
        this.messageTitle = messageTitle;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.onlineHelpReplyID = onlineHelpReplyID;
    }

    public OnlineHelp(String messageTitle, String message, long sentDateTime, int patientID, int doctorID, int onlineHelpReplyID) {
        this.messageTitle = messageTitle;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.onlineHelpReplyID = onlineHelpReplyID;
    }

    public OnlineHelp(String messageTitle, String message, long sentDateTime, int patientID, int doctorID) {
        this.messageTitle = messageTitle;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.patientID = patientID;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getSentDateTime() {
        return sentDateTime;
    }

    public void setSentDateTime(long sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getOnlineHelpReplyID() {
        return onlineHelpReplyID;
    }

    public void setOnlineHelpReplyID(int onlineHelpReplyID) {
        this.onlineHelpReplyID = onlineHelpReplyID;
    }
}
