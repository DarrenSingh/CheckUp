package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.AdminDao;

import java.util.ArrayList;

public class AdminAccountEditActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID, enteredLoginID;
    Intent getIntent;
    AdminDao adminDao;
    Admin adminAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_account);

        etFirstName = findViewById(R.id.editTxt_editAdminFirstName);
        etLastName = findViewById(R.id.editTxt_editAdminLastName);
        etLoginID = findViewById(R.id.editTxt_editAdminLoginID);
        etPassword = findViewById(R.id.editTxt_editAdminPassword);

        btnEditAccount = findViewById(R.id.btn_editAdminAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteAdminAccount);

        getIntent = getIntent();
        adminDao = new AdminDao(this);


        loginID = getIntent.getStringExtra("loginID");

        //TODO handle exception
        adminAccount = adminDao.find(loginID);

        etFirstName.setText(adminAccount.getFirstName());
        etLastName.setText(adminAccount.getLastName());
        etLoginID.setText(adminAccount.getLoginID());
        etPassword.setText(adminAccount.getPassword());

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adminAccount.setFirstName(etFirstName.getText().toString());
                adminAccount.setLastName(etLastName.getText().toString());
                adminAccount.setLoginID(etLoginID.getText().toString());
                adminAccount.setPassword(etPassword.getText().toString());

                //Edit loginID validation
                if (loginID.charAt(0) != 'A') {
                    etLoginID.setError("Admin Account has to start with letter 'A'.");

                    if(adminDao.exists(etLoginID.getText().toString())){
                        etLoginID.setError("Login ID is already exists");
                    }

                } else {
                    //update database
                    if(adminDao.insert(adminAccount)) {
                        Toast.makeText(AdminAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                    } else {
                        Toast.makeText(AdminAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adminDao.delete(loginID)) {
                    Toast.makeText(AdminAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(AdminAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
