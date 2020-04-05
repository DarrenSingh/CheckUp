package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.CreateAccountPackage.AccountTypeOptionActivity;
import com.Group6.checkup.EditAndUpdateAccountPackage.EditAndUpdateAccountSearchActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

    }

    public void buttonClicked(View view){
        if(view.getId() == R.id.btn_toCreateAccountActivity){
            startActivity(new Intent(AdminActivity.this, AccountTypeOptionActivity.class));
        } else if(view.getId() == R.id.btn_toEditAndUpdateAccountActivity){
            startActivity(new Intent(AdminActivity.this, EditAndUpdateAccountSearchActivity.class));
        } else if(view.getId() == R.id.btn_toViewUserHistoryActivity){
            startActivity(new Intent(AdminActivity.this, ViewUserHistoryActivity.class));
        } else if(view.getId() == R.id.btn_toReturnLogInActivity){
            startActivity(new Intent(AdminActivity.this, MainActivity.class));
        }
    }
}
