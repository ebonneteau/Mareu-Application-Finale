package com.example.mareu.controller.adapters;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.R;
import com.example.mareu.controller.BookedActivity;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.service.ColorGenerator;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private final List<Meetings> mMeetings;
    private ColorGenerator generator = ColorGenerator.MATERIAL;


    public MeetingsRecyclerViewAdapter(List<Meetings> items) {
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
        //Concatenate "object - startTime - place" in one single holder
        holder.mMeetingFirstLine.setText(new StringBuilder().append(meetings.getObject()).append(" - ")
                .append(meetings.getStartTime()).append(" - ").append(meetings.getPlace()).toString());

        Glide.with(holder.mPlaceHolder.getContext())
                .load(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mPlaceHolder);
        //Generate random colors on placeHolder
        holder.mPlaceHolder.setColorFilter(generator.getColor(position));
        //Create a separator between 2 attendees
        StringBuilder attendeesLookInList = new StringBuilder();
        for (Attendees mBookedAttendees : meetings.getAttendees()) {
            if (meetings.getAttendees().indexOf(mBookedAttendees) != 0) {
                attendeesLookInList.append(" , ");
            }
            attendeesLookInList.append(mBookedAttendees.getMailAddress());
        }
        holder.mMeetingBookedAttendees.setText(attendeesLookInList.toString());

        holder.mDeleteButton.setOnClickListener(v -> {

            EventBus.getDefault().post(new DeleteMeetingEvent(meetings));

        });

        //method to view details on item click
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(holder.itemView.getContext(), BookedActivity.class);

            intent.putExtra("item_meeting_place", meetings.getPlace());
            intent.putExtra("item_meeting_object", meetings.getObject());
            intent.putExtra("item_meeting_start_time", meetings.getStartTime());
            intent.putExtra("item_meeting_end_time", meetings.getEndTime());
            intent.putExtra("list_meeting_attendees",(meetings.getAttendees().toString()));



            //Launch MeetingDetails activity
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.item_list_meetings_place_holder)
        public ImageView mPlaceHolder;
        @BindView(R.id.item_list_meeting_main_line)
        public TextView mMeetingFirstLine;
        @BindView(R.id.item_list_booked_attendees)
        public TextView mMeetingBookedAttendees;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
