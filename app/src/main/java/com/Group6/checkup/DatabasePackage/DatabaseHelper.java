package com.Group6.checkup.DatabasePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DatabaseTable.DATABASE_NAME, null, DatabaseTable.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseEntries.SQL_ADMIN_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_PATIENT_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_DOCTOR_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_CASHIER_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_INVOICE_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_PAYMENT_NOTIFICATION_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_APPOINTMENT_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_ONLINE_HELP_REPLY_TABLE_CREATE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_ONLINE_HELP_TABLE_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DatabaseEntries.SQL_ADMIN_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_PATIENT_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_DOCTOR_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_CASHIER_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_INVOICE_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_PAYMENT_NOTIFICATION_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_APPOINTMENT_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_ONLINE_HELP_REPLY_TABLE_DELETE_ENTRIES);
        db.execSQL(DatabaseEntries.SQL_ONLINE_HELP_TABLE_DELETE_ENTRIES);

        onCreate(db);
    }

    //Foreign key constraint
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            String query = String.format("PRAGMA foreign_keys = %s", "ON");
            db.execSQL(query);
        } else {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


}
