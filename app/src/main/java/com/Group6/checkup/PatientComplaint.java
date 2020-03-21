package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientComplaint extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_complaint);

        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonPostSolution = findViewById(R.id.buttonPostSolution);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientComplaint.this, DoctorMessages.class));
            }
        });

        buttonPostSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //code for adding the solution posted by the doctor to the database before returning to the previous page

                startActivity(new Intent(PatientComplaint.this, DoctorMessages.class));
            }
        });

    }
}
