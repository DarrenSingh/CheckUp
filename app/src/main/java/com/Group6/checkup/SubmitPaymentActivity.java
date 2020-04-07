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

    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    EditText mEditPaymentAmount;
    TextView mTextViewBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_payment);
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


        //TODO SubmitPaymentActivity Logic

        //TODO make DB call for account balance
        String amountOwning = "0.00";

        //set textview to outstanding balance
        mTextViewBalance.setText("$" + amountOwning);

        //check redirected from appointment scheduling activity
        //mBtnPayLater.setVisibility(View.INVISIBLE);





        //UI Event Listeners
        mBtnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //catch if mEditPaymentAmount value throws NumberFormatException
                try{

                    //try to parse EditText text into a float
                    Float paymentAmount = Float.parseFloat(mEditPaymentAmount.getText().toString());

                    //check if the value is greater than 0
                    if(Float.parseFloat(mEditPaymentAmount.getText().toString()) <= 0){

                        //Notify user payment must be greater than 0
                        Toast.makeText(SubmitPaymentActivity.this, "Amount must be more than $0.00", Toast.LENGTH_SHORT).show();
                    } else {

                        //Notify user of the payment acceptance
                        Toast.makeText(SubmitPaymentActivity.this, "Payment of "+paymentAmount.toString()+" Processed", Toast.LENGTH_SHORT).show();
                        //TODO remove payment amount from account balance in the DB
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
                //TODO add owing amount to account balance in the DB

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
