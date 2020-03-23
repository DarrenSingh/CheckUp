package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PaymentMessage extends AppCompatActivity {

    double balance = 24;
    String date = "25th Match 2020";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_message);

        final TextView patient_name = findViewById(R.id.patient_name);
        final TextView payment_message = findViewById(R.id.payment_txt);
        final EditText msg = findViewById(R.id.et_msg);
        Button btn = findViewById(R.id.btn_send);

        Intent intent = getIntent();
        if(intent != null){
            String value = intent.getStringExtra("name");
            patient_name.setText(value);
            msg.setText("Dear patient, your account is overdue $" + balance +
                    " The payment is due by " + date + '\n');
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = msg.getText().toString();
                payment_message.append(message);
            }
        });
    }
}
