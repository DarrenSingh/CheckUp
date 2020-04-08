package com.Group6.checkup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Entities.PaymentNotification;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Dao.PaymentNotificationDao;

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

    PaymentNotificationDao paymentNotificationDao;
    PaymentNotification paymentNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_message);
        onlineHelpDao = new OnlineHelpDao(this);
        onlineHelpReplyDao = new OnlineHelpReplyDao(this);
        paymentNotificationDao = new PaymentNotificationDao(this);


        mTextViewRecipient = findViewById(R.id.text_help_recipient);
        mTextViewHelpSubject = findViewById(R.id.text_help_subject);
        mTextViewHelpBody = findViewById(R.id.text_help_body);
        mTextViewReplySubject = findViewById(R.id.text_reply_subject);
        mTextViewReplyBody = findViewById(R.id.text_reply_body);

        Intent intent = getIntent();

        if (intent.hasExtra("onlineHelpId")) {

            String messageId = intent.getStringExtra("onlineHelpId");

            message = onlineHelpDao.find(messageId);

            if (onlineHelpReplyDao.exists(String.valueOf(message.getOnlineHelpReplyID()))) {

                reply = onlineHelpReplyDao.find(String.valueOf(message.getOnlineHelpReplyID()));

                replyMessage = findViewById(R.id.card_reply_message);
                replyMessage.setVisibility(View.VISIBLE);

                mTextViewReplySubject.setText(reply.getMessageTitle());
                mTextViewReplyBody.setText(reply.getMessageContent());
            }

            mTextViewHelpSubject.setText(message.getMessageTitle());
            mTextViewHelpBody.setText(message.getMessage());

        } else if (intent.hasExtra("paymentNotificationId")) {

            String messageId = intent.getStringExtra("paymentNotificationId");

            paymentNotification = paymentNotificationDao.find(messageId);

            mTextViewHelpSubject.setText(paymentNotification.getMessageTitle());
            mTextViewHelpBody.setText(paymentNotification.getMessage());
        }

        String recipient = intent.getStringExtra("recipient");

        mTextViewRecipient.setText(recipient);


    }
}
