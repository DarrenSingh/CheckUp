package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;

public class DoctorAccountCreateActivity extends AppCompatActivity {
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo, doctorAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        btnCreateAccount = findViewById(R.id.btn_createDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_doctorFirstName);
        etLastName = findViewById(R.id.editTxt_doctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_doctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_doctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_doctorEmail);
        etLoginID = findViewById(R.id.editTxt_doctorLoginID);
        etPassword = findViewById(R.id.editTxt_doctorPassword);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AccountValidation.isEmpty(etFirstName)) {
                    etFirstName.setError("This field is required");
                } else {
                    if (AccountValidation.nameValidation(etFirstName) == false) {
                        etFirstName.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etLastName)) {
                    etLastName.setError("This field is required");
                } else {
                    if (AccountValidation.nameValidation(etLastName) == false) {
                        etLastName.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etOfficeAddress)) {
                    etOfficeAddress.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etPhoneNumber)) {
                    etPhoneNumber.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etEmail)) {
                    etEmail.setError("This field is required");
                } else {
                    if (!AccountValidation.emailValidation((etEmail))) {
                        etEmail.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etLoginID)) {
                    etLoginID.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etPassword)) {
                    etPassword.setError("This field is required");
                }
                if (!AccountValidation.isEmpty(etFirstName) && !AccountValidation.isEmpty(etLastName) && !AccountValidation.isEmpty(etLoginID) && !AccountValidation.isEmpty((etPassword)) &&
                        AccountValidation.nameValidation(etFirstName) && AccountValidation.nameValidation(etLastName) && !AccountValidation.isEmpty(etOfficeAddress) && !AccountValidation.isEmpty(etPhoneNumber) &&
                        !AccountValidation.isEmpty(etEmail) && AccountValidation.emailValidation(etEmail)) {

                    DoctorDao doctorDao = new DoctorDao(getApplicationContext());

                    //Searching and finding the data that has the same loginID.
                    if (etLoginID.getText().toString().charAt(0) != 'D') {
                        etLoginID.setError("Patient Account has to start with letter 'D'.");
                    } else {
                        //LoginID existence validation
                        if (doctorDao.exists(etLoginID.getText().toString())) {

                            Toast.makeText(DoctorAccountCreateActivity.this, "Login ID taken", Toast.LENGTH_SHORT).show();

                        } else {

                            boolean inserted = doctorDao.insert(new Doctor(
                                    etFirstName.getText().toString(),
                                    etLastName.getText().toString(),
                                    etOfficeAddress.getText().toString(),
                                    etLoginID.getText().toString(),
                                    etPassword.getText().toString(),
                                    etPhoneNumber.getText().toString(),
                                    etEmail.getText().toString(),
                                    new Session(getApplicationContext()).getUserId()
                            ));

                            //Give (un)successful prompt
                            if (inserted) {
                                Toast.makeText(DoctorAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), AccountTypeOptionActivity.class));
                            } else
                                Toast.makeText(DoctorAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}
