package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.R;

public class EditAdminAccountActivity extends AppCompatActivity {

    Button btnEditAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_account);

        etFirstName = findViewById(R.id.editTxt_editAdminFirstName);
        etLastName = findViewById(R.id.editTxt_editAdminLastName);
        etLoginID = findViewById(R.id.editTxt_editAdminLoginID);
        etPassword = findViewById(R.id.editTxt_editAdminPassword);

        btnEditAccount = findViewById(R.id.btn_editAdminAccount);

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //update database
            }
        });


    }
}
