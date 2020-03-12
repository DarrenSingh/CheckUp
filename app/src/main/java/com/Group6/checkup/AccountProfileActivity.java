package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);


        //UI Components
        Button mBtnEditProfile = findViewById(R.id.btn_edit_profile);
        Button mBtnPaymentHistory = findViewById(R.id.btn_payment_history);

        TextView mTextViewFullName = findViewById(R.id.text_patientprof_name);
        TextView mTextViewAddress = findViewById(R.id.text_patientprof_address);
        TextView mTextViewMSP = findViewById(R.id.text_patientprof_msp);

        ListView mListViewInbox = findViewById(R.id.list_patient_inbox);

        //TODO ListView Hashmap
            /*
            *
            * Grab patient message objects from inbox
            *
            *
            * */
        List<HashMap<String,String>> inboxData = new ArrayList<>();

        //TODO List from adapter (hashmap keys)
        String[] from = {};

        //TODO List to adapter (R.id.* from layout file)
            //TODO create inbox list layout file
        int[] to = {};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,-1,from,to);


        //UI Event Listeners

        //TODO Inbox item onItemClickListener
        mListViewInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on item click grab
                    //create intent
                    //put message object
                    //start activity
            }
        });

    }
}
