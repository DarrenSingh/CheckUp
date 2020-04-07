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

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        this.setTitle("My Profile");


        //        appSession = new Session(this);
        //UI Components
        Button mBtnEditProfile = findViewById(R.id.btn_edit_profile);
        Button mBtnPaymentHistory = findViewById(R.id.btn_payment_history);

        final TextView mTextViewFullName = findViewById(R.id.text_patientprof_name);
        final TextView mTextViewAddress = findViewById(R.id.text_patientprof_address);
        final TextView mTextViewMSP = findViewById(R.id.text_patientprof_msp);

        ListView mListViewInbox = findViewById(R.id.list_patient_inbox);

        //Activity Logic

//        patientDao = new PatientDao(getApplicationContext());
//        user = patientDao.find(appSession.getCurrentUser());
//        mTextViewFullName.setText(user.getFirstName() + " " + user.getLastName());
//        mTextViewAddress.setText(user.getAddress());
//        mTextViewMSP.setText(String.valueOf(user.getHealthCareCardNumber()));

        //TODO ListView Hashmap
        /*
         *
         * Grab patient message objects from inbox
         *
         *
         * */
        List<HashMap<String,String>> inboxData = new ArrayList<>();

        //TODO List from adapter (hashmap keys)
        String[] from = {};

        //TODO List to adapter (R.id.* from layout file)
        //TODO create inbox list layout file
        int[] to = {};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,-1,from,to);


        //UI Event Listeners
        mBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO start activity for result?
                startActivity(new Intent(AccountProfileActivity.this,PatientEditProfileActivity.class));
            }
        });

        mBtnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountProfileActivity.this,PatientPaymentHistoryActivity.class));
            }
        });



        //TODO Inbox item onItemClickListener
        mListViewInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on item click grab
                //create intent
                //put message object
                //start activity
            }
        });

    }

    // setting the toggle for display
    public void toggleSetUp(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    //method for closing and opening the drawer
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
                Intent h= new Intent(AccountProfileActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(AccountProfileActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(AccountProfileActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
