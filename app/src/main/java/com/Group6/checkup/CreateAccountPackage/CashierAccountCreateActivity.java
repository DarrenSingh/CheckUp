package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Session;

public class CashierAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account_create_activity);

        etFirstName = findViewById(R.id.editTxt_cashierFirstName);
        etLastName = findViewById(R.id.editTxt_cashierLastName);
        etLoginID = findViewById(R.id.editTxt_cashierLoginID);
        etPassword = findViewById(R.id.editTxt_cashierPassword);

        btnCreateAccount = findViewById(R.id.btn_createCashierAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CashierDao cashierDao = new CashierDao(getApplicationContext());

                //LoginID Validation
                if (etLoginID.getText().toString().charAt(0) != 'C') {
                    etLoginID.setError("Patient Account has to start with letter 'C'.");
                } else {

                    if (cashierDao.exists(etLoginID.getText().toString())) {
                        etLoginID.setError("Login Id is already exists");
                    } else {

                        //TODO insert adminID from session info
                        //creating new Cashier account object.
                        Cashier newCashierAccount = new Cashier(
                                etFirstName.getText().toString(),
                                etLastName.getText().toString(),
                                etLoginID.getText().toString(),
                                etPassword.getText().toString(),
                                new Session(getApplicationContext()).getUserId()
                        );

                        //Insert to Database
                        boolean inserted = cashierDao.insert(newCashierAccount);

                        //Give (un)successful prompt
                        if (inserted)
                            Toast.makeText(CashierAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(CashierAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
    }

}
