package com.Group6.checkup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Group6.checkup.Entities.OnlineHelp;
import com.Group6.checkup.Entities.Patient;
import com.Group6.checkup.Utils.Dao.OnlineHelpDao;

import java.util.ArrayList;
import java.util.List;

public class UserHistoryAdapter extends BaseAdapter {

    List<OnlineHelp> onlineHelps;
    Context context;
    private static LayoutInflater inflater=null;

    public UserHistoryAdapter(Context context, List<OnlineHelp> list){
        this.context = context;
        this.onlineHelps = list;
        inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        OnlineHelp help = onlineHelps.get(position);
        if(convertView == null){
            convertView = inflater.inflate( R.layout.card_view,null);
        }

        TextView name = convertView.findViewById(R.id.user_name);
        TextView message = convertView.findViewById(R.id.h_message);
        TextView time = convertView.findViewById(R.id.time);

        name.setText((CharSequence) help.getPatient());
        message.setText(help.getMessage());
        time.setText(help.getSentDateTime());

        return convertView;
    }
}
