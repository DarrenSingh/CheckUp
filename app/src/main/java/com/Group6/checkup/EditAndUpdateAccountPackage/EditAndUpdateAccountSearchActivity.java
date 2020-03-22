package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.Group6.checkup.CreateAccountPackage.CashierAccountCreateActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

import java.util.ArrayList;

public class EditAndUpdateAccountSearchActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editTxtLoginID;
    ListView listView;
    String loginID;
    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_update_account_search);

        btnSearch = findViewById(R.id.btn_accountSearch);
        editTxtLoginID = findViewById(R.id.editTxt_accountSearch);
        listView = findViewById(R.id.listView_accountSearch);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Database Search and put values in array list
                if (TextUtils.isEmpty(editTxtLoginID.getText().toString())) {
                    editTxtLoginID.setError("Please enter the loginID");
                } else {

                    boolean accountFound = false;
                    loginID = editTxtLoginID.getText().toString();

                    switch (loginID.charAt(0)) {

                        case 'A':
                            AdminDao adminDao = new AdminDao(getApplicationContext());
                            accountFound = adminDao.exists(loginID);
                            break;

                        case 'C':
                            CashierDao cashierDao = new CashierDao((getApplicationContext()));
                            accountFound = cashierDao.exists(loginID);
                            break;

                        case 'D':
                            DoctorDao doctorDao = new DoctorDao(getApplicationContext());
                            accountFound = doctorDao.exists(loginID);
                            break;

                        case 'P':
                            PatientDao patientDao = new PatientDao(getApplicationContext());
                            accountFound = patientDao.exists(loginID);
                            break;

                    }

                    //Save the loginID to array to display into the listview.
                    if (accountFound) {

                        String[] list = {loginID};

                        //listView adapter and set.
                        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
                        listView.setAdapter(adapter);
                    } else {
                        Toast.makeText(EditAndUpdateAccountSearchActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //According to first letter of the loginID, start individual activity.
                switch (loginID.charAt(0)) {
                    case 'A':
                        nextActivity = new Intent(getBaseContext(), AdminAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case 'P':
                        nextActivity = new Intent(getBaseContext(), PatientAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case 'D':
                        nextActivity = new Intent(getBaseContext(), DoctorAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case 'C':
                        nextActivity = new Intent(getBaseContext(), CashierAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                }
            }
        });
    }
}
