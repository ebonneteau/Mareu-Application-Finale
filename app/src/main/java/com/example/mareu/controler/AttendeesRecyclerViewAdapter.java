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
import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.events.DeleteAttendeesEvent;
import com.example.mareu.model.Attendees;
import com.example.mareu.service.ReuApiService;


import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;




public class AttendeesRecyclerViewAdapter extends RecyclerView.Adapter<AttendeesRecyclerViewAdapter.ViewHolder> {

    private final List<Attendees> mAttendees;
    private ReuApiService mApiService;


    AttendeesRecyclerViewAdapter(List<Attendees> items) {
        mAttendees = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_attendees, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Attendees attendees = mAttendees.get(position);
        holder.mAttendeesName.setText(attendees.getPlace());
        Glide.with(holder.mAttendeesName.getContext())
              .load(R.drawable.ic_launcher_foreground)
              .apply(RequestOptions.circleCropTransform())
              .into(holder.mFakeImage);


        holder.mDeleteButton.setOnClickListener(v -> {
            mApiService = DI.getReuApiService();
            EventBus.getDefault().post(new DeleteAttendeesEvent(attendees));

        });

        //method to view details on item click
        holder.mAttendeesName.setOnClickListener(v -> {

            Intent intent = new Intent(holder.mAttendeesName.getContext(), AttendeeDetailsActivity.class);

            intent.putExtra("item_list_name", attendees.getName());
            intent.putExtra("item_list_id", attendees.getId());

            //Launch NeighborDetails activity
            holder.mAttendeesName.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mAttendees.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_list_name)
        public TextView mAttendeesName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;
        @BindView(R.id.item_list_attendees)
                public ImageView mFakeImage;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
