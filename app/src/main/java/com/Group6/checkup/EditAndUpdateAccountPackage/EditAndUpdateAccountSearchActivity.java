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

import com.Group6.checkup.DatabasePackage.DAOPackage.AccountSearchDAO;
import com.Group6.checkup.R;

import java.util.ArrayList;

public class EditAndUpdateAccountSearchActivity extends AppCompatActivity {

    Button btnSearch;
    EditText editTxtLoginID;
    ListView listView;
    ArrayList<String> listItem;
    AccountSearchDAO dao;
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
        dao = new AccountSearchDAO();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Database Search and put values in array list
                if (TextUtils.isEmpty(editTxtLoginID.getText().toString())) {
                    editTxtLoginID.setError("Please enter the loginID");
                } else {

                    //Search from the database using loginId and return the data. Save them to array list called listItem.
                    listItem = dao.accountSearch(editTxtLoginID.getText().toString(), EditAndUpdateAccountSearchActivity.this);

                    //If listItem size is bigger than 0 -> loginId exists.
                    if (listItem.size() > 0) {

                        //Validate the type.
                        if (listItem.get(5).equals("admin") || listItem.get(6).equals("cashier")) {

                            //Result of search and save it to loginID variable
                            loginID = (listItem.get(3));

                            //Validate the type.
                        } else if (listItem.get(9).equals("doctor") || listItem.get(11).equals("patient")) {

                            //Result of search and save it to loginID variable
                            loginID = (listItem.get(4));
                        }

                        //Get the first letter to identify the account type.
                        firstLetter = (loginID.substring(0, 1));

                        //Save the loginID to array to display into the listview.
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
                switch (firstLetter) {
                    case "A":
                        nextActivity = new Intent(getBaseContext(), AdminAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "P":
                        nextActivity = new Intent(getBaseContext(), PatientAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "D":
                        nextActivity = new Intent(getBaseContext(), DoctorAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                    case "C":
                        nextActivity = new Intent(getBaseContext(), CashierAccountEditActivity.class);
                        nextActivity.putExtra("loginID", loginID);
                        startActivity(nextActivity);
                        break;
                }
            }
        });
    }
}
