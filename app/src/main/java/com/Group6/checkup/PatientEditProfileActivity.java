package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;

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
import android.widget.TextView;

public class PatientEditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Define the session in your activity class
//    private Session appSession;
//    private Patient user;
//    private PatientDao patientDao;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Edit My Profile");

        //first thing instantiate the session, and pass the context
//        appSession = new Session(this);


        //TODO com.Group6.checkup.Patient Edit profile logic

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_edit_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_edit_profile_address);
        TextView mTextViewMSP = findViewById(R.id.text_edit_profile_msp);

        final EditText mEditFirstName = findViewById(R.id.edit_first_name);
        final EditText mEditlastName = findViewById(R.id.edit_last_name);
        final EditText mEditAddress = findViewById(R.id.edit_address);

        Button mBtnConfirmChanges = findViewById(R.id.btn_edit_profile_confirm);

        //Activity Logic
        //TODO implement dao
//        patientDao = new PatientDao(this);
//        user = patientDao.getPatient(appSession.getCurrentUser());
//
//        String fullName = user.getFirstName() + " " + user.getLastName();
//
//        mTextViewName.setText(fullName);
//        mTextViewAddress.setText(user.getAddress());
//        mTextViewMSP.setText(String.valueOf(user.getHealthCareCardNumber()));
//
//        mEditFirstName.setText(user.getFirstName());
//        mEditlastName.setText(user.getLastName());
//        mEditAddress.setText(user.getAddress());


        //UI Event Listeners
        mBtnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                user.setFirstName(mEditFirstName.getText().toString());
//                user.setLastName(mEditlastName.getText().toString());
//                user.setAddress(mEditAddress.getText().toString());
//                if(patientDao.update(user)){
//                    Toast.makeText(PatientEditProfileActivity.this, "Profile Edited", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(PatientEditProfileActivity.this,AccountProfileActivity.class));
//                } else {
//                    Toast.makeText(PatientEditProfileActivity.this, "Unable to Edit", Toast.LENGTH_SHORT).show();
//                }
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
                Intent h= new Intent(PatientEditProfileActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(PatientEditProfileActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(PatientEditProfileActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
