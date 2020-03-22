package com.Group6.checkup.Entities;

public class Admin {

    //Attributes
    private int ID;
    private String firstName;
    private String lastName;
    private String loginID;
    private String password;

    //Constructors
    public Admin(int ID, String firstName, String lastName, String loginID, String password) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginID = loginID;
        this.password = password;
    }

    public Admin(String firstName, String lastName, String loginID, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.loginID = loginID;
        this.password = password;
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

}
