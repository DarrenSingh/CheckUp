package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.Group6.checkup.Cashier;
import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class CashierAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo;

    DatabaseDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account_create_activity);

        dao = new DatabaseDAO();

        currentLogin= getApplicationContext().getSharedPreferences("loginInfo", MODE_PRIVATE);

        etFirstName = findViewById(R.id.editTxt_cashierFirstName);
        etLastName = findViewById(R.id.editTxt_cashierLastName);
        etLoginID = findViewById(R.id.editTxt_cashierLoginID);
        etPassword = findViewById(R.id.editTxt_cashierPassword);

        btnCreateAccount = findViewById(R.id.btn_createCashierAccount);

        currentLoginInfo = dao.accountSearch(currentLogin.getString("loginID", "failed"), CashierAccountCreateActivity.this);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etLoginID.getText().toString().charAt(0) != 'C'){
                    etLoginID.setError("Patient Account has to start with letter 'C'.");
                }
                else {
                    Cashier newCashierAccount = new Cashier();
                    newCashierAccount.setFirstName(etFirstName.getText().toString());
                    newCashierAccount.setLastName(etLastName.getText().toString());
                    newCashierAccount.setLoginID(etLoginID.getText().toString());
                    newCashierAccount.setPassword(etPassword.getText().toString());
                    newCashierAccount.setAdminID(Integer.parseInt(currentLoginInfo.get(0)));

                    //Insert to Database
                    dao.cashierAccountInsert(newCashierAccount, CashierAccountCreateActivity.this);
                }
            }
        });
    }

}
