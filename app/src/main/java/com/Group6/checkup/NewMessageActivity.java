package com.Group6.checkup;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);

        //UI Components
        final EditText mEditSubject = findViewById(R.id.edit_send_subject);
        final EditText mEditMessage = findViewById(R.id.edit_send_message);
        Button mBtnSend = findViewById(R.id.btn_send_message);

        //Activity Logic

        //UI Event Listeners
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO logic adding message to the database
                //create new msg object

                //set msg attributes using UI Component values

                //make message entry in database using the message object

                //display success message
                Toast.makeText(NewMessageActivity.this, "Message Sent", Toast.LENGTH_SHORT).show();

                //clear edit text fields
                mEditSubject.setText("");
                mEditMessage.setText("");

            }
        });

    }
}
