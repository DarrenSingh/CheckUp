package com.Group6.checkup.CreateAccountPackage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.Group6.checkup.AdminActivity;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Session;
import com.Group6.checkup.ViewUserHistoryActivity;
import com.Group6.checkup.loginActivity;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DoctorAccountCreateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    SharedPreferences currentLogin;
    ArrayList<String> currentLoginInfo, doctorAccountInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_account_create);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Create Doctor Account");


        btnCreateAccount = findViewById(R.id.btn_createDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_doctorFirstName);
        etLastName = findViewById(R.id.editTxt_doctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_doctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_doctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_doctorEmail);
        etLoginID = findViewById(R.id.editTxt_doctorLoginID);
        etPassword = findViewById(R.id.editTxt_doctorPassword);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DoctorDao doctorDao = new DoctorDao(getApplicationContext());

                //Searching and finding the data that has the same loginID.
                if (etLoginID.getText().toString().charAt(0) != 'D') {
                    etLoginID.setError("Patient Account has to start with letter 'D'.");
                } else {
                    //LoginID existence validation
                    if (doctorDao.exists(etLoginID.getText().toString())) {

                        Toast.makeText(DoctorAccountCreateActivity.this, "Login ID taken", Toast.LENGTH_SHORT).show();

                    } else {

                        boolean inserted = doctorDao.insert(new Doctor(
                                etFirstName.getText().toString(),
                                etLastName.getText().toString(),
                                etOfficeAddress.getText().toString(),
                                etLoginID.getText().toString(),
                                etPassword.getText().toString(),
                                etPhoneNumber.getText().toString(),
                                etEmail.getText().toString(),
                                new Session(getApplicationContext()).getUserId()
                        ));

                        //Give (un)successful prompt
                        if (inserted)
                            Toast.makeText(DoctorAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(DoctorAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

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
                Intent h= new Intent(DoctorAccountCreateActivity.this, AdminActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(DoctorAccountCreateActivity.this, ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(DoctorAccountCreateActivity.this, loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
