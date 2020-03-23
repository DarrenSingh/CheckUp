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

    EditText mEditPaymentAmount;
    TextView mTextViewBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_payment);

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
                Intent intent = new Intent(SubmitPaymentActivity.this,PatientHomeActivity.class);
                //start activity
                startActivity(intent);
            }
        });

    }
}
