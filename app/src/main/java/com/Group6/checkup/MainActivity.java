package com.Group6.checkup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        }*/

        Button login = findViewById(R.id.button);
        Button admin = findViewById(R.id.btn_main_admin);
        Button cashier = findViewById(R.id.btn_main_cashier);
        Button doctor = findViewById(R.id.btn_main_doctor);
        Button patient = findViewById(R.id.btn_main_patient);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AdminActivity.class));
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,PatientHomeActivity.class));
            }
        });


    }
}
