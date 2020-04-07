package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitPaymentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Utils.Dao.InvoiceDao;

import java.text.DecimalFormat;

public class SubmitPaymentActivity extends AppCompatActivity {

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    EditText mEditPaymentAmount;
    TextView mTextViewBalance;
    Invoice currentInvoice;
    InvoiceDao invoiceDao;
    DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_payment);
        invoiceDao = new InvoiceDao(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();
        this.setTitle("Payment Process");

        //UI Components
        mTextViewBalance = findViewById(R.id.text_payment_owing);
        mEditPaymentAmount = findViewById(R.id.edit_payment_amount);
        Button mBtnPayNow = findViewById(R.id.btn_pay_now);
        Button mBtnPayLater = findViewById(R.id.btn_pay_later);

        decimalFormat = new DecimalFormat("###.##");

        //get intent
        Intent intent = getIntent();

        //check the intent for a invoice id value that was passed
        if(intent.hasExtra("invoiceId")){

            //get the invoice from the database
            currentInvoice = invoiceDao.find(intent.getStringExtra("invoiceId"));

            //set textview to outstanding balance
            String amount = "$" + decimalFormat.format(currentInvoice.getPrice());
            mTextViewBalance.setText(amount);

        }

        //UI Event Listeners
        mBtnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //catch if mEditPaymentAmount value throws NumberFormatException
                try{

                    //try to parse EditText text into a float
                    Double paymentAmount = Double.parseDouble(mEditPaymentAmount.getText().toString());

                    //check if the value is greater than 0
                    if(Double.parseDouble(mEditPaymentAmount.getText().toString()) <= 0.00){

                        //Notify user payment must be greater than 0
                        Toast.makeText(SubmitPaymentActivity.this, "Amount must be more than $0.00", Toast.LENGTH_SHORT).show();
                    } else if(Math.round(Double.parseDouble(mEditPaymentAmount.getText().toString())) > Math.round(currentInvoice.getPrice())) {
                        Toast.makeText(SubmitPaymentActivity.this, "Amount cannot be greater than " + currentInvoice.getPrice(), Toast.LENGTH_SHORT).show();
                    } else {

                        //Notify user of the payment acceptance
                        Toast.makeText(SubmitPaymentActivity.this, "Payment of "+paymentAmount.toString()+" Processed", Toast.LENGTH_SHORT).show();

                        double newBalance = currentInvoice.getPrice() - paymentAmount;

                        if(newBalance <= 0.00){
                            newBalance = 0.00F;
                            currentInvoice.setPaymentStatus("paid");
                        }

                        currentInvoice.setPrice(newBalance);
                        invoiceDao.update(currentInvoice);

                        mTextViewBalance.setText("$" + decimalFormat.format(currentInvoice.getPrice()));

                        Toast.makeText(SubmitPaymentActivity.this, "$"+paymentAmount+" Paid", Toast.LENGTH_SHORT).show();

                        //define home activity intent
                        Intent intent = new Intent(SubmitPaymentActivity.this,PatientHomeActivity.class);
                        //start activity
                        startActivity(intent);

                    }

                }catch(NumberFormatException e){
                    // Notify user payment amount must be entered
                    Toast.makeText(SubmitPaymentActivity.this, "Enter a Payment Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mBtnPayLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //define home activity intent
                Intent intent = new Intent(SubmitPaymentActivity.this, PatientHomeActivity.class);
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
                Intent h= new Intent(SubmitPaymentActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(SubmitPaymentActivity.this,ViewUserHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(SubmitPaymentActivity.this,loginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
