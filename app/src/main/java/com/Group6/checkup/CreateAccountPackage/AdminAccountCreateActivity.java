package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.R;

public class AdminAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password;

    SQLiteDatabase db;
    ContentValues adminData;
    DatabaseHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_create);


        adminData = new ContentValues();
        dbh = new DatabaseHelper(this, DatabaseTable.AdminTable.TABLE_NAME, DatabaseTable.AdminTable.DATABASE_VERSION);

        etFirstName = findViewById(R.id.editTxt_adminFirstName);
        etLastName = findViewById(R.id.editTxt_adminLastName);
        etLoginID = findViewById(R.id.editTxt_adminLoginID);
        etPassword = findViewById(R.id.editTxt_adminPassword);

        btnCreateAccount = findViewById(R.id.btn_createAdminAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();

                //Add to database
                adminData.put(DatabaseTable.AdminTable.FIRST_NAME, firstName);
                adminData.put(DatabaseTable.AdminTable.LAST_NAME, lastName);
                adminData.put(DatabaseTable.AdminTable.LOGIN_ID, loginID);
                adminData.put(DatabaseTable.AdminTable.PASSWORD, password);

                db = dbh.getWritableDatabase();

                long newRowId = db.insert(DatabaseTable.AdminTable.TABLE_NAME, null, adminData);
                if(newRowId == -1) {
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                    dbh.close();
                }else{
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                    dbh.close();
                }

            }
        });
    }
}
