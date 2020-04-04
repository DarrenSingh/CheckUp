package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorAppointmentSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_schedule);

        Button buttonBack = findViewById(R.id.buttonBack);
        Spinner spinnerAppointments = findViewById(R.id.spinnerAppointments);
        //the spinner is supposed to change the text of the patient information textview when selected hence displaying the patient's information(textViewPatientInformation.setText())
        //TODO


        TextView textViewPatientInformation = findViewById(R.id.textViewPatientInformation);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorAppointmentSchedule.this, DoctorActivity.class));
            }
        });
    }
}
