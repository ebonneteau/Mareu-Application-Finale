package com.example.mareu.controller;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;
import com.example.mareu.service.ReuApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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

    //Other UI components
    private Button mValidationButton;
    private ImageView mAttendeeAddButton;

    //Recyclers
    private RecyclerView mAttendeesRecyclerView;
    private RecyclerView mPlacesRecyclerView;
    private AttendeesRecyclerViewAdapter mAttendeesRecyclerViewAdapter;
    private BookingPlaceRecyclerViewAdapter mPlacesRecyclerViewAdapter;

    //Needed values for new meeting creation
    private String mMeetingObject;
    private String mStartTime;
    private String mEndTime;
    private String mSelectedPlace;
    private EditText mMeetingObjectInput;
    private EditText mAttendeeNameAdded;
    private List<Attendees> mAttendees = new ArrayList<>();
    private List<Places> mBookingPlaces;

    //Other
    private ReuApiService mApiService;
    private static final String TAG = "BookingActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        //soft input mode (manifest entry needed: android:windowSoftInputMode="adjustResize"
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        //Add this on the needed view: android:fitsSystemWindows="true"
        
        //De activate Validation until one attendee is entered
        mValidationButton = findViewById(R.id.validation_button);
        mValidationButton.setEnabled(false);

        mMeetingObjectInput = findViewById(R.id.meeting_object);
        mAttendeeAddButton = findViewById(R.id.add_attendee_in_list);
        mAttendeeNameAdded = findViewById(R.id.attendees_added_name);

        // Attendees RecyclerView
        // Get a handle to the RecyclerView.
        mAttendeesRecyclerView = findViewById(R.id.attendees_recycler_view);
        // Create an adapter and supply the data to be displayed.
        mAttendeesRecyclerViewAdapter = new AttendeesRecyclerViewAdapter(this, mAttendees);
        // Connect the adapter with the RecyclerView.
        mAttendeesRecyclerView.setAdapter(mAttendeesRecyclerViewAdapter);
        // Give the RecyclerView a default layout manager.
        mAttendeesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // BackButton
        // Add back button with option requireNonNull
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mStartTimeButton = findViewById(R.id.clock_start_time);

        // Places RecyclerView
        // Get a handle to the Places RecyclerView.
        mPlacesRecyclerView = findViewById(R.id.meeting_room);
        // Create an adapter and supply the data to be displayed.
        mPlacesRecyclerViewAdapter = new BookingPlaceRecyclerViewAdapter(this, mBookingPlaces,
                places -> {
                    mSelectedPlace = places.getPlace();
                    Log.d(TAG, "ClickedValue is:" + mSelectedPlace);

                });
        // Connect the adapter with the RecyclerView.
        mPlacesRecyclerView.setAdapter(mPlacesRecyclerViewAdapter);
        // Give the RecyclerView a default layout manager.
        mPlacesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Start timePicker
        mStartTimeButton.setOnClickListener(view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "from";
            DialogFragment startTimePicker = new StartTimePickerFragment();
            startTimePicker.show(getSupportFragmentManager(), "Start_Time_Picker");
        });

        //End timePicker
        mEndTimeButton = findViewById(R.id.clock_end_time);
        mEndTimeButton.setOnClickListener(view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "to";
            DialogFragment endTimePicker = new EndTimePickerFragment();
            endTimePicker.show(getSupportFragmentManager(), "End_Time_Picker");
        });

        //Attendees add button
        mAttendeeAddButton.setOnClickListener(view -> {

            String emailAddress = mAttendeeNameAdded.getText().toString();
            Attendees attendees = new Attendees(mAttendees.size(), emailAddress);
            mAttendees.add(attendees);
            Log.d(TAG, "mAttendees size: " + mAttendees.size());
            Objects.requireNonNull(mAttendeesRecyclerView.getAdapter()).notifyDataSetChanged();
            //Reactivate validate button if all values entered
            //if ((mMeetingObjectInput.getText()) != null && mAttendees.size()> 0 && mSelectedPlace != null
            //        && mStartTime != null && mMeetingEndTime != null){
            //    mValidationButton.setEnabled(true);
            //}
            mValidationButton.setEnabled(true);

        });


        mValidationButton.setOnClickListener(view -> {

            mMeetingObject = String.valueOf(mMeetingObjectInput.getText());
            mApiService = DI.getReuApiService();
            mApiService.addMeeting(new Meetings(mApiService.getMeetings().size(),
                    mMeetingObject, mStartTime, mEndTime, mSelectedPlace, mAttendees));
            finish();

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
            //Always set time as a String with 2 digits number format
            mMeetingStartTime.setText(new StringBuilder().append("Meeting starts at: ")
                    .append(String.format(Locale.FRANCE, "%02d", Hour)).append(":")
                    .append(String.format(Locale.FRANCE, "%02d", Minute)).toString());
            mStartHour = Hour;
            mStartMinute = Minute;
            //Always set time as a String with 2 digits number format
            mStartTime = String.format(Locale.FRANCE, "%02d", mStartHour)
                    + "h" + String.format(Locale.FRANCE, "%02d", mStartMinute);
            calculateMeetingEndTime();

        }
        if (Objects.equals(timeTag, "to")) {
            mMeetingEndTime = findViewById(R.id.meeting_end_time);
            //Always set time as a String with 2 digits number format
            mMeetingEndTime.setText(new StringBuilder().append("Meeting starts at: ")
                    .append(String.format(Locale.FRANCE, "%02d", Hour)).append(":")
                    .append(String.format(Locale.FRANCE, "%02d", Minute)).toString());
            mEndHour = Hour;
            mEndMinute = Minute;
            //Always set time as a String with 2 digits number format
            mEndTime = String.format(Locale.FRANCE, "%02d", mEndHour)
                    + ("h") + (String.format(Locale.FRANCE, "%02d", mEndMinute));

        }

    }

    public void calculateMeetingEndTime() {
        if (mStartMinute + 45 > 60) {
            mEndMinute = mStartMinute - 15;

            mEndHour = mStartHour + 1;
            mMeetingEndTime = findViewById(R.id.meeting_end_time);
            mMeetingEndTime.setText(new StringBuilder().append("Meeting ends at: ")
                    .append(String.format(Locale.FRANCE, "%02d", mEndHour)).append(":")
                    .append(String.format(Locale.FRANCE, "%02d", mEndMinute)).toString());

        }
        if (mStartMinute + 45 == 60) {
            mEndMinute = 0;
            mEndHour = mStartHour + 1;
            mMeetingEndTime = findViewById(R.id.meeting_end_time);
            mMeetingEndTime.setText(new StringBuilder().append("Meeting ends at: ")
                    .append(String.format(Locale.FRANCE, "%02d", mEndHour)).append(":")
                    .append(String.format(Locale.FRANCE, "%02d", mEndMinute)).toString());
        }
        if (mStartMinute + 45 < 60) {
            mEndMinute = mStartMinute + 45;
            mEndHour = mStartHour;
            mMeetingEndTime = findViewById(R.id.meeting_end_time);
            mMeetingEndTime.setText(new StringBuilder().append("Meeting ends at: ")
                    .append(String.format(Locale.FRANCE, "%02d", mEndHour)).append(":")
                    .append(String.format(Locale.FRANCE, "%02d", mEndMinute)).toString());
        }
    }

}
