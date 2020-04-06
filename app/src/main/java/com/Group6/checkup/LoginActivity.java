package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Admin;
import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AdminDao;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

public class LoginActivity extends AppCompatActivity {
    Session appSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appSession = new Session(getApplicationContext());

        final EditText editText_id = findViewById(R.id.editText_id);
        final EditText editText_password = findViewById(R.id.editText_password);
        Button button_login = findViewById(R.id.button_login);
        Button sign_up = findViewById(R.id.sign_up);

        Intent intent = getIntent();
        if(intent != null){
            String login_value = intent.getStringExtra("loginIDvalue");

            //set the ID created as a prompt in the login EditText box
            editText_id.setText(login_value);
        }


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = editText_id.getText().toString();
                String password = editText_password.getText().toString();
                String empty = "";

                if(id.equals(empty)){
                    Toast.makeText(LoginActivity.this, "Please Enter User Id",Toast.LENGTH_LONG).show();

                }else if(password.equals(empty)){
                    Toast.makeText(LoginActivity.this, "Please Enter User Password",Toast.LENGTH_LONG).show();

                }else if(id.charAt(0)=='D'||id.charAt(0)=='P'||id.charAt(0)=='A'||id.charAt(0)=='C') {

                    //logic for comparing doctor id and password with the database before logging into the doctor activity
                    if (id.charAt(0) == 'D') {
                        DoctorDao dao = new DoctorDao(LoginActivity.this);
                        if(dao.exists(editText_id.getText().toString())) {
                            Doctor user = dao.find(editText_id.getText().toString());
                            if (password.equals(user.getPassword())) {
                                appSession.setCurrentUsername(user.getLoginID());
                                appSession.setUserId(user.getID());
                                startActivity(new Intent(LoginActivity.this, DoctorActivity.class));
                            } else {
                                //code for account not existing
                                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    if (id.charAt(0) == 'P') {
                        //logic for comparing patient user Id and password to the database before logging into the patient activity
                        PatientDao pdao = new PatientDao(LoginActivity.this);
                        if (pdao.exists(editText_id.getText().toString())) {
                            Patient user = pdao.find(editText_id.getText().toString());
                            if (password.equals(user.getPassword())) {
                                appSession.setCurrentUsername(user.getLoginID());
                                appSession.setUserId(user.getID());
                                startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
                            } else {
                                //code for account not existing
                                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }
                        }

                    }


                    if (id.charAt(0) == 'A') {
                        //logic for comparing admin user Id and password to the database before logging into the admin activity
                        AdminDao adao = new AdminDao(LoginActivity.this);
                        if (adao.exists(editText_id.getText().toString())) {
                            Admin user = adao.find(editText_id.getText().toString());
                            if (password.equals(user.getPassword())) {
                                appSession.setCurrentUsername(user.getLoginID());
                                appSession.setUserId(user.getID());
                                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                            } else {
                                //code for account not existing
                                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    if (id.charAt(0) == 'C') {
                        //logic for comparing cashier user Id and password to the database before logging into the cashier activity
                        CashierDao cdao = new CashierDao(LoginActivity.this);
                        if (cdao.exists(editText_id.getText().toString())) {
                            Cashier user = cdao.find(editText_id.getText().toString());
                            if (password.equals(user.getPassword())) {
                                appSession.setCurrentUsername(user.getLoginID());
                                appSession.setUserId(user.getID());
                                startActivity(new Intent(LoginActivity.this, CashierActivity.class));
                            } else {
                                //code for account not existing
                                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Please Enter Valid User ID", Toast.LENGTH_LONG).show();
                }

            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, LoginPatientAccountCreateActivity.class));
            }
        });

    }
}

