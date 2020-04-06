package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Session;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PatientPaymentHistoryActivity extends AppCompatActivity {
    private Session appSession;
    private InvoiceDao invoiceDao;
    private List<Invoice> invoiceList;
    private TextView mTextViewOwing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_payment_history);
        appSession = new Session(this);
        invoiceDao = new InvoiceDao(this);
        invoiceList = new ArrayList<>();

        //UI Components
        mTextViewOwing = findViewById(R.id.text_history_owing);
        ListView mListView = findViewById(R.id.list_invoice_history);

        //get all invoices by patient id
        invoiceList = invoiceDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        populateListView(invoiceList, mListView,mTextViewOwing);

    }

    private void populateListView(List<Invoice> invoiceList, ListView listView, TextView mTextViewOwing){


        List<HashMap<String,String>> inboxData = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("###.##");
        double owingBalance = 0.00;

        for (int i = 0; i < invoiceList.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            Date date = new Date(invoiceList.get(i).getInvoiceDate());
            // format of the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
            String formattedDate = dateFormat.format(date);


            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("date",formattedDate);
            hashMap.put("amount", "$"+decimalFormat.format(invoiceList.get(i).getPrice()));
            hashMap.put("status", (invoiceList.get(i).getPaymentStatus()));
            hashMap.put("id", (String.valueOf(invoiceList.get(i).getID())));

            // add this hashmap to the list
            inboxData.add(hashMap);
            owingBalance += invoiceList.get(i).getPrice();
        }

        mTextViewOwing.setText("$"+decimalFormat.format(owingBalance));


        String[] from = {
                "date",
                "amount",
                "status",
                "id"
        };

        int[] to = {R.id.text_payment_date,R.id.text_payment_amount,R.id.text_payment_type,R.id.hidden_history_invoice_id};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,R.layout.item_payment_history,from,to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView invoiceId = view.findViewById(R.id.hidden_history_invoice_id);

                Intent intent = new Intent(PatientPaymentHistoryActivity.this,SubmitPaymentActivity.class);
                intent.putExtra("invoiceId",invoiceId.getText().toString());
                startActivity(intent);
            }
        });
    }
}
