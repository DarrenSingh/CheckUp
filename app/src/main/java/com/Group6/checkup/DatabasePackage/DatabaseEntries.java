package com.Group6.checkup.DatabasePackage;

public class DatabaseEntries {

    //Admin table creating query.
    public static final String SQL_ADMIN_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
            DatabaseTable.AdminTable.TABLE_NAME,
            DatabaseTable.AdminTable._ID,
            DatabaseTable.AdminTable.FIRST_NAME,
            DatabaseTable.AdminTable.LAST_NAME,
            DatabaseTable.AdminTable.LOGIN_ID,
            DatabaseTable.AdminTable.PASSWORD
    );

    //Patient table creating query.
    public static final String SQL_PATIENT_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (adminID) REFERENCES Admin (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.PatientTable.TABLE_NAME,
            DatabaseTable.PatientTable._ID,
            DatabaseTable.PatientTable.FIRST_NAME,
            DatabaseTable.PatientTable.LAST_NAME,
            DatabaseTable.PatientTable.ADDRESS,
            DatabaseTable.PatientTable.LOGIN_ID,
            DatabaseTable.PatientTable.PASSWORD,
            DatabaseTable.PatientTable.MSP_STATUS,
            DatabaseTable.PatientTable.PHONE_NUMBER,
            DatabaseTable.PatientTable.HEALTH_CARE_CARD_NUMBER,
            DatabaseTable.PatientTable.EMAIL_ADDRESS,
            DatabaseTable.PatientTable.ADMIN_ID
    );

    //Doctor table creating query.
    public static final String SQL_DOCTOR_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (adminID) REFERENCES Admin(_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.DoctorTable.TABLE_NAME,
            DatabaseTable.DoctorTable._ID,
            DatabaseTable.DoctorTable.FIRST_NAME,
            DatabaseTable.DoctorTable.LAST_NAME,
            DatabaseTable.DoctorTable.OFFICE_ADDRESS,
            DatabaseTable.DoctorTable.LOGIN_ID,
            DatabaseTable.DoctorTable.PASSWORD,
            DatabaseTable.DoctorTable.PHONE_NUMBER,
            DatabaseTable.DoctorTable.EMAIL_ADDRESS,
            DatabaseTable.DoctorTable.ADMIN_ID
    );

    //Cashier table creating query.
    public static final String SQL_CASHIER_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (adminID) REFERENCES Admin (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.CashierTable.TABLE_NAME,
            DatabaseTable.CashierTable._ID,
            DatabaseTable.CashierTable.FIRST_NAME,
            DatabaseTable.CashierTable.LAST_NAME,
            DatabaseTable.CashierTable.LOGIN_ID,
            DatabaseTable.CashierTable.PASSWORD,
            DatabaseTable.CashierTable.ADMIN_ID
    );

    //Invoice table creating query.
    public static final String SQL_INVOICE_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s REAL NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER, FOREIGN KEY (patientID) REFERENCES Patient (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (cashierID) REFERENCES Cashier (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (doctorID) REFERENCES Doctor (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (appointmentID) REFERENCES Appointment (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.InvoiceTable.TABLE_NAME,
            DatabaseTable.InvoiceTable._ID,
            DatabaseTable.InvoiceTable.PRICE,
            DatabaseTable.InvoiceTable.PAYMENT_DUE,
            DatabaseTable.InvoiceTable.PAYMENT_STATUS,
            DatabaseTable.InvoiceTable.INVOICE_DATE,
            DatabaseTable.InvoiceTable.PATIENT_ID,
            DatabaseTable.InvoiceTable.CASHIER_ID,
            DatabaseTable.InvoiceTable.DOCTOR_ID,
            DatabaseTable.InvoiceTable.APPOINTMENT_ID
    );

    //Payment_notification table creating query.
    public static final String SQL_PAYMENT_NOTIFICATION_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (patientID) REFERENCES Patient (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (cashierID) REFERENCES Cashier (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.PaymentNotificationTable.TABLE_NAME,
            DatabaseTable.PaymentNotificationTable._ID,
            DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE_TITLE,
            DatabaseTable.PaymentNotificationTable.NOTIFICATION_MESSAGE,
            DatabaseTable.PaymentNotificationTable.SENT_DATE_TIME,
            DatabaseTable.PaymentNotificationTable.PATIENT_ID,
            DatabaseTable.PaymentNotificationTable.CASHIER_ID
    );

    //Appointment table creating query.
    public static final String SQL_APPOINTMENT_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (patientID) REFERENCES Patient (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (doctorID) REFERENCES Doctor (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.AppointmentTable.TABLE_NAME,
            DatabaseTable.AppointmentTable._ID,
            DatabaseTable.AppointmentTable.APPOINTMENT_DATE_TIME,
            DatabaseTable.AppointmentTable.PATIENT_ID,
            DatabaseTable.AppointmentTable.DOCTOR_ID
    );

    //Online_help_reply table creating query.
    public static final String SQL_ONLINE_HELP_REPLY_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, FOREIGN KEY (doctorID) REFERENCES Doctor (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.OnlineHelpReplyTable.TABLE_NAME,
            DatabaseTable.OnlineHelpReplyTable._ID,
            DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_TITLE,
            DatabaseTable.OnlineHelpReplyTable.REPLY_MESSAGE_CONTENT,
            DatabaseTable.OnlineHelpReplyTable.REPLY_DATE_TIME,
            DatabaseTable.OnlineHelpReplyTable.DOCTOR_ID
    );

    //Online_help table creating query.
    public static final String SQL_ONLINE_HELP_TABLE_CREATE_ENTRIES = String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER NOT NULL, %s INTEGER NOT NULL, %s INTEGER, FOREIGN KEY (patientID) REFERENCES Patient (_id) ON UPDATE CASCADE ON DELETE NO ACTION, FOREIGN KEY (doctorID) REFERENCES Doctor (_id) ON UPDATE CASCADE ON DELETE NO ACTION FOREIGN KEY (onlineHelpReplyID) REFERENCES Online_help (_id) ON UPDATE CASCADE ON DELETE NO ACTION)",
            DatabaseTable.OnlineHelpTable.TABLE_NAME,
            DatabaseTable.OnlineHelpTable._ID,
            DatabaseTable.OnlineHelpTable.MESSAGE_TITLE,
            DatabaseTable.OnlineHelpTable.MESSAGE_CONTENT,
            DatabaseTable.OnlineHelpTable.SENT_DATE_TIME,
            DatabaseTable.OnlineHelpTable.PATIENT_ID,
            DatabaseTable.OnlineHelpTable.DOCTOR_ID,
            DatabaseTable.OnlineHelpTable.ONLINE_HELP_REPLY_ID
    );

    //Drop table if exists queries
    public static final String SQL_ADMIN_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.AdminTable.TABLE_NAME;

    public static final String SQL_PATIENT_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.PatientTable.TABLE_NAME;

    public static final String SQL_DOCTOR_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.DoctorTable.TABLE_NAME;

    public static final String SQL_CASHIER_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.CashierTable.TABLE_NAME;

    public static final String SQL_INVOICE_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.InvoiceTable.TABLE_NAME;

    public static final String SQL_PAYMENT_NOTIFICATION_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.PaymentNotificationTable.TABLE_NAME;

    public static final String SQL_APPOINTMENT_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.AppointmentTable.TABLE_NAME;

    public static final String SQL_ONLINE_HELP_REPLY_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.OnlineHelpReplyTable.TABLE_NAME;

    public static final String SQL_ONLINE_HELP_TABLE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DatabaseTable.OnlineHelpTable.TABLE_NAME;
}
