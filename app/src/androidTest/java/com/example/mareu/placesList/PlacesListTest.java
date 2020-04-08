
package com.example.mareu.placesList;

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
import com.example.mareu.service.ReuApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of favorites
 */
@RunWith(AndroidJUnit4.class)
public class PlacesListTest {


    private MainActivity mActivity;
    /**
     * Api service
     */
    private ReuApiService service;

    private static int ITEMS_COUNT = 10;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule( MainActivity.class );

    @Before
    public void setUp() {
        /* obtaining the Activity from the ActivityTestRule */
        this.mActivity = this.mActivityRule.getActivity();
        // Ensure activity is NOT NULL
        assertThat( mActivity, notNullValue() );

           }



    /**
     * We ensure that our recyclerView is displaying  elements
     */
    @Test
    public void myPlacesList_shouldNotBeEmpty() {
        onView( ViewMatchers.withId( R.id.fragment_item_list_places ) )
                .check( matches( hasMinimumChildCount( 9 ) ) );
    }



    @Test
    public void myPlacesList_deleteAction_shouldRemoveItem() {
        ViewInteraction tabView = onView(
                allOf( withContentDescription( "places" ),
                        childAtPosition(
                                childAtPosition(
                                        withId( R.id.tabs ),
                                        0 ),
                                1 ),
                        isDisplayed() ) );
        tabView.perform( click() );
        // Given : We remove the element at position 1
        onView(ViewMatchers.withId(R.id.fragment_item_list_places)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.fragment_item_list_places))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of element is deleted
        onView(ViewMatchers.withId(R.id.fragment_item_list_places)).check(withItemCount(ITEMS_COUNT - 1));

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