package com.Group6.checkup.TableClassPackage;

public class Invoice {

    private int invoiceID;
    private double price;
    private String paymentDue;
    private String paymentStatus;
    private String paymentDate;
    private int patientID;
    private int cashierID;
    private int doctorID;
    private int AppointmentID;

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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
        return AppointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        AppointmentID = appointmentID;
    }
}
