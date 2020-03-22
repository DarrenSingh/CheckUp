package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Doctor;

import java.util.ArrayList;
import java.util.List;

public class DoctorDao extends Dao<Doctor> {
    public DoctorDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... loginId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.DoctorTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.DoctorTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                loginId,          // The values for the WHERE clause
                null,                   //group the rows
                null,                   //filter by row groups
                null               //sort order
        );

        return (cursor.getCount() > 0) ? true : false;
    }

    @Override
    public Doctor find(String... loginId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'D001'
        String selection = DatabaseTable.DoctorTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.DoctorTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                loginId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        if(cursor.getCount() < 0)
            throw new SQLiteException("No such entry");

        cursor.move(1);

        Doctor recordObject = new Doctor(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getInt(8)
        );

        return recordObject;

    }

    @Override
    public List<Doctor> findAll() {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.DoctorTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Doctor> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Doctor recordObject = new Doctor(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(7),
                    cursor.getString(9),
                    cursor.getInt(10)
            );


            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }

    @Override
    public boolean insert(Doctor object) {
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.DoctorTable.FIRST_NAME,object.getFirstName());
        recordObject.put(DatabaseTable.DoctorTable.LAST_NAME,object.getLastName());
        recordObject.put(DatabaseTable.DoctorTable.OFFICE_ADDRESS,object.getOfficeAddress());
        recordObject.put(DatabaseTable.DoctorTable.LOGIN_ID,object.getLoginID());
        recordObject.put(DatabaseTable.DoctorTable.PASSWORD,object.getPassword());
        recordObject.put(DatabaseTable.DoctorTable.PHONE_NUMBER,object.getPhoneNumber());
        recordObject.put(DatabaseTable.DoctorTable.EMAIL_ADDRESS,object.getEmailAddress());
        recordObject.put(DatabaseTable.DoctorTable.ADMIN_ID,object.getAdminID());

        //get writable database
        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        Long result = dbConnection.insert(DatabaseTable.DoctorTable.TABLE_NAME,null,recordObject);

        return (result == -1) ? false : true;
    }

    @Override
    public boolean update(Doctor object) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.DoctorTable.FIRST_NAME,object.getFirstName());
        recordObject.put(DatabaseTable.DoctorTable.LAST_NAME,object.getLastName());
        recordObject.put(DatabaseTable.DoctorTable.OFFICE_ADDRESS,object.getOfficeAddress());
        recordObject.put(DatabaseTable.DoctorTable.LOGIN_ID,object.getLoginID());
        recordObject.put(DatabaseTable.DoctorTable.PASSWORD,object.getPassword());
        recordObject.put(DatabaseTable.DoctorTable.PHONE_NUMBER,object.getPhoneNumber());
        recordObject.put(DatabaseTable.DoctorTable.EMAIL_ADDRESS,object.getEmailAddress());
        recordObject.put(DatabaseTable.DoctorTable.ADMIN_ID,object.getAdminID());


        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.DoctorTable.LOGIN_ID + " = ?";
        String[] selectionArgs = { object.getLoginID() };

        int result = dbConnection.update(DatabaseTable.DoctorTable.TABLE_NAME,recordObject,selection,selectionArgs);

        return (result > 0)? true : false;

    }

    @Override
    public boolean delete(String... loginId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.DoctorTable.LOGIN_ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.DoctorTable.TABLE_NAME,selection,loginId);

        return (result > 0) ? true : false;
    }
}
