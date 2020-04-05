package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Utils.Dao.InvoiceDao;

import java.text.DecimalFormat;

public class SubmitPaymentActivity extends AppCompatActivity {

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
                Intent intent = new Intent(SubmitPaymentActivity.this,PatientHomeActivity.class);
                //start activity
                startActivity(intent);
            }
        });

    }
}
