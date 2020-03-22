package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.Utils.Session;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //allow database to create & seed
        dbh = DatabaseHelper.getInstance(this);

        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        }*/

        final Session appSession = new Session(this);

        Button login = findViewById(R.id.button);
        Button admin = findViewById(R.id.btn_main_admin);
        Button cashier = findViewById(R.id.btn_main_cashier);
        Button doctor = findViewById(R.id.btn_main_doctor);
        Button patient = findViewById(R.id.btn_main_patient);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AdminActivity.class));
                appSession.setCurrentUsername("A001");
                appSession.setUserId(1);

            }
        });

        cashier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CashierActivity.class));
                appSession.setCurrentUsername("C001");
                appSession.setUserId(1);
            }
        });

    }
}
