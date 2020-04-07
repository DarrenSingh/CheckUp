package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashierActivity extends AppCompatActivity {

    InvoiceDao invoiceDao;
    PatientDao patientDao;
    List<Invoice> invoices;
    List<HashMap<String,String>> overdueInvoicesData;
    ListView listView;
    OverdueInvoicesAdapter invoiceAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        //initialize daos
        invoiceDao = new InvoiceDao(this);
        patientDao = new PatientDao(this);

        //get all invoices
        invoices = invoiceDao.findAll();

        //create new array for adapter
        overdueInvoicesData = new ArrayList<>();

        //loop through
        for (int j = 0; j < invoices.size() ; j++) {

            Invoice currentInvoice = invoices.get(j);

           if(currentInvoice.getPaymentStatus().equals("unpaid")
                   && currentInvoice.getPaymentDue() > System.currentTimeMillis()){

                HashMap<String,String> map = new HashMap<>();


                Patient associatedPatient = patientDao.findById(String.valueOf(currentInvoice.getPatientID()));

                map.put("patientName", associatedPatient.getFirstName()
                        + " "
                        + associatedPatient.getLastName()
                );

                map.put("invoiceId",String.valueOf(currentInvoice.getID()));

                overdueInvoicesData.add(map);
            }
        }

        listView = findViewById(R.id.list_view);

        String[] from = {"patientName"};
        int[] to = {R.id.text_overdue_patient};

        invoiceAdapter = new OverdueInvoicesAdapter(this,overdueInvoicesData,R.layout.card_view,from,to);

        listView.setAdapter(invoiceAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //create intent
                intent = new Intent(CashierActivity.this,PaymentMessage.class);

                //pass values to intent from list item to intent
                intent.putExtra("invoiceId", String.valueOf(invoiceAdapter.data.get(position).get("invoiceId")));
                intent.putExtra("patientName" ,String.valueOf(invoiceAdapter.data.get(position).get("patientName")));

                //start activity
                startActivity(intent);
            }
        });

    }

}
