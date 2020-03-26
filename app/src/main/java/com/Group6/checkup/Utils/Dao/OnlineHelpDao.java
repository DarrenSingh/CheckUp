package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.OnlineHelp;

import java.util.ArrayList;
import java.util.List;

public class OnlineHelpDao extends Dao<OnlineHelp> {

    public OnlineHelpDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... recipientId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.OnlineHelpTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                recipientId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return (cursor.getCount() > 0) ? true : false;
    }

    @Override
    public OnlineHelp find(String...id) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.OnlineHelpTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                id,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.move(1);

        OnlineHelp recordObject = new OnlineHelp(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6)
        );


        return recordObject;
    }

    public List<OnlineHelp> findAllByDoctor(String... doctorId){

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.OnlineHelpTable.DOCTOR_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                doctorId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<OnlineHelp> onlineHelpList = new ArrayList<>();

        while(cursor.moveToNext()){

            OnlineHelp recordObject = new OnlineHelp(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );

            onlineHelpList.add(recordObject);

        }

        return onlineHelpList;
    }

    public List<OnlineHelp> findAllByPatient(String... patientId){

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.OnlineHelpTable.PATIENT_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                patientId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<OnlineHelp> onlineHelpList = new ArrayList<>();

        while(cursor.moveToNext()){

            OnlineHelp recordObject = new OnlineHelp(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );

            onlineHelpList.add(recordObject);

        }

        return onlineHelpList;
    }

    @Override
    public List<OnlineHelp> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        Cursor cursor;

        cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.OnlineHelpTable.TABLE_NAME, null);

        List<OnlineHelp> onlineHelpList = new ArrayList<>();

        while (cursor.moveToNext()){

            OnlineHelp recordObject = new OnlineHelp(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getInt(4),
                    cursor.getInt(5),
                    cursor.getInt(6)
            );

            onlineHelpList.add(recordObject);
        }

        return onlineHelpList;
    }

    @Override
    public boolean insert(OnlineHelp object) {

        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.OnlineHelpTable.MESSAGE_TITLE,object.getMessageTitle());
        recordObject.put(DatabaseTable.OnlineHelpTable.MESSAGE_CONTENT,object.getMessage());
        recordObject.put(DatabaseTable.OnlineHelpTable.SENT_DATE_TIME,object.getSentDateTime());
        recordObject.put(DatabaseTable.OnlineHelpTable.PATIENT_ID,object.getPatientID());
        recordObject.put(DatabaseTable.OnlineHelpTable.DOCTOR_ID,object.getDoctorID());
        if(object.getOnlineHelpReplyID() != 0) {
            recordObject.put(DatabaseTable.OnlineHelpTable.ONLINE_HELP_REPLY_ID, object.getOnlineHelpReplyID());
        }
        //get writable database
        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        Long result = dbConnection.insert(DatabaseTable.OnlineHelpTable.TABLE_NAME,null,recordObject);

        return (result == -1) ? false : true;
    }

    @Override
    public boolean update(OnlineHelp object) {
        return false;
    }

    @Override
    public boolean delete(String... messageId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.OnlineHelpTable._ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.OnlineHelpTable.TABLE_NAME,selection,messageId);

        return (result > 0) ? true : false;
    }
}
