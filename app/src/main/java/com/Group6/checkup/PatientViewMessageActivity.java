package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;

public class PatientViewMessageActivity extends AppCompatActivity {

    private CardView replyMessage;
    private TextView mTextViewRecipient;
    private TextView mTextViewHelpSubject;
    private TextView mTextViewHelpBody;
    private TextView mTextViewReplySubject;
    private TextView mTextViewReplyBody;

    OnlineHelpDao onlineHelpDao;
    OnlineHelpReplyDao onlineHelpReplyDao;

    OnlineHelp message;
    OnlineHelpReply reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_message);
        onlineHelpDao = new OnlineHelpDao(this);
        onlineHelpReplyDao = new OnlineHelpReplyDao(this);


        mTextViewRecipient = findViewById(R.id.text_help_recipient);
        mTextViewHelpSubject = findViewById(R.id.text_help_subject);
        mTextViewHelpBody = findViewById(R.id.text_help_body);
        mTextViewReplySubject = findViewById(R.id.text_reply_subject);
        mTextViewReplyBody = findViewById(R.id.text_reply_body);

        Intent intent = getIntent();
        String messageId = intent.getStringExtra("messageId");
        String recipient = intent.getStringExtra("recipient");

        message = onlineHelpDao.find(messageId);

        if(onlineHelpReplyDao.exists(String.valueOf(message.getOnlineHelpReplyID()))){

            reply = onlineHelpReplyDao.find(String.valueOf(message.getOnlineHelpReplyID()));

            replyMessage = findViewById(R.id.card_reply_message);
            replyMessage.setVisibility(View.VISIBLE);

            mTextViewReplySubject.setText(reply.getMessageTitle());
            mTextViewReplyBody.setText(reply.getMessageContent());
        }

        mTextViewRecipient.setText(recipient);
        mTextViewHelpSubject.setText(message.getMessageTitle());
        mTextViewHelpBody.setText(message.getMessage());


    }
}
