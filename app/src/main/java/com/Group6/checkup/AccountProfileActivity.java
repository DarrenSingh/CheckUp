package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Group6.checkup.Entities.Cashier;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Entities.PaymentNotification;
import com.Group6.checkup.Utils.Dao.CashierDao;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Dao.PaymentNotificationDao;
import com.Group6.checkup.Utils.Session;
import com.Group6.checkup.Utils.Sort;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AccountProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Session appSession;
    private PatientDao patientDao;
    private Patient currentUser;
    private PaymentNotificationDao paymentNotificationDao;
    private OnlineHelpDao onlineHelpDao;
    private TextView mTextViewFullName;
    private TextView mTextViewAddress;
    private TextView mTextViewMSP;
    private ListView mListViewInbox;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_profile);
        appSession = new Session(this);
        patientDao = new PatientDao(this);
        paymentNotificationDao = new PaymentNotificationDao(this);
        onlineHelpDao = new OnlineHelpDao(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        toggleSetUp();

        this.setTitle("My Profile");


        //        appSession = new Session(this);
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

        //check if user has healthcare
        if(currentUser.getHealthCareCardNumber() == 0) {
            mTextViewMSP.setText(R.string.text_no_healthcare);
        } else {
            String healthNo = "Health#: "+String.valueOf(currentUser.getHealthCareCardNumber());
            mTextViewMSP.setText(healthNo);
        }

        //Obtain the online help items for this user
        List<OnlineHelp> onlineHelps = onlineHelpDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        //Obtain the payment notifications for this user
        List<PaymentNotification> paymentNotifications = paymentNotificationDao.findAllByPatient(String.valueOf(appSession.getUserId()));

        //Populate the inbox with messages from the database
        populateListView(onlineHelps,paymentNotifications,mListViewInbox);

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


        mListViewInbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on item click grab
                TextView messageType = view.findViewById(R.id.hidden_message_type);
                TextView messageId = view.findViewById(R.id.hidden_message_id);
                TextView recipient = view.findViewById(R.id.text_message_recipient);

                //create intent
                Intent intent = new Intent(getApplicationContext(),PatientViewMessageActivity.class);

                if(messageType.getText().toString().equals("help")) {
                    //put onlineHelp object id
                    intent.putExtra("onlineHelpId", messageId.getText().toString());

                } else if(messageType.getText().toString().equals("payment")){
                    //put paymentNotification object id
                    intent.putExtra("paymentNotificationId", messageId.getText().toString());

                }

                //put recipient Name
                intent.putExtra("recipient", recipient.getText().toString());

                startActivity(intent);

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

        //check if user has healthcare
        if(currentUser.getHealthCareCardNumber() == 0) {
            mTextViewMSP.setText(R.string.text_no_healthcare);
        } else {
            String healthNo = "Health#: "+String.valueOf(currentUser.getHealthCareCardNumber());
            mTextViewMSP.setText(healthNo);
        }
    }

    private void populateListView(List<OnlineHelp> onlineHelpList, List<PaymentNotification> paymentNotifications, ListView listView){

        List<HashMap<String,String>> inboxData = new ArrayList<>();

        for (int i = 0; i < onlineHelpList.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            //get the doctor who sent the msg by row id
            Doctor sender = new DoctorDao(this).findByID(String.valueOf(onlineHelpList.get(i).getDoctorID()));

            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("type","help");
            hashMap.put("id",String.valueOf(onlineHelpList.get(i).getID()));
            hashMap.put("time",String.valueOf(onlineHelpList.get(i).getSentDateTime()));
            hashMap.put("from",sender.getFirstName()+" "+sender.getLastName());
            hashMap.put("title", onlineHelpList.get(i).getMessageTitle());
            hashMap.put("body", (onlineHelpList.get(i).getMessage()));

            // add this hashmap to the list
            inboxData.add(hashMap);
        }

        for (int i = 0; i < paymentNotifications.size() ; i++) {

            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            //get the doctor who sent the msg by row id
            Cashier sender = new CashierDao(this).findByID(String.valueOf(paymentNotifications.get(i).getCashierID()));

            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("type","payment");
            hashMap.put("id",String.valueOf(paymentNotifications.get(i).getID()));
            hashMap.put("time",String.valueOf(paymentNotifications.get(i).getSentDateTime()));
            hashMap.put("from",sender.getFirstName()+" "+sender.getLastName());
            hashMap.put("title", paymentNotifications.get(i).getMessageTitle());
            hashMap.put("body", (paymentNotifications.get(i).getMessage()));

            // add this hashmap to the list
            inboxData.add(hashMap);

        }

        Sort.byKeyValue(inboxData,"time",long.class,Sort.DESCENDING);

        String[] from = {
                "type",
                "id",
                "from",
                "title",
                "body"
        };

        int[] to = {R.id.hidden_message_type,R.id.hidden_message_id,R.id.text_message_recipient,R.id.text_message_subject,R.id.text_message_description};

        //ListView Adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,inboxData,R.layout.item_inbox_message,from,to);
        listView.setAdapter(simpleAdapter);
    }

    // setting the toggle for display
    public void toggleSetUp(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    //method for closing and opening the drawer
    public void onBackPressed() {
        drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.nav_home:
                Intent h= new Intent(AccountProfileActivity.this, PatientHomeActivity.class);
                startActivity(h);
                break;
            case R.id.nav_history:
                Intent g= new Intent(AccountProfileActivity.this, PatientAppointmentHistoryActivity.class);
                startActivity(g);
                break;
            case R.id.nav_logout:
                Intent s= new Intent(AccountProfileActivity.this,LoginActivity.class);
                startActivity(s);
                break;

        }

        drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
