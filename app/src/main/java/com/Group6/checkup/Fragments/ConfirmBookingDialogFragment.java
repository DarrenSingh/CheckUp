package com.Group6.checkup.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.PatientHomeActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.SubmitPaymentActivity;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmBookingDialogFragment extends DialogFragment {

    Context context;
    AppointmentDao appointmentDao;
    Appointment appointment;
    PatientDao patientDao;

    public ConfirmBookingDialogFragment() {
    }

    @SuppressLint("ValidFragment")
    public ConfirmBookingDialogFragment(Context context, Appointment appointment) {
        this.appointment = appointment;
        this.context = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        Date date = new Date(appointment.getAppointmentDateTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMMM dd, YYYY");
        SimpleDateFormat hourFormat = new SimpleDateFormat("h:mm a");


        String dateString = dateFormat.format(date);
        String hourString = hourFormat.format(date);

        String textFormatted = String.format(getResources().getString(R.string.text_booking_confirmation), dateString, hourString);

        builder.setMessage(textFormatted)
                .setPositiveButton(R.string.btn_booking_confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        appointmentDao = new AppointmentDao(context);
                        patientDao = new PatientDao(context);

                        InvoiceDao invoiceDao = new InvoiceDao(context);

                        //insert the appointment and return the row id
                        long appointmentId = appointmentDao.insertWithResult(appointment);
                        Patient patient = patientDao.findById(String.valueOf(appointment.getPatientID()));
                        if (patient.getHealthCareCardNumber() == 0) {

                            //create a new intent
                            Intent intent = new Intent(context, SubmitPaymentActivity.class);

                            long monthToMilliseconds = 2592000000L;

                            //create new invoice object
                            Invoice invoice = new Invoice(
                                    19.99,
                                    //set due date to 1 month from time created
                                    System.currentTimeMillis() + monthToMilliseconds,
                                    "unpaid",
                                    System.currentTimeMillis(),
                                    appointment.getPatientID(),
                                    1,
                                    appointment.getDoctorID(),
                                    (int) appointmentId
                            );

                            long insertedRow = invoiceDao.insertWithResult(invoice);

                            if (insertedRow > -1) {
                                intent.putExtra("invoiceId", String.valueOf(insertedRow));
                                startActivity(intent);
                            } else {
                                Toast.makeText(context, "Unable to book at this moment", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(context, PatientHomeActivity.class);
                            Toast.makeText(context, "Appointment Booked", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }

                    }
                })
                .setNegativeButton(R.string.btn_booking_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        return builder.create();
    }
}