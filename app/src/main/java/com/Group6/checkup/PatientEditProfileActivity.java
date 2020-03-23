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

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

public class PatientEditProfileActivity extends AppCompatActivity {

    // Private Attributes
    private Session appSession;
    private Patient currentUser;
    private PatientDao patientDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        appSession = new Session(this);
        patientDao = new PatientDao(this);



        //TODO com.Group6.checkup.Patient Edit profile logic

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_edit_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_edit_profile_address);
        TextView mTextViewMSP = findViewById(R.id.text_edit_profile_msp);

        final EditText mEditFirstName = findViewById(R.id.edit_first_name);
        final EditText mEditLastName = findViewById(R.id.edit_last_name);
        final EditText mEditAddress = findViewById(R.id.edit_address);

        Button mBtnConfirmChanges = findViewById(R.id.btn_edit_profile_confirm);

        //Activity Logic
        currentUser = patientDao.find(appSession.getCurrentUsername());

        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();

        mTextViewName.setText(fullName);
        mTextViewAddress.setText(currentUser.getAddress());
        mTextViewMSP.setText(String.valueOf(currentUser.getHealthCareCardNumber()));

        mEditFirstName.setText(currentUser.getFirstName());
        mEditLastName.setText(currentUser.getLastName());
        mEditAddress.setText(currentUser.getAddress());


        //UI Event Listeners
        mBtnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.setFirstName(mEditFirstName.getText().toString());
                currentUser.setLastName(mEditLastName.getText().toString());
                currentUser.setAddress(mEditAddress.getText().toString());
                if(patientDao.update(currentUser)){
                    Toast.makeText(PatientEditProfileActivity.this, "Profile Edited", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientEditProfileActivity.this,AccountProfileActivity.class));
                } else {
                    Toast.makeText(PatientEditProfileActivity.this, "Unable to Edit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
