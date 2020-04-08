package com.Group6.checkup;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.Message;
import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.OnlineHelpReply;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;
import com.Group6.checkup.Utils.Dao.OnlineHelpReplyDao;
import com.Group6.checkup.Utils.Session;

import java.util.ArrayList;
import java.util.List;

public class AdminViewHistoryActivity extends AppCompatActivity {

    String loginID;
    private Session s;
    OnlineHelpDao onlineHelpDao;
    OnlineHelpReplyDao onlineHelpReplyDao;
    List<OnlineHelpReply> onlineHelpRepy;
    List<OnlineHelp> onlineHelp;
    List<Message> allMessages;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_history);

        this.setTitle("User History");

        allMessages = new ArrayList<>();

        lv = findViewById(R.id.history);
        onlineHelpDao = new OnlineHelpDao(this);
        onlineHelpReplyDao = new OnlineHelpReplyDao(this);
        s = new Session(this);

        loginID = String.valueOf(s.getUserId());
       // loginID = "P001";

        messageForAdmin();

    }

    public void messageForAdmin(){
        onlineHelp = onlineHelpDao.findAll();
        onlineHelpRepy = onlineHelpReplyDao.findAll();

        for (int i = 0; i <onlineHelp.size() ; i++) {
            allMessages.add(onlineHelp.get(i));
        }

        for (int i = 0; i <onlineHelpRepy.size() ; i++) {
            allMessages.add(onlineHelpRepy.get(i));
        }

        lv.setAdapter(new UserHistoryAdapter(AdminViewHistoryActivity.this,allMessages));
    }

}
