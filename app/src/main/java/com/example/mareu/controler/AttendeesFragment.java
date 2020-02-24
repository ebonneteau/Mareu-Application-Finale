package com.example.mareu.controler;

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
import com.example.mareu.events.DeleteAttendeesEvent;
import com.example.mareu.model.Attendees;
import com.example.mareu.service.ReuApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;


public class AttendeesFragment extends Fragment {

    private ReuApiService mApiService;
    private List<Attendees> mAttendees;
    private RecyclerView mRecyclerView;


    /**
     * Create and return a new instance
     *
     * @param i
     * @return @{@link AttendeesFragment}
     */
    public static AttendeesFragment newInstance(int i) {
        AttendeesFragment fragment;
        fragment = new AttendeesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getReuApiService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attendees_list, container, false);

        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Added requireNonNull option
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

        initList();

        return view;
    }

    /**
     * Init the List of neighbours
     */
    private void initList() {

        mAttendees = mApiService.getAttendees();
        mRecyclerView.setAdapter(new AttendeesRecyclerViewAdapter(mAttendees));
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    /**
     * Fired if the user clicks on a delete button
     *
     * @param event
     */
    @Subscribe
    public void onDeleteAttendees (DeleteAttendeesEvent event) {
        mApiService.deleteAttendees(event.attendees);
        Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();

    }




}
