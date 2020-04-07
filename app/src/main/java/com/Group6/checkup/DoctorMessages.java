package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorMessages extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    private PatientDao patientDao;
    private OnlineHelpDao onlineHelpDao;

    private PatientAdapter pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_messages);
        patientDao = new PatientDao(this);
        onlineHelpDao = new OnlineHelpDao(this);


        this.setTitle("Messages");

        ListView listView = findViewById(R.id.list_view);

        List<OnlineHelp> patientMessages = onlineHelpDao.findAllByDoctor(String.valueOf(new Session(this).getUserId()));
        setContentView(R.layout.activity_doctor_messages);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Message");

        //create list<Map>
        List<Map<String,String>> appointmentData = new ArrayList<>();

        //loop through messsages
        for (int i = 0; i < patientMessages.size() ; i++) {

            //create a hashmap
            Map<String,String> map = new HashMap<>();

            //get patient name
            Patient appointmentOwner = patientDao.findById(String.valueOf(patientMessages.get(i).getPatientID()));
            String patientName = appointmentOwner.getFirstName() + " " + appointmentOwner.getLastName();

            //place
            map.put("messageId",String.valueOf(patientMessages.get(i).getID()));
            map.put("patientName",patientName);

            appointmentData.add(map);
        }

        String[] from = {"patientName","messageId"};

        //textmessage patient stands for name, hidden message stands for message id
        int[] to = {R.id.text_message_patient_name ,R.id.hidden_message_id};

        SimpleAdapter adapter = new SimpleAdapter(this,appointmentData,R.layout.item_doctor_message,from,to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.hidden_message_id);
                TextView patientNameTextView = view.findViewById(R.id.text_message_patient_name);

                Intent in = new Intent(DoctorMessages.this,PatientComplaint.class);
                in.putExtra("messageId" ,textView.getText().toString());
                in.putExtra("name",patientNameTextView.getText().toString());
                startActivity(in);
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
                Intent h= new Intent(DoctorMessages.this, DoctorActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(DoctorMessages.this, ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(DoctorMessages.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
