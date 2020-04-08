package com.Group6.checkup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Entities.Message;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.Dao.PatientDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserHistoryAdapter extends BaseAdapter {

    List<Message> onlineHelps;
    Context context;

    private static LayoutInflater inflater = null;

    public UserHistoryAdapter(Context context, List<Message> list) {
        this.context = context;
        this.onlineHelps = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return onlineHelps.size();
    }

    @Override
    public Object getItem(int position) {
        return onlineHelps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Message help = onlineHelps.get(position);

        //find patient in list via id
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_admin_history_view, null);
        }

        TextView sender = convertView.findViewById(R.id.text_admin_history_sender);
        TextView subject = convertView.findViewById(R.id.text_admin_history_subject);
        TextView body = convertView.findViewById(R.id.text_admin_history_body);
        TextView time = convertView.findViewById(R.id.text_admin_history_date);

        Date d = new Date(help.getTimestamp());
        String senderId = String.valueOf(help.getSenderID());

        if (help.isReply()) {
            //get doctor from OnlineHelpReply dao usingsender id
            Patient patient = new PatientDao(context).findById(senderId);
            senderId = patient.getLoginID();
        } else {
            //get patient from OnlineHelpReply dao usingsender id
            Doctor doctor = new DoctorDao(context).findByID(senderId);
            senderId = doctor.getLoginID();
        }

        sender.setText(senderId);
        subject.setText(help.getSubject());
        body.setText(help.getBody());
        time.setText(new SimpleDateFormat("MM/dd/YYYY\nhh:mm a").format(d));

        return convertView;
    }
}
