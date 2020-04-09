package com.example.mareu.controller;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mareu.R;
import com.example.mareu.controller.adapters.ListMeetingsPagerAdapter;
import com.example.mareu.events.SortMeetingsByPlaceEvent;
import com.example.mareu.events.SortMeetingsByTimeEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    // UI Components
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.container)
    ViewPager mViewPager;
    //Adding FloatingActionButton
    @BindView(R.id.add_meeting)
    FloatingActionButton mFloatingActionButton;
    ListMeetingsPagerAdapter mPagerAdapter;

    private int mStateMenuShow = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );
        setSupportActionBar( mToolbar );

        mPagerAdapter = new ListMeetingsPagerAdapter( getSupportFragmentManager() );
        mViewPager.setAdapter( mPagerAdapter );
        mViewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( mTabLayout ) );
        // Adding FloatingActionButton method
        // FloatingActionButton will appear only on meeting page listener
        mViewPager.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mFloatingActionButton.show();
                        mStateMenuShow = 1; //setting state to true
                        invalidateOptionsMenu(); // onCreateOptionsMenu(...) is called again
                        break;
                    case 1:
                    default:
                        mFloatingActionButton.hide();
                        mStateMenuShow = 0; // setting state to false
                        invalidateOptionsMenu(); //onCreateOptionsMenu(...) is called again
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        } );
        mTabLayout.addOnTabSelectedListener( new TabLayout.ViewPagerOnTabSelectedListener( mViewPager ) );


        mFloatingActionButton.setOnClickListener( view -> {
            Intent intent = new Intent( MainActivity.this, BookingActivity.class );
            startActivity( intent );

        } );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (mStateMenuShow == 0) {
            getMenuInflater().inflate( R.menu.menu_main, menu );
            return false;

        }
        if (mStateMenuShow == 1) {
            getMenuInflater().inflate( R.menu.menu_main, menu );
            return true;
        }

        getMenuInflater().inflate( R.menu.menu_main, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_1) {
            EventBus.getDefault().post( new SortMeetingsByTimeEvent() );
            return true;
        }
        if (id == R.id.action_settings_2) {
            EventBus.getDefault().post( new SortMeetingsByPlaceEvent() );
            return true;
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
