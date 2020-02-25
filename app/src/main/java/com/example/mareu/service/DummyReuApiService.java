package com.example.mareu.service;

import com.example.mareu.model.Attendees;


import java.util.ArrayList;
import java.util.List;


/**
 * Dummy mock for the Api
 */
public class DummyReuApiService implements ReuApiService {

    private List<Attendees> attendeeObject = DummyReuGenerator.generateAttendees();



    /**
     * {@inheritDoc}
     */
    @Override
    public List<Attendees> getAttendees() {
        return attendeeObject;
    }

    @Override
    public void deleteAttendees(Attendees attendees) {
        attendeeObject.remove(attendees);

    }

    @Override
    public void addAttendees() {

    }


}
