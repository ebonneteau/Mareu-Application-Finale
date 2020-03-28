package com.example.mareu.controller.adapters;

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
    private int selected_position = -1;
    private int old_position = -1;

    public BookingPlaceRecyclerViewAdapter(Context context, List<Places> places, OnSelectedBookedRoom bookedRoom ) {
        mInflater = LayoutInflater.from(context);
        this.mBookedRoom = bookedRoom;
        this.mBookingPlaces = places;
    }

    @NonNull
    @Override
    public BookingPlaceRecyclerViewAdapter.PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.fragment_booked_place_item, parent, false);

        return new PlacesViewHolder (mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesViewHolder holder, int position) {
        int mPosition = holder.getAdapterPosition();
        Places places = mBookingPlaces.get(position);
        holder.mPlacesListView.setText(places.getPlace());
        //holder.mPlacesListView.setTextColor(generator.getRandomColor());
        holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.item_un_selected));
        holder.itemView.setOnClickListener(view -> {

            //change color on clicked item
            if ((selected_position == -1)&& (old_position == -1)){
                //notifyDataSetChanged();
                holder.itemView.setBackgroundColor(view.getResources().getColor(R.color.button_pressed_false));
                mBookedRoom.onClick(places);
                Snackbar.make(view, "Confirmation: " + holder.mPlacesListView.getText() + " is booked !" , Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                selected_position = mPosition;
                old_position = mPosition;
                return;

            }
            if (selected_position == mPosition && old_position == mPosition){
                holder.itemView.setBackgroundColor(view.getResources().getColor(R.color.item_un_selected));
                mBookedRoom.onClick(places);
                Snackbar.make(view,  "Confirmation: " + holder.mPlacesListView.getText() + " is cancelled !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                old_position = -1;
                selected_position = -1;
                return;

            }if (old_position != mPosition && selected_position != mPosition ){
                notifyDataSetChanged();
                mBookedRoom.onClick(places);
                old_position = -1;
                selected_position = -1;

                Snackbar.make(view,  " PLEASE SELECT A ROOM", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

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
