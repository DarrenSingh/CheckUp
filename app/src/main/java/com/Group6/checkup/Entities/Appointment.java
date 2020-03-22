package com.Group6.checkup.Entities;

public class Appointment {

    //Attributes
    private int ID;
    private long appointmentDateTime;
    private int patientID;
    private int doctorID;

    //Constructors
    public Appointment(int ID, long appointmentDateTime, int patientID, int doctorID) {
        this.ID = ID;
        this.appointmentDateTime = appointmentDateTime;
        this.patientID = patientID;
        this.doctorID = doctorID;
    }

    public Appointment(long appointmentDateTime, int patientID, int doctorID) {
        this.appointmentDateTime = appointmentDateTime;
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

    public long getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(long appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
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

}
