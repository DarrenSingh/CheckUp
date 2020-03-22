package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientDao extends Dao<Patient>{

    public PatientDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... searchId) {
        SQLiteDatabase dbConnection = db.getReadableDatabase();


        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.PatientTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.PatientTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                searchId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return (cursor.getCount() > 0) ? true : false;
    }

    @Override
    public Patient find(String... searchId) {

        SQLiteDatabase dbConnection = db.getReadableDatabase();


        // Filter results WHERE "loginId" = 'searchId'
        String selection = DatabaseTable.PatientTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.PatientTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                searchId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.getCount() < 0)
            throw new SQLiteException("No such entry");

        cursor.move(1);

        Patient patientRecord = new Patient(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                Boolean.parseBoolean(cursor.getString(6)),
                cursor.getString(7),
                cursor.getInt(8),
                cursor.getString(9),
                cursor.getInt(10)
        );

        return patientRecord;
    }

    @Override
    public List<Patient> findAll() {
        SQLiteDatabase dbConnection = db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.PatientTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Patient> patientsList = new ArrayList<>();

        while(cursor.moveToNext()){


            Patient patientRecord = new Patient(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    Boolean.parseBoolean(cursor.getString(6)),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getString(9),
                    cursor.getInt(10)
            );

            patientsList.add(patientRecord);

        }

        return patientsList;    }

    @Override
    public boolean insert(Patient object) {

        //get writable database
        SQLiteDatabase dbConnection = db.getWritableDatabase();

        //create entry
        ContentValues objectRecord = new ContentValues();

        //populate entry with patient attributes
        objectRecord.put(DatabaseTable.PatientTable.FIRST_NAME,object.getFirstName());
        objectRecord.put(DatabaseTable.PatientTable.LAST_NAME,object.getLastName());
        objectRecord.put(DatabaseTable.PatientTable.ADDRESS,object.getAddress());
        objectRecord.put(DatabaseTable.PatientTable.LOGIN_ID,object.getLoginID());
        objectRecord.put(DatabaseTable.PatientTable.PASSWORD,object.getPassword());
        objectRecord.put(DatabaseTable.PatientTable.MSP_STATUS,object.getMspStatus());
        objectRecord.put(DatabaseTable.PatientTable.PHONE_NUMBER,object.getPhoneNumber());
        objectRecord.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER,object.getHealthCareCardNumber());
        objectRecord.put(DatabaseTable.PatientTable.EMAIL_ADDRESS,object.getEmailAddress());
        objectRecord.put(DatabaseTable.PatientTable.ADMIN_ID,object.getAdminID());

        Long result = dbConnection.insert(DatabaseTable.PatientTable.TABLE_NAME,null,objectRecord);

        return (result == -1) ? false : true;
    }

    @Override
    public boolean update(Patient object) {

        SQLiteDatabase dbConnection = db.getReadableDatabase();

        //create entry
        ContentValues objectRecord = new ContentValues();

        //populate entry with patient attributes
        objectRecord.put(DatabaseTable.PatientTable.FIRST_NAME,object.getFirstName());
        objectRecord.put(DatabaseTable.PatientTable.LAST_NAME,object.getLastName());
        objectRecord.put(DatabaseTable.PatientTable.ADDRESS,object.getAddress());
        objectRecord.put(DatabaseTable.PatientTable.LOGIN_ID,object.getLoginID());
        objectRecord.put(DatabaseTable.PatientTable.PASSWORD,object.getPassword());
        objectRecord.put(DatabaseTable.PatientTable.MSP_STATUS,object.getMspStatus());
        objectRecord.put(DatabaseTable.PatientTable.PHONE_NUMBER,object.getPhoneNumber());
        objectRecord.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER,object.getHealthCareCardNumber());
        objectRecord.put(DatabaseTable.PatientTable.EMAIL_ADDRESS,object.getEmailAddress());
        objectRecord.put(DatabaseTable.PatientTable.ADMIN_ID,object.getAdminID());


        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.PatientTable.LOGIN_ID + " = ?";
        String[] selectionArguments = { object.getLoginID() };

        int result = dbConnection.update(DatabaseTable.PatientTable.TABLE_NAME,objectRecord,selection,selectionArguments);

        return (result > 0)? true : false;    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = db.getWritableDatabase();

        String selection = DatabaseTable.PatientTable.LOGIN_ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.PatientTable.TABLE_NAME,selection,searchId);

        return (result > 0) ? true : false;
    }

}