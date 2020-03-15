package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
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

public class PatientAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String msp, mspStatus;
    String[] yesOrNo = {"yes", "no"};
    DatabaseDAO dao;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account_create);

        dao = new DatabaseDAO();

        currentLogin = getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);

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

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, yesOrNo);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYesOrNo.setAdapter(adapter);

        currentLoginInfo = dao.accountSearch(currentLogin.getString("loginID", "failed"), PatientAccountCreateActivity.this);


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
                if (etLoginID.getText().toString().charAt(0) != 'P') {
                    etLoginID.setError("Patient Account has to start with letter 'P'.");
                } else {
                    msp = spinnerYesOrNo.getSelectedItem().toString();

                    if (msp.equals("yes")) {
                        mspStatus = "true";
                    } else {
                        mspStatus = "false";
                    }

                    Patient newPatient = new Patient();
                    newPatient.setFirstName(etFirstName.getText().toString());
                    newPatient.setLastName(etLastName.getText().toString());
                    newPatient.setAddress(etAddress.getText().toString());
                    newPatient.setPhoneNumber(etPhoneNumber.getText().toString());
                    newPatient.setEmailAddress(etEmail.getText().toString());
                    newPatient.setLoginID(etLoginID.getText().toString());
                    newPatient.setPassword(etPassword.getText().toString());
                    newPatient.setHealthCareCardNumber(etHealthCardNumber.getText().toString());
                    newPatient.setMspStatus(mspStatus);
                    newPatient.setAdminID(Integer.parseInt(currentLoginInfo.get(0)));

                    //Insert to Database;
                    dao.patientAccountInsert(newPatient, PatientAccountCreateActivity.this);
                }
            }
        });
    }
}
