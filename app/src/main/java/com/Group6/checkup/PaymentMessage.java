package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.PaymentNotification;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PaymentNotificatonDao;
import com.Group6.checkup.Utils.Session;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentMessage extends AppCompatActivity {

    PaymentNotificatonDao paymentDao;
    InvoiceDao invoiceDao;
    Intent intent;
    String date ;
    Session appSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_message);
        appSession = new Session(this);
        paymentDao = new PaymentNotificatonDao(this);


        final TextView patient_name = findViewById(R.id.patient_name);
        final TextView payment_message = findViewById(R.id.payment_txt);
        final EditText msg = findViewById(R.id.et_msg);
        Button btn = findViewById(R.id.btn_send);



        intent = getIntent();

        if(intent != null){
            String id = intent.getStringExtra("invoiceId");
            String name = intent.getStringExtra("patientName");


            // grab invoice object
            Invoice invoice = invoiceDao.find(id);

            //get price form object
            String price = String.valueOf(invoice.getPrice());

            // grab invoice date from object
            date = String.valueOf(invoice.getInvoiceDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");

            Date d = null;
            try {
                d = sdf.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            calendar.add(Calendar.DAY_OF_YEAR, 10);


            patient_name.setText(name);
            msg.setText("Dear " + name + ", your account is overdue $" + price +
                    " The payment is due by " + calendar + '\n');
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String message = msg.getText().toString();
               int pId = Integer.parseInt(intent.getStringExtra("id"));
               int cId = appSession.getUserId();
               String title = "Payment Overdue Reminder";

                paymentDao.insert(new PaymentNotification(title,message,System.currentTimeMillis(),pId,cId));

                payment_message.append(message);
            }
        });
    }
}
