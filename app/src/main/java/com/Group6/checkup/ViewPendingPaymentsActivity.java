package com.Group6.checkup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Adapters.OverdueInvoicesAdapter;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewPendingPaymentsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    private InvoiceDao invoiceDao;
    private PatientDao patientDao;
    List<Invoice> invoices;
    List<HashMap<String, String>> overdueInvoicesData;
    ListView listView;
    OverdueInvoicesAdapter invoiceAdapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_payments);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        this.setTitle("Pending Payments");

        //initialize daos
        invoiceDao = new InvoiceDao(this);
        patientDao = new PatientDao(this);


        //get all invoices
        invoices = invoiceDao.findAll();

        //create new array for adapter
        overdueInvoicesData = new ArrayList<>();

        //loop through
        for (int j = 0; j < invoices.size(); j++) {

            if (invoices.get(j).getPaymentStatus().equals("unpaid")) {

                HashMap<String, String> map = new HashMap<>();

                Invoice invoice = invoices.get(j);
                Patient associatedPatient = patientDao.findById(String.valueOf(invoice.getPatientID()));

                map.put("patientName", associatedPatient.getFirstName()
                        + " "
                        + associatedPatient.getLastName()
                );

                map.put("invoiceId", String.valueOf(invoice.getID()));

                overdueInvoicesData.add(map);
            }
        }

        listView = findViewById(R.id.list_view);

        String[] from = {"patientName"};
        int[] to = {R.id.text_overdue_patient};

        invoiceAdapter = new OverdueInvoicesAdapter(this, overdueInvoicesData, R.layout.card_view, from, to);

        listView.setAdapter(invoiceAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                listView.getChildAt(position).setBackgroundColor(Color.LTGRAY);

                //create intent
                intent = new Intent(ViewPendingPaymentsActivity.this, PaymentMessageActivity.class);

                //pass values to intent from list item to intent
                intent.putExtra("invoiceId", String.valueOf(invoiceAdapter.data.get(position).get("invoiceId")));
                intent.putExtra("patientName", String.valueOf(invoiceAdapter.data.get(position).get("patientName")));

                //start activity
                startActivity(intent);
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
                Intent h = new Intent(ViewPendingPaymentsActivity.this, CashierHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_logout:
                Intent is = new Intent(ViewPendingPaymentsActivity.this, LoginActivity.class);
                startActivity(is);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
