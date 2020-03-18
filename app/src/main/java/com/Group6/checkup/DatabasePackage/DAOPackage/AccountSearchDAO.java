package com.Group6.checkup.DatabasePackage.DAOPackage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;

import java.util.ArrayList;

public class AccountSearchDAO {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    Cursor adminCursor, patientCursor, doctorCursor, cashierCursor;

    //default constructor;
    public AccountSearchDAO() {

    }

    //Account Search method. Search by loginID and return the data.
    public ArrayList<String> accountSearch(String loginID, Context context) {
        adminCursor = getAdminCursor(context);
        patientCursor = getPatientCursor(context);
        doctorCursor = getDoctorCursor(context);
        cashierCursor = getCashierCursor(context);

        ArrayList<String> existAccount = new ArrayList<String>();

        while (adminCursor.moveToNext()) {
            if (loginID.equals(adminCursor.getString(3))) {
                existAccount.add(adminCursor.getString(0));
                existAccount.add(adminCursor.getString(1));
                existAccount.add(adminCursor.getString(2));
                existAccount.add(adminCursor.getString(3));
                existAccount.add(adminCursor.getString(4));
                existAccount.add("admin");
            }
        }

        while (patientCursor.moveToNext()) {
            if (loginID.equals(patientCursor.getString(4))) {
                existAccount.add(patientCursor.getString(0));
                existAccount.add(patientCursor.getString(1));
                existAccount.add(patientCursor.getString(2));
                existAccount.add(patientCursor.getString(3));
                existAccount.add(patientCursor.getString(4));
                existAccount.add(patientCursor.getString(5));
                existAccount.add(patientCursor.getString(6));
                existAccount.add(patientCursor.getString(7));
                existAccount.add(patientCursor.getString(8));
                existAccount.add(patientCursor.getString(9));
                existAccount.add(patientCursor.getString(10));
                existAccount.add("patient");
            }
        }

        while (doctorCursor.moveToNext()) {
            if (loginID.equals(doctorCursor.getString(4))) {
                existAccount.add(doctorCursor.getString(0));
                existAccount.add(doctorCursor.getString(1));
                existAccount.add(doctorCursor.getString(2));
                existAccount.add(doctorCursor.getString(3));
                existAccount.add(doctorCursor.getString(4));
                existAccount.add(doctorCursor.getString(5));
                existAccount.add(doctorCursor.getString(6));
                existAccount.add(doctorCursor.getString(7));
                existAccount.add(doctorCursor.getString(8));
                existAccount.add("doctor");
            }
        }

        while (cashierCursor.moveToNext()) {
            if (loginID.equals(cashierCursor.getString(3))) {
                existAccount.add(cashierCursor.getString(0));
                existAccount.add(cashierCursor.getString(1));
                existAccount.add(cashierCursor.getString(2));
                existAccount.add(cashierCursor.getString(3));
                existAccount.add(cashierCursor.getString(4));
                existAccount.add(cashierCursor.getString(5));
                existAccount.add("cashier");
            }
        }
        return existAccount;
    }

    //Admin table cursor
    public Cursor getAdminCursor(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.AdminTable.TABLE_NAME, null, null, null, null, null, null);
    }

    //Patient table cursor
    public Cursor getPatientCursor(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.PatientTable.TABLE_NAME, null, null, null, null, null, null);
    }

    //Doctor table cursor
    public Cursor getDoctorCursor(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.DoctorTable.TABLE_NAME, null, null, null, null, null, null);
    }

    //Casher table cursor
    public Cursor getCashierCursor(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.CashierTable.TABLE_NAME, null, null, null, null, null, null);
    }
}
