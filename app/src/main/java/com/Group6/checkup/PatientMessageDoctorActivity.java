package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

public class PatientMessageDoctorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private Session appSession;
    private OnlineHelpDao onlineHelpDao;
    private OnlineHelp onlineHelp;
    int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_message_doctor);
        appSession = new Session(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Message");

        //UI Components
        TextView mTextViewRecipient = findViewById(R.id.text_recipient_name);
        final EditText mEditSubject = findViewById(R.id.edit_send_subject);
        final EditText mEditMessage = findViewById(R.id.edit_send_message);
        Button mBtnSend = findViewById(R.id.btn_send_message);

        //Activity Logic
        Intent getIntent = getIntent();
        //pass intent with doctorID from other activity
        doctorId = Integer.parseInt(getIntent.getStringExtra("doctorId"));
        mTextViewRecipient.setText(getIntent.getStringExtra("doctorName"));

        //UI Event Listeners
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onlineHelpDao = new OnlineHelpDao(getApplicationContext());

                //create new msg object
                onlineHelp = new OnlineHelp(
                        mEditSubject.getText().toString(),
                        mEditMessage.getText().toString(),
                        System.currentTimeMillis(),
                        appSession.getUserId(),
                        doctorId
                );

                //make message entry in database using the message object
                boolean result = onlineHelpDao.insert(onlineHelp);

                //display success message
                if (result) {
                    Toast.makeText(PatientMessageDoctorActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    //set msg attributes using UI Component values
                    startActivity(new Intent(PatientMessageDoctorActivity.this, PatientHomeActivity.class));

                } else {
                    Toast.makeText(PatientMessageDoctorActivity.this, "Unable to sent message", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void toggleSetUp() {
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
        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:
                Intent h = new Intent(PatientMessageDoctorActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g = new Intent(PatientMessageDoctorActivity.this, PatientAppointmentHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s = new Intent(PatientMessageDoctorActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
