package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;

import java.util.ArrayList;

public class DoctorAccountEditActivity extends AppCompatActivity {
    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    String firstName, lastName, officeAddress, phoneNumber, email, loginID, password, rowID, adminID, enteredLoginID;
    Intent getIntent;
    DoctorDao doctorDao;
    Doctor doctorAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_account);

        btnEditAccount = findViewById(R.id.btn_editDoctorAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_editDoctorFirstName);
        etLastName = findViewById(R.id.editTxt_editDoctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_editDoctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editDoctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editDoctorEmail);
        etLoginID = findViewById(R.id.editTxt_editDoctorLoginID);
        etPassword = findViewById(R.id.editTxt_editDoctorPassword);

        getIntent = getIntent();
        doctorDao = new DoctorDao(this);

        loginID = getIntent.getStringExtra("loginID");

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etOfficeAddress.setText(officeAddress);
        etLoginID.setText(loginID);
        etPassword.setText(password);
        etPhoneNumber.setText(phoneNumber);
        etEmail.setText(email);

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Edit loginID validation
                if (loginID.charAt(0) != 'D') {
                    etLoginID.setError("Doctor Account has to start with letter 'D'.");

                    if (doctorDao.exists(loginID)) {
                        etLoginID.setError("Login ID is already exists");
                    }

                } else {

                    firstName = etFirstName.getText().toString();
                    lastName = etLastName.getText().toString();
                    officeAddress = etOfficeAddress.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();
                    email = etEmail.getText().toString();
                    enteredLoginID = etLoginID.getText().toString();
                    password = etPassword.getText().toString();

                    doctorAccount.setFirstName(firstName);
                    doctorAccount.setLastName(lastName);
                    doctorAccount.setOfficeAddress(officeAddress);
                    doctorAccount.setLoginID(loginID);
                    doctorAccount.setPassword(password);
                    doctorAccount.setPhoneNumber(phoneNumber);
                    doctorAccount.setEmailAddress(email);

                    //update database
                    if(doctorDao.insert(doctorAccount)) {
                        Toast.makeText(DoctorAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DoctorAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                    } else {
                        Toast.makeText(DoctorAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doctorDao.delete(loginID)) {
                    Toast.makeText(DoctorAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoctorAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(DoctorAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
                }            }
        });


    }
}
