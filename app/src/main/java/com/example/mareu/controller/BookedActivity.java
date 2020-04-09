package com.example.mareu.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.mareu.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookedActivity extends AppCompatActivity {


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
    @BindView(R.id.cardView_meeting_object_container)
    CardView mMeetingObjectCardView;
    @BindView(R.id.cardView_select_meeting_time_title_container)
    CardView mCardViewWhereMeetingObjectIsNowInserted;
    @BindView(R.id.cardView_select_meeting_room_title_container)
    CardView mMeetingRoomCardView;
    @BindView(R.id.cardView_add_attendees_title_container)
    CardView mAddAttendeesTitleCardView;
    @BindView(R.id.cardView_meeting_room_recycler_container)
    CardView mPlacesCardViewRecyclerContainer;
    @BindView(R.id.cardView_recycler_attendees_container)
    CardView mAttendeesCardView;
    @BindView(R.id.cardView_input_attendees_mailAddress)
    CardView mInputAttendeeMailAddress;
    @BindView(R.id.recycler_meeting_room)
    RecyclerView mAttendeesRecyclerView;
    @BindView(R.id.recyclerView_attendees)
    RecyclerView mPlacesRecyclerView;


    //Needed values for meeting consultation
    private String mMeetingObject;
    private String mStartTime;
    private String mEndTime;
    private String mSelectedPlace;
    private String mSelectedAttendees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_booked );
        ButterKnife.bind( this );
        // Add back button with option requireNonNull
        Objects.requireNonNull( getSupportActionBar() ).setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        //Disable not needed fields
        mMeetingObjectInput.setVisibility( View.GONE );
        mMeetingObjectCardView.setVisibility( View.GONE );
        mStartTimeButton.setVisibility( View.GONE );
        mEndTimeButton.setVisibility( View.GONE );
        mAttendeesCardView.setVisibility( View.GONE );
        mAttendeeNameAdded.setVisibility( View.GONE );
        mAttendeesRecyclerView.setVisibility( View.GONE );
        mPlacesRecyclerView.setVisibility( View.GONE );
        mInputAttendeeMailAddress.setVisibility( View.GONE );
        mPlacesCardViewRecyclerContainer.setVisibility( View.GONE );
        //Define needed values for new layout
        int width = LayoutParams.MATCH_PARENT;
        int height = getResources().getDimensionPixelSize( R.dimen.cardView_title_general_height ) * 3;
        int marginLeft = getResources().getDimensionPixelSize( R.dimen.cardViews_general_margin_left_right );
        int marginRight = getResources().getDimensionPixelSize( R.dimen.cardViews_general_margin_left_right );
        int marginTop = getResources().getDimensionPixelSize( R.dimen.cardView_select_meeting_time_title_container_marginTop );
        int marginBottom = getResources().getDimensionPixelSize( R.dimen.cardView_select_meeting_time_title_container_marginBottom );
        //New parameters
        LayoutParams mNewParams = new LayoutParams( width, height );
        mNewParams.setMargins( marginLeft, marginTop, marginRight, marginBottom );

        //Apply to views
        mCardViewWhereMeetingObjectIsNowInserted.setLayoutParams( mNewParams );
        mAddAttendeesTitleCardView.setLayoutParams( mNewParams );

        //Activate  mValidationButton
        mValidationButton.setVisibility( View.VISIBLE );
        mValidationButton.setOnClickListener( view -> finish() );

        getIncomingIntent();
    }


    private void getIncomingIntent() {
        // This method rescues
        // values from a click on recyclerView
        // intent.putExtra
        // Don't forget to call this method in the onCreate

        if (getIntent().hasExtra( "item_meeting_place" )) {
            mSelectedPlace = getIntent().getStringExtra( "item_meeting_place" );
            mMeetingObject = getIntent().getStringExtra( "item_meeting_object" );
            mStartTime = getIntent().getStringExtra( "item_meeting_start_time" );
            mEndTime = getIntent().getStringExtra( "item_meeting_end_time" );
            mSelectedAttendees = getIntent().getStringExtra( "list_meeting_attendees" );


            //Method called to replace some values of layout given fields
            setAllNeededValues();


        }
    }

    private void setAllNeededValues() {

        mValidationButton.setText( getString( R.string.button_validation_gotIt ) );
        mBookingPlaceTextView.setText( new StringBuilder().append( "ROOM SELECTED: " ).append( mSelectedPlace ) );
        mBookingTimeTextView.setText( new StringBuilder().append( "MEETING OBJECT: " ).append( mMeetingObject ) );
        mMeetingStartTime.setText( new StringBuilder().append( "STARTS AT: " ).append( mStartTime ) );
        mMeetingEndTime.setText( new StringBuilder().append( "ENS AT: " ).append( mEndTime ) );
        //Remove first and last char of the string ("[" and "]")
        //To do this I record the string from 2nd (1) position to beforeLast position (length -1)
        mSelectedAttendees = mSelectedAttendees.substring( 1, (mSelectedAttendees.length() - 1) );
        mBookingAttendeesTextView.setText( new StringBuilder().append( "ATTENDEES: " )
                .append( mSelectedAttendees ) );


    }

    //Method for home button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();

        }
        return super.onOptionsItemSelected( item );
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
