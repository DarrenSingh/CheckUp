package com.Group6.checkup.TableClassPackage;

public class PaymentNotification {

    private int paymentNotificationID;
    private String messageTitle;
    private String message;
    private String SentDateTime;
    private int patientID;
    private int cashierID;

    public int getPaymentNotificationID() {
        return paymentNotificationID;
    }

    public void setPaymentNotificationID(int paymentNotificationID) {
        this.paymentNotificationID = paymentNotificationID;
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

    public String getSentDateTime() {
        return SentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        SentDateTime = sentDateTime;
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
