package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.TableClassPackage.Cashier;
import com.Group6.checkup.DatabasePackage.DAOPackage.AccountSearchDAO;
import com.Group6.checkup.DatabasePackage.DAOPackage.CashierDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class CashierAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo, cashierAccountInfo;
    CashierDAO cashierDAO;
    AccountSearchDAO accountSearchDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account_create_activity);

        //Creating DAO Objects.
        cashierDAO = new CashierDAO();
        accountSearchDAO = new AccountSearchDAO();

        //Shared Preference to check loginStatus.
        currentLogin = getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);

        etFirstName = findViewById(R.id.editTxt_cashierFirstName);
        etLastName = findViewById(R.id.editTxt_cashierLastName);
        etLoginID = findViewById(R.id.editTxt_cashierLoginID);
        etPassword = findViewById(R.id.editTxt_cashierPassword);

        btnCreateAccount = findViewById(R.id.btn_createCashierAccount);

        //Account Search and check current loginID status.
        currentLoginInfo = accountSearchDAO.accountSearch(currentLogin.getString("loginID", "failed"), CashierAccountCreateActivity.this);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cashierAccountInfo = new ArrayList<String>();

                //Searching and finding the data that has the same loginID.
                cashierAccountInfo = accountSearchDAO.accountSearch(etLoginID.getText().toString(), CashierAccountCreateActivity.this);

                //LoginID Validation
                if (etLoginID.getText().toString().charAt(0) != 'C') {
                    etLoginID.setError("Patient Account has to start with letter 'C'.");
                }

                //LoginID existence validation
                if (0 < cashierAccountInfo.size()) {
                    etLoginID.setError("Login Id is already exists");
                }

                //If user Input pass the validation, insert to database.
                if (etLoginID.getText().toString().charAt(0) == 'C' && 0 == cashierAccountInfo.size()) {

                    //creating new Cashier account object.
                    Cashier newCashierAccount = new Cashier();
                    newCashierAccount.setFirstName(etFirstName.getText().toString());
                    newCashierAccount.setLastName(etLastName.getText().toString());
                    newCashierAccount.setLoginID(etLoginID.getText().toString());
                    newCashierAccount.setPassword(etPassword.getText().toString());
                    newCashierAccount.setAdminID(Integer.parseInt(currentLoginInfo.get(0)));

                    //Insert to Database
                    cashierDAO.cashierAccountInsert(newCashierAccount, CashierAccountCreateActivity.this);
                }
            }
        });
    }

}
