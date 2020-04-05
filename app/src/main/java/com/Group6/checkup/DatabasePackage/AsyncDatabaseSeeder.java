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
import com.Group6.checkup.Entities.PaymentNotification;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Dao.PaymentNotificationDao;

public class AsyncDatabaseSeeder extends AsyncTask<Void,Void,Void> {

    private AdminDao adminDao;
    private CashierDao cashierDao;
    private DoctorDao doctorDao;
    private AppointmentDao appointmentDao;
    private InvoiceDao invoiceDao;
    private OnlineHelpDao onlineHelpDao;
    private OnlineHelpReplyDao onlineHelpReplyDao;
    private PatientDao patientDao;
    private PaymentNotificationDao paymentNotificationDao;

    public AsyncDatabaseSeeder() {

        adminDao = new AdminDao(null);
        cashierDao = new CashierDao(null);
        doctorDao = new DoctorDao(null);
        appointmentDao = new AppointmentDao(null);
        invoiceDao = new InvoiceDao(null);
        onlineHelpDao = new OnlineHelpDao(null);
        onlineHelpReplyDao = new OnlineHelpReplyDao(null);
        patientDao = new PatientDao(null);
        paymentNotificationDao = new PaymentNotificationDao(null);

    }

    @Override
    protected Void doInBackground(Void... voids) {

            //Create Admin Users
            int adminId =this.adminDao.insertWithResult(new Admin("Super","User", "A001", "root"));

            //Create Cashier Users
            this.cashierDao.insert(new Cashier("Rachel", "Green", "C001", "password", adminId));
            this.cashierDao.insert(new Cashier("Monica", "Gellar", "C002", "password", adminId));

            //Create Doctor Users
            this.doctorDao.insert(new Doctor("Micheal", "Scarn", "15122 72 Ave, Surrey, BC V3S 2G2", "D001", "password", "604-555-2144", "scarn@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Deandre", "Jordan", "6844 King George Blvd, Surrey, BC V3W 4Z9", "D002", "password", "604-555-2143", "jordan@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Elaine", "Jackson", "615300 105 Avenue, Surrey, BC", "D003", "password", "604-555-2543", "elaine@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Robin", "Lopez", "4731 King George Blvd, Surrey, BC V3W 4Z9", "D004", "password", "604-555-2343", "lopez@medicine.com", adminId));

            //Create Patient Users
            this.patientDao.insert(new Patient("Jane", "Doe", "1234 Fake St", "P001", "password", true, "604-555-1234", 123000998, "janedoe@mail.com", adminId));
            this.patientDao.insert(new Patient("John", "Doe", "123 Fake St", "P002", "password1", true, "604-555-1234", 123000999, "johndoe@mail.com", adminId));

            //Create Appointments
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis(), 1, 1));
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis(), 1, 2));
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis(), 1, 4));
            this.appointmentDao.insert(new Appointment(System.currentTimeMillis(), 2, 3));

            //Create Invoices
            this.invoiceDao.insert(new Invoice(29.99, "May,20,2020", "unpaid", System.currentTimeMillis(), 1, 1, 1, 1));
            this.invoiceDao.insert(new Invoice(19.99, "May,23,2020", "unpaid", System.currentTimeMillis(), 1, 2, 2, 2));
            this.invoiceDao.insert(new Invoice(19.99, "April,23,2020", "unpaid", System.currentTimeMillis(), 1, 1, 4, 2));
            this.invoiceDao.insert(new Invoice(29.99, "April,28,2020", "unpaid", System.currentTimeMillis(), 2, 1, 3, 2));

            //Create Payment Notifications
            this.paymentNotificationDao.insert(new PaymentNotification("Unpaid Balance", "Lorem Ipsum $0.00", System.currentTimeMillis() + 1000000L, 1, 1));
            this.paymentNotificationDao.insert(new PaymentNotification("Unpaid Balance #2", "Lorem Ipsum $9.99", System.currentTimeMillis() + 10800000L, 1, 2));
            this.paymentNotificationDao.insert(new PaymentNotification("Unpaid Balance #2", "Lorem Ipsum $9.99", System.currentTimeMillis() + 54000000L, 1, 2));

            //Create Online Help
            this.onlineHelpDao.insert(new OnlineHelp("Test Message1", "Lorem Ipsum", System.currentTimeMillis(), 1, 1));
            this.onlineHelpDao.insert(new OnlineHelp("Test Message1", "Lorem Ipsum", System.currentTimeMillis() + 54000000L, 1, 4));
            this.onlineHelpDao.insert(new OnlineHelp("Test Message2", "Lorem Ipsum", System.currentTimeMillis() + 3600000L, 1, 2, 1));

            //Create Online Help
            this.onlineHelpReplyDao.insert(new OnlineHelpReply("Test Reply", "Lorem Ipsum", System.currentTimeMillis(), 2));

        return null;

    }
}
