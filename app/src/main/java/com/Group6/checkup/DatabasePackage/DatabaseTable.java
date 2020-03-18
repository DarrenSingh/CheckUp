package com.Group6.checkup.DatabasePackage;

import android.provider.BaseColumns;

public class DatabaseTable {

    private DatabaseTable() {

    }

    public static final String DATABASE_NAME = "dpInteractionDatabase.db";

    public static final int DATABASE_VERSION = 1;

    public static class AdminTable implements BaseColumns {
        public static final String TABLE_NAME = "Admin";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String LOGIN_ID = "loginID";
        public static final String PASSWORD = "password";
    }

    public static class PatientTable implements BaseColumns {
        public static final String TABLE_NAME = "Patient";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String ADDRESS = "address";
        public static final String LOGIN_ID = "loginID";
        public static final String PASSWORD = "password";
        public static final String MSP_STATUS = "mspStatus";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String HEALTH_CARE_CARD_NUMBER = "healthCareCardNumber";
        public static final String EMAIL_ADDRESS = "email";
        public static final String ADMIN_ID = "adminID";
    }

    public static class DoctorTable implements BaseColumns {
        public static final String TABLE_NAME = "Doctor";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String OFFICE_ADDRESS = "officeAddress";
        public static final String LOGIN_ID = "loginID";
        public static final String PASSWORD = "password";
        public static final String PHONE_NUMBER = "phoneNumber";
        public static final String EMAIL_ADDRESS = "email";
        public static final String ADMIN_ID = "adminID";
    }

    public static class CashierTable implements BaseColumns {
        public static final String TABLE_NAME = "Cashier";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String LOGIN_ID = "loginID";
        public static final String PASSWORD = "password";
        public static final String ADMIN_ID = "adminID";
    }

    public static class InvoiceTable implements BaseColumns {
        public static final String TABLE_NAME = "Invoice";
        public static final String PRICE = "price";
        public static final String PAYMENT_DUE = "paymentDue";
        public static final String PAYMENT_STATUS = "paymentStatus";
        public static final String INVOICE_DATE = "invoiceDate";
        public static final String PATIENT_ID = "patientID";
        public static final String CASHIER_ID = "cashierID";
        public static final String DOCTOR_ID = "doctorID";
        public static final String APPOINTMENT_ID = "appointmentID";
    }

    public static class PaymentNotificationTable implements BaseColumns {
        public static final String TABLE_NAME = "Payment_notification";
        public static final String NOTIFICATION_MESSAGE_TITLE = "notificationMessageTitle";
        public static final String NOTIFICATION_MESSAGE = "notificationMessage";
        public static final String SENT_DATE_Time = "sentDateTime";
        public static final String PATIENT_ID = "patientID";
        public static final String CASHIER_ID = "cashierID";
    }

    public static class AppointmentTable implements BaseColumns {
        public static final String TABLE_NAME = "Appointment";
        public static final String APPOINTMENT_DATE_TIME = "appointmentDateTime";
        public static final String PATIENT_ID = "patientID";
        public static final String DOCTOR_ID = "doctorID";
    }

    public static class OnlineHelpReplyTable implements BaseColumns {
        public static final String TABLE_NAME = "Online_help_reply";
        public static final String REPLY_MESSAGE_TITLE = "replyMessageTitle";
        public static final String REPLY_MESSAGE_CONTENT = "replyMessageContent";
        public static final String REPLY_DATE_TIME = "replyDateTime";
        public static final String DOCTOR_ID = "doctorID";
    }

    public static class OnlineHelpTable implements BaseColumns {
        public static final String TABLE_NAME = "Online_help";
        public static final String MESSAGE_TITLE = "messageTitle";
        public static final String MESSAGE_CONTENT = "messageContent";
        public static final String SENT_DATE_TIME = "sentDateTime";
        public static final String PATIENT_ID = "patientID";
        public static final String DOCTOR_ID = "doctorID";
        public static final String ONLINE_HELP_REPLY_ID = "onlineHelpReplyID";
    }

}
