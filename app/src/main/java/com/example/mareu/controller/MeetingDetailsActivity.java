package com.example.mareu.controller;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.mareu.R;

import java.util.Objects;

public class MeetingDetailsActivity extends AppCompatActivity {
    private String mSelectedPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Add back button with option requireNonNull
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getIncomingIntent();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_attendee_in_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getIncomingIntent() {
        // This method rescues
        // values from a click on recyclerView
        //  intent.putExtra
        //Don't forget to call this method in the onCreate

        if (getIntent().hasExtra("item_meeting_place")) {
            String selectedPlace = getIntent().getStringExtra("item_meeting_place");
            mSelectedPlace = selectedPlace;
            //Method called to replace some values of layout given fields
            setAllNeededValues(selectedPlace);

        }
    }

    private void setAllNeededValues(String selectedPlace) {

        // By default the CollapsingToolbarLayout name displayed, is the app name.
        // In this case it is "Neighbour Detail"
        // This method displays any given value
        // instead of default title.
        CollapsingToolbarLayout myTitleBar = findViewById(R.id.toolbar_layout);
        myTitleBar.setTitle("Meeting room: " + selectedPlace);

    }

    //Method for home button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
