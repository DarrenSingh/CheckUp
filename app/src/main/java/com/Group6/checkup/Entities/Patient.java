package com.Group6.checkup.Entities;

public class Patient {

    private int ID;
    private String firstName;
    private String lastName;
    private String address;
    private String loginID;
    private String password;
    private boolean mspStatus;
    private String phoneNumber;
    private int healthCareCardNumber;
    private String emailAddress;
    private int adminID;

    public Patient(int ID, String firstName, String lastName, String address, String loginID, String password, boolean mspStatus, String phoneNumber, int healthCareCardNumber, String emailAddress, int adminID) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.loginID = loginID;
        this.password = password;
        this.mspStatus = mspStatus;
        this.phoneNumber = phoneNumber;
        this.healthCareCardNumber = healthCareCardNumber;
        this.emailAddress = emailAddress;
        this.adminID = adminID;
    }

    public Patient(String firstName, String lastName, String address, String loginID, String password, boolean mspStatus, String phoneNumber, int healthCareCardNumber, String emailAddress, int adminID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.loginID = loginID;
        this.password = password;
        this.mspStatus = mspStatus;
        this.phoneNumber = phoneNumber;
        this.healthCareCardNumber = healthCareCardNumber;
        this.emailAddress = emailAddress;
        this.adminID = adminID;
    }

    public Patient(String firstName, String lastName, String address, String loginID, String password, boolean mspStatus, String phoneNumber, int healthCareCardNumber, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.loginID = loginID;
        this.password = password;
        this.mspStatus = mspStatus;
        this.phoneNumber = phoneNumber;
        this.healthCareCardNumber = healthCareCardNumber;
        this.emailAddress = emailAddress;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean getMspStatus() {
        return mspStatus;
    }

    public void setMspStatus(boolean mspStatus) {
        this.mspStatus = mspStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getHealthCareCardNumber() {
        return healthCareCardNumber;
    }

    public void setHealthCareCardNumber(int healthCareCardNumber) {
        this.healthCareCardNumber = healthCareCardNumber;
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
