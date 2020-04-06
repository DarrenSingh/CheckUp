package com.Group6.checkup.Entities;

public class Invoice {

    //Attributes
    private int ID;
    private double price;
    private String paymentDue;
    private String paymentStatus;
    private long invoiceDate;
    private int patientID;
    private int cashierID;
    private int doctorID;
    private int appointmentID;

    //Constructors
    public Invoice(int ID, double price, String paymentDue, String paymentStatus, long invoiceDate, int patientID, int cashierID, int doctorID, int appointmentID) {
        this.ID = ID;
        this.price = price;
        this.paymentDue = paymentDue;
        this.paymentStatus = paymentStatus;
        this.invoiceDate = invoiceDate;
        this.patientID = patientID;
        this.cashierID = cashierID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
    }

    public Invoice(double price, String paymentDue, String paymentStatus, long invoiceDate, int patientID, int cashierID, int doctorID, int appointmentID) {
        this.price = price;
        this.paymentDue = paymentDue;
        this.paymentStatus = paymentStatus;
        this.invoiceDate = invoiceDate;
        this.patientID = patientID;
        this.cashierID = cashierID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
    }

    public Invoice(double price, String paymentDue, String paymentStatus, long invoiceDate, int patientID, int doctorID, int appointmentID) {
        this.price = price;
        this.paymentDue = paymentDue;
        this.paymentStatus = paymentStatus;
        this.invoiceDate = invoiceDate;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
    }

    //Getters & Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPaymentDue() {
        return paymentDue;
    }

    public void setPaymentDue(String paymentDue) {
        this.paymentDue = paymentDue;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public long getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(long invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) { this.appointmentID = appointmentID; }
}
