package com.example.mareu.controler;


import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

//import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.R;
import com.example.mareu.model.Places;
import com.example.mareu.service.ColorGenerator;
import com.example.mareu.service.ReuApiService;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlacesRecyclerViewAdapter extends RecyclerView.Adapter<PlacesRecyclerViewAdapter.ViewHolder> {

    private final List<Places> mPlaces;
    private ReuApiService mApiService;
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    ImageView mFakeImage;


    PlacesRecyclerViewAdapter(List<Places> items) {
        mPlaces = items;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_places, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Places places = mPlaces.get(position);
        holder.mMeetingPlace.setText(places.getPlace());



        Glide.with(holder.mFakeImageHolder.getContext())
         .load(R.drawable.ic_launcher_background)
         .apply(RequestOptions.circleCropTransform())
         .into(holder.mFakeImageHolder);
        //Generate random colors on placeHolder
        holder.mFakeImageHolder.setColorFilter(generator.getRandomColor());




        holder.mDeleteButton.setOnClickListener(v -> {
            //mApiService = DI.getReuApiService();
            //EventBus.getDefault().post(new DeleteMeetingEvent(meetings));

        });

        //method to view details on item click
        holder.mMeetingPlace.setOnClickListener(v -> {

            Intent intent = new Intent(holder.mMeetingPlace.getContext(), MeetingDetailsActivity.class);
            intent.putExtra("item_meeting_place", places.getPlace());


            //Launch MeetingDetails activity
            holder.mMeetingPlace.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_places)
        public ImageView mFakeImageHolder;
        @BindView(R.id.item_list_place)
        public TextView mMeetingPlace;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
