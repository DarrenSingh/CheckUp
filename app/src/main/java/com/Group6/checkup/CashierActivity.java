package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CashierActivity extends AppCompatActivity {

    String[] attractions = {"Louis Litt",
            "Harvey Specter","Donna Paulsen"};

    private PatientAdapter pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);

        this.setTitle("Welcome Shivangi!");

        ListView listView = findViewById(R.id.list_view);

        pAdapter = new PatientAdapter(CashierActivity.this,attractions);
        listView.setAdapter(pAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(CashierActivity.this,PaymentMessage.class);
                in.putExtra("name" , attractions[position]);
                startActivity(in);
            }
        });

    }
}