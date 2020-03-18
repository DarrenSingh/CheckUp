package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.Group6.checkup.DatabasePackage.DAOPackage.AccountSearchDAO;
import com.Group6.checkup.TableClassPackage.Admin;
import com.Group6.checkup.DatabasePackage.DAOPackage.AdminDAO;
import com.Group6.checkup.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    ArrayList<String> adminAccountInfo;
    AdminDAO adminDAO;
    AccountSearchDAO accountSearchDAO;

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
                accountSearchDAO = new AccountSearchDAO();
                adminDAO = new AdminDAO();
                adminAccountInfo = new ArrayList<String>();

                //Searching and finding the data that has the same loginID.
                adminAccountInfo = accountSearchDAO.accountSearch(etLoginID.getText().toString(), AdminAccountCreateActivity.this);

                //LoginID Validation
                if (etLoginID.getText().toString().charAt(0) != 'A') {
                    etLoginID.setError("Admin Account has to start with letter 'A'.");
                }

                //LoginID existence validation
                if (0 < adminAccountInfo.size()) {
                    etLoginID.setError("Login Id is already exists");
                }

                //If user Input pass the validation, insert to database.
                if(etLoginID.getText().toString().charAt(0) == 'A' && 0 == adminAccountInfo.size())
                {
                    //Creating new Admin Class object.
                    Admin newAdminAccount = new Admin();
                    newAdminAccount.setFirstName(etFirstName.getText().toString());
                    newAdminAccount.setLastName(etLastName.getText().toString());
                    newAdminAccount.setLoginID(etLoginID.getText().toString());
                    newAdminAccount.setPassword(etPassword.getText().toString());

                    //Call admin account insert class from adminDAO, and pass the object.
                    adminDAO.adminAccountInsert(newAdminAccount, AdminAccountCreateActivity.this);
                }
            }
        });
    }
}
