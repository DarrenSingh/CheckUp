package com.Group6.checkup.DatabasePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;

import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context);
        }
        return sInstance;
    }

    private DatabaseHelper(@Nullable Context context) {
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

        new DatabaseSeedAsync().execute();
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


    private class DatabaseSeedAsync extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            //Create Admin Users
            AdminDao adminDao = new AdminDao(null);
            adminDao.insert(new Admin("Super","User", "A001", "root"));

            //Create Cashier Users
            CashierDao cashierDao = new CashierDao(null);
            cashierDao.insert(new Cashier("Rachel","Green","C001","password",1));

            //Create Doctor Users
            DoctorDao doctorDao = new DoctorDao(null);
            doctorDao.insert(new Doctor("Micheal","Scarn","15122 72 Ave, Surrey, BC V3S 2G2","D001","password","604-555-2144","scarn@medicine.com",1));
            doctorDao.insert(new Doctor("Deandre","Jordan","6844 King George Blvd, Surrey, BC V3W 4Z9","D002","password","604-555-2143","jordan@medicine.com",1));
            doctorDao.insert(new Doctor("Elaine","Jackson","615300 105 Avenue, Surrey, BC","D003","password","604-555-2543","elaine@medicine.com",1));
            doctorDao.insert(new Doctor("Robin","Lopez","4731 King George Blvd, Surrey, BC V3W 4Z9","D004","password","604-555-2343","lopez@medicine.com",1));

            //Create Patient Users
            PatientDao patientDao = new PatientDao(null);
            patientDao.insert(new Patient("Jane","Doe","1234 Fake St","P001","password",true,"604-555-1234",123000998,"janedoe@mail.com",1));
            patientDao.insert(new Patient("John","Doe","123 Fake St","P002","password1",true,"604-555-1234",123000999,"johndoe@mail.com",1));

            //Create Appointments
            AppointmentDao appointmentDao = new AppointmentDao(null);
            appointmentDao.insert(new Appointment(System.currentTimeMillis(), 1,1));
            appointmentDao.insert(new Appointment(System.currentTimeMillis()-3000, 1,1));

            //Create Invoices
            InvoiceDao invoiceDao = new InvoiceDao(null);
            invoiceDao.insert(new Invoice(29.99, "May,20,2020", "paid", System.currentTimeMillis(), 1, 1, 1, 1));
            invoiceDao.insert(new Invoice(19.99, "May,23,2020", "paid", System.currentTimeMillis(), 1, 1, 1, 2));

            //Create Online Help
            OnlineHelpDao onlineHelpDao = new OnlineHelpDao(null);
            onlineHelpDao.insert(new OnlineHelp("Test Message1","Lorem Ipsum",System.currentTimeMillis()-8000,1,1));
            onlineHelpDao.insert(new OnlineHelp("Test Message2","Lorem Ipsum",System.currentTimeMillis()-5000,1,2,1));

            //Create Online Help
            OnlineHelpReplyDao onlineHelpReplyDao = new OnlineHelpReplyDao(null);
            onlineHelpReplyDao.insert(new OnlineHelpReply("Test Reply","Lorem Ipsum",System.currentTimeMillis(),2));

            Log.e("DATABASE HELPER","SEEDING COMPLETE");

            return null;
        }
    }


}
