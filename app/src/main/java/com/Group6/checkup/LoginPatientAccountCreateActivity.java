package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.PatientDao;

public class LoginPatientAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String msp;
    boolean mspStatus;
    int healthCardNumber = 0;
    String[] yesOrNo = {"yes", "no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account_create);

        btnCreateAccount = findViewById(R.id.btn_createPatientAccount);
        spinnerYesOrNo = findViewById(R.id.spinner_patientMSPStatus);

        etFirstName = findViewById(R.id.editTxt_patientFirstName);
        etLastName = findViewById(R.id.editTxt_patientLastName);
        etAddress = findViewById(R.id.editTxt_patientAddress);
        etPhoneNumber = findViewById(R.id.editTxt_patientPhoneNumber);
        etEmail = findViewById(R.id.editTxt_patientEmail);
        etLoginID = findViewById(R.id.editTxt_patientLoginID);
        etPassword = findViewById(R.id.editTxt_patientPassword);
        etHealthCardNumber = findViewById(R.id.editTxt_patientHealthCardNumber);

        Log.e("HealthCareNumber", etHealthCardNumber.getText().toString());

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, yesOrNo);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYesOrNo.setAdapter(adapter);


        spinnerYesOrNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    etHealthCardNumber.setHint("Health Card Number");
                    etHealthCardNumber.setEnabled(true);
                } else if (position == 1) {
                    etHealthCardNumber.setHint("N/A");
                    etHealthCardNumber.setText("");
                    etHealthCardNumber.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please choose MSP Status", Toast.LENGTH_SHORT).show();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msp = spinnerYesOrNo.getSelectedItem().toString();

                if (msp.equals("yes")) {
                    mspStatus = true;
                } else {
                    mspStatus = false;
                }

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
                if (AccountValidation.isEmpty(etAddress)) {
                    etAddress.setError("This field is required");
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
                if(mspStatus == true && AccountValidation.isEmpty(etHealthCardNumber)){
                    etHealthCardNumber.setError("This field is required");
                }

                if (!AccountValidation.isEmpty(etFirstName) && !AccountValidation.isEmpty(etLastName) && !AccountValidation.isEmpty(etLoginID) && !AccountValidation.isEmpty((etPassword)) &&
                        AccountValidation.nameValidation(etFirstName) && AccountValidation.nameValidation(etLastName) && !AccountValidation.isEmpty(etAddress) && !AccountValidation.isEmpty(etPhoneNumber) &&
                        !AccountValidation.isEmpty(etEmail) && AccountValidation.emailValidation(etEmail) && ((mspStatus == true  && !AccountValidation.isEmpty(etHealthCardNumber)) || mspStatus == false)) {

                    //Searching and finding the data that has the same loginID.
                    if (etLoginID.getText().toString().charAt(0) != 'P') {
                        etLoginID.setError("Patient Account has to start with letter 'P'.");
                    } else {

                        PatientDao patientDao = new PatientDao(getApplicationContext());

                        //LoginID existence validation
                        if (patientDao.exists(etLoginID.getText().toString())) {
                            etLoginID.setError("Login Id is already exists");
                        } else {

                            healthCardNumber = (etHealthCardNumber.getText().toString().compareTo("") == 0)
                                    ? 0 : Integer.parseInt(etHealthCardNumber.getText().toString());

                            //Creating patient Object.
                            Patient newPatient = new Patient(
                                    etFirstName.getText().toString(),
                                    etLastName.getText().toString(),
                                    etAddress.getText().toString(),
                                    etLoginID.getText().toString(),
                                    etPassword.getText().toString(),
                                    mspStatus,
                                    etPhoneNumber.getText().toString(),
                                    healthCardNumber,
                                    etEmail.getText().toString()
                            );

                            //Insert to Database;
                            boolean inserted = patientDao.insert(newPatient);

                            //Give (un)successful prompt
                            if (inserted) {
                                Toast.makeText(LoginPatientAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getBaseContext(), LoginActivity.class));

                                //
                                Intent in = new Intent(LoginPatientAccountCreateActivity.this, LoginActivity.class);
                                in.putExtra("loginIDvalue" ,etLoginID.getText().toString());
                                startActivity(in);
                            } else
                                Toast.makeText(LoginPatientAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });
    }
}
