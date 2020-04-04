package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.InvoiceDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashierActivity extends AppCompatActivity {

    InvoiceDao invoiceDao;
    Session s;
    Patient patient;
    PatientDao patientDao;
    List<Patient> q;
    ListView listView;
    Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        invoiceDao = new InvoiceDao(this);
        //patientDao = new PatientDao(this);
        //s = new Session(this);

        //patient = patientDao.find(s.getCurrentUsername());

        //this.setTitle("Welcome " + patient.getFirstName() + " " + patient.getLastName());

        q = invoiceDao.pendingPayment();

        listView = findViewById(R.id.list_view);

        listView.setAdapter(new Adapter(CashierActivity.this, q ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                in = new Intent(CashierActivity.this,PaymentMessage.class);
                in.putExtra("id", String.valueOf(invoiceDao.pendingPayment().get(2)));
                in.putExtra("name" , String.valueOf(invoiceDao.pendingPayment().get(position)));
                startActivity(in);
            }
        });

    }

    public class Adapter extends BaseAdapter {

        List<Patient> patientArrayList;
        Context context;
        LayoutInflater inflater;

        public Adapter(Context context, List<Patient> list){
            this.context = context;
            this.patientArrayList = list;
            inflater = (LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return patientArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return patientArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            patient = patientArrayList.get(position);
            if(convertView == null) {
                convertView = inflater.inflate( R.layout.card_view, parent,false);
            }

            TextView patient_name = convertView.findViewById(R.id.patient_name);

            patient_name.setText(patient.getFirstName() + " " + patient.getLastName());

            //convertView.setTag(patient.getID());

            return convertView;
        }
    }


}
