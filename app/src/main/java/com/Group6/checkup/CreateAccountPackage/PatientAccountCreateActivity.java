package com.Group6.checkup.CreateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.AdminActivity;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.LoginActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;
import com.Group6.checkup.AdminViewHistoryActivity;
import com.google.android.material.navigation.NavigationView;

public class PatientAccountCreateActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnCreateAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String msp;
    boolean mspStatus;
    int healthCardNumber = 0;
    String[] yesOrNo = {"yes", "no"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_account_create);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Create Patient Account");

        btnCreateAccount = findViewById(R.id.btn_createPatientAccount);
        spinnerYesOrNo = findViewById(R.id.spinner_patientMSPStatus);

        etFirstName = findViewById(R.id.editTxt_patientFirstName);
        etLastName = findViewById(R.id.editTxt_patientLastName);
        etAddress = findViewById(R.id.editTxt_patientAddress);
        etPhoneNumber = findViewById(R.id.editTxt_patientPhoneNumber);
        etEmail = findViewById(R.id.editTxt_patientEmail);
        etLoginID = findViewById(R.id.editTxt_patientLoginID);
        etPassword = findViewById(R.id.editTxt_patientPassword);
        etHealthCardNumber = findViewById(R.id.editTxt_patientHealthCardNumber);

        Log.e("HealthCareNumber", etHealthCardNumber.getText().toString());

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, yesOrNo);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYesOrNo.setAdapter(adapter);


        spinnerYesOrNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    etHealthCardNumber.setHint("Health Card Number");
                    etHealthCardNumber.setEnabled(true);
                } else if (position == 1) {
                    etHealthCardNumber.setHint("N/A");
                    etHealthCardNumber.setText("");
                    etHealthCardNumber.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please choose MSP Status", Toast.LENGTH_SHORT).show();
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                msp = spinnerYesOrNo.getSelectedItem().toString();

                if (msp.equals("yes")) {
                    mspStatus = true;
                } else {
                    mspStatus = false;
                }

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
                if (AccountValidation.isEmpty(etAddress)) {
                    etAddress.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etPhoneNumber)) {
                    etPhoneNumber.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etEmail)) {
                    etEmail.setError("This field is required");
                } else {
                    if (!AccountValidation.emailValidation((etEmail))) {
                        etEmail.setError("Invalid input");
                    }
                }
                if (AccountValidation.isEmpty(etLoginID)) {
                    etLoginID.setError("This field is required");
                }
                if (AccountValidation.isEmpty(etPassword)) {
                    etPassword.setError("This field is required");
                }
                if(mspStatus == true && AccountValidation.isEmpty(etHealthCardNumber)){
                        etHealthCardNumber.setError("This field is required");
                }

                if (!AccountValidation.isEmpty(etFirstName) && !AccountValidation.isEmpty(etLastName) && !AccountValidation.isEmpty(etLoginID) && !AccountValidation.isEmpty((etPassword)) &&
                        AccountValidation.nameValidation(etFirstName) && AccountValidation.nameValidation(etLastName) && !AccountValidation.isEmpty(etAddress) && !AccountValidation.isEmpty(etPhoneNumber) &&
                        !AccountValidation.isEmpty(etEmail) && AccountValidation.emailValidation(etEmail) && ((mspStatus == true  && !AccountValidation.isEmpty(etHealthCardNumber)) || mspStatus == false)) {

                    //Searching and finding the data that has the same loginID.
                    if (etLoginID.getText().toString().charAt(0) != 'P') {
                        etLoginID.setError("Patient Account has to start with letter 'P'.");
                    } else {

                        PatientDao patientDao = new PatientDao(getApplicationContext());

                        //LoginID existence validation
                        if (patientDao.exists(etLoginID.getText().toString())) {
                            etLoginID.setError("Login Id is already exists");
                        } else {

                            healthCardNumber = (etHealthCardNumber.getText().toString().compareTo("") == 0)
                                    ? 0 : Integer.parseInt(etHealthCardNumber.getText().toString());

                            //Creating patient Object.
                            Patient newPatient = new Patient(
                                    etFirstName.getText().toString(),
                                    etLastName.getText().toString(),
                                    etAddress.getText().toString(),
                                    etLoginID.getText().toString(),
                                    etPassword.getText().toString(),
                                    mspStatus,
                                    etPhoneNumber.getText().toString(),
                                    healthCardNumber,
                                    etEmail.getText().toString(),
                                    new Session(getApplicationContext()).getUserId()
                            );

                            //Insert to Database;
                            boolean inserted = patientDao.insert(newPatient);

                            //Give (un)successful prompt
                            if (inserted) {
                                Toast.makeText(PatientAccountCreateActivity.this, etLoginID.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getBaseContext(), AccountTypeOptionActivity.class));
                            } else
                                Toast.makeText(PatientAccountCreateActivity.this, "Unable to Create Account", Toast.LENGTH_SHORT).show();

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
                Intent h= new Intent(PatientAccountCreateActivity.this, AdminActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(PatientAccountCreateActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(PatientAccountCreateActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
