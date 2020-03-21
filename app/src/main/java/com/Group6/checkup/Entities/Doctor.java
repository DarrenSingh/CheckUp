package com.Group6.checkup.Entities;

public class Doctor {

    //Attributes
    private int ID;
    private String firstName;
    private String lastName;
    private String officeAddress;
    private String loginID;
    private String password;
    private String phoneNumber;
    private String emailAddress;
    private int adminID;

    //Constructors
    public Doctor(int ID, String firstName, String lastName, String officeAddress, String loginID, String password, String phoneNumber, String emailAddress, int adminID) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.officeAddress = officeAddress;
        this.loginID = loginID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.adminID = adminID;
    }

    public Doctor(String firstName, String lastName, String officeAddress, String loginID, String password, String phoneNumber, String emailAddress, int adminID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.officeAddress = officeAddress;
        this.loginID = loginID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.adminID = adminID;
    }

    public Doctor(String firstName, String lastName, String officeAddress, String loginID, String password, String phoneNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.officeAddress = officeAddress;
        this.loginID = loginID;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    //Getters & Setters
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }



}
