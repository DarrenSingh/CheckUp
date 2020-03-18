package com.Group6.checkup.DatabasePackage.DAOPackage;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.Group6.checkup.DatabasePackage.DatabaseHelper;
import com.Group6.checkup.DatabasePackage.DatabaseTable;
import com.Group6.checkup.TableClassPackage.Admin;

public class AdminDAO {
    ContentValues values;
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    //Insert method
    public void adminAccountInsert(Admin admin, Context context) {

        values = new ContentValues();
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();

        values.put(DatabaseTable.AdminTable.FIRST_NAME, admin.getFirstName());
        values.put(DatabaseTable.AdminTable.LAST_NAME, admin.getLastName());
        values.put(DatabaseTable.AdminTable.LOGIN_ID, admin.getLoginID());
        values.put(DatabaseTable.AdminTable.PASSWORD, admin.getPassword());

        long check = db.insert(DatabaseTable.AdminTable.TABLE_NAME, null, values);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Created", Toast.LENGTH_SHORT).show();
        }
    }

    //Edit method
    public void adminAccountEdit(Admin admin, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        values.put(DatabaseTable.AdminTable.FIRST_NAME, admin.getFirstName());
        values.put(DatabaseTable.AdminTable.LAST_NAME, admin.getLastName());
        values.put(DatabaseTable.AdminTable.LOGIN_ID, admin.getLoginID());
        values.put(DatabaseTable.AdminTable.PASSWORD, admin.getPassword());

        int check = db.update(DatabaseTable.AdminTable.TABLE_NAME, values, DatabaseTable.AdminTable._ID + " = " + admin.getAdminID(), null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
        }

    }

    //Delete method
    public void adminAccountDelete(int rowID, Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        values = new ContentValues();

        int check = db.delete(DatabaseTable.AdminTable.TABLE_NAME, DatabaseTable.AdminTable._ID + " = " + rowID, null);
        if (check == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        }

    }

}
