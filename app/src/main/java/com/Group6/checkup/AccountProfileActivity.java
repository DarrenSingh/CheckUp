package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountProfileActivity extends AppCompatActivity {

    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        patientDao = new PatientDao(this);
        appSession = new Session(this);

        //UI Components
        Button mBtnEditProfile = findViewById(R.id.btn_edit_profile);
        Button mBtnPaymentHistory = findViewById(R.id.btn_payment_history);

        final TextView mTextViewFullName = findViewById(R.id.text_patientprof_name);
        final TextView mTextViewAddress = findViewById(R.id.text_patientprof_address);
        final TextView mTextViewMSP = findViewById(R.id.text_patientprof_msp);

        ListView mListViewInbox = findViewById(R.id.list_patient_inbox);

        //Activity Logic

        currentUser = patientDao.find(appSession.getCurrentUsername());
        mTextViewFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        mTextViewAddress.setText(currentUser.getAddress());
        mTextViewMSP.setText(String.valueOf(currentUser.getHealthCareCardNumber()));

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
        mBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountProfileActivity.this,PatientEditProfileActivity.class));
            }
        });

        mBtnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountProfileActivity.this,PatientPaymentHistoryActivity.class));
            }
        });



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
