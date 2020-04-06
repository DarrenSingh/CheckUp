package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorMessages extends AppCompatActivity {

    private PatientDao patientDao;
    private OnlineHelpDao onlineHelpDao;

    private PatientAdapter pAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        patientDao = new PatientDao(this);
        onlineHelpDao = new OnlineHelpDao(this);


        this.setTitle("Messages");

        ListView listView = findViewById(R.id.list_view);

        List<OnlineHelp> patientMessages = onlineHelpDao.findAllByDoctor(String.valueOf(new Session(this).getUserId()));

        //create list<Map>
        List<Map<String,String>> appointmentData = new ArrayList<>();

        //loop through messsages
        for (int i = 0; i < patientMessages.size() ; i++) {

            //create a hashmap
            Map<String,String> map = new HashMap<>();

            //get patient name
            Patient appointmentOwner = patientDao.findById(String.valueOf(patientMessages.get(i).getPatientID()));
            String patientName = appointmentOwner.getFirstName() + " " + appointmentOwner.getLastName();

            //place
            map.put("messageId",String.valueOf(patientMessages.get(i).getID()));
            map.put("patientName",patientName);

            appointmentData.add(map);
        }

        String[] from = {"patientName","messageId"};

        //textmessage patient stands for name, hidden message stands for message id
        int[] to = {R.id.text_message_patient_name ,R.id.hidden_message_id};

        SimpleAdapter adapter = new SimpleAdapter(this,appointmentData,R.layout.item_doctor_message,from,to);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = view.findViewById(R.id.hidden_message_id);
                TextView patientNameTextView = view.findViewById(R.id.text_message_patient_name);

                Intent in = new Intent(DoctorMessages.this,PatientComplaint.class);
                in.putExtra("messageId" ,textView.getText().toString());
                in.putExtra("name",patientNameTextView.getText().toString());
                startActivity(in);
            }
        });

    }

}
