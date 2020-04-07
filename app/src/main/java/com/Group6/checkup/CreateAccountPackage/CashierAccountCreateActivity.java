package com.Group6.checkup.CreateAccountPackage;

import android.content.Intent;
import android.os.Bundle;

import com.Group6.checkup.AdminActivity;
import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.CashierDao;
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

public class CashierAccountCreateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier_account_create);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Create Cashier Account");

        etFirstName = findViewById(R.id.editTxt_cashierFirstName);
        etLastName = findViewById(R.id.editTxt_cashierLastName);
        etLoginID = findViewById(R.id.editTxt_cashierLoginID);
        etPassword = findViewById(R.id.editTxt_cashierPassword);

        btnCreateAccount = findViewById(R.id.btn_createCashierAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CashierDao cashierDao = new CashierDao(getApplicationContext());

                //LoginID Validation
                if (etLoginID.getText().toString().charAt(0) != 'C') {
                    etLoginID.setError("Patient Account has to start with letter 'C'.");
                } else {

                    if (cashierDao.exists(etLoginID.getText().toString())) {
                        etLoginID.setError("Login Id is already exists");
                    } else {

                        //TODO insert adminID from session info
                        //creating new Cashier account object.
                        Cashier newCashierAccount = new Cashier(
                                etFirstName.getText().toString(),
                                etLastName.getText().toString(),
                                etLoginID.getText().toString(),
                                etPassword.getText().toString(),
                                new Session(getApplicationContext()).getUserId()
                        );

                        //Insert to Database
                        boolean inserted = cashierDao.insert(newCashierAccount);

                        //Give (un)successful prompt
                        if (inserted)
                            Toast.makeText(CashierAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(CashierAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

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
                Intent h= new Intent(CashierAccountCreateActivity.this, AdminActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(CashierAccountCreateActivity.this, ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(CashierAccountCreateActivity.this, loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
