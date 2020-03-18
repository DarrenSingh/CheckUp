package com.Group6.checkup.DatabasePackage.DAOPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.TableClassPackage.Patient;

public class PatientDAO {

    ContentValues values;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    //Insert method
    public void patientAccountInsert(Patient patient, Context context) {
        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.PatientTable.FIRST_NAME, patient.getFirstName());
        values.put(DatabaseTable.PatientTable.LAST_NAME, patient.getLastName());
        values.put(DatabaseTable.PatientTable.ADDRESS, patient.getAddress());
        values.put(DatabaseTable.PatientTable.LOGIN_ID, patient.getLoginID());
        values.put(DatabaseTable.PatientTable.PASSWORD, patient.getPassword());
        values.put(DatabaseTable.PatientTable.MSP_STATUS, patient.getMspStatus());
        values.put(DatabaseTable.PatientTable.PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER, patient.getHealthCareCardNumber());
        values.put(DatabaseTable.PatientTable.EMAIL_ADDRESS, patient.getEmailAddress());
        values.put(DatabaseTable.PatientTable.ADMIN_ID, patient.getAdminID());

        long check = db.insert(DatabaseTable.PatientTable.TABLE_NAME, null, values);

        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }

    //Edit method
    public void patientAccountEdit(Patient patient, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.PatientTable.FIRST_NAME, patient.getFirstName());
        values.put(DatabaseTable.PatientTable.LAST_NAME, patient.getLastName());
        values.put(DatabaseTable.PatientTable.ADDRESS, patient.getAddress());
        values.put(DatabaseTable.PatientTable.LOGIN_ID, patient.getLoginID());
        values.put(DatabaseTable.PatientTable.PASSWORD, patient.getPassword());
        values.put(DatabaseTable.PatientTable.MSP_STATUS, patient.getMspStatus());
        values.put(DatabaseTable.PatientTable.PHONE_NUMBER, patient.getPhoneNumber());
        values.put(DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER, patient.getHealthCareCardNumber());
        values.put(DatabaseTable.PatientTable.EMAIL_ADDRESS, patient.getAddress());

        int check = db.update(DatabaseTable.PatientTable.TABLE_NAME, values, DatabaseTable.PatientTable._ID + " = " + patient.getPatientID(), null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete method
    public void patientAccountDelete(int rowID, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.PatientTable.TABLE_NAME, DatabaseTable.PatientTable._ID + " = " + rowID, null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }


}
