package com.example.mareu.controller.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mareu.R;
import com.example.mareu.model.Attendees;

import java.util.List;


public class BookingAttendeeRecyclerViewAdapter extends RecyclerView.Adapter<BookingAttendeeRecyclerViewAdapter.AttendeesViewHolder> {

    private final List<Attendees> mAttendees;
    private LayoutInflater mInflater;
    private static final String TAG = "RecyclerAttendee";
    private onSelectedRemovedAttendee mRemovedAttendee;
    private ImageView mRemovedButton;

    public BookingAttendeeRecyclerViewAdapter(Context context, List<Attendees> attendees, onSelectedRemovedAttendee removedAttendee   ) {
        mInflater = LayoutInflater.from(context);
        this.mAttendees = attendees;
        this.mRemovedAttendee = removedAttendee;
    }

    @NonNull
    @Override
    public BookingAttendeeRecyclerViewAdapter.AttendeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.fragment_booked_attendee_item, parent, false);
        return new AttendeesViewHolder (mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesViewHolder holder, int position) {
        Attendees attendees = mAttendees.get(position);
        holder.mAttendeesListView.setText(attendees.getMailAddress());
        mRemovedButton.setOnClickListener(view->{

            mRemovedAttendee.onClick(attendees);
            notifyDataSetChanged();
        });


    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Attendess list size"  +mAttendees.size() );
        return mAttendees.size();

    }
    class AttendeesViewHolder extends RecyclerView.ViewHolder{
        public final TextView mAttendeesListView;
        final BookingAttendeeRecyclerViewAdapter mAdapter;

        public AttendeesViewHolder(@NonNull View itemView, BookingAttendeeRecyclerViewAdapter adapter ) {
            super(itemView);
            mAttendeesListView = itemView.findViewById(R.id.an_attendee);
            mRemovedButton = itemView.findViewById(R.id.attendee_delete_button);
            this.mAdapter = adapter;
        }
    }
    //interface to rescue click on delete button attendee in BookingActivity
    public interface onSelectedRemovedAttendee{
        void onClick (Attendees attendees);
    }
}
