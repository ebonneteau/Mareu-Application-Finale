
package com.example.mareu.meetingsList;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.example.mareu.DI.DI;
import com.example.mareu.R;
import com.example.mareu.controller.MainActivity;
import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.service.ReuApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.StringContains;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewExternalMethods.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
public class MeetingsListTest {


    private MainActivity mActivity;
    /**
     * Api service
     */
    private ReuApiService service;




    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule( MainActivity.class );

    @Before
    public void setUp() {
        /* obtaining the Activity from the ActivityTestRule */
        this.mActivity = this.mActivityRule.getActivity();
        //Inject 3 fake meetings
        service = DI.getReuApiService();
        List<Attendees> mAttendees = new ArrayList<>();
        String emailAddress = ("rico@gmail.com");
        Attendees attendees = new Attendees( mAttendees.size(), emailAddress );
        mAttendees.add( attendees );
        service.addMeeting( new Meetings( service.getMeetings().size(),
                "Sujet A", "23h00", "23h45", "Room1", mAttendees ) );
        service.addMeeting( new Meetings( service.getMeetings().size(),
                "Sujet B", "08h00", "09h00", "Place2", mAttendees ) );
        service.addMeeting( new Meetings( service.getMeetings().size(),
                "Sujet C", "10h00", "11h45", "Athena", mAttendees ) );
        // Ensure activity is NOT NULL
        assertThat( mActivity, notNullValue() );


    }




    /**
     * We ensure that our recyclerView is displaying 3 elements
     */
    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        onView( ViewMatchers.withId( R.id.fragment_item_list_meetings ) )
                .check( matches( hasMinimumChildCount( 1 ) ) );
    }
    @Test
    public void myMeetingList_shouldFilterByTime() {
        openActionBarOverflowOrOptionsMenu( getInstrumentation().getTargetContext() );

        ViewInteraction appCompatTextView = onView(
                allOf( withId( R.id.title ), withText( "Sort by time" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.content ),
                                        0 ),
                                0 ),
                        isDisplayed() ) );
        appCompatTextView.perform( click() );
        // When perform a click by time

        ViewInteraction textView2 = onView(
                allOf( withId( R.id.item_list_meeting_main_line ),
                        childAtPosition(
                                allOf( withId( R.id.parent_layout ),
                                        childAtPosition(
                                                withId( R.id.fragment_item_list_meetings ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        textView2.check( matches( withText( StringContains.containsString("08h00"  )  ) ) );





    }
    @Test
    public void myMeetingList_shouldFilterByPlace() {
        openActionBarOverflowOrOptionsMenu( getInstrumentation().getTargetContext() );

        ViewInteraction appCompatTextView = onView(
                allOf( withId( R.id.title ), withText( "Sort by place" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.content ),
                                        0 ),
                                0 ),
                        isDisplayed() ) );
        appCompatTextView.perform( click() );
        // When perform a click by place

        ViewInteraction textView2 = onView(
                allOf( withId( R.id.item_list_meeting_main_line ),
                        childAtPosition(
                                allOf( withId( R.id.parent_layout ),
                                        childAtPosition(
                                                withId( R.id.fragment_item_list_meetings ),
                                                0 ) ),
                                1 ),
                        isDisplayed() ) );
        textView2.check( matches( withText( StringContains.containsString("Athena"  )  ) ) );

    }

    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        int ITEMS_COUNT = service.getMeetings().size() ;
        // Given : We remove the element at position 1
        onView(ViewMatchers.withId(R.id.fragment_item_list_meetings)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.fragment_item_list_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element decreases by 1
        onView(ViewMatchers.withId(R.id.fragment_item_list_meetings)).check(withItemCount(ITEMS_COUNT - 1));

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

}