package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.Utils.Session;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    Session appSession;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appSession = new Session(this);
  
        //allow database to create & seed
        dbh = DatabaseHelper.getInstance(this);

        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        }*/



        Button login = findViewById(R.id.button);
        Button admin = findViewById(R.id.btn_main_admin);
        Button cashier = findViewById(R.id.btn_main_cashier);
        Button doctor = findViewById(R.id.btn_main_doctor);
        Button patient = findViewById(R.id.btn_main_patient);
  
         login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,loginActivity.class));
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setCurrentUser("D001");
                appSession.setUserId(1);
                startActivity(new Intent(MainActivity.this,DoctorActivity.class));
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setCurrentUser("P001");
                appSession.setUserId(1);
                startActivity(new Intent(MainActivity.this,PatientHomeActivity.class));
            }
        });
  
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setCurrentUsername("A001");
                appSession.setUserId(1);
                startActivity(new Intent(MainActivity.this,AdminActivity.class));
            }
        });

        cashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appSession.setCurrentUsername("C001");
                appSession.setUserId(1);
                startActivity(new Intent(MainActivity.this, CashierActivity.class));
            }
        });

    }
}
