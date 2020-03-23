package com.example.mareu.controller;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.service.ColorGenerator;
import com.example.mareu.service.ReuApiService;


import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private final List<Meetings> mMeetings;

    private ReuApiService mApiService;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private static final String TAG = "MeetingsRecyclerView";
    private String mMeetingObjectSeparator;


    MeetingsRecyclerViewAdapter(List<Meetings> items) {
        mMeetings = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meetings, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Meetings meetings = mMeetings.get(position);

        holder.mMeetingPlace.setText(meetings.getPlace());
        //Create the separator after mMeetingObject
        holder.mMeetingObject.setText(new StringBuilder().append(meetings.getObject()).append(" - ").toString());
        //Create the separator after mMeetingStartTime
        holder.mMeetingStartTime.setText(new StringBuilder().append(meetings.getStartTime()).append(" - ").toString());

        Glide.with(holder.mPlaceHolder.getContext())
        .load(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mPlaceHolder);
        //Generate random colors on placeHolder
        holder.mPlaceHolder.setColorFilter(generator.getRandomColor());
        mApiService = DI.getReuApiService();
        //Create a separator between 2 attendees
        StringBuilder attendeesLookInList = new StringBuilder();
        for (Attendees mBookedAttendees : meetings.getAttendees()){
            if (meetings.getAttendees().indexOf(mBookedAttendees) != 0){
                attendeesLookInList.append(" , ");
            }
            attendeesLookInList.append(mBookedAttendees.getMailAddress());
        }
        holder.mMeetingBookedAttendees.setText(attendeesLookInList.toString());

        holder.mDeleteButton.setOnClickListener(v -> {
            mApiService = DI.getReuApiService();
            EventBus.getDefault().post(new DeleteMeetingEvent(meetings));

        });

        //method to view details on item click
        holder.mMeetingPlace.setOnClickListener(v -> {

            Intent intent = new Intent(holder.mMeetingPlace.getContext(), MeetingDetailsActivity.class);

            intent.putExtra("item_meeting_place", meetings.getPlace());


            //Launch MeetingDetails activity
            holder.mMeetingPlace.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_place)
        public TextView mMeetingPlace;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.item_list_meetings_place_holder)
        public ImageView mPlaceHolder;
        @BindView(R.id.item_list_meeting_object)
        public TextView mMeetingObject;
        @BindView(R.id.item_list_start_time)
        public TextView mMeetingStartTime;
        @BindView(R.id.item_list_booked_attendees)
        public TextView mMeetingBookedAttendees;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
