package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.R;

public class EditDoctorAccountActivity extends AppCompatActivity {
    Button btnEditAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    String firstName, lastName, officeAddress, phoneNumber, email, loginID, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_account);

        btnEditAccount = findViewById(R.id.btn_editDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_editDoctorFirstName);
        etLastName = findViewById(R.id.editTxt_editDoctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_editDoctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editDoctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editDoctorEmail);
        etLoginID = findViewById(R.id.editTxt_editDoctorLoginID);
        etPassword = findViewById(R.id.editTxt_editDoctorPassword);

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
            }
        });
    }
}
