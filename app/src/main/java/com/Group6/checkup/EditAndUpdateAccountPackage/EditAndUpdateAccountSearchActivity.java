package com.Group6.checkup.EditAndUpdateAccountPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.Group6.checkup.R;

public class EditAndUpdateAccountSearchActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editTxtLoginID;
    ListView listView;
    String[] listItem = {"a", "b", "c", "d"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_update_account_search);

        btnSearch = findViewById(R.id.btn_accountSearch);
        editTxtLoginID = findViewById(R.id.editTxt_accountSearch);
        listView = findViewById(R.id.listView_accountSearch);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Database Search and put values in array

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        startActivity(new Intent(getBaseContext(), EditAdminAccountActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(getBaseContext(), EditPatientAccountActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getBaseContext(), EditDoctorAccountActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(getBaseContext(), EditCashierAccountActivity.class));
                        break;
                }
            }
        });
    }
}
