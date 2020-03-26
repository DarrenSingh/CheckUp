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
import android.widget.Toast;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Invoice;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountProfileActivity extends AppCompatActivity {

    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;
    private TextView mTextViewFullName;
    private TextView mTextViewAddress;
    private TextView mTextViewMSP;
    private ListView mListViewInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        appSession = new Session(this);
        patientDao = new PatientDao(this);

        //UI Components
        Button mBtnEditProfile = findViewById(R.id.btn_edit_profile);
        Button mBtnPaymentHistory = findViewById(R.id.btn_payment_history);

        mTextViewFullName = findViewById(R.id.text_patientprof_name);
        mTextViewAddress = findViewById(R.id.text_patientprof_address);
        mTextViewMSP = findViewById(R.id.text_patientprof_msp);

        mListViewInbox = findViewById(R.id.list_patient_inbox);

        //Activity Logic

        currentUser = patientDao.find(appSession.getCurrentUsername());
        mTextViewFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        mTextViewAddress.setText(currentUser.getAddress());
        mTextViewMSP.setText(String.valueOf(currentUser.getHealthCareCardNumber()));

        OnlineHelpDao onlineHelpDao = new OnlineHelpDao(this);

        List<OnlineHelp> onlineHelps = onlineHelpDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        populateListView(onlineHelps,mListViewInbox);

        //UI Event Listeners
        mBtnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountProfileActivity.this,PatientEditProfileActivity.class);
                startActivity(intent);
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
                TextView messageId = view.findViewById(R.id.text_message_id);
                TextView recipient = view.findViewById(R.id.text_message_recipient);

                Intent intent = new Intent(getApplicationContext(),PatientViewMessageActivity.class);
                intent.putExtra("messageId",messageId.getText().toString());
                intent.putExtra("recipient",recipient.getText().toString());
                startActivity(intent);


                Toast.makeText(AccountProfileActivity.this, messageId.getText().toString(), Toast.LENGTH_SHORT).show();

                    //create intent
                    //put message object
                    //start activity
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Grab user and update text fields on activity resume
        currentUser = patientDao.find(appSession.getCurrentUsername());
        mTextViewFullName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        mTextViewAddress.setText(currentUser.getAddress());
        mTextViewMSP.setText(String.valueOf(currentUser.getHealthCareCardNumber()));
    }

    private void populateListView(List<OnlineHelp> onlineHelpList, ListView listView){

        List<HashMap<String,String>> inboxData = new ArrayList<>();

        for (int i = 0; i < onlineHelpList.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            Doctor sender = new DoctorDao(this).findByID(String.valueOf(onlineHelpList.get(i).getDoctorID()));

            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("id",String.valueOf(onlineHelpList.get(i).getID()));
            hashMap.put("from",sender.getFirstName()+" "+sender.getLastName());
            hashMap.put("title", onlineHelpList.get(i).getMessageTitle());
            hashMap.put("body", (onlineHelpList.get(i).getMessage()));

            // add this hashmap to the list
            inboxData.add(hashMap);
        }

        //TODO List from adapter (hashmap keys)
        String[] from = {
                "id",
                "from",
                "title",
                "body"
        };

        //TODO List to adapter (R.id.* from layout file)
        //TODO create inbox list layout file
        int[] to = {R.id.text_message_id,R.id.text_message_recipient,R.id.text_message_subject,R.id.text_message_description};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,R.layout.item_inbox_message,from,to);
        listView.setAdapter(simpleAdapter);
    }

}
