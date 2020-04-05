package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Session;

public class PatientComplaint extends AppCompatActivity {

    DoctorDao doctorDao;
    Doctor currentUser;
    Session appSession;
    OnlineHelpDao onlineHelpDao;
    OnlineHelp onlineHelp;
    OnlineHelpReplyDao onlineHelpReplyDao;
    OnlineHelpReply onlineHelpReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_complaint);

        appSession = new Session(this);
        doctorDao = new DoctorDao(this);
        onlineHelpDao = new OnlineHelpDao(this);
        onlineHelpReplyDao = new OnlineHelpReplyDao(this);
        currentUser = doctorDao.find(appSession.getCurrentUsername());

        this.setTitle("Patient Complaint");
        final TextView patient_name = findViewById(R.id.patient_name);
        TextView title = findViewById(R.id.message_title);
        TextView message = findViewById(R.id.patient_message);
        TextView headerSolution = findViewById(R.id.textView12);
        EditText reply = findViewById(R.id.editTextDoctorSolution);
        Button buttonBack = findViewById(R.id.buttonBack);
        Button buttonPostSolution = findViewById(R.id.buttonPostSolution);

        Intent intent = getIntent();
        if(intent != null){
            String messageId = intent.getStringExtra("messageId");
            String name = intent.getStringExtra("name");

            //grab patient from previous activity and setText
            patient_name.setText(name);

            //get message
            onlineHelp = onlineHelpDao.find(messageId);
        }

        //set texts from message
        title.setText(onlineHelp.getMessageTitle());
        message.setText(onlineHelp.getMessage());

        if(onlineHelp.getOnlineHelpReplyID() > 0){

            onlineHelpReply = onlineHelpReplyDao.find(
                    String.valueOf(onlineHelp.getOnlineHelpReplyID()));

            headerSolution.setText(R.string.text_solution_posted);

            reply.setText(onlineHelpReply.getMessageContent());

            reply.setEnabled(false);
            buttonPostSolution.setEnabled(false);
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientComplaint.this, DoctorMessages.class));
            }
        });

        buttonPostSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doctorReply = reply.getText().toString();
                String empty = "";

                //validation for the doctor's reply
                if(doctorReply.equals(empty)){
                    Toast.makeText(PatientComplaint.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                }else{

                    try {

                        onlineHelpReply = new OnlineHelpReply(
                                "REPLY:"+onlineHelp.getMessageTitle(),
                                doctorReply,
                                System.currentTimeMillis(),
                                appSession.getUserId()
                        );

                        int result = onlineHelpReplyDao.insertWithResult(onlineHelpReply);

                        if(result == -1){
                            throw new Exception("Unable to insert the record into the database");
                        }

                        //update the onlinehelp item in the database with the new reply foreign key
                        onlineHelp.setOnlineHelpReplyID(result);
                        boolean update = onlineHelpDao.update(onlineHelp);

                        startActivity(new Intent(PatientComplaint.this, DoctorMessages.class));

                        Toast.makeText(PatientComplaint.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    }catch (Exception e){

                        Toast.makeText(PatientComplaint.this, "Unable to send message", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }
}
