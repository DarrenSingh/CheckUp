package com.Group6.checkup.DatabasePackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.Group6.checkup.Admin;
import com.Group6.checkup.Cashier;
import com.Group6.checkup.Doctor;
import com.Group6.checkup.Patient;

import java.util.ArrayList;

public class DatabaseDAO {
    ContentValues values;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    Cursor adminCursor, patientCursor, doctorCursor, cashierCursor;

    public DatabaseDAO(){

    }


    public ArrayList<String> accountSearch (String loginID, Context context){
        adminCursor = getAdminCursor(context);
        patientCursor = getPatientCursor(context);
        doctorCursor = getDoctorCursor(context);
        cashierCursor = getCashierCursor(context);

        ArrayList<String> existAccount = new ArrayList<String>();

        while(adminCursor.moveToNext()){
            if(loginID.equals(adminCursor.getString(3))){
                existAccount.add(adminCursor.getString(0));
                existAccount.add(adminCursor.getString(1));
                existAccount.add(adminCursor.getString(2));
                existAccount.add(adminCursor.getString(3));
                existAccount.add(adminCursor.getString(4));
                existAccount.add("admin");
            }
        }

        while(patientCursor.moveToNext()){
            if(loginID.equals(patientCursor.getString(4))){
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

        while(doctorCursor.moveToNext()){
            if(loginID.equals(doctorCursor.getString(4))){
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

        while(cashierCursor.moveToNext()){
            if(loginID.equals(cashierCursor.getString(3))){
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


    public void adminAccountInsert(Admin admin, Context context){

        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.AdminTable.FIRST_NAME, admin.getFirstName());
        values.put(DatabaseTable.AdminTable.LAST_NAME, admin.getLastName());
        values.put(DatabaseTable.AdminTable.LOGIN_ID, admin.getLoginID());
        values.put(DatabaseTable.AdminTable.PASSWORD, admin.getPassword());

        long check = db.insert(DatabaseTable.AdminTable.TABLE_NAME, null, values);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }


    public void adminAccountEdit(Admin admin, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.AdminTable.FIRST_NAME, admin.getFirstName());
        values.put(DatabaseTable.AdminTable.LAST_NAME, admin.getLastName());
        values.put(DatabaseTable.AdminTable.LOGIN_ID, admin.getLoginID());
        values.put(DatabaseTable.AdminTable.PASSWORD, admin.getPassword());

        int check = db.update(DatabaseTable.AdminTable.TABLE_NAME, values, DatabaseTable.AdminTable._ID + " = " + admin.getAdminID(), null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }

    }

    public void adminAccountDelete(int rowID, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.AdminTable.TABLE_NAME, DatabaseTable.AdminTable._ID + " = " + rowID, null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }

    }

    public void patientAccountInsert(Patient patient, Context context){
        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.PatientTable.FIRST_NAME, patient.getFirstName());
        values.put(DatabaseTable.PatientTable.LAST_NAME, patient.getLastName());
        values.put(DatabaseTable.PatientTable.ADDRESS, patient.getAddress());
        values.put(DatabaseTable.PatientTable.LOGIN_ID, patient.getLoginID());
        values.put(DatabaseTable.PatientTable.PASSWORD, patient.getPassword());
        values.put(DatabaseTable.PatientTable.MSP_STATUS, patient.getMspStatus());
        values.put(DatabaseTable.PatientTable.PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER, patient.getHealthCareCardNumber());
        values.put(DatabaseTable.PatientTable.EMAIL_ADDRESS, patient.getEmailAddress());
        values.put(DatabaseTable.PatientTable.ADMIN_ID, patient.getAdminID());

        long check = db.insert(DatabaseTable.PatientTable.TABLE_NAME, null, values);

        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }



    public void patientAccountEdit(Patient patient, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.PatientTable.FIRST_NAME, patient.getFirstName());
        values.put(DatabaseTable.PatientTable.LAST_NAME, patient.getLastName());
        values.put(DatabaseTable.PatientTable.ADDRESS, patient.getAddress());
        values.put(DatabaseTable.PatientTable.LOGIN_ID, patient.getLoginID());
        values.put(DatabaseTable.PatientTable.PASSWORD, patient.getPassword());
        values.put(DatabaseTable.PatientTable.MSP_STATUS, patient.getMspStatus());
        values.put(DatabaseTable.PatientTable.PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER, patient.getHealthCareCardNumber());
        values.put(DatabaseTable.PatientTable.EMAIL_ADDRESS, patient.getAddress());

        int check = db.update(DatabaseTable.PatientTable.TABLE_NAME, values, DatabaseTable.PatientTable._ID + " = " + patient.getPatientID(), null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void patientAccountDelete(int rowID, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.PatientTable.TABLE_NAME, DatabaseTable.PatientTable._ID + " = " + rowID, null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void cashierAccountInsert(Cashier cashier, Context context){
        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.CashierTable.FIRST_NAME, cashier.getFirstName());
        values.put(DatabaseTable.CashierTable.LAST_NAME, cashier.getLastName());
        values.put(DatabaseTable.CashierTable.LOGIN_ID, cashier.getLoginID());
        values.put(DatabaseTable.CashierTable.PASSWORD, cashier.getPassword());
        values.put(DatabaseTable.CashierTable.ADMIN_ID, cashier.getAdminID());

        long check = db.insert(DatabaseTable.CashierTable.TABLE_NAME, null, values);

        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }


    public void cashierAccountEdit(Cashier cashier, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.CashierTable.FIRST_NAME, cashier.getFirstName());
        values.put(DatabaseTable.CashierTable.LAST_NAME, cashier.getLastName());
        values.put(DatabaseTable.CashierTable.LOGIN_ID, cashier.getLoginID());
        values.put(DatabaseTable.CashierTable.PASSWORD, cashier.getPassword());

        int check = db.update(DatabaseTable.CashierTable.TABLE_NAME, values, DatabaseTable.CashierTable._ID + " = " + cashier.getCashierID(), null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void cashierAccountDelete(int rowID, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.CashierTable.TABLE_NAME, DatabaseTable.CashierTable._ID + " = " + rowID, null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public void doctorAccountInsert(Doctor doctor, Context context){
        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.DoctorTable.FIRST_NAME, doctor.getFirstName());
        values.put(DatabaseTable.DoctorTable.LAST_NAME, doctor.getLastName());
        values.put(DatabaseTable.DoctorTable.OFFICE_ADDRESS, doctor.getOfficeAddress());
        values.put(DatabaseTable.DoctorTable.LOGIN_ID, doctor.getLoginID());
        values.put(DatabaseTable.DoctorTable.PASSWORD, doctor.getPassword());
        values.put(DatabaseTable.DoctorTable.PHONE_NUMBER, doctor.getPhoneNumber());
        values.put(DatabaseTable.DoctorTable.EMAIL_ADDRESS, doctor.getEmailAddress());
        values.put(DatabaseTable.DoctorTable.ADMIN_ID, doctor.getAdminID());

        long check = db.insert(DatabaseTable.DoctorTable.TABLE_NAME, null, values);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }

    public void doctorAccountEdit(Doctor doctor, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.DoctorTable.FIRST_NAME, doctor.getFirstName());
        values.put(DatabaseTable.DoctorTable.LAST_NAME, doctor.getLastName());
        values.put(DatabaseTable.DoctorTable.OFFICE_ADDRESS, doctor.getOfficeAddress());
        values.put(DatabaseTable.DoctorTable.LOGIN_ID, doctor.getLoginID());
        values.put(DatabaseTable.DoctorTable.PASSWORD, doctor.getPassword());
        values.put(DatabaseTable.DoctorTable.PHONE_NUMBER, doctor.getPhoneNumber());
        values.put(DatabaseTable.DoctorTable.EMAIL_ADDRESS, doctor.getEmailAddress());

        int check = db.update(DatabaseTable.DoctorTable.TABLE_NAME, values, DatabaseTable.DoctorTable._ID + " = " + doctor.getDoctorID(), null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }

    }

    public void doctorAccountDelete(int rowID, Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.DoctorTable.TABLE_NAME, DatabaseTable.DoctorTable._ID + " = " + rowID, null);
        if(check == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getAdminCursor(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.AdminTable.TABLE_NAME, null,null,null,null,null,null);
    }
    public Cursor getPatientCursor(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.PatientTable.TABLE_NAME, null,null,null,null,null,null);
    }
    public Cursor getDoctorCursor(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.DoctorTable.TABLE_NAME, null,null,null,null,null,null);
    }
    public Cursor getCashierCursor(Context context){
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getReadableDatabase();
        return db.query(DatabaseTable.CashierTable.TABLE_NAME, null,null,null,null,null,null);
    }



}
