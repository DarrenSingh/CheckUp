package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.CashierDao;


public class CashierAccountEditActivity extends AppCompatActivity {

    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID, adminID, enteredLoginID;
    Intent getIntent;
    CashierDao cashierDao;
    Cashier cashierAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cashier_account);


        etFirstName = findViewById(R.id.editTxt_editCashierFirstName);
        etLastName = findViewById(R.id.editTxt_editCashierLastName);
        etLoginID = findViewById(R.id.editTxt_editCashierLoginID);
        etPassword = findViewById(R.id.editTxt_editCashierPassword);

        btnEditAccount = findViewById(R.id.btn_editCashierAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteCashierAccount);

        getIntent = getIntent();
        cashierDao = new CashierDao(this);

        loginID = getIntent.getStringExtra("loginID");

        cashierAccount = cashierDao.find(loginID);

        etFirstName.setText(cashierAccount.getFirstName());
        etLastName.setText(cashierAccount.getLastName());
        etLoginID.setText(cashierAccount.getLoginID());
        etPassword.setText(cashierAccount.getPassword());

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashierAccount.setFirstName(etFirstName.getText().toString());
                cashierAccount.setLastName(etLastName.getText().toString());
                cashierAccount.setLoginID(etLoginID.getText().toString());
                cashierAccount.setPassword(etPassword.getText().toString());

                //Edit loginID validation
                if (loginID.charAt(0) != 'C') {
                    etLoginID.setError("Cashier Account has to start with letter 'C'.");

                    if (cashierDao.exists(etLoginID.getText().toString())) {
                        etLoginID.setError("Login ID is already exists");
                    }
                } else {
                    //update database
                    if(cashierDao.insert(cashierAccount)) {
                        Toast.makeText(CashierAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CashierAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                    } else {
                        Toast.makeText(CashierAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cashierDao.delete(loginID)) {
                    Toast.makeText(CashierAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CashierAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(CashierAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
