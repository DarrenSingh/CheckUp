package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Admin;

import java.util.ArrayList;
import java.util.List;

public class AdminDao extends Dao<Admin>{

    public AdminDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.AdminTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AdminTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                searchId,          // The values for the WHERE clause
                null,                   //group the rows
                null,                   //filter by row groups
                null               //sort order
        );

        return (cursor.getCount() > 0) ? true : false;
    }

    @Override
    public Admin find(String... searchId) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AdminTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.AdminTable.TABLE_NAME,   // The table to query
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

        Admin recordObject = new Admin(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );

        return recordObject;
    }

    @Override
    public List<Admin> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.AdminTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<Admin> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Admin recordObject = new Admin(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );


            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }

    @Override
    public boolean insert(Admin object) {

        ContentValues recordObject = new ContentValues();
        SQLiteDatabase dbConnection = db.getWritableDatabase();

        recordObject.put(DatabaseTable.AdminTable.FIRST_NAME, object.getFirstName());
        recordObject.put(DatabaseTable.AdminTable.LAST_NAME, object.getLastName());
        recordObject.put(DatabaseTable.AdminTable.LOGIN_ID, object.getLoginID());
        recordObject.put(DatabaseTable.AdminTable.PASSWORD, object.getPassword());

        long result = dbConnection.insert(DatabaseTable.AdminTable.TABLE_NAME, null, recordObject);

        return (result >= 0) ? true : false;
    }

    @Override
    public boolean update(Admin object) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.AdminTable.FIRST_NAME, object.getFirstName());
        recordObject.put(DatabaseTable.AdminTable.LAST_NAME, object.getLastName());
        recordObject.put(DatabaseTable.AdminTable.LOGIN_ID, object.getLoginID());
        recordObject.put(DatabaseTable.AdminTable.PASSWORD, object.getPassword());


        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.AdminTable.LOGIN_ID + " = ?";
        String[] selectionArgs = { object.getLoginID() };

        int result = dbConnection.update(DatabaseTable.AdminTable.TABLE_NAME,recordObject,selection,selectionArgs);

        return (result > 0) ? true : false;
    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.AdminTable.LOGIN_ID + " = ?";

        int result = dbConnection.delete(
                DatabaseTable.AdminTable.TABLE_NAME,
                selection,
                searchId
        );

        return (result > 0) ? true : false;
    }

}
