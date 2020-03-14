package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.Admin;
import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditAdminAccountActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID;
    ArrayList<String> adminInfo;
    Intent getIntent;
    DatabaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_account);

        getIntent = getIntent();
        dao = new DatabaseDAO();
        adminInfo = new ArrayList<>();

        loginID = getIntent.getStringExtra("loginID");
        adminInfo = dao.accountSearch(loginID, EditAdminAccountActivity.this);

        rowID = adminInfo.get(0);
        firstName = adminInfo.get(1);
        lastName = adminInfo.get(2);
        password = adminInfo.get(4);

        etFirstName = findViewById(R.id.editTxt_editAdminFirstName);
        etLastName = findViewById(R.id.editTxt_editAdminLastName);
        etLoginID = findViewById(R.id.editTxt_editAdminLoginID);
        etPassword = findViewById(R.id.editTxt_editAdminPassword);

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etLoginID.setText(loginID);
        etPassword.setText(password);

        btnEditAccount = findViewById(R.id.btn_editAdminAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteAdminAccount);

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                Admin updateAdmin = new Admin();
                updateAdmin.setAdminID(Integer.parseInt(rowID));
                updateAdmin.setFirstName(firstName);
                updateAdmin.setLastName(lastName);
                updateAdmin.setLoginID(loginID);
                updateAdmin.setPassword(password);

                dao.adminAccountEdit(updateAdmin, EditAdminAccountActivity.this);
                //update database
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.adminAccountDelete(Integer.parseInt(rowID), EditAdminAccountActivity.this);
            }
        });


    }
}
