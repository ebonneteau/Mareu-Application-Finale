package com.example.mareu.controler;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.model.Attendees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    // TimePickers values
    Button mStartTimeButton;
    Button mEndTimeButton;
    TextView mMeetingStartTime;
    TextView mMeetingEndTime;
    int mStartHour;
    int mStartMinute;
    int mEndHour;
    int mEndMinute;
    //Time Tag String Value for TimePickers
    private String timeTag;
    private EditText mMeetingObjectButton;
    private String mMeetingObject;
    private Button mValidationButton;
    private FloatingActionButton mAttendeeAddButton;
    private EditText mAttendeeNameAdded;
    private RecyclerView mAttendeesRecyclerView;
    private List<Attendees> mAttendees = new ArrayList<>();
    private AttendeesRecyclerViewAdapter mAdapter;
    private static final String TAG = "BookingActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        //De activate Validation until one attendee is entered
        mValidationButton = findViewById(R.id.validation_button);
        mValidationButton.setEnabled(false);
        mMeetingObjectButton = findViewById(R.id.meeting_object);
        mAttendeeAddButton = findViewById(R.id.add_attendee_in_list);
        mAttendeeNameAdded = findViewById(R.id.attendees_added_name);
        // Get a handle to the RecyclerView.
        mAttendeesRecyclerView = findViewById(R.id.attendees_recycler_view);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new AttendeesRecyclerViewAdapter(this,mAttendees);
        // Connect the adapter with the RecyclerView.
        mAttendeesRecyclerView.setAdapter(mAdapter);
        // Give the RecyclerView a default layout manager.
        mAttendeesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add back button with option requireNonNull
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStartTimeButton = findViewById(R.id.clock_start_time);

        mStartTimeButton.setOnClickListener(view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "from";
            DialogFragment startTimePicker = new StartTimePickerFragment();
            startTimePicker.show(getSupportFragmentManager(), "Start_Time_Picker");
        });
        mEndTimeButton = findViewById(R.id.clock_end_time);
        mEndTimeButton.setOnClickListener(view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "to";
            DialogFragment endTimePicker = new EndTimePickerFragment();
            endTimePicker.show(getSupportFragmentManager(), "End_Time_Picker");
        });
        mAttendeeAddButton.setOnClickListener(view -> {

            String emailAddress = mAttendeeNameAdded.getText().toString();
            Attendees attendees = new Attendees(null, emailAddress);
            mAttendees.add(attendees);
            Objects.requireNonNull(mAttendeesRecyclerView.getAdapter()).notifyDataSetChanged();
            //Reactivate validate button
            mValidationButton.setEnabled(true);


        } );
        mMeetingObjectButton.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        mValidationButton.setOnClickListener(view -> {
            mMeetingObject = String.valueOf(mMeetingObjectButton.getText());
            Log.d(TAG, "meeting object: " + mMeetingObject);

        });
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


    @Override
    public void onTimeSet(TimePicker startTimePicker, int Hour, int Minute) {
        if (Objects.equals(timeTag, "from")) {
            mMeetingStartTime = findViewById(R.id.meeting_start_time);
            mMeetingStartTime.setText("Meeting starts at: " + Hour + ":" + Minute);
            mStartHour = Hour;
            mStartMinute = Minute;
        }
        if (Objects.equals(timeTag, "to")) {
            mMeetingEndTime = findViewById(R.id.meeting_end_time);
            mMeetingEndTime.setText("Meeting ends at: " + Hour + ":" + Minute);
            mEndHour = Hour;
            mEndMinute = Minute;


        }

    }






}
