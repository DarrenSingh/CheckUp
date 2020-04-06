package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Session;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorActivity extends AppCompatActivity {
    DoctorDao doctorDao;
    Doctor currentUser;
    Session appSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        appSession = new Session(this);
        doctorDao = new DoctorDao(this);


        currentUser = doctorDao.find(appSession.getCurrentUsername());
        String doctorFirstName = currentUser.getFirstName();
        String doctorLastName = currentUser.getLastName();

        //load the strings to textviews
        String FullName = "Welcome Dr. "+doctorFirstName+" "+doctorLastName;

        Button buttonViewSchedule = findViewById(R.id.buttonViewSchedule);
        Button buttonViewMessages = findViewById(R.id.buttonViewMessage);
        Button buttonReturnToLogin = findViewById(R.id.buttonReturnToLogin);
        TextView doctorName = findViewById(R.id.doctor_name);


        //setting the title "welcome user" to the name of the doctor
        doctorName.setText(FullName);

        buttonViewMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorMessages.class));
            }
        });

        buttonViewSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorActivity.this, DoctorAppointmentSchedule.class));
            }
        });

        buttonReturnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset session info
                appSession.setCurrentUsername(null);
                appSession.setUserId(0);
                Intent intent = new Intent(DoctorActivity.this, LoginActivity.class);
                //clear task back stack before and send user to login screen
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
