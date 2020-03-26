package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import org.w3c.dom.Text;

public class PatientHomeActivity extends AppCompatActivity {
    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;
    private AppointmentDao appointmentDao;
    private Appointment upcomingAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        appSession = new Session(this);
        patientDao = new PatientDao(this);

        currentUser = patientDao.find(appSession.getCurrentUsername());

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_patienthome_name);
        TextView mTextViewAppointment = findViewById(R.id.text_upcoming_appointment);
        TextView mTextViewBalance = findViewById(R.id.text_account_balance);

        Button mBtnLocateDoctor = findViewById(R.id.btn_locate_doctor);
        Button mBtnProfile = findViewById(R.id.btn_account_profile);
        Button mBtnLogout = findViewById(R.id.btn_home_logout);

        //Activity Logic

        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();

        //TODO get user appointment from database, if any
        String upcomingAppointment = "Date";

        //TODO get user balance from database
        String accountBalance = "$" + "Amount";


        //Set Component Text
        mTextViewName.setText(fullName);
        mTextViewAppointment.setText(upcomingAppointment);
        mTextViewBalance.setText(accountBalance);


        //UI Event Listeners
        mBtnLocateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create new intent for LocateDoctorActivity.Class
                //Intent intent = new Intent(PatientHomeActivity.this,);
                //startActivity(intent);
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
