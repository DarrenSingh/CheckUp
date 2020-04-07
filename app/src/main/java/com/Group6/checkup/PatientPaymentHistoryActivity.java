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
import androidx.drawerlayout.widget.DrawerLayout;

import android.widget.Button;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Session;
import com.google.android.material.navigation.NavigationView;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PatientPaymentHistoryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Session appSession;
    private InvoiceDao invoiceDao;
    private List<Invoice> invoiceList;
    private TextView mTextViewOwing;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment_history);
        appSession = new Session(this);
        invoiceDao = new InvoiceDao(this);
        invoiceList = new ArrayList<>();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Payment History");

        //UI Components
        mTextViewOwing = findViewById(R.id.text_history_owing);
        ListView mListView = findViewById(R.id.list_invoice_history);

        //get all invoices by patient id
        invoiceList = invoiceDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        populateListView(invoiceList, mListView,mTextViewOwing);

    }

    private void populateListView(List<Invoice> invoiceList, ListView listView, TextView mTextViewOwing){


        List<HashMap<String,String>> inboxData = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        double owingBalance = 0.00;

        for (int i = 0; i < invoiceList.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            Date date = new Date(invoiceList.get(i).getInvoiceDate());
            // format of the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = dateFormat.format(date);


            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("date",formattedDate);
            hashMap.put("amount", "$"+decimalFormat.format(invoiceList.get(i).getPrice()));
            hashMap.put("status", (invoiceList.get(i).getPaymentStatus()));
            hashMap.put("id", (String.valueOf(invoiceList.get(i).getID())));

            // add this hashmap to the list
            inboxData.add(hashMap);
            owingBalance += invoiceList.get(i).getPrice();
        }

        mTextViewOwing.setText("$"+decimalFormat.format(owingBalance));


        String[] from = {
                "date",
                "amount",
                "status",
                "id"
        };

        int[] to = {R.id.text_payment_date,R.id.text_payment_amount,R.id.text_payment_type,R.id.hidden_history_invoice_id};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,R.layout.item_payment_history,from,to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView invoiceId = view.findViewById(R.id.hidden_history_invoice_id);

                Intent intent = new Intent(PatientPaymentHistoryActivity.this,SubmitPaymentActivity.class);
                intent.putExtra("invoiceId",invoiceId.getText().toString());
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
                Intent h= new Intent(PatientPaymentHistoryActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(PatientPaymentHistoryActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(PatientPaymentHistoryActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
