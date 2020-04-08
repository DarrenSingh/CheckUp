package com.Group6.checkup.EditAndUpdateAccountPackage;

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
import com.Group6.checkup.AdminViewHistoryActivity;
import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class AdminAccountEditActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnEditAccount, btnDeleteAccount;
    EditText etFirstName, etLastName, etLoginID, etPassword;
    String firstName, lastName, loginID, password, rowID, enteredLoginID;
    Intent getIntent;
    AdminDao adminDao;
    Admin adminAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Edit Admin Account");

        etFirstName = findViewById(R.id.editTxt_editAdminFirstName);
        etLastName = findViewById(R.id.editTxt_editAdminLastName);
        etLoginID = findViewById(R.id.editTxt_editAdminLoginID);
        etPassword = findViewById(R.id.editTxt_editAdminPassword);

        btnEditAccount = findViewById(R.id.btn_editAdminAccount);
        btnDeleteAccount = findViewById(R.id.btn_deleteAdminAccount);

        getIntent = getIntent();
        adminDao = new AdminDao(this);


        loginID = getIntent.getStringExtra("loginID");

        adminAccount = adminDao.find(loginID);

        etFirstName.setText(adminAccount.getFirstName());
        etLastName.setText(adminAccount.getLastName());
        etLoginID.setText(adminAccount.getLoginID());
        etPassword.setText(adminAccount.getPassword());

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
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
                    adminAccount.setFirstName(etFirstName.getText().toString());
                    adminAccount.setLastName(etLastName.getText().toString());
                    adminAccount.setLoginID(etLoginID.getText().toString());
                    adminAccount.setPassword(etPassword.getText().toString());

                    //Edit loginID validation
                    if (loginID.charAt(0) != 'A') {
                        etLoginID.setError("Admin Account has to start with letter 'A'.");

                        if (adminDao.exists(etLoginID.getText().toString())) {
                            etLoginID.setError("Login ID is already exists");
                        }

                    } else {
                        //update database
                        if (adminDao.update(adminAccount)) {
                            Toast.makeText(AdminAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AdminAccountEditActivity.this, EditAndUpdateAccountSearchActivity.class));
                        } else {
                            Toast.makeText(AdminAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adminDao.delete(loginID)) {
                    Toast.makeText(AdminAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AdminAccountEditActivity.this,EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(AdminAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
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
                Intent h= new Intent(AdminAccountEditActivity.this, AdminHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(AdminAccountEditActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(AdminAccountEditActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
