package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;

import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Session;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;

public class CashierActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    InvoiceDao invoiceDao;
    PatientDao patientDao;
    List<Invoice> invoices;
    List<HashMap<String,String>> overdueInvoicesData;
    ListView listView;
    OverdueInvoicesAdapter invoiceAdapter;
    Intent intent;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        //initialize daos
        invoiceDao = new InvoiceDao(this);
        patientDao = new PatientDao(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();


        //get all invoices
        invoices = invoiceDao.findAll();

        //create new array for adapter
        overdueInvoicesData = new ArrayList<>();

        //loop through
        for (int j = 0; j < invoices.size() ; j++) {

            Invoice currentInvoice = invoices.get(j);

           if(currentInvoice.getPaymentStatus().equals("unpaid")
                   && currentInvoice.getPaymentDue() > System.currentTimeMillis()){

                HashMap<String,String> map = new HashMap<>();


                Patient associatedPatient = patientDao.findById(String.valueOf(currentInvoice.getPatientID()));

                map.put("patientName", associatedPatient.getFirstName()
                        + " "
                        + associatedPatient.getLastName()
                );


                map.put("invoiceId",String.valueOf(currentInvoice.getID()));

                overdueInvoicesData.add(map);
            }
        }

        listView = findViewById(R.id.list_view);

        String[] from = {"patientName"};
        int[] to = {R.id.text_overdue_patient};

        invoiceAdapter = new OverdueInvoicesAdapter(this,overdueInvoicesData,R.layout.card_view,from,to);

        listView.setAdapter(invoiceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //create intent
                intent = new Intent(CashierActivity.this,PaymentMessage.class);

                //pass values to intent from list item to intent
                intent.putExtra("invoiceId", String.valueOf(invoiceAdapter.data.get(position).get("invoiceId")));
                intent.putExtra("patientName" ,String.valueOf(invoiceAdapter.data.get(position).get("patientName")));

                //start activity
                startActivity(intent);
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
                Intent h= new Intent(CashierActivity.this, CashierActivity.class);
                startActivity(h);
                break;
            case R.id.nav_logout:
                Intent is= new Intent(CashierActivity.this,loginActivity.class);
                startActivity(is);
                break;

        }

        drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
