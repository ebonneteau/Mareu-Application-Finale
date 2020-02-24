package com.example.mareu.service;

import com.example.mareu.model.Attendees;



import java.util.List;


/**
 * Dummy mock for the Api
 */
public class DummyReuApiService implements ReuApiService {

    private List<Attendees> attendees = DummyReuGenerator.generateAttendees();



    /**
     * {@inheritDoc}
     */
    @Override
    public List<Attendees> getAttendees() {
        return attendees;
    }

    @Override
    public void deleteAttendees(Attendees attendees) {

    }

    @Override
    public void addAttendees() {

    }


}
