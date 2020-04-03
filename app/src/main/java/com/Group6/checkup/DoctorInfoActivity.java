package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DoctorInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        Intent intent = getIntent();

        TextView mTextViewName = findViewById(R.id.text_doctor_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_doctor_profile_address);

        mTextViewName.setText(intent.getStringExtra("doctorName"));
        mTextViewAddress.setText(intent.getStringExtra("doctorAddress"));
    }
}
