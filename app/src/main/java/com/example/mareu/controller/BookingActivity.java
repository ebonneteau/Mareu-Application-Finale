package com.example.mareu.controller;


import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.controller.adapters.BookingAttendeeRecyclerViewAdapter;
import com.example.mareu.controller.adapters.BookingPlaceRecyclerViewAdapter;
import com.example.mareu.controller.fragments.EndTimePickerFragment;
import com.example.mareu.controller.fragments.StartTimePickerFragment;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;
import com.example.mareu.service.ReuApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BookingActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    // UI Components

    @BindView(R.id.clock_start_time_button)
    Button mStartTimeButton;
    @BindView(R.id.button_validation)
    Button mValidationButton;
    @BindView(R.id.clock_end_time_button)
    Button mEndTimeButton;
    @BindView(R.id.imageView_add_attendee_in_list)
    ImageView mAttendeeAddButton;
    @BindView(R.id.meeting_start_time_textView)
    TextView mMeetingStartTime;
    @BindView(R.id.meeting_end_time_text)
    TextView mMeetingEndTime;
    @BindView(R.id.booking_time_title)
    TextView mBookingTimeTextView;
    @BindView(R.id.textView_card_booking_room_title)
    TextView mBookingPlaceTextView;
    @BindView(R.id.textView_attendee_title_container)
    TextView mBookingAttendeesTextView;
    @BindView(R.id.editText_meeting_object)
    EditText mMeetingObjectInput;
    @BindView(R.id.editText_attendees_added_name_input_text)
    EditText mAttendeeNameAdded;

    // TimePickers values

    private int mStartHour;
    private int mStartMinute;
    private int mEndHour;
    private int mEndMinute;
    private String timeTag;


    //Recyclers
    private RecyclerView mAttendeesRecyclerView;
    private RecyclerView mPlacesRecyclerView;
    private BookingAttendeeRecyclerViewAdapter mBookingAttendeeRecyclerViewAdapter;
    private BookingPlaceRecyclerViewAdapter mPlacesRecyclerViewAdapter;


    //Needed values for new meeting creation
    private String mMeetingObject;
    private String mStartTime;
    private String mEndTime;
    private String mSelectedPlace = null;
    private String mPreviousSelectedPlace = null;
    private List<Attendees> mAttendees = new ArrayList<>();
    private List<Places> mBookingPlaces = new ArrayList<>();

    //Other
    private ReuApiService mApiService;
    private static final String TAG = "BookingActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booking );
        ButterKnife.bind( this );

        //soft input mode (manifest entry needed: android:windowSoftInputMode="adjustResize"
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN );
        //Add this on the needed view: android:fitsSystemWindows="true"

        // BackButton
        // Add back button with option requireNonNull
        Objects.requireNonNull( getSupportActionBar() ).setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        //De activate Validation until one attendee is entered
        mValidationButton.setEnabled( false );
        //De activate add attendee button until one input field is filled
        mAttendeeAddButton.setEnabled( false );
        //Request focus on mMeetingObjectInput EditText
        mMeetingObjectInput.requestFocus();


        //*********
        //Recyclers
        //*********

        // *******************
        // Places RecyclerView
        // *******************

        // Get a handle to the Places RecyclerView.
        mPlacesRecyclerView = findViewById( R.id.recycler_meeting_room );
        // Create an adapter and supply the data to be displayed.
        // Then match Value (or non value) with the recycler visual selection
        mPlacesRecyclerViewAdapter = new BookingPlaceRecyclerViewAdapter( this, mBookingPlaces,
                places -> {

                    if (mPreviousSelectedPlace == null && mSelectedPlace == null) {
                        mSelectedPlace = places.getPlace();
                        mPreviousSelectedPlace = places.getPlace();

                        return;
                    }
                    if (Objects.requireNonNull( mPreviousSelectedPlace ).equals( mSelectedPlace )) {
                        mSelectedPlace = null;
                        mPreviousSelectedPlace = null;


                    }
                    if (mSelectedPlace != null) {
                        mSelectedPlace = places.getPlace();
                        mPreviousSelectedPlace = null;

                    }
                } );
        // Connect the adapter with the RecyclerView.
        mPlacesRecyclerView.setAdapter( mPlacesRecyclerViewAdapter );
        // Give the RecyclerView a default layout manager.
        mPlacesRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        // Add a divider
        mPlacesRecyclerView.addItemDecoration( new DividerItemDecoration( mPlacesRecyclerView.getContext(), DividerItemDecoration.VERTICAL ) );

        // **********************
        // Attendees RecyclerView
        // **********************

        //Activated Add attendees to list button on text input
        mAttendeeNameAdded.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAttendeeAddButton.setVisibility( View.VISIBLE );
                mAttendeeAddButton.setEnabled( s.toString().length() != 0 );
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        } );
        // Get a handle to the RecyclerView.
        mAttendeesRecyclerView = findViewById( R.id.recyclerView_attendees );
        // Create an adapter and supply the data to be displayed.
        mBookingAttendeeRecyclerViewAdapter = new BookingAttendeeRecyclerViewAdapter( this, mAttendees,
                attendees -> mAttendees.remove( attendees ) );


        // Connect the adapter with the RecyclerView.
        mAttendeesRecyclerView.setAdapter( mBookingAttendeeRecyclerViewAdapter );
        // Give the RecyclerView a default layout manager.
        mAttendeesRecyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        // Add a divider
        mAttendeesRecyclerView.addItemDecoration( new DividerItemDecoration( mAttendeesRecyclerView.getContext(), DividerItemDecoration.VERTICAL ) );

        // Attendees add button
        mAttendeeAddButton.setOnClickListener( view -> {

            String emailAddress = mAttendeeNameAdded.getText().toString();
            Attendees attendees = new Attendees( mAttendees.size(), emailAddress );
            mAttendees.add( attendees );
            Log.d( TAG, "mAttendees size: " + mAttendees.size() );
            Objects.requireNonNull( mAttendeesRecyclerView.getAdapter() ).notifyDataSetChanged();
            //Clear text
            mAttendeeNameAdded.getText().clear();
            // Turn Hint text color to blue
            mAttendeeNameAdded.setHintTextColor( getResources().getColor( R.color.myBlue ) );
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( mStartTimeButton.getWindowToken(), 0 );
            //Activate and show Validation Button
            mValidationButton.getOnFocusChangeListener();
            mValidationButton.setFocusable( true );
            mValidationButton.setVisibility( View.VISIBLE );
            mValidationButton.setFocusableInTouchMode( true );
            mValidationButton.requestFocus( View.KEEP_SCREEN_ON );
            mValidationButton.setEnabled( true );


        } );
        //***********
        //TimePickers
        //***********

        //Start timePicker
        mStartTimeButton.setOnClickListener( view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "from";
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( mStartTimeButton.getWindowToken(), 0 );
            DialogFragment startTimePicker = new StartTimePickerFragment();
            startTimePicker.show( getSupportFragmentManager(), "Start_Time_Picker" );
            mStartTimeButton.setText( R.string.meeting_end_time_want_to_change );
            mStartTimeButton.setTextColor( getResources().getColor( R.color.myBlue ) );
        } );
        //End timePicker

        mEndTimeButton.setOnClickListener( view -> {
            //Set string value to check which TimePicker is opened
            timeTag = "to";
            //Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
            imm.hideSoftInputFromWindow( mEndTimeButton.getWindowToken(), 0 );
            DialogFragment endTimePicker = new EndTimePickerFragment();
            endTimePicker.show( getSupportFragmentManager(), "End_Time_Picker" );
        } );
        mValidationButton.setOnClickListener( view -> {
            mMeetingObject = String.valueOf( mMeetingObjectInput.getText() );
            if (mMeetingObject.isEmpty()) {

                mMeetingObjectInput.setFocusableInTouchMode( true );
                mMeetingObjectInput.requestFocus();
                Snackbar.make( view, " MEETING OBJECT MISSING !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return;

            }
            if (mSelectedPlace == null) {
                mBookingPlaceTextView.setFocusableInTouchMode( true );
                mBookingPlaceTextView.requestFocus();
                Snackbar.make( view, " ROOM MISSING !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return;
            }
            if (mStartTime == null) {
                mBookingTimeTextView.setFocusableInTouchMode( true );
                mBookingTimeTextView.requestFocus();
                Snackbar.make( view, " START TIME MISSING !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                return;
            }
            if (mAttendees.size() == 0) {
                mBookingAttendeesTextView.setFocusableInTouchMode( true );
                mBookingAttendeesTextView.requestFocus();
                Snackbar.make( view, " ATTENDEES MISSING !!", Snackbar.LENGTH_LONG )
                        .setAction( "Action", null ).show();
                Log.d( TAG, "size of mAttendees: " + mAttendees.size() );
            } else {
                mApiService = DI.getReuApiService();
                mApiService.addMeeting( new Meetings( mApiService.getMeetings().size(),
                        mMeetingObject, mStartTime, mEndTime, mSelectedPlace, mAttendees ) );
                finish();
            }
        } );
    }
    //*************
    //Other methods
    //*************

    //Method for home button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();

        }
        return super.onOptionsItemSelected( item );
    }

    //Methods for timePickers
    @Override
    public void onTimeSet(TimePicker startTimePicker, int Hour, int Minute) {
        if (Objects.equals( timeTag, "from" )) {

            //Always set time as a String with 2 digits number format
            mMeetingStartTime.setText( new StringBuilder().append( "Meeting starts at: " )
                    .append( String.format( Locale.FRANCE, "%02d", Hour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", Minute ) ).toString() );
            mStartHour = Hour;
            mStartMinute = Minute;
            //Always set time as a String with 2 digits number format
            mStartTime = String.format( Locale.FRANCE, "%02d", mStartHour )
                    + "h" + String.format( Locale.FRANCE, "%02d", mStartMinute );
            calculateMeetingEndTime();
        }
        if (Objects.equals( timeTag, "to" )) {

            //Always set time as a String with 2 digits number format
            mMeetingEndTime.setText( new StringBuilder().append( "Meeting starts at: " )
                    .append( String.format( Locale.FRANCE, "%02d", Hour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", Minute ) ).toString() );
            mEndHour = Hour;
            mEndMinute = Minute;
            //Always set time as a String with 2 digits number format
            mEndTime = String.format( Locale.FRANCE, "%02d", mEndHour )
                    + ("h") + (String.format( Locale.FRANCE, "%02d", mEndMinute ));
        }
    }

    //Calculate end time according to start time given
    public void calculateMeetingEndTime() {
        if (mStartMinute + 45 > 60) {
            mEndMinute = mStartMinute - 15;

            mEndHour = mStartHour + 1;
            mMeetingEndTime = findViewById( R.id.meeting_end_time_text );
            mMeetingEndTime.setText( new StringBuilder().append( "Meeting ends at: " )
                    .append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString() );
            //mEndTime is the same value without text "Meetings ends at: "
            mEndTime = new StringBuilder().append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString();

        }
        if (mStartMinute + 45 == 60) {
            mEndMinute = 0;
            mEndHour = mStartHour + 1;
            mMeetingEndTime = findViewById( R.id.meeting_end_time_text );
            mMeetingEndTime.setText( new StringBuilder().append( "Meeting ends at: " )
                    .append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString() );
            //mEndTime is the same value without text "Meetings ends at: "
            mEndTime = new StringBuilder().append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString();
        }
        if (mStartMinute + 45 < 60) {
            mEndMinute = mStartMinute + 45;
            mEndHour = mStartHour;
            mMeetingEndTime = findViewById( R.id.meeting_end_time_text );
            mMeetingEndTime.setText( new StringBuilder().append( "Meeting ends at: " )
                    .append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString() );
            //mEndTime is the same value without text "Meetings ends at: "
            mEndTime = new StringBuilder().append( String.format( Locale.FRANCE, "%02d", mEndHour ) ).append( ":" )
                    .append( String.format( Locale.FRANCE, "%02d", mEndMinute ) ).toString();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
