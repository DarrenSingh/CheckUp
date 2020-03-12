package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SubmitPaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_payment);

        //UI Components
        final TextView mTextViewBalance = findViewById(R.id.text_payment_owing);
        final EditText mEditPaymentAmount = findViewById(R.id.edit_payment_amount);
        Button mBtnPayNow = findViewById(R.id.btn_pay_now);
        Button mBtnPayLater = findViewById(R.id.btn_pay_later);


        //TODO SubmitPaymentActivity Logic

        //TODO make database call for outstanding balance
        String amountOwning = "0.00";

        //set textview to outstanding balance
        mTextViewBalance.setText("$" + amountOwning);

        //check redirected from appointment scheduling activity
            //mBtnPayLater.setVisibility(View.INVISIBLE);





        //UI Event Listeners
        mBtnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float paymentAmount = Float.parseFloat(mEditPaymentAmount.getText().toString());

                //check if mEditPaymentAmount has valid input
                if( paymentAmount == null  || paymentAmount <= 0){
                    Toast.makeText(SubmitPaymentActivity.this, "Enter a Payment Amount", Toast.LENGTH_SHORT).show();
                } else{
                    //TODO dbcall update user entry for account balance

                }
            }
        });

        mBtnPayLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add owing amount database

                //define home activity intent
                Intent intent = new Intent(SubmitPaymentActivity.this,PatientHomeActivity.class);
                //start activity
                startActivity(intent);
            }
        });

    }
}
