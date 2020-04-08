package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.AdminHomeActivity;
import com.Group6.checkup.AdminViewHistoryActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class EditAndUpdateAccountSearchActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnSearch;
    EditText editTxtLoginID;
    ListView listView;
    String loginID;
    Intent nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_and_update_account_search);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Search Account");

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

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(null);
    }

    public void toggleSetUp(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(EditAndUpdateAccountSearchActivity.this, AdminHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(EditAndUpdateAccountSearchActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(EditAndUpdateAccountSearchActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
