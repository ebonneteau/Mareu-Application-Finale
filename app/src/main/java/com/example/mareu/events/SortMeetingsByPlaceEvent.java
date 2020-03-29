package com.example.mareu.events;


import com.example.mareu.model.Meetings;

/**
 * Event fired when a user request for menu "Sort by time"
 */
public class SortMeetingsByPlaceEvent {

    /**
     * Meeting to sort
     */
    public Meetings meetings;

    /**
     * Constructor.
     */
    public SortMeetingsByPlaceEvent() {
    }

}
