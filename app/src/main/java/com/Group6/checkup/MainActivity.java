package com.Group6.checkup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    SharedPreferences loginInfo;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        }*/
        loginInfo = getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);
        editor = loginInfo.edit();

        Button login = findViewById(R.id.button);
        Button admin = findViewById(R.id.btn_main_admin);
        Button cashier = findViewById(R.id.btn_main_cashier);
        Button doctor = findViewById(R.id.btn_main_doctor);
        Button patient = findViewById(R.id.btn_main_patient);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AdminActivity.class));
                editor.putString("loginID", "Admin");
                editor.commit();
            }
        });


    }
}
