package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.TableClassPackage.Admin;
import com.Group6.checkup.DatabasePackage.DAOPackage.AccountSearchDAO;
import com.Group6.checkup.DatabasePackage.DAOPackage.AdminDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class AdminAccountEditActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID, enteredLoginID;
    ArrayList<String> adminInfo, newAdminInfo;
    Intent getIntent;
    AdminDAO adminDAO;
    AccountSearchDAO accountSearchDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_account);

        getIntent = getIntent();

        adminDAO = new AdminDAO();
        accountSearchDAO = new AccountSearchDAO();

        adminInfo = new ArrayList<>();
        newAdminInfo = new ArrayList<>();

        loginID = getIntent.getStringExtra("loginID");
        adminInfo = accountSearchDAO.accountSearch(loginID, AdminAccountEditActivity.this);

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
                enteredLoginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                newAdminInfo = accountSearchDAO.accountSearch(enteredLoginID, AdminAccountEditActivity.this);

                //Edit loginID validation
                if (loginID.charAt(0) != 'A') {
                    etLoginID.setError("Admin Account has to start with letter 'A'.");
                }

                if(!enteredLoginID.equals(loginID) && newAdminInfo.size() > 0){
                    etLoginID.setError("Login ID is already exists");
                }

                //If user input pass the validation, edit the data.
                if (loginID.charAt(0) == 'A' && enteredLoginID.equals(loginID)) {

                    Admin updateAdmin = new Admin();
                    updateAdmin.setAdminID(Integer.parseInt(rowID));
                    updateAdmin.setFirstName(firstName);
                    updateAdmin.setLastName(lastName);
                    updateAdmin.setLoginID(loginID);
                    updateAdmin.setPassword(password);

                    adminDAO.adminAccountEdit(updateAdmin, AdminAccountEditActivity.this);
                    //update database
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminDAO.adminAccountDelete(Integer.parseInt(rowID), AdminAccountEditActivity.this);
            }
        });


    }
}
