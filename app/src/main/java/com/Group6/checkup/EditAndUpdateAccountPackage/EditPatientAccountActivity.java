package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.Group6.checkup.R;

public class EditPatientAccountActivity extends AppCompatActivity {

    Button btnEditAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String firstName, lastName, address, phoneNumber, email, loginID, password,healthCardNumber, msp;
    Boolean mspStatus;
    String[] yesOrNo = {"yes", "no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_account);

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
                    mspStatus = true;
                }else{
                    mspStatus = false;
                }

                //Update to Database;
            }
        });
    }
}
