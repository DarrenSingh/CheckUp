package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        Button buttonViewSchedule = findViewById(R.id.buttonViewSchedule);
        Button buttonViewMessages = findViewById(R.id.buttonViewMessage);
        Button buttonReturnToLogin = findViewById(R.id.buttonReturnToLogin);

        buttonViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorMessages.class));
            }
        });

        buttonViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorAppointmentSchedule.class));
            }
        });

        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(DoctorActivity.this,loginActivity.class));
            }
        });
    }
}
