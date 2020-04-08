package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAppointmentScheduleActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment_schedule);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Appointment schedule");

        PatientDao dao = new PatientDao(this);
        AppointmentDao adao = new AppointmentDao(this);

        List<Appointment> doctorAppointments = adao.findAllByDoctor(String.valueOf(new Session(this).getUserId()));

        //create list<Map>
        List<Map<String, String>> appointmentData = new ArrayList<>();

        for (int i = 0; i < doctorAppointments.size(); i++) {

            Map<String, String> map = new HashMap<>();

            Patient appointmentOwner = dao.findById(String.valueOf(doctorAppointments.get(i).getPatientID()));
            String patientName = appointmentOwner.getFirstName() + " " + appointmentOwner.getLastName();

            Date date = new Date(doctorAppointments.get(i).getAppointmentDateTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd YYYY h:mm a");

            map.put("patient", patientName);
            map.put("time", dateFormat.format(date));

            appointmentData.add(map);
        }

        String[] from = {"patient", "time"};
        int[] to = {R.id.text_appointment_patient, R.id.text_appointment_time};

        SimpleAdapter adapter = new SimpleAdapter(this, appointmentData, R.layout.item_doctor_appointment, from, to);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //cycle through appointments
        //create hashmap
        //put patient name
        //put appointment time
        //create the adapter
        //set adapter

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
                Intent h = new Intent(DoctorAppointmentScheduleActivity.this, DoctorHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_logout:
                Intent s = new Intent(DoctorAppointmentScheduleActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
