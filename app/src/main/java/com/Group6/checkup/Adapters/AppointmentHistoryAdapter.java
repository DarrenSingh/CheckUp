package com.Group6.checkup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.R;
import com.Group6.checkup.Utils.Dao.DoctorDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AppointmentHistoryAdapter extends BaseAdapter {


    List<Appointment> appointments;
    Context context;
    private static LayoutInflater inflater = null;

    public AppointmentHistoryAdapter(Context context, List<Appointment> list) {
        this.context = context;
        this.appointments = list;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return appointments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Appointment appointment = appointments.get(position);

        //find patient in list via id
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_patient_appointment, null);
        }

        TextView doctorName = convertView.findViewById(R.id.text_appointment_doctor);
        TextView address = convertView.findViewById(R.id.text_appointment_address);
        TextView time = convertView.findViewById(R.id.text_appointment_date);

        Date d = new Date(appointment.getAppointmentDateTime());
        String senderId = String.valueOf(appointment.getDoctorID());

        //get patient from OnlineHelpReply dao usingsender id
        Doctor doctor = new DoctorDao(context).findByID(senderId);
        senderId = doctor.getLoginID();

        doctorName.setText(doctor.getFirstName() + " " + doctor.getLastName());
        address.setText(doctor.getOfficeAddress());
        time.setText(new SimpleDateFormat("MM/dd/YYYY\nhh:mm a").format(d));

        return convertView;
    }

}
