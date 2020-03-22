package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.PatientDao;

public class PatientAccountEditActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String firstName, lastName, address, phoneNumber, email, loginID, password, rowID, enteredLoginID;
    boolean msp;
    int healthCardNumber;
    String[] yesOrNo = {"yes", "no"};
    Intent getIntent;
    PatientDao patientDao;
    Patient patientAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_account);

        btnEditAccount = findViewById(R.id.btn_editPatientAccount);
        btnDeleteAccount = findViewById(R.id.btn_deletePatientAccount);

        spinnerYesOrNo = findViewById(R.id.spinner_editPatientMSPStatus);

        etFirstName = findViewById(R.id.editTxt_editPatientFirstName);
        etLastName = findViewById(R.id.editTxt_editPatientLastName);
        etAddress = findViewById(R.id.editTxt_editPatientAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editPatientPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editPatientEmail);
        etLoginID = findViewById(R.id.editTxt_editPatientLoginID);
        etPassword = findViewById(R.id.editTxt_editPatientPassword);
        etHealthCardNumber = findViewById(R.id.editTxt_editPatientHealthCardNumber);

        getIntent = getIntent();
        patientDao = new PatientDao(this);

        loginID = getIntent.getStringExtra("loginID");

        //TODO handle exception
        patientAccount = patientDao.find(loginID);

        etFirstName.setText(patientAccount.getFirstName());
        etLastName.setText(patientAccount.getLastName());
        etAddress.setText(patientAccount.getAddress());
        etPhoneNumber.setText(patientAccount.getPhoneNumber());
        etEmail.setText(patientAccount.getEmailAddress());
        etLoginID.setText(patientAccount.getLoginID());
        etPassword.setText(patientAccount.getPassword());
        etHealthCardNumber.setText(String.valueOf(patientAccount.getHealthCareCardNumber()));

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, yesOrNo);
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

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                address = etAddress.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                enteredLoginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();
                healthCardNumber = Integer.parseInt(etHealthCardNumber.getText().toString());
                msp = (spinnerYesOrNo.getSelectedItem().toString() == "yes");

                patientAccount.setFirstName(firstName);
                patientAccount.setLastName(lastName);
                patientAccount.setAddress(address);
                patientAccount.setPhoneNumber(phoneNumber);
                patientAccount.setEmailAddress(email);
                patientAccount.setLoginID(loginID);
                patientAccount.setPassword(password);
                patientAccount.setHealthCareCardNumber(healthCardNumber);
                patientAccount.setMspStatus(msp);


                //Edit loginID validation
                if (loginID.charAt(0) != 'P') {
                    etLoginID.setError("Patient Account has to start with letter 'P'.");

                    if (patientDao.exists(loginID)) {
                        etLoginID.setError("Login ID is already exists");
                    }
                } else{
                    //update database
                    if(patientDao.insert(patientAccount)) {
                        Toast.makeText(PatientAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(PatientAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                    } else {
                        Toast.makeText(PatientAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(patientDao.delete(loginID)) {
                    Toast.makeText(PatientAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(PatientAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
