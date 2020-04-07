package com.Group6.checkup.CreateAccountPackage;

import android.content.Intent;
import android.os.Bundle;

import com.Group6.checkup.AdminActivity;
import com.Group6.checkup.R;
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

public class AccountTypeOptionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_type_option);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Accounts");

    }

    //Button click move to corresponding activities
    public void buttonClicked(View view) {
        if (view.getId() == R.id.btn_toAdminAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, AdminAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toPatientAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, PatientAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toDoctorAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, DoctorAccountCreateActivity.class));
        } else if (view.getId() == R.id.btn_toCashierAccountCreateActivity) {
            startActivity(new Intent(AccountTypeOptionActivity.this, CashierAccountCreateActivity.class));
        }
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
                Intent h= new Intent(AccountTypeOptionActivity.this, AdminActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(AccountTypeOptionActivity.this, ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(AccountTypeOptionActivity.this, loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
