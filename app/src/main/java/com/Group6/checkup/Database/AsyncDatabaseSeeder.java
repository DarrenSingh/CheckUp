package com.Group6.checkup.Database;

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

    private long monthToMilliseconds = 2592000000L;

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
            int adminId =this.adminDao.insertWithResult(new Admin("Bokki","Min", "A001", "secret"));

            //Create Cashier Users
            this.cashierDao.insert(new Cashier("Shivangi", "Patel", "C001", "secret", adminId));
            this.cashierDao.insert(new Cashier("Rachel", "Green", "C002", "password", adminId));
            this.cashierDao.insert(new Cashier("Monica", "Geller", "C003", "password", adminId));

            //Create Doctor Users
            this.doctorDao.insert(new Doctor("Tonny", "Ogange", "15122 72 Ave, Surrey, BC V3S 2G2", "D001", "secret", "604-555-2144", "scarn@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Micheal", "Scarn", "777 W Broadway, Vancouver, BC V5Z 4J7", "D002", "password", "604-555-2144", "scarn@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Elaine", "Jackson", "615300 105 Avenue, Surrey, BC", "D003", "password", "604-555-2543", "elaine@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Marty", "Byrde", "27107 Fraser Hwy, Aldergrove, BC V4W 3R2", "D004", "password", "604-555-2343", "lopez@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Marty", "Byrde", "4731 King George Blvd, Surrey, BC V3W 4Z9", "D005", "password", "604-555-2343", "lopez@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Walter", "White", "3825 Sunset St, Burnaby, BC V5G 1T4", "D006", "password", "604-555-2343", "lopez@medicine.com", adminId));
            this.doctorDao.insert(new Doctor("Miranda", "Bailey", "6844 King George Blvd, Surrey, BC V3W 4Z9", "D007", "password", "604-555-2143", "jordan@medicine.com", adminId));


            //Create Patient Users
            this.patientDao.insert(new Patient("Darren", "Singh", "13464 56Ave Surrey, BC V3S 2G2", "P001", "secret", false, "604-555-1234", 0, "janedoe@mail.com", adminId));
            this.patientDao.insert(new Patient("Jane", "Doe", "1234 Fake St", "P002", "password", true, "604-555-1234", 123000998, "janedoe@mail.com", adminId));
            this.patientDao.insert(new Patient("John", "Doe", "123 Fake St", "P003", "password", false, "604-555-1234", 276528947, "johndoe@mail.com", adminId));
            this.patientDao.insert(new Patient("Patient", "Zero", "123 Fake St", "P004", "password", true, "604-555-1234", 375017529, "johndoe@mail.com", adminId));

            //Create Appointments
            this.appointmentDao.insert(new Appointment(1586451600000L, 1,  1));
            this.appointmentDao.insert(new Appointment(1586455200000L, 2, 2));
            this.appointmentDao.insert(new Appointment(1586466000000L, 1, 2));
            this.appointmentDao.insert(new Appointment(1586473200000L, 3, 1));
            this.appointmentDao.insert(new Appointment(1586476800000L, 1, 2));
            this.appointmentDao.insert(new Appointment(1586541600000L, 2, 3));
            this.appointmentDao.insert(new Appointment(1586548800000L, 1, 4));
            this.appointmentDao.insert(new Appointment(1586710800000L, 2, 3));
            this.appointmentDao.insert((new Appointment(1586714400000L,1,2)));
            this.appointmentDao.insert((new Appointment(1586725200000L,2,5)));
            this.appointmentDao.insert((new Appointment(1586804400000L,1,4)));
            this.appointmentDao.insert((new Appointment(1586768400000L,3,5)));
            this.appointmentDao.insert((new Appointment(1586898000000L,1,3)));
            this.appointmentDao.insert((new Appointment(1586908800000L,3,1)));

            //Create Invoices
            this.invoiceDao.insert(new Invoice(19.99,System.currentTimeMillis()+monthToMilliseconds, "unpaid", System.currentTimeMillis(), 1, 1, 1, 1));
            this.invoiceDao.insert(new Invoice(0,System.currentTimeMillis(), "paid", System.currentTimeMillis(), 1, 2, 2, 2));
            this.invoiceDao.insert(new Invoice(19.99,System.currentTimeMillis(),  "unpaid", System.currentTimeMillis(), 1, 1, 4, 2));
            this.invoiceDao.insert(new Invoice(29.99, System.currentTimeMillis()+monthToMilliseconds, "unpaid", System.currentTimeMillis(), 2, 1, 3, 2));

            //Create Payment Notifications
            this.paymentNotificationDao.insert(new PaymentNotification("Payment Overdue Reminder", "Dear Jane Doe,\n Your account is overdue $19.99 The payment was due by February 10th 2020.\nPlease make your payment", System.currentTimeMillis(), 2, 1));

            //Create Online Help
            this.onlineHelpDao.insert(new OnlineHelp("80 Days", "80 days around the world, we’ll find a pot of gold just sitting where the rainbow’s ending. ", System.currentTimeMillis(), 1, 5));
            this.onlineHelpDao.insert(new OnlineHelp("Around the World", "80 days around the world, no we won’t say a word before the ship is really back. ", System.currentTimeMillis() - 3600000L, 2, 2, 1));
            this.onlineHelpDao.insert(new OnlineHelp("Super Guy", "Round, round, all around the world. Round, all around the world. Round, all around the world. Round, all around the world.", System.currentTimeMillis() - 7200000L, 3, 3, 2));
            this.onlineHelpDao.insert(new OnlineHelp("Cape Crusader", "When the going gets tough, he’s really rough,", System.currentTimeMillis() - 4800000L, 1, 1, 3));

            //Create Online Help
            this.onlineHelpReplyDao.insert(new OnlineHelpReply("When courage is needed", "Time — we’ll fight against the time, and we’ll fly on the white wings of the wind.", System.currentTimeMillis(), 2));
            this.onlineHelpReplyDao.insert(new OnlineHelpReply("RE:Cape Crusader", "Michael Knight, a young loner on a crusade to champion the cause of the innocent, the helpless in a world of criminals who operate above the law.", System.currentTimeMillis(), 3));
            this.onlineHelpReplyDao.insert(new OnlineHelpReply("Fizzy Drinks and Liquorice", "He’s got style, a groovy style, and a car that just won’t stop.", System.currentTimeMillis(), 1));

        return null;

    }
}
