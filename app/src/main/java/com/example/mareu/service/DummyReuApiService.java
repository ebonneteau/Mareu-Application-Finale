package com.example.mareu.service;



import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;

import java.util.ArrayList;
import java.util.List;


/**
 * Dummy mock for the Api
 */
public class DummyReuApiService implements ReuApiService {

    private List<Places> placeObject = DummyReuGenerator.generatePlaces();
    private List<Attendees> attendeeObject = new ArrayList<>();
    private List <Meetings> meetingObject = new ArrayList<>();




    /**
     * {@inheritDoc}
     */

    @Override
    public List<Places> getPlaces() {
        return placeObject;
    }
    @Override
    public List<Meetings> getMeetings() {
        return meetingObject;
    }


    @Override
    public void deleteAttendees(Attendees attendees) {
        attendeeObject.remove(attendees);

    }

    @Override
    public void deleteMeeting(Meetings meetings) {

    }

    @Override
    public void addAttendees() {

    }


}
