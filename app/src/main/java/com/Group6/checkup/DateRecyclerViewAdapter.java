package com.Group6.checkup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateRecyclerViewAdapter extends RecyclerView.Adapter<DateRecyclerViewAdapter.DateRecyclerViewHolder> {

    //Dataset
    List<Long> unixTime;
    List<String> dateRange;
    OnDateClickListener onDateClickListener;
    int selected = -1;
    int previouslySelected = -1;
    boolean hasBeenSelected = false;

    public DateRecyclerViewAdapter(OnDateClickListener onDateClickListener) {

        this.onDateClickListener = onDateClickListener;
        dateRange = new ArrayList<>();
        unixTime = new ArrayList<>();
        long currentTime = System.currentTimeMillis();
        unixTime.add(currentTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d");

        dateRange.add("Today");

        for (int i = 0; i < 30 ; i++) {
            currentTime = currentTime + 86400000;
            unixTime.add(currentTime);
            Date date = new Date(currentTime);
            String dateText = dateFormat.format(date);
            dateRange.add(dateText);

        }

    }

    @NonNull
    @Override
    public DateRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //inflate a view from item_date.xml layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date,parent,false);

        // return a view holder
        return new DateRecyclerViewHolder(view, onDateClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DateRecyclerViewHolder holder, int position) {
        //data to bind
        holder.mTextView.setText(dateRange.get(position));

        //if the view position is the same as the selected position
            //draw underline
        if(!hasBeenSelected){
            holder.mViewUnderline.setVisibility(View.INVISIBLE);
        } else {
            holder.mViewUnderline.setVisibility(View.INVISIBLE);
            if (holder.getAdapterPosition() == previouslySelected) {
                holder.mViewUnderline.setVisibility(View.INVISIBLE);
            }
            if(holder.getAdapterPosition() == selected) {
                holder.mViewUnderline.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return dateRange.size();
    }

    public static class DateRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //UI components (item layout)
        TextView mTextView;
        View mViewUnderline;
        OnDateClickListener onDateClickListener;

        private DateRecyclerViewHolder(@NonNull View itemView, OnDateClickListener onDateClickListener) {
            super(itemView);
            this.onDateClickListener = onDateClickListener;

            //view (item layout)
            mTextView = itemView.findViewById(R.id.text_schedule_date);
            itemView.setOnClickListener(this);
            mViewUnderline = itemView.findViewById(R.id.view_selected_date);

        }

        @Override
        public void onClick(View v) {
            onDateClickListener.onDateClick(getAdapterPosition());
            mViewUnderline.setVisibility(View.VISIBLE);
        }

    }

    public interface OnDateClickListener {
        void onDateClick(int position);
    }

}
