package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.Group6.checkup.Admin;
import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.R;

public class AdminAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    DatabaseDAO dao;

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
                if (etLoginID.getText().toString().charAt(0) != 'A') {
                    etLoginID.setError("Admin Account has to start with letter 'A'.");
                } else {

                    dao = new DatabaseDAO();

                    Admin newAdminAccount = new Admin();
                    newAdminAccount.setFirstName(etFirstName.getText().toString());
                    newAdminAccount.setLastName(etLastName.getText().toString());
                    newAdminAccount.setLoginID(etLoginID.getText().toString());
                    newAdminAccount.setPassword(etPassword.getText().toString());

                    dao.adminAccountInsert(newAdminAccount, AdminAccountCreateActivity.this);
                }
            }
        });
    }
}
