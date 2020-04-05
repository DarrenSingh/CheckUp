package com.Group6.checkup;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class OverdueInvoicesAdapter extends SimpleAdapter {

    List<? extends Map<String, ?>> data;

    public OverdueInvoicesAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView patient_name = view.findViewById(R.id.text_overdue_patient);

        patient_name.setText(data.get(position).get("patientName").toString());

        return view;

    }
}
