package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Group6.checkup.R;

public class AccountTypeOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type_option);

    }
    //Button click move to corresponding activities
    public void buttonClicked(View view) {
        if (view.getId() == R.id.btn_toAdminAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, AdminAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toPatientAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, PatientAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toDoctorAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, DoctorAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toCashierAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, CashierAccountCreateActivity.class));
        }
    }
}
