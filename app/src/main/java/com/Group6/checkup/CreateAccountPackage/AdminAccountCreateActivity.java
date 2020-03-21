package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.R;

public class AdminAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_create);

        etFirstName = findViewById(R.id.editTxt_adminFirstName);
        etLastName = findViewById(R.id.editTxt_adminLastName);
        etLoginID = findViewById(R.id.editTxt_adminLoginID);
        etPassword = findViewById(R.id.editTxt_adminPassword);

        btnCreateAccount = findViewById(R.id.btn_createAdminAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Creating account search DAO to search and fetching matching data and save to Array list.
                AdminDao adminDao = new AdminDao(getApplicationContext());

                char firstChar = etLoginID.getText().toString().charAt(0);

                //LoginID Validation
                if (firstChar != 'A') {
                    etLoginID.setError("Admin Account has to start with letter 'A'.");
                } else {

                    //LoginID existence validation
                    if (adminDao.exists(etLoginID.getText().toString())) {

                        Toast.makeText(AdminAccountCreateActivity.this, "Login Id is already exists", Toast.LENGTH_SHORT).show();

                    } else {

                        //insert user into database
                        boolean inserted = adminDao.insert(
                                new Admin(
                                        etFirstName.getText().toString(),
                                        etLastName.getText().toString(),
                                        etLoginID.getText().toString(),
                                        etPassword.getText().toString()
                                )
                        );

                        //Give (un)successful prompt
                        if (inserted)
                            Toast.makeText(AdminAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AdminAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }
}
