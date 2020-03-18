package com.Group6.checkup.DatabasePackage.DAOPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.TableClassPackage.Doctor;

public class DoctorDAO {

    ContentValues values;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    //Insert method
    public void doctorAccountInsert(Doctor doctor, Context context) {
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
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }

    //Edit method
    public void doctorAccountEdit(Doctor doctor, Context context) {
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
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }

    }

    //Delete method
    public void doctorAccountDelete(int rowID, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.DoctorTable.TABLE_NAME, DatabaseTable.DoctorTable._ID + " = " + rowID, null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
