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

import com.Group6.checkup.DatabasePackage.DatabaseDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditAndUpdateAccountSearchActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editTxtLoginID;
    ListView listView;
    ArrayList<String> listItem;
    DatabaseDAO dao;
    String firstLetter, loginID;
    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_update_account_search);

        btnSearch = findViewById(R.id.btn_accountSearch);
        editTxtLoginID = findViewById(R.id.editTxt_accountSearch);
        listView = findViewById(R.id.listView_accountSearch);
        listItem = new ArrayList<String>();
        dao = new DatabaseDAO();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database Search and put values in array
                if (TextUtils.isEmpty(editTxtLoginID.getText().toString())) {
                    editTxtLoginID.setError("Please enter the loginID");
                }else {
                    listItem = dao.accountSearch(editTxtLoginID.getText().toString(), EditAndUpdateAccountSearchActivity.this);
                    if(listItem.size() > 0) {
                        if(listItem.get(5).equals("admin") || listItem.get(6).equals("cashier")){
                            loginID = (listItem.get(3));
                        }
                        else if(listItem.get(9).equals("doctor")|| listItem.get(11).equals("patient")){
                            loginID = (listItem.get(4));
                        }

                        firstLetter = (loginID.substring(0, 1));
                        String[] list = {loginID};
                        ArrayAdapter adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, list);
                        listView.setAdapter(adapter);
                    }else{
                        Toast.makeText(EditAndUpdateAccountSearchActivity.this, "Not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (firstLetter) {
                    case "A":
                        nextActivity = new Intent(getBaseContext(), EditAdminAccountActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "P":
                        nextActivity = new Intent(getBaseContext(), EditPatientAccountActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "D":
                        nextActivity = new Intent(getBaseContext(), EditDoctorAccountActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "C":
                        nextActivity = new Intent(getBaseContext(), EditCashierAccountActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                }
            }
        });
    }
}
