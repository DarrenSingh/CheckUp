package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import androidx.annotation.Nullable;

import com.Group6.checkup.Database.DatabaseTable;
import com.Group6.checkup.Entities.OnlineHelpReply;

import java.util.ArrayList;
import java.util.List;

public class OnlineHelpReplyDao extends Dao<OnlineHelpReply> {
    public OnlineHelpReplyDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... id) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.OnlineHelpReplyTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpReplyTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                id,          // The values for the WHERE clause
                null,                   //group the rows
                null,                   //filter by row groups
                null               //sort order
        );

        return cursor.getCount() > 0;
    }

    @Override
    public OnlineHelpReply find(String... id) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.OnlineHelpReplyTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.OnlineHelpReplyTable.TABLE_NAME,   // The table to query
                null,             // array of columns to return - null to get all
                selection,              // The columns for the WHERE clause
                id,
                null,
                null,
                null
        );

        if (cursor.getCount() <= 0)
            throw new SQLiteException("No such entry");

        cursor.move(1);

        OnlineHelpReply recordObject = new OnlineHelpReply(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3),
                cursor.getInt(4)
        );

        return recordObject;
    }

    @Override
    public List<OnlineHelpReply> findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();
        List<OnlineHelpReply> recordObjectList = new ArrayList<>();

        try {

            Cursor cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.OnlineHelpReplyTable.TABLE_NAME, null);

            if (cursor.getCount() <= 0)
                throw new SQLiteException("No database entries");


            while (cursor.moveToNext()) {

                OnlineHelpReply recordObject = new OnlineHelpReply(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3),
                        cursor.getInt(4)
                );

                recordObjectList.add(recordObject);

            }

        } catch (SQLiteException e) {
            e.printStackTrace();
        }

        return recordObjectList;
    }

    @Override
    public boolean insert(OnlineHelpReply object) {

        SQLiteDatabase dbConnection = db.getWritableDatabase();

        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_TITLE, object.getMessageTitle());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_CONTENT, object.getMessageContent());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_DATE_TIME, object.getDateTime());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.DOCTOR_ID, object.getDoctorID());

        long result = dbConnection.insert(DatabaseTable.OnlineHelpReplyTable.TABLE_NAME, null, recordObject);

        return result != -1;
    }

    @Override
    public boolean update(OnlineHelpReply object) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_TITLE, object.getMessageTitle());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_CONTENT, object.getMessageContent());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_DATE_TIME, object.getDateTime());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.DOCTOR_ID, object.getDoctorID());


        // Filter results WHERE "_ID" = 'n'
        String selection = DatabaseTable.OnlineHelpReplyTable._ID + " = ?";
        String[] selectionArgs = {String.valueOf(object.getID())};

        int result = dbConnection.update(DatabaseTable.AdminTable.TABLE_NAME, recordObject, selection, selectionArgs);

        return result > 0;
    }

    @Override
    public boolean delete(String... id) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.OnlineHelpReplyTable._ID + " = ?";

        int result = dbConnection.delete(
                DatabaseTable.OnlineHelpReplyTable.TABLE_NAME,
                selection,
                id
        );

        return result > 0;
    }

    public int getLastRow() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        try {

            Cursor cursor = dbConnection.rawQuery("SELECT MAX(" + DatabaseTable.OnlineHelpReplyTable._ID + ") FROM " + DatabaseTable.OnlineHelpReplyTable.TABLE_NAME, null);

            if (cursor.getCount() <= 0)
                throw new SQLiteException("No database entries");

            cursor.move(1);

            return cursor.getInt(0);

        } catch (SQLiteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int insertWithResult(OnlineHelpReply object) {

        SQLiteDatabase dbConnection = db.getWritableDatabase();

        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_TITLE, object.getMessageTitle());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_CONTENT, object.getMessageContent());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.REPLY_DATE_TIME, object.getDateTime());
        recordObject.put(DatabaseTable.OnlineHelpReplyTable.DOCTOR_ID, object.getDoctorID());

        long result = dbConnection.insert(DatabaseTable.OnlineHelpReplyTable.TABLE_NAME, null, recordObject);

        return (int) result;
    }
}
