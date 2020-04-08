package com.example.mareu.controller;


import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.mareu.R;
import com.example.mareu.service.ReuApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewExternalMethods.waitFor;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BookingMeetingActivityFullUITest {
    /**
     * Api service
     */
    private ReuApiService service;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>( MainActivity.class );

    @After
    public void deleteData(){
        onView(isRoot()).perform(waitFor(700));
        onView( ViewMatchers.withId(R.id.fragment_item_list_meetings))
                .perform( RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(isRoot()).perform(waitFor(1000));

    }

    @Test
    public void bookingMeetingActivityFullUITest() {

        /*
         * Given the click on "Add meeting button"
         */

        ViewInteraction imageButton = onView(
                allOf( withId( R.id.add_meeting ),
                        childAtPosition(
                                allOf( withId( R.id.main_content ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                2 ),
                        isDisplayed() ) );
        imageButton.check( matches( isDisplayed() ) );

        ViewInteraction imageButton2 = onView(
                allOf( withId( R.id.add_meeting ),
                        childAtPosition(
                                allOf( withId( R.id.main_content ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                2 ),
                        isDisplayed() ) );
        imageButton2.check( matches( isDisplayed() ) );

        ViewInteraction floatingActionButton = onView(
                allOf( withId( R.id.add_meeting ),
                        childAtPosition(
                                allOf( withId( R.id.main_content ),
                                        childAtPosition(
                                                withId( android.R.id.content ),
                                                0 ) ),
                                2 ),
                        isDisplayed() ) );
        floatingActionButton.perform( click() );

        //Wait a bit
        onView(isRoot()).perform(waitFor(700));

        /*
          When we fill all required fields - here meeting object
         */

        ViewInteraction EditTextMeetingObject = onView(
                allOf( withId( R.id.editText_meeting_object ),
                        childAtPosition(
                                allOf( withId( R.id.cardView_meeting_object_container ),
                                        childAtPosition(
                                                withClassName( is( "android.widget.LinearLayout" ) ),
                                                0 ) ),
                                0 ),
                        isDisplayed() ) );
        EditTextMeetingObject.perform( replaceText( "Sujet A" ), closeSoftKeyboard() );

        /*
          Select at least meeting start time (end time is auto-suggest according to start time (plus 45 minutes)
         */

        ViewInteraction ButtonStartTime = onView(
                allOf( withId( R.id.clock_start_time_button ), withText( "Set Start Time" ),
                        childAtPosition(
                                allOf( withId( R.id.CardView_time_container ),
                                        childAtPosition(
                                                withClassName( is( "android.widget.LinearLayout" ) ),
                                                2 ) ),
                                2 ),
                        isDisplayed() ) );
        ButtonStartTime.perform( click() );

        ViewInteraction ButtonOkTimePickerStartTime = onView(
                allOf( withId( android.R.id.button1 ), withText( "OK" ),
                        childAtPosition(
                                childAtPosition(
                                        withClassName( is( "android.widget.ScrollView" ) ),
                                        0 ),
                                3 ) ) );
        ButtonOkTimePickerStartTime.perform( scrollTo(), click() );

        ViewInteraction textViewEndTime = onView(
                allOf( withId( R.id.meeting_end_time_text ),
                        childAtPosition(
                                allOf( withId( R.id.CardView_time_container ),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf( android.widget.LinearLayout.class ),
                                                2 ) ),
                                4 ),
                        isDisplayed() ) );

        textViewEndTime.perform(swipeUp());
        textViewEndTime.perform(swipeUp());
        onView(isRoot()).perform(waitFor(700));

        /*
          Select a meeting room
         */

        ViewInteraction PlacesRecyclerView = onView(
                allOf( withId( R.id.recycler_meeting_room ),
                        childAtPosition(
                                withId( R.id.cardView_meeting_room_recycler_container ),
                                0 ) ) );
        PlacesRecyclerView.perform( actionOnItemAtPosition( 1, click() ) );

        ViewInteraction PlacesLinearLayout4 = onView(
                allOf( childAtPosition(
                        allOf( withId( R.id.recycler_meeting_room ),
                                childAtPosition(
                                        withId( R.id.cardView_meeting_room_recycler_container ),
                                        0 ) ),
                        1 ),
                        isDisplayed() ) );
        PlacesLinearLayout4.check( matches( isDisplayed() ) );


        /*
          Add at least one attendee (this makes validation button to show up)
         */

        ViewInteraction textView = onView(
                allOf( withId( R.id.textView_attendee_title_container ), withText( "ADD ATTENDEES" ),
                        childAtPosition(
                                allOf( withId( R.id.cardView_add_attendees_title_container ),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf( android.widget.LinearLayout.class ),
                                                5 ) ),
                                0 ),
                        isDisplayed() ) );
        textView.check( matches( withText( "ADD ATTENDEES" ) ) );
        textView.perform(swipeUp());
        textView.perform(swipeUp());
        textView.perform(swipeUp());
        textView.perform(swipeUp());


        ViewInteraction appCompatEditText = onView(
                allOf( withId( R.id.editText_attendees_added_name_input_text ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.cardView_input_attendees_mailAddress ),
                                        0 ),
                                0 ),
                        isDisplayed() ) );
        appCompatEditText.perform( replaceText( "rico@gmail.com" ), closeSoftKeyboard() );

        ViewInteraction imageView = onView(
                allOf( withId( R.id.imageView_add_attendee_in_list ),
                        childAtPosition(
                                allOf( withId( R.id.cardView_input_attendees_mailAddress ),
                                        childAtPosition(
                                                IsInstanceOf.<View>instanceOf( android.widget.LinearLayout.class ),
                                                6 ) ),
                                1 ),
                        isDisplayed() ) );

        imageView.check( matches( isDisplayed() ) );
        onView(isRoot()).perform(waitFor(700));
        imageView.perform( click() );
        onView(isRoot()).perform(waitFor(700));

        ViewInteraction appCompatEditText2 = onView(
                allOf( withId( R.id.editText_attendees_added_name_input_text ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.cardView_input_attendees_mailAddress ),
                                        0 ),
                                0 ),
                        isDisplayed() ) );
        appCompatEditText2.perform( replaceText( "fredo@gmail.com" ), closeSoftKeyboard() );

        ViewInteraction appCompatImageView2 = onView(
                allOf( withId( R.id.imageView_add_attendee_in_list ),
                        childAtPosition(
                                allOf( withId( R.id.cardView_input_attendees_mailAddress ),
                                        childAtPosition(
                                                withClassName( is( "android.widget.LinearLayout" ) ),
                                                6 ) ),
                                1 ),
                        isDisplayed() ) );
        onView(isRoot()).perform(waitFor(700));
        appCompatImageView2.perform( click() );
        onView(isRoot()).perform(waitFor(700));


        /*
          Click on validate
         */

        ViewInteraction appCompatButton5 = onView(
                allOf( withId( R.id.button_validation ), withText( "VALIDATE" ),
                        childAtPosition(
                                childAtPosition(
                                        withClassName( is( "android.support.v4.widget.NestedScrollView" ) ),
                                        0 ),
                                8 ),
                        isDisplayed() ) );
        appCompatButton5.perform( click() );

        //Wait a bit
        onView(isRoot()).perform(waitFor(2000));

        /*
          Then the expected meeting shows in Meetings recycler fragment
         */

        ViewInteraction viewGroup = onView(
                allOf( withId( R.id.parent_layout ),
                        childAtPosition(
                                allOf( withId( R.id.fragment_item_list_meetings ),
                                        withParent( withId( R.id.container ) ) ),
                                0 ),
                        isDisplayed() ) );
        viewGroup.check( matches( isDisplayed() ) );


    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText( "Child at position " + position + " in parent " );
                parentMatcher.describeTo( description );
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches( parent )
                        && view.equals( ((ViewGroup) parent).getChildAt( position ) );
            }
        };
    }
    /*
     * Perform action of waiting for a specific time.
     */

}
