package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.Cashier;
import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditCashierAccountActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID, adminID;
    Intent getIntent;
    DatabaseDAO dao;
    ArrayList<String> cashierInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cashier_account);

        getIntent = getIntent();
        dao = new DatabaseDAO();
        cashierInfo = new ArrayList<>();

        loginID = getIntent.getStringExtra("loginID");
        cashierInfo = dao.accountSearch(loginID, EditCashierAccountActivity.this);

        rowID = cashierInfo.get(0);
        firstName = cashierInfo.get(1);
        lastName = cashierInfo.get(2);
        loginID = cashierInfo.get(3);
        password = cashierInfo.get(4);
        adminID = cashierInfo.get(5);

        etFirstName = findViewById(R.id.editTxt_editCashierFirstName);
        etLastName = findViewById(R.id.editTxt_editCashierLastName);
        etLoginID = findViewById(R.id.editTxt_editCashierLoginID);
        etPassword = findViewById(R.id.editTxt_editCashierPassword);

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etLoginID.setText(loginID);
        etPassword.setText(password);

        btnEditAccount = findViewById(R.id.btn_editCashierAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteCashierAccount);

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //Insert to Database
                Cashier updateCashier = new Cashier();
                updateCashier.setCashierID(Integer.parseInt(rowID));
                updateCashier.setFirstName(firstName);
                updateCashier.setLastName(lastName);
                updateCashier.setLoginID(loginID);
                updateCashier.setPassword(password);

                dao.cashierAccountEdit(updateCashier, EditCashierAccountActivity.this);
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao.cashierAccountDelete(Integer.parseInt(rowID), EditCashierAccountActivity.this);
            }
        });
    }
}
