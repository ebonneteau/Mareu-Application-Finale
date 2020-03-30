package com.example.mareu.service;


import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AllActionsReuApiService implements ReuApiService {

    private List<Places> placeObject = DummyPlacesGenerator.generatePlaces();
    private List<Meetings> meetingObject = new ArrayList<>();


    /**
     * Lists Display
     */

    @Override
    public List<Places> getPlaces() {
        return placeObject;
    }

    @Override
    public List<Meetings> getMeetings() {
        return meetingObject;
    }





    /**
     * Menu actions
     */
    @Override
    public List<Meetings> getMeetingsByTime() {

        return getMeetingObjectByTime();
    }
    @Override
    public List<Meetings> getMeetingsByPlace() {

        return getMeetingObjectByPlace();
    }


    /**
     * On Recycler holders actions
     */
    @Override
    public void deleteMeeting(Meetings meetings) {
        meetingObject.remove(meetings);

    }

    @Override
    public void addMeeting(Meetings meetings) {
        meetingObject.add(meetings);

    }


    @Override
    public void deletePlace(Places places) {
        placeObject.remove(places);

    }

    /**
     * Collections methods
     */

    private List<Meetings> getMeetingObjectByTime() {
        Collections.sort(meetingObject, (o1, o2) -> o1.getStartTime().compareTo(o2.getStartTime()));
        return meetingObject;
    }
    private List<Meetings> getMeetingObjectByPlace() {
        Collections.sort(meetingObject, (o1, o2) -> o1.getPlace().compareTo(o2.getPlace()));
        return meetingObject;
    }
}
