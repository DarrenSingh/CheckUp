package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PatientEditProfileActivity extends AppCompatActivity {

    // Define the session in your activity class
//    private Session appSession;
//    private Patient user;
//    private PatientDao patientDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        //first thing instantiate the session, and pass the context
//        appSession = new Session(this);


        //TODO com.Group6.checkup.Patient Edit profile logic

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_edit_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_edit_profile_address);
        TextView mTextViewMSP = findViewById(R.id.text_edit_profile_msp);

        final EditText mEditFirstName = findViewById(R.id.edit_first_name);
        final EditText mEditlastName = findViewById(R.id.edit_last_name);
        final EditText mEditAddress = findViewById(R.id.edit_address);

        Button mBtnConfirmChanges = findViewById(R.id.btn_edit_profile_confirm);

        //Activity Logic
        //TODO implement dao
//        patientDao = new PatientDao(this);
//        user = patientDao.getPatient(appSession.getCurrentUser());
//
//        String fullName = user.getFirstName() + " " + user.getLastName();
//
//        mTextViewName.setText(fullName);
//        mTextViewAddress.setText(user.getAddress());
//        mTextViewMSP.setText(String.valueOf(user.getHealthCareCardNumber()));
//
//        mEditFirstName.setText(user.getFirstName());
//        mEditlastName.setText(user.getLastName());
//        mEditAddress.setText(user.getAddress());


        //UI Event Listeners
        mBtnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                user.setFirstName(mEditFirstName.getText().toString());
//                user.setLastName(mEditlastName.getText().toString());
//                user.setAddress(mEditAddress.getText().toString());
//                if(patientDao.update(user)){
//                    Toast.makeText(PatientEditProfileActivity.this, "Profile Edited", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(PatientEditProfileActivity.this,AccountProfileActivity.class));
//                } else {
//                    Toast.makeText(PatientEditProfileActivity.this, "Unable to Edit", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}
