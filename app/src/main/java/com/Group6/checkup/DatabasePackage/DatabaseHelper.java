package com.Group6.checkup.DatabasePackage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.Nullable;

import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
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
            cashierDao.insert(new Cashier(1,"Rachel","Green","C001","password",1));

            //Create Doctor Users
            DoctorDao doctorDao = new DoctorDao(null);
            doctorDao.insert(new Doctor(1,"Micheal","Scarn","322 Sydney Way","D001","password","604-555-2144","scarn@medicine.com",1));

            //Create Patient Users
            PatientDao patientDao = new PatientDao(null);
            patientDao.insert(new Patient(1,"Jane","Doe","1234 Fake St","P001","password",true,"604-555-1234",123000998,"janedoe@mail.com",1));
            patientDao.insert(new Patient(2,"John","Doe","123 Fake St","P002","password1",true,"604-555-1234",123000999,"johndoe@mail.com",1));


            InvoiceDao invoiceDao = new InvoiceDao(null);
            invoiceDao.insert(new Invoice(25,"May,05,2019","unpaid",System.currentTimeMillis(),1,1,1,1));
            invoiceDao.insert(new Invoice(25,"May,05,2019","unpaid",System.currentTimeMillis(),2,1,1,1));

            AppointmentDao appointmentDao = new AppointmentDao(null);
            appointmentDao.insert(new Appointment(System.currentTimeMillis(),1,1));
            //Create Messages
            OnlineHelpDao onlineHelpDao = new OnlineHelpDao(null);
            //TODO look into foreign key constraint error
            onlineHelpDao.insert(new OnlineHelp("Test Message1","Lorem Ipsum","Jan 23, 2020",1,1));
            onlineHelpDao.insert(new OnlineHelp("Test Message2","Lorem Ipsum","Jan 24, 2020",1,1));
//            onlineHelpDao.insert(new OnlineHelp("Test Message3","Lorem Ipsum","Jan 27, 2020",1,1));

            return null;
        }
    }


}
