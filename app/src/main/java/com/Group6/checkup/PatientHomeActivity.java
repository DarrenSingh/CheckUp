package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PatientHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_patienthome_name);
        TextView mTextViewAppointment = findViewById(R.id.text_upcoming_appointment);
        TextView mTextViewBalance = findViewById(R.id.text_account_balance);

        Button mBtnLocateDoctor = findViewById(R.id.btn_locate_doctor);
        Button mBtnProfile = findViewById(R.id.btn_account_profile);
        Button mBtnLogout = findViewById(R.id.btn_home_logout);

        //Activity Logic

        //TODO get user from database, set full name
        String fullName = "Full" + " " + "Name";

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
                //TODO Create new intent for LoginActivity.Class
                //Intent intent = new Intent(PatientHomeActivity.this,);
                //startActivity(intent);
            }
        });


    }
}
