package com.Group6.checkup.DatabasePackage;

import android.os.AsyncTask;

import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

public class AsyncDatabaseSeeder extends AsyncTask<Void,Void,Void> {

    private AdminDao adminDao;
    private CashierDao cashierDao;
    private DoctorDao doctorDao;
    private AppointmentDao appointmentDao;
    private InvoiceDao invoiceDao;
    private OnlineHelpDao onlineHelpDao;
    private OnlineHelpReplyDao onlineHelpReplyDao;
    private PatientDao patientDao;

    public AsyncDatabaseSeeder() {

        adminDao = new AdminDao(null);
        cashierDao = new CashierDao(null);
        doctorDao = new DoctorDao(null);
        appointmentDao = new AppointmentDao(null);
        invoiceDao = new InvoiceDao(null);
        onlineHelpDao = new OnlineHelpDao(null);
        onlineHelpReplyDao = new OnlineHelpReplyDao(null);
        patientDao = new PatientDao(null);

    }

    @Override
    protected Void doInBackground(Void... voids) {

            //Create Admin Users
            this.adminDao.insert(new Admin("Super","User", "A001", "root"));

            //Create Cashier Users
            this.cashierDao.insert(new Cashier("Rachel","Green","C001","password",1));

            //Create Doctor Users
            this.doctorDao.insert(new Doctor("Micheal","Scarn","15122 72 Ave, Surrey, BC V3S 2G2","D001","password","604-555-2144","scarn@medicine.com",1));
            this.doctorDao.insert(new Doctor("Deandre","Jordan","6844 King George Blvd, Surrey, BC V3W 4Z9","D002","password","604-555-2143","jordan@medicine.com",1));
            this.doctorDao.insert(new Doctor("Elaine","Jackson","615300 105 Avenue, Surrey, BC","D003","password","604-555-2543","elaine@medicine.com",1));
            this.doctorDao.insert(new Doctor("Robin","Lopez","4731 King George Blvd, Surrey, BC V3W 4Z9","D004","password","604-555-2343","lopez@medicine.com",1));

            //Create Patient Users
            this.patientDao.insert(new Patient("Jane","Doe","1234 Fake St","P001","password",true,"604-555-1234",123000998,"janedoe@mail.com",1));
            this.patientDao.insert(new Patient("John","Doe","123 Fake St","P002","password1",true,"604-555-1234",123000999,"johndoe@mail.com",1));

            //Create Appointments
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis(), 1,1));
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis()-3000, 1,1));

            //Create Invoices
            this.invoiceDao.insert(new Invoice(29.99, "May,20,2020", "unpaid", System.currentTimeMillis(), 1, 1, 1, 1));
            this.invoiceDao.insert(new Invoice(19.99, "May,23,2020", "unpaid", System.currentTimeMillis(), 1, 1, 1, 2));
            this.invoiceDao.insert(new Invoice(19.99, "April,23,2020", "unpaid", System.currentTimeMillis(), 1, 1, 1, 2));
            this.invoiceDao.insert(new Invoice(29.99, "April,28,2020", "unpaid", System.currentTimeMillis(), 1, 1, 1, 2));

            //Create Online Help
            this.onlineHelpDao.insert(new OnlineHelp("Test Message1","Lorem Ipsum",System.currentTimeMillis()-8000,1,1));
            this.onlineHelpDao.insert(new OnlineHelp("Test Message2","Lorem Ipsum",System.currentTimeMillis()-5000,1,2,1));

            //Create Online Help
            this.onlineHelpReplyDao.insert(new OnlineHelpReply("Test Reply","Lorem Ipsum",System.currentTimeMillis(),2));

            return null;

    }
}
