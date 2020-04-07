package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;

import com.Group6.checkup.AdminActivity;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;
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

public class DoctorAccountEditActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etOfficeAddress, etPhoneNumber, etEmail, etLoginID, etPassword;
    String firstName, lastName, officeAddress, phoneNumber, email, loginID, password, enteredLoginID;
    Intent getIntent;
    DoctorDao doctorDao;
    Doctor doctorAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doctor_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Edit Doctor Account");

        btnEditAccount = findViewById(R.id.btn_editDoctorAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteDoctorAccount);

        etFirstName = findViewById(R.id.editTxt_editDoctorFirstName);
        etLastName = findViewById(R.id.editTxt_editDoctorLastName);
        etOfficeAddress = findViewById(R.id.editTxt_editDoctorAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editDoctorPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editDoctorEmail);
        etLoginID = findViewById(R.id.editTxt_editDoctorLoginID);
        etPassword = findViewById(R.id.editTxt_editDoctorPassword);

        getIntent = getIntent();
        doctorDao = new DoctorDao(this);

        loginID = getIntent.getStringExtra("loginID");

        doctorAccount = doctorDao.find(loginID);

        etFirstName.setText(doctorAccount.getFirstName());
        etLastName.setText(doctorAccount.getLastName());
        etOfficeAddress.setText(doctorAccount.getOfficeAddress());
        etLoginID.setText(doctorAccount.getLoginID());
        etPassword.setText(doctorAccount.getPassword());
        etPhoneNumber.setText(doctorAccount.getPhoneNumber());
        etEmail.setText(doctorAccount.getEmailAddress());

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Edit loginID validation
                if (loginID.charAt(0) != 'D') {
                    etLoginID.setError("Doctor Account has to start with letter 'D'.");

                    if (doctorDao.exists(loginID)) {
                        etLoginID.setError("Login ID is already exists");
                    }

                } else {

                    firstName = etFirstName.getText().toString();
                    lastName = etLastName.getText().toString();
                    officeAddress = etOfficeAddress.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();
                    email = etEmail.getText().toString();
                    enteredLoginID = etLoginID.getText().toString();
                    password = etPassword.getText().toString();

                    doctorAccount.setFirstName(firstName);
                    doctorAccount.setLastName(lastName);
                    doctorAccount.setOfficeAddress(officeAddress);
                    doctorAccount.setLoginID(loginID);
                    doctorAccount.setPassword(password);
                    doctorAccount.setPhoneNumber(phoneNumber);
                    doctorAccount.setEmailAddress(email);

                    //update database
                    if(doctorDao.insert(doctorAccount)) {
                        Toast.makeText(DoctorAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DoctorAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                    } else {
                        Toast.makeText(DoctorAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(doctorDao.delete(loginID)) {
                    Toast.makeText(DoctorAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DoctorAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(DoctorAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
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
                Intent h= new Intent(DoctorAccountEditActivity.this, AdminActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(DoctorAccountEditActivity.this, ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(DoctorAccountEditActivity.this, loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
