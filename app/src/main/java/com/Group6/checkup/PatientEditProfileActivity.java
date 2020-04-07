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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

public class PatientEditProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    // Private Attributes
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    private Session appSession;
    private Patient currentUser;
    private PatientDao patientDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_profile);
        appSession = new Session(this);
        patientDao = new PatientDao(this);
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
        final EditText mEditLastName = findViewById(R.id.edit_last_name);
        final EditText mEditAddress = findViewById(R.id.edit_address);

        Button mBtnConfirmChanges = findViewById(R.id.btn_edit_profile_confirm);

        //Activity Logic
        currentUser = patientDao.find(appSession.getCurrentUsername());

        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();

        mTextViewName.setText(fullName);
        mTextViewAddress.setText(currentUser.getAddress());
        mTextViewMSP.setText(String.valueOf(currentUser.getHealthCareCardNumber()));

        mEditFirstName.setText(currentUser.getFirstName());
        mEditLastName.setText(currentUser.getLastName());
        mEditAddress.setText(currentUser.getAddress());

        //UI Event Listeners
        mBtnConfirmChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.setFirstName(mEditFirstName.getText().toString());
                currentUser.setLastName(mEditLastName.getText().toString());
                currentUser.setAddress(mEditAddress.getText().toString());
                if(patientDao.update(currentUser)){
                    Toast.makeText(PatientEditProfileActivity.this, "Profile Edited", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PatientEditProfileActivity.this,AccountProfileActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientEditProfileActivity.this, "Unable to Edit", Toast.LENGTH_SHORT).show();
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
