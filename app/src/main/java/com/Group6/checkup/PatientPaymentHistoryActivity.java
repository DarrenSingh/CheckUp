package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Session;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

public class PatientPaymentHistoryActivity extends AppCompatActivity {
    private Session appSession;
    private InvoiceDao invoiceDao;
    private List<Invoice> invoiceList;

    private ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment_history);
        appSession = new Session(this);
        invoiceDao = new InvoiceDao(this);
        invoiceList = new ArrayList<>();

        //UI Components
        Button mButtonMakePayment = findViewById(R.id.btn_history_make_payment);
        mListview = findViewById(R.id.list_invoice_history);

        //TODO com.Group6.checkup.Patient payment history logic
        //get all invoices by patient id
        invoiceList = invoiceDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        //TODO ListView Hashmap
        populateListview(invoiceList,mListview);

        // UI Event Listeners
        mButtonMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientPaymentHistoryActivity.this,SubmitPaymentActivity.class));
            }
        });


    }

    private void populateListview(List<Invoice> invoiceList, ListView listView){

        List<HashMap<String,String>> inboxData = new ArrayList<>();

        for (int i = 0; i < invoiceList.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            Date date = new Date(invoiceList.get(i).getInvoiceDate());
            // format of the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = dateFormat.format(date);


            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("date",formattedDate);
            hashMap.put("amount", Double.toString(invoiceList.get(i).getPrice()));
            hashMap.put("status", (invoiceList.get(i).getPaymentStatus()));

            // add this hashmap to the list
            inboxData.add(hashMap);
        }

        //TODO List from adapter (hashmap keys)
        String[] from = {
                "date",
                "amount",
                "status"
        };

        //TODO List to adapter (R.id.* from layout file)
        //TODO create inbox list layout file
        int[] to = {R.id.text_payment_date,R.id.text_payment_amount,R.id.text_payment_type};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,R.layout.item_payment_history,from,to);
        listView.setAdapter(simpleAdapter);
    }
}
