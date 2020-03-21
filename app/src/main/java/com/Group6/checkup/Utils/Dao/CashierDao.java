package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Cashier;

import java.util.ArrayList;
import java.util.List;

public class CashierDao extends Dao<Cashier> {


    public CashierDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'C001'
        String selection = DatabaseTable.CashierTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.CashierTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                searchId,          // The values for the WHERE clause
                null,                   //group the rows
                null,                   //filter by row groups
                null               //sort order
        );

        return (cursor.getCount() > -1) ? true : false;
    }

    @Override
    public Cashier find(String... searchId) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.CashierTable.LOGIN_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.CashierTable.TABLE_NAME,   // The table to query
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

        Cashier recordObject = new Cashier(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getInt(5)
        );

        return recordObject;
    }

    @Override
    public List<Cashier> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.CashierTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");

        List<Cashier> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            Cashier recordObject = new Cashier(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getInt(5)
            );

            recordObjectList.add(recordObject);

        }

        return recordObjectList;

    }

    @Override
    public boolean insert(Cashier object) {

        SQLiteDatabase dbConnection = db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DatabaseTable.CashierTable.FIRST_NAME, object.getFirstName());
        values.put(DatabaseTable.CashierTable.LAST_NAME, object.getLastName());
        values.put(DatabaseTable.CashierTable.LOGIN_ID, object.getLoginID());
        values.put(DatabaseTable.CashierTable.PASSWORD, object.getPassword());
        values.put(DatabaseTable.CashierTable.ADMIN_ID, object.getAdminID());

        long result = dbConnection.insert(DatabaseTable.CashierTable.TABLE_NAME, null, values);

        return (result == -1) ? false : true;
    }

    @Override
    public boolean update(Cashier object) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.CashierTable.FIRST_NAME, object.getFirstName());
        recordObject.put(DatabaseTable.CashierTable.LAST_NAME, object.getLastName());
        recordObject.put(DatabaseTable.CashierTable.LOGIN_ID, object.getLoginID());
        recordObject.put(DatabaseTable.CashierTable.PASSWORD, object.getPassword());
        recordObject.put(DatabaseTable.CashierTable.ADMIN_ID, object.getAdminID());


        // Filter results WHERE "loginID" = 'C001'
        String selection = DatabaseTable.CashierTable.LOGIN_ID + " = ?";
        String[] selectionArgs = { object.getLoginID() };

        int result = dbConnection.update(DatabaseTable.CashierTable.TABLE_NAME,recordObject,selection,selectionArgs);

        return (result > 0)? true : false;
    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.AdminTable.LOGIN_ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.CashierTable.TABLE_NAME,selection,searchId);

        return (result > 0) ? true : false;
    }

}
