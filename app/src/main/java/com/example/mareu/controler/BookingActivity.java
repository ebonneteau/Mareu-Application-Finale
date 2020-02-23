package com.example.mareu.controler;



import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.mareu.R;

public class BookingActivity extends AppCompatActivity {
    CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        mCalendarView = findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year , int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year  , Toast.LENGTH_LONG).show();
            }
        });
    }
}
