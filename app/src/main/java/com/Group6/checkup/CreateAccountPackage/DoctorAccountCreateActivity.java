package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.Doctor;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class DoctorAccountCreateActivity extends AppCompatActivity {
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    DatabaseDAO dao;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        dao = new DatabaseDAO();

        currentLogin = getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);

        btnCreateAccount = findViewById(R.id.btn_createDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_doctorFirstName);
        etLastName = findViewById(R.id.editTxt_doctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_doctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_doctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_doctorEmail);
        etLoginID = findViewById(R.id.editTxt_doctorLoginID);
        etPassword = findViewById(R.id.editTxt_doctorPassword);

        currentLoginInfo = dao.accountSearch(currentLogin.getString("loginID", "failed"), DoctorAccountCreateActivity.this);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etLoginID.getText().toString().charAt(0) != 'D') {
                    etLoginID.setError("Patient Account has to start with letter 'D'.");
                }
                else {

                    Doctor newDoctor = new Doctor();
                    newDoctor.setFirstName(etFirstName.getText().toString());
                    newDoctor.setLastName(etLastName.getText().toString());
                    newDoctor.setOfficeAddress(etOfficeAddress.getText().toString());
                    newDoctor.setPhoneNumber(etPhoneNumber.getText().toString());
                    newDoctor.setEmailAddress(etEmail.getText().toString());
                    newDoctor.setLoginID(etLoginID.getText().toString());
                    newDoctor.setPassword(etPassword.getText().toString());
                    newDoctor.setAdminID(Integer.parseInt(currentLoginInfo.get(0)));

                    //Insert to Database
                    dao.doctorAccountInsert(newDoctor, DoctorAccountCreateActivity.this);

                }
            }
        });
    }
}
