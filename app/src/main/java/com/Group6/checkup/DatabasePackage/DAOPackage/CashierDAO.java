package com.Group6.checkup.DatabasePackage.DAOPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.TableClassPackage.Cashier;

public class CashierDAO {

    ContentValues values;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    //Insert method
    public void cashierAccountInsert(Cashier cashier, Context context) {
        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.CashierTable.FIRST_NAME, cashier.getFirstName());
        values.put(DatabaseTable.CashierTable.LAST_NAME, cashier.getLastName());
        values.put(DatabaseTable.CashierTable.LOGIN_ID, cashier.getLoginID());
        values.put(DatabaseTable.CashierTable.PASSWORD, cashier.getPassword());
        values.put(DatabaseTable.CashierTable.ADMIN_ID, cashier.getAdminID());

        long check = db.insert(DatabaseTable.CashierTable.TABLE_NAME, null, values);

        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }

    //Edit method
    public void cashierAccountEdit(Cashier cashier, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.CashierTable.FIRST_NAME, cashier.getFirstName());
        values.put(DatabaseTable.CashierTable.LAST_NAME, cashier.getLastName());
        values.put(DatabaseTable.CashierTable.LOGIN_ID, cashier.getLoginID());
        values.put(DatabaseTable.CashierTable.PASSWORD, cashier.getPassword());

        int check = db.update(DatabaseTable.CashierTable.TABLE_NAME, values, DatabaseTable.CashierTable._ID + " = " + cashier.getCashierID(), null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }
    }

    //Delete method
    public void cashierAccountDelete(int rowID, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.CashierTable.TABLE_NAME, DatabaseTable.CashierTable._ID + " = " + rowID, null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
