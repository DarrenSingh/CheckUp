package com.Group6.checkup;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Message;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentHistoryActivity extends AppCompatActivity {

    String loginID;
    private Session s;
    AppointmentDao appointmentDao;
    List<Appointment> appointments;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_history);

        this.setTitle("Appointment History");

        appointments = new ArrayList<>();

        lv = findViewById(R.id.history);
        appointmentDao = new AppointmentDao(this);
        s = new Session(this);

        loginID = String.valueOf(s.getUserId());
        // loginID = "P001";

        appointmentsForPatient(loginID);

    }


    public void appointmentsForPatient(String loginID){
        appointments = appointmentDao.findAllByPatient(loginID);

        lv.setAdapter(new AppointmentHistoryAdapter(this,appointments));
    }
}
