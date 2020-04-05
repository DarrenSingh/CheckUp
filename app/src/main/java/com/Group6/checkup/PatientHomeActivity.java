package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {
    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;
    private AppointmentDao appointmentDao;
    private InvoiceDao invoiceDao;
    private List<Appointment> appointments;
    private List<Invoice> invoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        appSession = new Session(this);
        patientDao = new PatientDao(this);
        appointmentDao = new AppointmentDao(this);
        invoiceDao = new InvoiceDao(this);


        //UI Components
        TextView mTextViewName = findViewById(R.id.text_patienthome_name);
        TextView mTextViewAppointment = findViewById(R.id.text_upcoming_appointment);
        TextView mTextViewBalance = findViewById(R.id.text_account_balance);

        Button mBtnLocateDoctor = findViewById(R.id.btn_locate_doctor);
        Button mBtnProfile = findViewById(R.id.btn_account_profile);
        Button mBtnLogout = findViewById(R.id.btn_home_logout);

        //Activity Logic

        //Set User Name
        currentUser = patientDao.find(appSession.getCurrentUsername());
        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();

        //Set Next Appointment
        try {

            appointments = appointmentDao.findAllByPatient(String.valueOf(appSession.getUserId()));
            Date date = new Date(appointments.get(0).getAppointmentDateTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMMM dd, YYYY @ h:mm a");
            String upcomingAppointment = dateFormat.format(date);
            mTextViewAppointment.setText(upcomingAppointment);

        }catch (Exception e){
            mTextViewAppointment.setText("No upcoming appointments");
            e.printStackTrace();
        }


        //Set Account Balance
        invoices = invoiceDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        double balanceOwing = 0.00;

        for (int i = 0; i < invoices.size(); i++) {
            balanceOwing += invoices.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        String accountBalance = "$" + decimalFormat.format(balanceOwing);


        //Set Component Text
        mTextViewName.setText(fullName);
        mTextViewBalance.setText(accountBalance);


        //UI Event Listeners
        mBtnLocateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, LocateDoctorActivity.class);
                startActivity(intent);
            }
        });

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this,AccountProfileActivity.class);
                startActivity(intent);
            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset session info
                appSession.setCurrentUsername(null);
                appSession.setUserId(0);
                Intent intent = new Intent(PatientHomeActivity.this,loginActivity.class);
                //clear task back stack before and send user to login screen
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


    }
}
