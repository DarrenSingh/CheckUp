package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.DatabasePackage.DAOPackage.AccountSearchDAO;
import com.Group6.checkup.DatabasePackage.DAOPackage.DoctorDAO;
import com.Group6.checkup.TableClassPackage.Doctor;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class DoctorAccountCreateActivity extends AppCompatActivity {
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    AccountSearchDAO accountSearchDAO;
    DoctorDAO doctorDAO;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo, doctorAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        accountSearchDAO = new AccountSearchDAO();
        doctorDAO = new DoctorDAO();

        //Shared Preference to check loginStatus.
        currentLogin = getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);

        btnCreateAccount = findViewById(R.id.btn_createDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_doctorFirstName);
        etLastName = findViewById(R.id.editTxt_doctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_doctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_doctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_doctorEmail);
        etLoginID = findViewById(R.id.editTxt_doctorLoginID);
        etPassword = findViewById(R.id.editTxt_doctorPassword);

        //Account Search and check current loginID status.
        currentLoginInfo = accountSearchDAO.accountSearch(currentLogin.getString("loginID", "failed"), DoctorAccountCreateActivity.this);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                doctorAccountInfo = new ArrayList<String>();

                //Searching and finding the data that has the same loginID.
                doctorAccountInfo = accountSearchDAO.accountSearch(etLoginID.getText().toString(), DoctorAccountCreateActivity.this);

                //Searching and finding the data that has the same loginID.
                if (etLoginID.getText().toString().charAt(0) != 'D') {
                    etLoginID.setError("Patient Account has to start with letter 'D'.");
                }

                //LoginID existence validation
                if (0 < doctorAccountInfo.size()) {
                    etLoginID.setError("Login Id is already exists");
                }

                //If user Input pass the validation, insert to database.
                if (etLoginID.getText().toString().charAt(0) == 'D' && 0 == doctorAccountInfo.size()) {

                    //Creating new Doctor class object.
                    Doctor newDoctor = new Doctor();
                    newDoctor.setFirstName(etFirstName.getText().toString());
                    newDoctor.setLastName(etLastName.getText().toString());
                    newDoctor.setOfficeAddress(etOfficeAddress.getText().toString());
                    newDoctor.setPhoneNumber(etPhoneNumber.getText().toString());
                    newDoctor.setEmailAddress(etEmail.getText().toString());
                    newDoctor.setLoginID(etLoginID.getText().toString());
                    newDoctor.setPassword(etPassword.getText().toString());
                    newDoctor.setAdminID(Integer.parseInt(currentLoginInfo.get(0)));

                    //Call admin account insert class from adminDAO, and pass the object.
                    doctorDAO.doctorAccountInsert(newDoctor, DoctorAccountCreateActivity.this);

                }
            }
        });
    }
}
