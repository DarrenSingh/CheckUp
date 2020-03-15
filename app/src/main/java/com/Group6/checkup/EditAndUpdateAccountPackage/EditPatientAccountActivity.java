package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.Patient;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditPatientAccountActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String firstName, lastName, address, phoneNumber, email, loginID, password,healthCardNumber, adminID, msp, mspStatus, rowID;
    String[] yesOrNo = {"yes", "no"};
    ArrayList<String> patientInfo;
    Intent getIntent;
    DatabaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_account);

        getIntent = getIntent();
        dao = new DatabaseDAO();
        patientInfo = new ArrayList<>();

        loginID = getIntent.getStringExtra("loginID");
        patientInfo = dao.accountSearch(loginID, EditPatientAccountActivity.this);

        rowID = patientInfo.get(0);
        firstName = patientInfo.get(1);
        lastName = patientInfo.get(2);
        address = patientInfo.get(3);
        loginID = patientInfo.get(4);
        password = patientInfo.get(5);
        mspStatus = patientInfo.get(6);
        phoneNumber = patientInfo.get(7);
        healthCardNumber = patientInfo.get(8);
        email = patientInfo.get(9);
        adminID = patientInfo.get(10);

        btnEditAccount = findViewById(R.id.btn_editPatientAccount);
        spinnerYesOrNo = findViewById(R.id.spinner_editPatientMSPStatus);

        etFirstName = findViewById(R.id.editTxt_editPatientFirstName);
        etLastName = findViewById(R.id.editTxt_editPatientLastName);
        etAddress = findViewById(R.id.editTxt_editPatientAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editPatientPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editPatientEmail);
        etLoginID = findViewById(R.id.editTxt_editPatientLoginID);
        etPassword = findViewById(R.id.editTxt_editPatientPassword);
        etHealthCardNumber = findViewById(R.id.editTxt_editPatientHealthCardNumber);

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etAddress.setText(address);
        etPhoneNumber.setText(phoneNumber);
        etEmail.setText(email);
        etLoginID.setText(loginID);
        etPassword.setText(password);
        etHealthCardNumber.setText(healthCardNumber);

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

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                address = etAddress.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();
                healthCardNumber = etHealthCardNumber.getText().toString();
                msp = spinnerYesOrNo.getSelectedItem().toString();
                if(msp.equals("yes")){
                    mspStatus = "true";
                }else{
                    mspStatus = "false";
                }

                //Update to Database;
                Patient updatePatient = new Patient();
                updatePatient.setPatientID(Integer.parseInt(rowID));
                updatePatient.setFirstName(firstName);
                updatePatient.setLastName(lastName);
                updatePatient.setAddress(address);
                updatePatient.setLoginID(loginID);
                updatePatient.setPassword(password);
                updatePatient.setMspStatus(mspStatus);
                updatePatient.setPhoneNumber(phoneNumber);
                updatePatient.setHealthCareCardNumber(healthCardNumber);
                updatePatient.setEmailAddress(email);

                dao.patientAccountEdit(updatePatient, EditPatientAccountActivity.this);
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.patientAccountDelete(Integer.parseInt(rowID), EditPatientAccountActivity.this);
            }
        });

    }
}
