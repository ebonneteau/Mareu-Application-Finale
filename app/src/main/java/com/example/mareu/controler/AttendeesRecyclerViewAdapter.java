package com.example.mareu.controler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mareu.R;
import com.example.mareu.model.Attendees;

import java.util.LinkedList;
import java.util.List;


public class AttendeesRecyclerViewAdapter extends RecyclerView.Adapter<AttendeesRecyclerViewAdapter.AttendeesViewHolder> {

    private final List<Attendees> mAttendees;
    private LayoutInflater mInflater;
    private static final String TAG = "RecyclerAttendee";

    public AttendeesRecyclerViewAdapter(Context context, List<Attendees> attendees ) {
        mInflater = LayoutInflater.from(context);
        this.mAttendees = attendees;
    }

    @NonNull
    @Override
    public AttendeesRecyclerViewAdapter.AttendeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.attendee_item_list, parent, false);
        return new AttendeesViewHolder (mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull AttendeesViewHolder holder, int position) {
        Attendees attendees = mAttendees.get(position);
        holder.mAttendeesListView.setText(attendees.getmailAddress());

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "Attendess list size"  +mAttendees.size() );
        return mAttendees.size();

    }
    class AttendeesViewHolder extends RecyclerView.ViewHolder{
        public final TextView mAttendeesListView;
        final AttendeesRecyclerViewAdapter mAdapter;

        public AttendeesViewHolder(@NonNull View itemView,AttendeesRecyclerViewAdapter adapter ) {
            super(itemView);
            mAttendeesListView = itemView.findViewById(R.id.an_attendee);
            this.mAdapter = adapter;
        }
    }
}
