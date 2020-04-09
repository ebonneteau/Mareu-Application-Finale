package com.example.mareu.controller.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.controller.adapters.PlacesRecyclerViewAdapter;
import com.example.mareu.events.DeletePlaceEvent;
import com.example.mareu.model.Places;
import com.example.mareu.service.ReuApiService;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;


public class PlacesFragment extends Fragment {

    private ReuApiService mApiService;
    private List<Places> mPlaces;
    private RecyclerView mRecyclerView;


    /**
     * Create and return a new instance
     *
     * @param i
     * @return @{@link PlacesFragment}
     */
    public static PlacesFragment newInstance(int i) {
        PlacesFragment fragment;
        fragment = new PlacesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        mApiService = DI.getReuApiService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_places_list, container, false );

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager( new LinearLayoutManager( context ) );
        //Added requireNonNull option
        mRecyclerView.addItemDecoration( new DividerItemDecoration( Objects.requireNonNull( getContext() ), DividerItemDecoration.VERTICAL ) );

        initList();

        return view;
    }

    /**
     * Init the List of places
     */
    private void initList() {

        mPlaces = mApiService.getPlaces();
        mRecyclerView.setAdapter( new PlacesRecyclerViewAdapter( mPlaces ) );
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register( this );
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister( this );
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeletePlace(DeletePlaceEvent event) {
        mApiService.deletePlace( event.places );
        Objects.requireNonNull( mRecyclerView.getAdapter() ).notifyDataSetChanged();

    }


}
