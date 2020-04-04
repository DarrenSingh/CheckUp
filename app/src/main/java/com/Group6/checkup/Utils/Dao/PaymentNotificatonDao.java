package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.PaymentNotification;

import java.util.ArrayList;
import java.util.List;

public class PaymentNotificatonDao extends Dao<PaymentNotification>{

    public PaymentNotificatonDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.PaymentNotificationTable.PATIENT_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.PaymentNotificationTable.TABLE_NAME,   // The table to query
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
    public PaymentNotification find(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.PaymentNotificationTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.PaymentNotificationTable.TABLE_NAME,   // The table to query
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

        PaymentNotification recordObject = new PaymentNotification(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getInt(5)
        );

        return recordObject;
    }

    @Override
    public List<PaymentNotification> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();


        Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.PaymentNotificationTable.TABLE_NAME, null);

        if(cursor.getCount() < 0)
            throw new SQLiteException("No database entries");


        List<PaymentNotification> recordObjectList = new ArrayList<>();

        while(cursor.moveToNext()){

            PaymentNotification recordObject = new PaymentNotification(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5)
            );

            recordObjectList.add(recordObject);

        }

        return recordObjectList;
    }

    @Override
    public boolean insert(PaymentNotification object) {

        ContentValues recordObject = new ContentValues();
        SQLiteDatabase dbConnection = db.getWritableDatabase();

        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE_TITLE, object.getMessageTitle());
        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.SENT_DATE_TIME, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.CASHIER_ID, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.PATIENT_ID, object.getMessage());

        long result = dbConnection.insert(DatabaseTable.PaymentNotificationTable.TABLE_NAME, null, recordObject);

        return result != -1;
    }

    @Override
    public boolean update(PaymentNotification object) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE_TITLE, object.getMessageTitle());
        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.SENT_DATE_TIME, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE, object.getMessage());
        recordObject.put(DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE, object.getMessage());


        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.PaymentNotificationTable._ID + " = ?";
        String[] selectionArgs = { String.valueOf(object.getID()) };

        int result = dbConnection.update(DatabaseTable.AdminTable.TABLE_NAME,recordObject,selection,selectionArgs);

        return result > 0;
    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.PaymentNotificationTable._ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.PaymentNotificationTable.TABLE_NAME,selection,searchId);

        return result > 0;
    }

}
