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
import com.Group6.checkup.Entities.PaymentNotification;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PaymentNotificationDao;
import com.Group6.checkup.Utils.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentMessage extends AppCompatActivity {

    PaymentNotificationDao paymentDao;
    InvoiceDao invoiceDao;
    Intent intent;
    String date ;
    Session appSession;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_message);
        appSession = new Session(this);
        paymentDao = new PaymentNotificationDao(this);
        invoiceDao = new InvoiceDao(this);


        final TextView patient_name = findViewById(R.id.patient_name);
        final TextView payment_message = findViewById(R.id.payment_txt);
        final EditText msg = findViewById(R.id.et_msg);
        Button btn = findViewById(R.id.btn_send);



        intent = getIntent();

        if(intent != null){
             id = intent.getStringExtra("invoiceId");
             name = intent.getStringExtra("patientName");


            // grab invoice object
            Invoice invoice = invoiceDao.find(id);

            //get price form object
            String price = String.valueOf(invoice.getPrice());

            // grab invoice date from object
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            Date d = new Date(invoice.getPaymentDue());

            date = sdf.format(d);

            patient_name.setText(name);
            msg.setText("Dear " + name + ", your account is overdue $" + price +
                    " The payment was due by " + date + '\n' + "Please make your payment" );


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   String message = msg.getText().toString();
                   int cId = appSession.getUserId();
                   String title = "Payment Overdue Reminder";

                    paymentDao.insert(new PaymentNotification(title,message,System.currentTimeMillis(),Integer.parseInt(id),cId));

                    payment_message.append(message);

                    Toast.makeText(PaymentMessage.this, "Message Sent", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PaymentMessage.this,CashierActivity.class));
                }
            });

        }

    }
}
