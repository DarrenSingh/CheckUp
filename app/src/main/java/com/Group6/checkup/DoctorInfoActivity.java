package com.Group6.checkup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Group6.checkup.Entities.Appointment;
import com.Group6.checkup.Utils.Dao.AppointmentDao;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorInfoActivity extends AppCompatActivity {

    RecyclerView mRecyclerDateList;
    ListView mListViewAvailability;
    DateRecyclerViewAdapter dateRecyclerViewAdapter;
    SimpleAdapter availabilitySimpleAdapter;
    List<Map<String,String>> availabilityData;
    AppointmentDao appointmentDao;
    List<Appointment> appointments;
    int currentMonth;
    int currentDate;
    Date currentTime;
    int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_info);

        Intent intent = getIntent();

        availabilityData = new ArrayList<>();
        currentTime = new Date(System.currentTimeMillis());
        currentMonth = currentTime.getMonth();
        currentDate = currentTime.getDate();

        TextView mTextViewName = findViewById(R.id.text_doctor_profile_name);
        TextView mTextViewAddress = findViewById(R.id.text_doctor_profile_address);
        mRecyclerDateList = findViewById(R.id.list_schedule_days);
        mListViewAvailability= findViewById(R.id.list_schedule_availability);

        mTextViewName.setText(intent.getStringExtra("doctorName"));
        mTextViewAddress.setText(intent.getStringExtra("doctorAddress"));

        appointmentDao = new AppointmentDao(this);
        appointments = appointmentDao.findAllByDoctor("1");
//        intent.getStringArrayExtra("doctorId");



        String[] from = {"time", "status"};

        int[] to = {R.id.text_availability_time, R.id.text_availability_status};

        availabilitySimpleAdapter = new SimpleAdapter(this,availabilityData,R.layout.item_availability_slot,from,to);
        mListViewAvailability.setAdapter(availabilitySimpleAdapter);

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
                        booked.add(appointmentDate.getHours() - 1);
                    }
                }

                //make changes to the availability list using the newly created booked appointments
                resetAvailabilityData(availabilityData,booked);
                //notify the adapter of the dataset changes
                availabilitySimpleAdapter.notifyDataSetChanged();
            }
        };

        //create a new date recycler list view adapter, passing the onDateClick lister we created
        dateRecyclerViewAdapter = new DateRecyclerViewAdapter(dateListener);

        //set the adapter to the recycler list view component
        mRecyclerDateList.setAdapter(dateRecyclerViewAdapter);

//        mRecyclerDateList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                TextView mTextViewDate = v.findViewById(R.id.text_schedule_date);
//                Toast.makeText(DoctorInfoActivity.this, mTextViewDate.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                //update availability data to reflect availability for the selected date
//
//                    //cycle through appointments
//                    // if appointment date == selected date
//                        //add to hash map
//
//
//                //notify adapter of changes
//            }
//        });
    }

    private void resetAvailabilityData(List<Map<String,String>> data,List<Integer> appointmentTimes){

        //clear any data we currently have in our availabilty list
        data.clear();

        //loop through the business hours
        for (int i = 9; i < 18 ; i++) {

            //create a hashmap
            Map<String,String> map = new HashMap<>();

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
}
