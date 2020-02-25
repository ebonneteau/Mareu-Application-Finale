package com.example.mareu.service;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.mareu.model.Attendees;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


abstract class DummyReuGenerator {

    static List<Attendees> DUMMY_ATTENDEES = Arrays.asList(
            new Attendees(1, "Caroline", "08:59:00" ,"Réunion 1"),
            new Attendees(2, "Jack","23:59:59" ,"Réunion 2"),
            new Attendees(3, "Chloé", "09:59:59" ,"Réunion 3"),
            new Attendees(4, "Vincent", "06:00:00" ,"Réunion 4"),
            new Attendees(5, "Elodie", "10:59:59" ,"Réunion 5"),
            new Attendees(6, "Sylvain", "12:00:00" ,"Réunion 6"),
            new Attendees(7, "Laetitia", "13:00:00" ,"Réunion 7"),
            new Attendees(8, "Dan", "14:00:00" ,"Réunion 8"),
            new Attendees(9, "Joseph", "15:00:00" ,"Réunion 9"),
            new Attendees(10, "Emma", "12:00:00" ,"Réunion 10")

    );

    static List<Attendees> generateAttendees() {

        return new ArrayList<>(DUMMY_ATTENDEES);
    }


}
