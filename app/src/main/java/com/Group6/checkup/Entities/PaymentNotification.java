package com.Group6.checkup.Entities;

public class PaymentNotification {

    //Attributes
    private int ID;
    private String messageTitle;
    private String message;
    private long sentDateTime;
    private int patientID;
    private int cashierID;

    //Constructors
    public PaymentNotification(int ID, String messageTitle, String message, long sentDateTime, int patientID, int cashierID) {
        this.ID = ID;
        this.messageTitle = messageTitle;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.patientID = patientID;
        this.cashierID = cashierID;
    }

    public PaymentNotification(String messageTitle, String message, long sentDateTime, int patientID, int cashierID) {
        this.messageTitle = messageTitle;
        this.message = message;
        this.sentDateTime = sentDateTime;
        this.patientID = patientID;
        this.cashierID = cashierID;
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

    public int getCashierID() {
        return cashierID;
    }

    public void setCashierID(int cashierID) {
        this.cashierID = cashierID;
    }
}
