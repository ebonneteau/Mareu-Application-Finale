package com.example.mareu.controler;


import android.content.Intent;
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
import com.example.mareu.model.Meetings;
import com.example.mareu.service.ReuApiService;


import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    private final List<Meetings> mMeetings;
    private ReuApiService mApiService;


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



        holder.mDeleteButton.setOnClickListener(v -> {
            //mApiService = DI.getReuApiService();
            //EventBus.getDefault().post(new DeleteMeetingEvent(meetings));

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

        @BindView(R.id.item_list_meetings)
        public TextView mMeetingPlace;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        //@BindView(R.id.item_list_place)
                //public ImageView mFakeImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
