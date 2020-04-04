package com.Group6.checkup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PatientAdapter extends BaseAdapter {

    private Context context;
    private String[] list;

    public PatientAdapter(Context context, String[] list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int position) {
        return list[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = View.inflate(context, R.layout.card_view,null);
        }

        TextView patient_name = convertView.findViewById(R.id.patient_name);

        patient_name.setText(list[position]);

        return convertView;
    }
}
