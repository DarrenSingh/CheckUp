package com.Group6.checkup.TableClassPackage;

public class OnlineHelpReply {

    private int onlineHelpReplyID;
    private String messageTitle;
    private String message;
    private String sentDateTime;
    private int doctorID;

    public int getOnlineHelpReplyID() {
        return onlineHelpReplyID;
    }

    public void setOnlineHelpReplyID(int onlineHelpReplyID) {
        this.onlineHelpReplyID = onlineHelpReplyID;
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
        return sentDateTime;
    }

    public void setSentDateTime(String sentDateTime) {
        this.sentDateTime = sentDateTime;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
