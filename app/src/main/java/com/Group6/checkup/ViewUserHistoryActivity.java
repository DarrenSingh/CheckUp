package com.Group6.checkup;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;

import java.util.List;

public class ViewUserHistoryActivity extends AppCompatActivity {

    String loginID;
    OnlineHelpDao onlineHelpDao;
    List<OnlineHelp> onlineHelp;
    ListView lv = findViewById(R.id.history);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_history);

        onlineHelpDao = new OnlineHelpDao(this);
        switch (loginID.charAt(0)){
            case 'P':
                messagesFromDoctors();
                break;
            case 'D':
                messagesFromPatients();
                break;
            case 'A':
                messageForAdmin();
                break;

        }
    }

    public void messagesFromDoctors(){
        onlineHelp = onlineHelpDao.findAllByPatient(loginID);
        lv.setAdapter(new UserHistoryAdapter(ViewUserHistoryActivity.this,onlineHelp));
    }

    public void messagesFromPatients(){
        onlineHelp = onlineHelpDao.findAllByDoctor(loginID);
        lv.setAdapter(new UserHistoryAdapter(ViewUserHistoryActivity.this,onlineHelp));
    }

    public void messageForAdmin(){
        onlineHelp = onlineHelpDao.findAll();
        lv.setAdapter(new UserHistoryAdapter(ViewUserHistoryActivity.this,onlineHelp));
    }
}
