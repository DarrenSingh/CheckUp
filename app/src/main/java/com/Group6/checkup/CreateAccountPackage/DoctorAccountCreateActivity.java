package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.R;

public class DoctorAccountCreateActivity extends AppCompatActivity {
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    String firstName, lastName, officeAddress, phoneNumber, email, loginID, password;

    SQLiteDatabase db;
    DatabaseHelper dbh;
    ContentValues doctorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);

        dbh = new DatabaseHelper(this);
        doctorData = new ContentValues();

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

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                officeAddress = etOfficeAddress.getText().toString();
                phoneNumber = etPhoneNumber.getText().toString();
                email = etEmail.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //Insert to Database
                db = dbh.getWritableDatabase();

                doctorData.put(DatabaseTable.DoctorTable.FIRST_NAME, firstName);
                doctorData.put(DatabaseTable.DoctorTable.LAST_NAME, lastName);
                doctorData.put(DatabaseTable.DoctorTable.OFFICE_ADDRESS, officeAddress);
                doctorData.put(DatabaseTable.DoctorTable.PHONE_NUMBER, phoneNumber);
                doctorData.put(DatabaseTable.DoctorTable.EMAIL_ADDRESS, email);
                doctorData.put(DatabaseTable.DoctorTable.LOGIN_ID, loginID);
                doctorData.put(DatabaseTable.DoctorTable.PASSWORD, password);
                doctorData.put(DatabaseTable.DoctorTable.ADMIN_ID, 1);

                long newRowId = db.insert(DatabaseTable.DoctorTable.TABLE_NAME, null, doctorData);
                if (newRowId == -1){
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
