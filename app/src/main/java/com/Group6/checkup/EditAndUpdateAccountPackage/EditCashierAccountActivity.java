package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.Group6.checkup.R;

public class EditCashierAccountActivity extends AppCompatActivity {

    Button btnEditAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cashier_account);

        etFirstName = findViewById(R.id.editTxt_editCashierFirstName);
        etLastName = findViewById(R.id.editTxt_editCashierLastName);
        etLoginID = findViewById(R.id.editTxt_editCashierLoginID);
        etPassword = findViewById(R.id.editTxt_editCashierPassword);

        btnEditAccount = findViewById(R.id.btn_editCashierAccount);

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //Insert to Database
            }
        });
    }
}
