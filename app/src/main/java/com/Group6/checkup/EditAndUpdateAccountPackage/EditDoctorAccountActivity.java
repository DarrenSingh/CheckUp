package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.Doctor;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditDoctorAccountActivity extends AppCompatActivity {
    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    String firstName, lastName, officeAddress, phoneNumber, email, loginID, password, rowID, adminID;
    ArrayList<String> doctorInfo;
    Intent getIntent;
    DatabaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_account);

        getIntent = getIntent();
        dao = new DatabaseDAO();
        doctorInfo = new ArrayList<>();

        loginID = getIntent.getStringExtra("loginID");
        doctorInfo = dao.accountSearch(loginID, EditDoctorAccountActivity.this);

        rowID = doctorInfo.get(0);
        firstName = doctorInfo.get(1);
        lastName = doctorInfo.get(2);
        officeAddress = doctorInfo.get(3);
        loginID = doctorInfo.get(4);
        password = doctorInfo.get(5);
        phoneNumber = doctorInfo.get(6);
        email = doctorInfo.get(7);
        adminID = doctorInfo.get(8);

        btnEditAccount = findViewById(R.id.btn_editDoctorAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_editDoctorFirstName);
        etLastName = findViewById(R.id.editTxt_editDoctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_editDoctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editDoctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editDoctorEmail);
        etLoginID = findViewById(R.id.editTxt_editDoctorLoginID);
        etPassword = findViewById(R.id.editTxt_editDoctorPassword);

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

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                officeAddress = etOfficeAddress.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //Update to Database
                Doctor updateDoctor = new Doctor();
                updateDoctor.setDoctorID(Integer.parseInt(rowID));
                updateDoctor.setFirstName(firstName);
                updateDoctor.setLastName(lastName);
                updateDoctor.setOfficeAddress(officeAddress);
                updateDoctor.setLoginID(loginID);
                updateDoctor.setPassword(password);
                updateDoctor.setPhoneNumber(phoneNumber);
                updateDoctor.setEmailAddress(email);

                dao.doctorAccountEdit(updateDoctor, EditDoctorAccountActivity.this);
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.doctorAccountDelete(Integer.parseInt(rowID), EditDoctorAccountActivity.this);
            }
        });
    }
}
