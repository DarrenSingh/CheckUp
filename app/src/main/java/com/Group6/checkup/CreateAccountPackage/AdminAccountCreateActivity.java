package com.Group6.checkup.CreateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.AdminHomeActivity;
import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.LoginActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Adapters.AdminViewHistoryActivity;
import com.google.android.material.navigation.NavigationView;
public class AdminAccountCreateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnCreateAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_account_create);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Create Admin Account");

        etFirstName = findViewById(R.id.editTxt_adminFirstName);
        etLastName = findViewById(R.id.editTxt_adminLastName);
        etLoginID = findViewById(R.id.editTxt_adminLoginID);
        etPassword = findViewById(R.id.editTxt_adminPassword);

        btnCreateAccount = findViewById(R.id.btn_createAdminAccount);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (AccountValidation.isEmpty(etFirstName)) {
                    etFirstName.setError("This field is required");
                } else {
                    if (AccountValidation.nameValidation(etFirstName) == false) {
                        etFirstName.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etLastName)) {
                    etLastName.setError("This field is required");
                } else {
                    if (AccountValidation.nameValidation(etLastName) == false) {
                        etLastName.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etLoginID)) {
                    etLoginID.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etPassword)) {
                    etPassword.setError("This field is required");
                }
                if (!AccountValidation.isEmpty(etFirstName) && !AccountValidation.isEmpty(etLastName) && !AccountValidation.isEmpty(etLoginID) && !AccountValidation.isEmpty((etPassword)) && AccountValidation.nameValidation(etFirstName) && AccountValidation.nameValidation(etLastName)) {
                    //Creating account search DAO to search and fetching matching data and save to Array list.
                    AdminDao adminDao = new AdminDao(getApplicationContext());

                    char firstChar = etLoginID.getText().toString().charAt(0);

                    //LoginID Validation
                    if (firstChar != 'A') {
                        etLoginID.setError("Admin Account has to start with letter 'A'.");
                    } else {

                        //LoginID existence validation
                        if (adminDao.exists(etLoginID.getText().toString())) {

                            Toast.makeText(AdminAccountCreateActivity.this, "Login Id is already exists", Toast.LENGTH_SHORT).show();

                        } else {

                            //insert user into database
                            boolean inserted = adminDao.insert(
                                    new Admin(
                                            etFirstName.getText().toString(),
                                            etLastName.getText().toString(),
                                            etLoginID.getText().toString(),
                                            etPassword.getText().toString()
                                    )
                            );

                            //Give (un)successful prompt
                            if (inserted) {
                                Toast.makeText(AdminAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), AccountTypeOptionActivity.class));
                            } else
                                Toast.makeText(AdminAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

                        }
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
                Intent h= new Intent(AdminAccountCreateActivity.this, AdminHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(AdminAccountCreateActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(AdminAccountCreateActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
