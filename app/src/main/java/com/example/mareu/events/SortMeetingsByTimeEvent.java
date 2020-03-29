package com.example.mareu.events;


import com.example.mareu.model.Meetings;

/**
 * Event fired when a user request for menu "Sort by time"
 */
public class SortMeetingsByTimeEvent {

    /**
     * Meeting to sort
     */
    public Meetings meetings;

    /**
     * Constructor.
     */
    public SortMeetingsByTimeEvent() {
    }

}
