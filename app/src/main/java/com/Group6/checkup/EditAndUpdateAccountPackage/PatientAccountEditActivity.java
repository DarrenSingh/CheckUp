package com.Group6.checkup.EditAndUpdateAccountPackage;

import android.content.Intent;
import android.os.Bundle;
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

import com.Group6.checkup.AdminHomeActivity;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.LoginActivity;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.AccountValidation;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Adapters.AdminViewHistoryActivity;
import com.google.android.material.navigation.NavigationView;

public class PatientAccountEditActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Button btnEditAccount, btnDeleteAccount;
    Spinner spinnerYesOrNo;
    EditText etFirstName, etLastName, etAddress, etPhoneNumber, etEmail, etLoginID, etPassword, etHealthCardNumber;
    String firstName, lastName, address, phoneNumber, email, loginID, password, rowID, enteredLoginID, msp;
    boolean mspStatus;
    int healthCardNumber;
    String[] yesOrNo = {"yes", "no"};
    Intent getIntent;
    PatientDao patientDao;
    Patient patientAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_patient_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Edit Patient Account");


        btnEditAccount = findViewById(R.id.btn_editPatientAccount);
        btnDeleteAccount = findViewById(R.id.btn_deletePatientAccount);

        spinnerYesOrNo = findViewById(R.id.spinner_editPatientMSPStatus);

        etFirstName = findViewById(R.id.editTxt_editPatientFirstName);
        etLastName = findViewById(R.id.editTxt_editPatientLastName);
        etAddress = findViewById(R.id.editTxt_editPatientAddress);
        etPhoneNumber = findViewById(R.id.editTxt_editPatientPhoneNumber);
        etEmail = findViewById(R.id.editTxt_editPatientEmail);
        etLoginID = findViewById(R.id.editTxt_editPatientLoginID);
        etPassword = findViewById(R.id.editTxt_editPatientPassword);
        etHealthCardNumber = findViewById(R.id.editTxt_editPatientHealthCardNumber);

        getIntent = getIntent();
        patientDao = new PatientDao(this);

        loginID = getIntent.getStringExtra("loginID");

        patientAccount = patientDao.find(loginID);

        etFirstName.setText(patientAccount.getFirstName());
        etLastName.setText(patientAccount.getLastName());
        etAddress.setText(patientAccount.getAddress());
        etPhoneNumber.setText(patientAccount.getPhoneNumber());
        etEmail.setText(patientAccount.getEmailAddress());
        etLoginID.setText(patientAccount.getLoginID());
        etPassword.setText(patientAccount.getPassword());
        mspStatus = patientAccount.getMspStatus();
        etHealthCardNumber.setText(String.valueOf(patientAccount.getHealthCareCardNumber()));

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, yesOrNo);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYesOrNo.setAdapter(adapter);
        if(mspStatus == true){
            spinnerYesOrNo.setSelection(0);
        }else{
            spinnerYesOrNo.setSelection(1);
        }
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
                    healthCardNumber = 0;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getBaseContext(), "Please choose MSP Status", Toast.LENGTH_SHORT).show();
            }
        });

        btnEditAccount.setOnClickListener(new View.OnClickListener() {
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
                if (AccountValidation.isEmpty(etHealthCardNumber)){
                    etHealthCardNumber.setError("This field is required");
                }
                if(mspStatus == true && AccountValidation.isEmpty(etHealthCardNumber)){
                    etHealthCardNumber.setError("This field is required");
                }
                if (!AccountValidation.isEmpty(etFirstName) && !AccountValidation.isEmpty(etLastName) && !AccountValidation.isEmpty(etLoginID) && !AccountValidation.isEmpty((etPassword)) &&
                        AccountValidation.nameValidation(etFirstName) && AccountValidation.nameValidation(etLastName) && !AccountValidation.isEmpty(etAddress) && !AccountValidation.isEmpty(etPhoneNumber) &&
                        !AccountValidation.isEmpty(etEmail) && AccountValidation.emailValidation(etEmail) && ((mspStatus == true  && !AccountValidation.isEmpty(etHealthCardNumber)) || mspStatus == false)) {

                    firstName = etFirstName.getText().toString();
                    lastName = etLastName.getText().toString();
                    address = etAddress.getText().toString();
                    phoneNumber = etPhoneNumber.getText().toString();
                    email = etEmail.getText().toString();
                    enteredLoginID = etLoginID.getText().toString();
                    password = etPassword.getText().toString();
                    // set healthcare number to null if no value input
                    healthCardNumber = (etHealthCardNumber.getText().toString().compareTo("") == 0)
                            ? 0 : Integer.parseInt(etHealthCardNumber.getText().toString());
                    mspStatus = (spinnerYesOrNo.getSelectedItem().toString() == "yes");

                    patientAccount.setFirstName(firstName);
                    patientAccount.setLastName(lastName);
                    patientAccount.setAddress(address);
                    patientAccount.setPhoneNumber(phoneNumber);
                    patientAccount.setEmailAddress(email);
                    patientAccount.setLoginID(loginID);
                    patientAccount.setPassword(password);
                    patientAccount.setHealthCareCardNumber(healthCardNumber);
                    patientAccount.setMspStatus(mspStatus);


                    //Edit loginID validation
                    if (loginID.charAt(0) != 'P') {
                        etLoginID.setError("Patient Account has to start with letter 'P'.");

                        if (patientDao.exists(loginID)) {
                            etLoginID.setError("Login ID is already exists");
                        }
                    } else {
                        //update database
                        if (patientDao.update(patientAccount)) {
                            Toast.makeText(PatientAccountEditActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PatientAccountEditActivity.this, EditAndUpdateAccountSearchActivity.class));
                        } else {
                            Toast.makeText(PatientAccountEditActivity.this, "Unable to Update Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        //Delete button
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (patientDao.delete(loginID)) {
                    Toast.makeText(PatientAccountEditActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PatientAccountEditActivity.this, EditAndUpdateAccountSearchActivity.class));
                } else {
                    Toast.makeText(PatientAccountEditActivity.this, "Unable to Delete Account", Toast.LENGTH_SHORT).show();
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
                Intent h= new Intent(PatientAccountEditActivity.this, AdminHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(PatientAccountEditActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(PatientAccountEditActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
