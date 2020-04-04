package com.Group6.checkup.Utils.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;

import java.util.ArrayList;
import java.util.List;

public class InvoiceDao extends Dao<Invoice> {

    public InvoiceDao(@Nullable Context context) {
        super(context);
    }

    @Override
    public boolean exists(String... patientId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.InvoiceTable.PATIENT_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.PatientTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                patientId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        return (cursor.getCount() > 0) ? true : false;
    }

    @Override
    public Invoice find(String... invoiceId) {
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.InvoiceTable._ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.InvoiceTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                invoiceId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        cursor.move(1);

        Invoice recordObject = new Invoice(
                cursor.getInt(0),
                cursor.getDouble(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getLong(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8)
        );

        return recordObject;
    }

    @Override
    public List findAll() {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        Cursor cursor;

        cursor = dbConnection.rawQuery("SELECT * FROM " + DatabaseTable.InvoiceTable.TABLE_NAME, null);

        List<Invoice> invoiceList = new ArrayList<>();

        while (cursor.moveToNext()){


            Invoice recordObject = new Invoice(
                    cursor.getInt(0),
                    cursor.getDouble(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );
            invoiceList.add(recordObject);
        }

        return invoiceList;
    }

    public List findAllByPatient(String... patientId) {

        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        // Filter results WHERE "title" = 'My Title'
        String selection = DatabaseTable.InvoiceTable.PATIENT_ID + " = ?";

        Cursor cursor = dbConnection.query(
                DatabaseTable.InvoiceTable.TABLE_NAME,   // The table to query
                null,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                patientId,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<Invoice> invoiceList = new ArrayList<>();

        while(cursor.moveToNext()){

            Invoice recordObject = new Invoice(
                    cursor.getInt(0),
                    cursor.getDouble(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getLong(4),
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8)
            );

            invoiceList.add(recordObject);
        }

        return invoiceList;
    }

    @Override
    public boolean insert(Invoice object) {

        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.InvoiceTable.PRICE,object.getPrice());
        recordObject.put(DatabaseTable.InvoiceTable.PAYMENT_DUE,object.getPaymentDue());
        recordObject.put(DatabaseTable.InvoiceTable.INVOICE_DATE,object.getInvoiceDate());
        recordObject.put(DatabaseTable.InvoiceTable.PATIENT_ID,object.getPatientID());
        recordObject.put(DatabaseTable.InvoiceTable.CASHIER_ID,object.getCashierID());
        recordObject.put(DatabaseTable.InvoiceTable.DOCTOR_ID,object.getDoctorID());
        recordObject.put(DatabaseTable.InvoiceTable.APPOINTMENT_ID,object.getAppointmentID());

        //get writable database
        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        Long result = dbConnection.insert(DatabaseTable.InvoiceTable.TABLE_NAME,null,recordObject);

        return result != -1;
    }

    @Override
    public boolean update(Invoice object) {


        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        //create entry
        ContentValues recordObject = new ContentValues();

        //populate entry with object attributes
        recordObject.put(DatabaseTable.InvoiceTable.PRICE, object.getPrice());
        recordObject.put(DatabaseTable.InvoiceTable.PAYMENT_DUE, object.getPaymentDue());
        recordObject.put(DatabaseTable.InvoiceTable.PAYMENT_STATUS, object.getPaymentStatus());
        recordObject.put(DatabaseTable.InvoiceTable.INVOICE_DATE, object.getInvoiceDate());
        recordObject.put(DatabaseTable.InvoiceTable.PATIENT_ID, object.getPatientID());
        recordObject.put(DatabaseTable.InvoiceTable.CASHIER_ID, object.getCashierID());
        recordObject.put(DatabaseTable.InvoiceTable.DOCTOR_ID, object.getDoctorID());
        recordObject.put(DatabaseTable.InvoiceTable.APPOINTMENT_ID, object.getAppointmentID());


        // Filter results WHERE "loginID" = 'A001'
        String selection = DatabaseTable.InvoiceTable._ID + " = ?";
        String[] selectionArgs = { String.valueOf(object.getID()) };

        int result = dbConnection.update(DatabaseTable.InvoiceTable.TABLE_NAME,recordObject,selection,selectionArgs);

        return result > 0;
    }

    @Override
    public boolean delete(String... searchId) {

        SQLiteDatabase dbConnection = this.db.getWritableDatabase();

        String selection = DatabaseTable.InvoiceTable._ID + " = ?";

        int result = dbConnection.delete(DatabaseTable.InvoiceTable.TABLE_NAME,selection,searchId);

        return result > 0;
    }

    public List<Patient> pendingPayment(){
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        String query = "SELECT " + DatabaseTable.PatientTable.FIRST_NAME + ", "
                 + DatabaseTable.PatientTable.LAST_NAME + ", " + DatabaseTable.InvoiceTable.PATIENT_ID +
                " FROM " + DatabaseTable.PatientTable.TABLE_NAME + " p " +
                " JOIN " + DatabaseTable.InvoiceTable.TABLE_NAME + " i "
                + " ON i." + DatabaseTable.InvoiceTable.PATIENT_ID + " = p." +
                DatabaseTable.PatientTable._ID + " WHERE " +
                DatabaseTable.InvoiceTable.PAYMENT_STATUS + " = 'pending'";

        Cursor c = dbConnection.rawQuery(query,null);

        List<Patient> pendingList = new ArrayList<Patient>();

        while (c.moveToNext()){
            Patient pList = new Patient();
            pList.setFirstName(c.getString(0));
            pList.setLastName(c.getString(1));

            Invoice invoice = new Invoice();
            invoice.setPatientID(c.getInt(2));

            pList.setInvoice(invoice);

            pendingList.add(pList);

            }
        c.close();
        return pendingList;
    }

    public Invoice getPrice(String id){
        SQLiteDatabase dbConnection = this.db.getReadableDatabase();

        String query = "SELECT " + DatabaseTable.InvoiceTable.PRICE + " FROM " +
                        DatabaseTable.InvoiceTable.TABLE_NAME + " WHERE " +
                        DatabaseTable.InvoiceTable.PATIENT_ID + " =?";

        Cursor cursor = dbConnection.rawQuery(query, new String[]{String.valueOf(DatabaseTable.InvoiceTable.PATIENT_ID)});
        Invoice recordObject = null;
        try {
            if (cursor.moveToFirst()) {
                // read column data
                recordObject.setPrice(cursor.getDouble(0));
            }
        } finally {
            cursor.close();
        }
        return recordObject;
    }
}
