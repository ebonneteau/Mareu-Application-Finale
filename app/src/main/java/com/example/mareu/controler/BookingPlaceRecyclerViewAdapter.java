package com.example.mareu.controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.model.Places;
import com.example.mareu.service.ColorGenerator;
import com.example.mareu.service.ReuApiService;

import java.util.List;


public class BookingPlaceRecyclerViewAdapter extends RecyclerView.Adapter<BookingPlaceRecyclerViewAdapter.PlacesViewHolder> {

    private List<Places> mBookingPlaces;
    private LayoutInflater mInflater;
    private ReuApiService mApiService;
    private static final String TAG = "RecyclerPlaces";
    private ColorGenerator generator = ColorGenerator.MATERIAL;
    private OnSelectedBookedRoom mBookedRoom;

    public BookingPlaceRecyclerViewAdapter(Context context, List<Places> places, OnSelectedBookedRoom bookedRoom ) {
        mInflater = LayoutInflater.from(context);
        this.mBookedRoom = bookedRoom;
        this.mBookingPlaces = places;
    }

    @NonNull
    @Override
    public BookingPlaceRecyclerViewAdapter.PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.fragment_booking_place_list, parent, false);
        return new PlacesViewHolder (mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {

        Places places = mBookingPlaces.get(position);
        holder.mPlacesListView.setText(places.getPlace());
        holder.mPlacesListView.setTextColor(generator.getRandomColor());
        holder.itemView.setOnClickListener(view -> {
            mBookedRoom.onClick(places);

        });

    }

    @Override
    public int getItemCount() {
        //Initiate List with api service
        mApiService = DI.getReuApiService();
        mBookingPlaces = mApiService.getPlaces();
        return mBookingPlaces.size();

    }
    class PlacesViewHolder extends RecyclerView.ViewHolder{
        public final TextView mPlacesListView;
        final BookingPlaceRecyclerViewAdapter mAdapter;

        public PlacesViewHolder(@NonNull View itemView, BookingPlaceRecyclerViewAdapter adapter ) {
            super(itemView);
            mPlacesListView = itemView.findViewById(R.id.a_place);
            this.mAdapter = adapter;
        }
    }
    //interface to rescue selected booked room in BookingActivity
    public interface OnSelectedBookedRoom {
         void onClick(Places places);
    }
}
