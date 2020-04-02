package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDao extends Dao<Appointment> {

    public AppointmentDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... patientId) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.AppointmentTable.PATIENT_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AppointmentTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                patientId,          // The values for the WHERE clause
                null,                   //group the rows
                null,                   //filter by row groups
                null               //sort order
        );

        return cursor.getCount() > -1;
    }

    @Override
    public Appointment find(String... searchId) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AppointmentTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AppointmentTable.TABLE_NAME,   // The table to query
                null,             // array of columns to return - null to get all
                selection,              // The columns for the WHERE clause
                searchId,
                null,
                null,
                null
        );

        if(cursor.getCount() < 0)
            throw new SQLiteException("No such entry");

        cursor.move(1);

        Appointment recordObject = new Appointment(
                cursor.getInt(0),
                cursor.getLong(1),
                cursor.getInt(2),
                cursor.getInt(3)
        );

        return recordObject;
    }

    @Override
    public List<Appointment> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.AdminTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Appointment> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Appointment recordObject = new Appointment(
                    cursor.getInt(0),
                    cursor.getLong(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );

            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }

    public List<Appointment> findAllByPatient(String... patientId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AppointmentTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AppointmentTable.TABLE_NAME,   // The table to query
                null,             // array of columns to return - null to get all
                selection,              // The columns for the WHERE clause
                patientId,
                null,
                null,
                null
        );
        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Appointment> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Appointment recordObject = new Appointment(
                    cursor.getInt(0),
                    cursor.getLong(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );

            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }


    public List<Appointment> findAllByDoctor(String... doctorId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AppointmentTable.DOCTOR_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AppointmentTable.TABLE_NAME,   // The table to query
                null,             // array of columns to return - null to get all
                selection,              // The columns for the WHERE clause
                doctorId,
                null,
                null,null
//                DatabaseTable.AppointmentTable.APPOINTMENT_DATE_TIME + " DESC"
        );
        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Appointment> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Appointment recordObject = new Appointment(
                    cursor.getInt(0),
                    cursor.getLong(1),
                    cursor.getInt(2),
                    cursor.getInt(3)
            );

            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }

    @Override
    public boolean insert(Appointment object) {

        ContentValues recordObject = new ContentValues();
        SQLiteDatabase dbConnection = db.getWritableDatabase();

        recordObject.put(DatabaseTable.AppointmentTable.APPOINTMENT_DATE_TIME, object.getAppointmentDateTime());
        recordObject.put(DatabaseTable.AppointmentTable.PATIENT_ID, object.getPatientID());
        recordObject.put(DatabaseTable.AppointmentTable.DOCTOR_ID, object.getDoctorID());

        long result = dbConnection.insert(DatabaseTable.AppointmentTable.TABLE_NAME, null, recordObject);

        return result != -1;
    }

    @Override
    public boolean update(Appointment object) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.AppointmentTable.APPOINTMENT_DATE_TIME, object.getAppointmentDateTime());
        recordObject.put(DatabaseTable.AppointmentTable.PATIENT_ID, object.getPatientID());
        recordObject.put(DatabaseTable.AppointmentTable.DOCTOR_ID, object.getDoctorID());



        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AppointmentTable._ID + " = ?";
        String[] selectionArgs = { String.valueOf(object.getID()) };

        int result = dbConnection.update(
                DatabaseTable.AppointmentTable.TABLE_NAME,
                recordObject,
                selection,
                selectionArgs
        );

        return result > 0;
    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.AppointmentTable._ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.AppointmentTable.TABLE_NAME,selection,searchId);

        return result > 0;
    }
}
