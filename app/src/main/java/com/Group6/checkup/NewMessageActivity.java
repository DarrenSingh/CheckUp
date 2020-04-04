package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Session;

public class NewMessageActivity extends AppCompatActivity {

    private Session appSession;
    private OnlineHelpDao onlineHelpDao;
    private OnlineHelp onlineHelp;
    int doctorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        appSession = new Session(this);
        //UI Components
        TextView mTextViewRecipient = findViewById(R.id.text_recipient_name);
        final EditText mEditSubject = findViewById(R.id.edit_send_subject);
        final EditText mEditMessage = findViewById(R.id.edit_send_message);
        Button mBtnSend = findViewById(R.id.btn_send_message);

        //Activity Logic
        Intent getIntent = getIntent();
        //pass intent with doctorID from other activity
        doctorId = Integer.parseInt(getIntent.getStringExtra("doctorId"));
        mTextViewRecipient.setText(getIntent.getStringExtra("doctorName").toString());

        //UI Event Listeners
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onlineHelpDao = new OnlineHelpDao(getApplicationContext());

                //create new msg object
                onlineHelp = new OnlineHelp(
                        mEditSubject.getText().toString(),
                        mEditMessage.getText().toString(),
                        System.currentTimeMillis(),
                        appSession.getUserId(),
                        doctorId
                );

                //TODO check for foreign key error
                //make message entry in database using the message object
                boolean result = onlineHelpDao.insert(onlineHelp);

                //display success message
                if(result) {
                    Toast.makeText(NewMessageActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                    //set msg attributes using UI Component values
                    startActivity(new Intent(NewMessageActivity.this,PatientHomeActivity.class));

                } else {
                    Toast.makeText(NewMessageActivity.this, "Unable to sent message", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
