package com.Group6.checkup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Utils.Dao.AppointmentDao;
import com.Group6.checkup.Utils.Session;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorInfoActivity extends AppCompatActivity {

    Session appSession;
    int doctorId;
    String doctorName;
    String doctorAddress;
    RecyclerView mRecyclerDateList;
    ListView mListViewAvailability;
    DateRecyclerViewAdapter dateRecyclerViewAdapter;
    SimpleAdapter availabilitySimpleAdapter;
    List<Map<String,String>> availabilityData;
    AppointmentDao appointmentDao;
    List<Appointment> appointments;
    int currentMonth;
    int currentDate;
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);
        appSession = new Session(this);

        Intent previousIntent = getIntent();
        doctorId = Integer.parseInt(previousIntent.getStringExtra("doctorId"));

        availabilityData = new ArrayList<>();
        Date currentTime = new Date(System.currentTimeMillis());
        currentMonth = currentTime.getMonth();
        currentDate = currentTime.getDate();

        TextView mTextViewName = findViewById(R.id.text_doctor_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_doctor_profile_address);
        ImageView mImageMessage = findViewById(R.id.img_create_message);
        mRecyclerDateList = findViewById(R.id.list_schedule_days);
        mListViewAvailability= findViewById(R.id.list_schedule_availability);

        doctorName = previousIntent.getStringExtra("doctorName");
        doctorAddress = previousIntent.getStringExtra("doctorAddress");

        mTextViewName.setText(doctorName);
        mTextViewAddress.setText(doctorAddress);

        appointmentDao = new AppointmentDao(this);
        appointments = appointmentDao.findAllByDoctor(String.valueOf(doctorId));

        String[] from = {"time", "status","timestamp"};

        int[] to = {R.id.text_availability_time, R.id.text_availability_status,R.id.hidden_availability_epoch};

        //Create an adapter and set it to the availability list
        availabilitySimpleAdapter = new SimpleAdapter(this,availabilityData,R.layout.item_availability_slot,from,to);
        mListViewAvailability.setAdapter(availabilitySimpleAdapter);

        //Set and define an onClicklistener for availability items
        mListViewAvailability.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //get unix timestamp of the appointment
                TextView appointmentStatus = view.findViewById(R.id.text_availability_status);
                TextView appointmentTimestamp = view.findViewById(R.id.hidden_availability_epoch);

                if(appointmentStatus.getText().toString().equals("available")) {

                    long appointmentDateTime = Long.parseLong(appointmentTimestamp.getText().toString());

                    //create new appointment
                    Appointment appointment = new Appointment(appointmentDateTime,appSession.getUserId(),doctorId);
                    //pass appointment to the dialogue menu

                    DialogFragment fragment = new ConfirmBookingDialogFragment(appointment);
                    fragment.show(getSupportFragmentManager(), "appointment");

                }
            }
        });

        //Create a layout manager and set it to the list
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        mRecyclerDateList.setLayoutManager(layoutManager);

        DateRecyclerViewAdapter.OnDateClickListener dateListener = new DateRecyclerViewAdapter.OnDateClickListener() {
            @Override
            public void onDateClick(int position) {
                Toast.makeText(DoctorInfoActivity.this, dateRecyclerViewAdapter.dateRange.get(position), Toast.LENGTH_SHORT).show();

                //my sloppy way of removing underlines on unselected dates...
                if(selected !=-1) {
                    RecyclerView.ViewHolder vh = mRecyclerDateList.findViewHolderForAdapterPosition(selected);
                    if(vh != null){
                        View underline = vh.itemView.findViewById(R.id.view_selected_date);
                        underline.setVisibility(View.INVISIBLE);
                    }

                    dateRecyclerViewAdapter.hasBeenSelected = true;
                    dateRecyclerViewAdapter.previouslySelected = selected;
                }
                selected = position;
                dateRecyclerViewAdapter.selected = position;

                //create a new list of booked items
                List<Integer> booked = new ArrayList<>();

                //get the date of the currently selected date from the list
                Date selectedDate = new Date(dateRecyclerViewAdapter.unixTime.get(position));

                //loop through the appointments
                for (int i = 0; i < appointments.size(); i++) {

                    //get appointment date
                    Date appointmentDate = new Date(appointments.get(i).getAppointmentDateTime());

                    //if the month and date of the appointment are the same as the selected date
                    if (appointmentDate.getMonth() == selectedDate.getMonth()
                            && appointmentDate.getDate() == selectedDate.getDate()) {
                        //add appointment to booked list
                        booked.add(appointmentDate.getHours());
                    }
                }

                //make changes to the availability list using the newly created booked appointments
                resetAvailabilityData(selectedDate,availabilityData,booked);
                //notify the adapter of the data set changes
                availabilitySimpleAdapter.notifyDataSetChanged();
            }
        };

        //create a new date recycler list view adapter, passing the onDateClick lister we created
        dateRecyclerViewAdapter = new DateRecyclerViewAdapter(dateListener);

        initializeAvailabilityData();

        //set the adapter to the recycler list view component
        mRecyclerDateList.setAdapter(dateRecyclerViewAdapter);

        mImageMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorInfoActivity.this,NewMessageActivity.class);
                intent.putExtra("doctorId",String.valueOf(doctorId));
                intent.putExtra("doctorName",doctorName);
                startActivity(intent);
            }
        });
    }

    private void initializeAvailabilityData(){

            dateRecyclerViewAdapter.hasBeenSelected = true;
            dateRecyclerViewAdapter.previouslySelected = selected;
            selected = 0;
            dateRecyclerViewAdapter.selected = 0;

        //create a new list of booked items
        List<Integer> booked = new ArrayList<>();

        //get the date of the currently selected date from the list
        Date todaysDate = new Date(System.currentTimeMillis());

        //loop through the appointments
        for (int i = 0; i < this.appointments.size(); i++) {

            //get appointment date
            Date appointmentDate = new Date(this.appointments.get(i).getAppointmentDateTime());

            //if the month and date of the appointment are the same as the selected date
            if (appointmentDate.getMonth() == todaysDate.getMonth()
                    && appointmentDate.getDate() == todaysDate.getDate()) {
                //add appointment to booked list
                booked.add(appointmentDate.getHours());
            }
        }

        resetAvailabilityData(todaysDate,availabilityData,booked);

    }

    private void resetAvailabilityData(Date selectedDate, List<Map<String,String>> data,List<Integer> appointmentTimes){

        //clear any data we currently have in our availability list
        data.clear();

        //loop through the business hours
        for (int i = 9; i < 18 ; i++) {

            Date appointmentTime = new Date(
                    selectedDate.getYear(),
                    selectedDate.getMonth(),
                    selectedDate.getDate(),
                    i,
                    0
                    );

            //create a hash map
            Map<String,String> map = new HashMap<>();

            map.put("timestamp",appointmentTime.getTime()+"");

            //convert from 24hr to 12hr time
            if(i > 12){
                map.put("time",i-12+":00 PM");
            } else{
                map.put("time",i+ ":00 AM");
            }

            //if the hr is located in the appointmentTimes list, set to "unavailable" else set to "available"
            if(appointmentTimes.contains(i)){
                map.put("status", "unavailable");
            } else {
                map.put("status", "available");
            }
            //add the hash map to the list
            data.add(map);
        }

    }

    public static class ConfirmBookingDialogFragment extends DialogFragment {

        AppointmentDao appointmentDao;
        Appointment appointment;

        public ConfirmBookingDialogFragment(Appointment appointment) {
            this.appointment = appointment;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            Date date = new Date(appointment.getAppointmentDateTime());

            SimpleDateFormat dateFormat = new SimpleDateFormat("EE MMMM dd, YYYY");
            SimpleDateFormat hourFormat = new SimpleDateFormat("h:mm a");
 ;
            String dateString = dateFormat.format(date);
            String hourString = hourFormat.format(date);
//
//            MessageFormat text = new MessageFormat());
//
//            String textFormat = text.format(dateString,hourString);

            String textFormatted = String.format(getResources().getString(R.string.text_booking_confirmation).toString(), dateString,hourString);


            builder.setMessage(textFormatted)
                    .setPositiveButton(R.string.btn_booking_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                            appointmentDao = new AppointmentDao(getContext());
                            appointmentDao.insert(appointment);
                            startActivity(new Intent(getContext(),PatientHomeActivity.class));
                            Toast.makeText(getContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton(R.string.btn_booking_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            Toast.makeText(getContext(), "Dismissed", Toast.LENGTH_SHORT).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}
