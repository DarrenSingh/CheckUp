package com.Group6.checkup.CreateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.R;

public class CashierAccountCreateActivity extends AppCompatActivity {

    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password;
    String key;

    SQLiteDatabase db;
    ContentValues cashierData;
    DatabaseHelper dbh;

    Cursor adminCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account_create_activity);

        cashierData = new ContentValues();
        dbh = new DatabaseHelper(this, DatabaseTable.CashierTable.TABLE_NAME, DatabaseTable.CashierTable.DATABASE_VERSION);

        adminCursor = getAdminCursor();
        while(adminCursor.moveToNext()) {
            key = adminCursor.getString(0);
        }

        etFirstName = findViewById(R.id.editTxt_cashierFirstName);
        etLastName = findViewById(R.id.editTxt_cashierLastName);
        etLoginID = findViewById(R.id.editTxt_cashierLoginID);
        etPassword = findViewById(R.id.editTxt_cashierPassword);

        btnCreateAccount = findViewById(R.id.btn_createCashierAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firstName = etFirstName.getText().toString();
                lastName = etLastName.getText().toString();
                loginID = etLoginID.getText().toString();
                password = etPassword.getText().toString();
                String number = "1";

                //Insert to Database
                db = dbh.getWritableDatabase();

                cashierData.put(DatabaseTable.CashierTable.ADMIN_ID, 1);
                cashierData.put(DatabaseTable.CashierTable.FIRST_NAME, firstName);
                cashierData.put(DatabaseTable.CashierTable.LAST_NAME, lastName);
                cashierData.put(DatabaseTable.CashierTable.LOGIN_ID, loginID);
                cashierData.put(DatabaseTable.CashierTable.PASSWORD, password);

                long newRowId = db.insert(DatabaseTable.CashierTable.TABLE_NAME, null, cashierData);
                if(newRowId == -1) {
                    Toast.makeText(getBaseContext(), "Failed", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private Cursor getAdminCursor(){
        db = dbh.getReadableDatabase();
        return db.query(DatabaseTable.AdminTable.TABLE_NAME, null, null, null, null,null, null);
    }
}
