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
import android.widget.TextView;

public class PatientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        this.setTitle("Home");

        //UI Components
        TextView mTextViewName = findViewById(R.id.text_patienthome_name);
        TextView mTextViewAppointment = findViewById(R.id.text_upcoming_appointment);
        TextView mTextViewBalance = findViewById(R.id.text_account_balance);

        Button mBtnLocateDoctor = findViewById(R.id.btn_locate_doctor);
        Button mBtnProfile = findViewById(R.id.btn_account_profile);

        //Activity Logic

        //TODO get user from database, set full name
        String fullName = "Full" + " " + "Name";

        //TODO get user appointment from database, if any
        String upcomingAppointment = "Date";

        //TODO get user balance from database
        String accountBalance = "$" + "Amount";


        //Set Component Text
        mTextViewName.setText(fullName);
        mTextViewAppointment.setText(upcomingAppointment);
        mTextViewBalance.setText(accountBalance);


        //UI Event Listeners
        mBtnLocateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Create new intent for LocateDoctorActivity.Class
                //Intent intent = new Intent(PatientHomeActivity.this,);
                //startActivity(intent);
            }
        });

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, AccountProfileActivity.class);
                startActivity(intent);
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
                Intent h= new Intent(PatientHomeActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(PatientHomeActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(PatientHomeActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
