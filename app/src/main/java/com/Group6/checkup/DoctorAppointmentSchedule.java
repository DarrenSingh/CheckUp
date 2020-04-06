package com.Group6.checkup;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.PatientDao;
import com.Group6.checkup.Utils.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAppointmentSchedule extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        //sets page title
        this.setTitle("Appointment Schedule");

        PatientDao dao = new PatientDao(this);
        AppointmentDao adao = new AppointmentDao(this);

        List<Appointment> doctorAppointments = adao.findAllByDoctor(String.valueOf(new Session(this).getUserId()));

        //create list<Map>
        List<Map<String,String>> appointmentData = new ArrayList<>();

        for (int i = 0; i < doctorAppointments.size() ; i++) {

            Map<String, String> map = new HashMap<>();

            Patient appointmentOwner = dao.findById(String.valueOf(doctorAppointments.get(i).getPatientID()));
            String patientName = appointmentOwner.getFirstName() + " " + appointmentOwner.getLastName();

            Date date = new Date(doctorAppointments.get(i).getAppointmentDateTime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd YYYY h:mm a");

            map.put("patient", patientName);
            map.put("time", dateFormat.format(date));

            appointmentData.add(map);
        }

        String[] from = {"patient","time"};
        int[] to = {R.id.text_appointment_patient ,R.id.text_appointment_time};

        SimpleAdapter adapter = new SimpleAdapter(this,appointmentData,R.layout.item_doctor_appointment,from,to);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //cycle through appointments
            //create hashmap
            //put patient name
            //put appointment time
        //create the adapter
        //set adapter

    }
}
