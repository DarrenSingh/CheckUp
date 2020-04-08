package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;
    private AppointmentDao appointmentDao;
    private InvoiceDao invoiceDao;
    private List<Appointment> appointments;
    private List<Invoice> invoices;
    TextView mTextViewName;
    TextView mTextViewAppointment;
    TextView mTextViewBalance;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        appSession = new Session(this);
        patientDao = new PatientDao(this);
        appointmentDao = new AppointmentDao(this);
        invoiceDao = new InvoiceDao(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        this.setTitle("Home");

        //UI Components
        mTextViewName = findViewById(R.id.text_patienthome_name);
        mTextViewAppointment = findViewById(R.id.text_upcoming_appointment);
        mTextViewBalance = findViewById(R.id.text_account_balance);

        Button mBtnLocateDoctor = findViewById(R.id.btn_locate_doctor);
        Button mBtnProfile = findViewById(R.id.btn_account_profile);

        //Activity Logic

        //Set User Name
        currentUser = patientDao.find(appSession.getCurrentUsername());
        String fullName = currentUser.getFirstName() + " " + currentUser.getLastName();

        //Set Account Balance
        String accountBalance = getAccountBalance();

        //Set Next Appointment
        String nextAppointment = getNextAppointment();

        //Set Component Text
        mTextViewAppointment.setText(nextAppointment);
        mTextViewName.setText(fullName);
        mTextViewBalance.setText(accountBalance);


        //UI Event Listeners
        mBtnLocateDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, LocateDoctorActivity.class);
                startActivity(intent);
            }
        });

        mBtnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientHomeActivity.this, AccountProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //get latest data form database
        mTextViewAppointment.setText(getNextAppointment());
        mTextViewBalance.setText(getAccountBalance());
    }

    private String getAccountBalance() {
        //Set Account Balance
        invoices = invoiceDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        double balanceOwing = 0.00;

        for (int i = 0; i < invoices.size(); i++) {
            balanceOwing += invoices.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        String accountBalance = "$" + decimalFormat.format(balanceOwing);

        return accountBalance;
    }

    private String getNextAppointment() {

        String upcomingAppointment;

        try {

            appointments = appointmentDao.findAllByPatient(AppointmentDao.ASC, String.valueOf(appSession.getUserId()));
            Date date = new Date(appointments.get(0).getAppointmentDateTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMMM dd, YYYY @ h:mm a");
            upcomingAppointment = dateFormat.format(date);

        } catch (Exception e) {
            upcomingAppointment = "No upcoming appointments";
            e.printStackTrace();
        }

        return upcomingAppointment;
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
                Intent h = new Intent(PatientHomeActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g = new Intent(PatientHomeActivity.this, PatientAppointmentHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s = new Intent(PatientHomeActivity.this, LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
