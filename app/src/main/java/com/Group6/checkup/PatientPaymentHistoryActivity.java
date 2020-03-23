package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientPaymentHistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment_history);

        //TODO com.Group6.checkup.Patient payment history logic

        //UI Components
        Button mButtonMakePayment = findViewById(R.id.btn_history_make_payment);

        // UI Event Listeners
        mButtonMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientPaymentHistoryActivity.this,SubmitPaymentActivity.class));
            }
        });


    }
}
