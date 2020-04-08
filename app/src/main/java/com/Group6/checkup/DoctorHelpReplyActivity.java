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

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

public class DoctorHelpReplyActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    DoctorDao doctorDao;
    Doctor currentUser;
    Session appSession;
    OnlineHelpDao onlineHelpDao;
    OnlineHelp onlineHelp;
    OnlineHelpReplyDao onlineHelpReplyDao;
    OnlineHelpReply onlineHelpReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_help_reply);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Patient Message");

        appSession = new Session(this);
        doctorDao = new DoctorDao(this);
        onlineHelpDao = new OnlineHelpDao(this);
        onlineHelpReplyDao = new OnlineHelpReplyDao(this);
        currentUser = doctorDao.find(appSession.getCurrentUsername());

        final TextView patient_name = findViewById(R.id.patient_name);
        TextView title = findViewById(R.id.message_title);
        TextView message = findViewById(R.id.patient_message);
        TextView headerSolution = findViewById(R.id.textView12);
        EditText reply = findViewById(R.id.editTextDoctorSolution);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonPostSolution = findViewById(R.id.buttonPostSolution);

        Intent intent = getIntent();
        if (intent != null) {
            String messageId = intent.getStringExtra("messageId");
            String name = intent.getStringExtra("name");

            //grab patient from previous activity and setText
            patient_name.setText(name);

            //get message
            onlineHelp = onlineHelpDao.find(messageId);
        }

        //set texts from message
        title.setText(onlineHelp.getMessageTitle());
        message.setText(onlineHelp.getMessage());

        if (onlineHelp.getOnlineHelpReplyID() > 0) {

            onlineHelpReply = onlineHelpReplyDao.find(
                    String.valueOf(onlineHelp.getOnlineHelpReplyID()));

            headerSolution.setText(R.string.text_solution_posted);

            reply.setText(onlineHelpReply.getMessageContent());

            reply.setEnabled(false);
            buttonPostSolution.setEnabled(false);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorHelpReplyActivity.this, DoctorMessages.class));
            }
        });

        buttonPostSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorReply = reply.getText().toString();
                String empty = "";

                //validation for the doctor's reply
                if (doctorReply.equals(empty)) {
                    Toast.makeText(DoctorHelpReplyActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {

                    try {

                        onlineHelpReply = new OnlineHelpReply(
                                "REPLY:" + onlineHelp.getMessageTitle(),
                                doctorReply,
                                System.currentTimeMillis(),
                                appSession.getUserId()
                        );

                        int result = onlineHelpReplyDao.insertWithResult(onlineHelpReply);

                        if (result == -1) {
                            throw new Exception("Unable to insert the record into the database");
                        }

                        //update the onlinehelp item in the database with the new reply foreign key
                        onlineHelp.setOnlineHelpReplyID(result);

                        boolean updated = onlineHelpDao.update(onlineHelp);

                        if (!updated) {
                            throw new Exception("Unable update foreign key value" + result + " for reply at row " + onlineHelp.getID());
                        }

                        startActivity(new Intent(DoctorHelpReplyActivity.this, DoctorMessages.class));

                        Toast.makeText(DoctorHelpReplyActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {

                        Toast.makeText(DoctorHelpReplyActivity.this, "Unable to send message", Toast.LENGTH_SHORT).show();
                    }

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
                Intent h = new Intent(DoctorHelpReplyActivity.this, DoctorHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g = new Intent(DoctorHelpReplyActivity.this, AdminViewHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s = new Intent(DoctorHelpReplyActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
