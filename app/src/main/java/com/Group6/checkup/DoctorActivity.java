package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Session;

public class DoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DoctorDao doctorDao;
    Doctor currentUser;
    Session appSession;

    //private Session s;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        appSession = new Session(this);
        doctorDao = new DoctorDao(this);


        currentUser = doctorDao.find(appSession.getCurrentUsername());
        String doctorFirstName = currentUser.getFirstName();
        String doctorLastName = currentUser.getLastName();

        //load the strings to textviews
        String FullName = "Welcome Dr. "+doctorFirstName+" "+doctorLastName;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        Button buttonViewSchedule = findViewById(R.id.buttonViewSchedule);
        Button buttonViewMessages = findViewById(R.id.buttonViewMessage);
        Button buttonReturnToLogin = findViewById(R.id.buttonReturnToLogin);
        TextView doctorName = findViewById(R.id.doctor_name);


        //setting the title "welcome user" to the name of the doctor
        doctorName.setText(FullName);

        buttonViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorMessages.class));
            }
        });

        buttonViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorAppointmentSchedule.class));
            }
        });

        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset session info
                appSession.setCurrentUsername(null);
                appSession.setUserId(0);
                Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
                //clear task back stack before and send user to login screen
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
                Intent h= new Intent(DoctorActivity.this, DoctorActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(DoctorActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(DoctorActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
