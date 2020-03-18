package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.Group6.checkup.CreateAccountPackage.AccountTypeOptionActivity;
import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.EditAndUpdateAccountPackage.EditAndUpdateAccountSearchActivity;

public class AdminActivity extends AppCompatActivity {

    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbh = new DatabaseHelper(this);

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
