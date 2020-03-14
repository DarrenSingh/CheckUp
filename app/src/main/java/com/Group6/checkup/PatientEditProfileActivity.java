package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientEditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);

        //TODO Patient Edit profile logic

        //UI Components

        Button mBtnConfirmChanges = findViewById(R.id.btn_edit_profile_confirm);

        //UI Event Listeners

        mBtnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientEditProfileActivity.this,AccountProfileActivity.class));
            }
        });
    }
}
