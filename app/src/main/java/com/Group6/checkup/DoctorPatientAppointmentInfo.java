package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorPatientAppointmentInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_patient_appointment_info);

        this.setTitle("Appointment Information");
        final TextView patient_name = findViewById(R.id.patient_name);
        Button backButton = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        if(intent != null){
            String value = intent.getStringExtra("name");
            patient_name.setText(value);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorPatientAppointmentInfo.this, DoctorAppointmentSchedule.class));
            }
        });
    }
}
