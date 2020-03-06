package com.example.mareu.events;


import com.example.mareu.model.Meetings;

/**
 * Event fired when a user wants to add an Attendee
 */
public class GetMeetingsEvent {
    public Meetings meetings;


    /**
     * Meetings to get
     */
    public GetMeetingsEvent(Meetings meetings) {
        this.meetings = meetings;


    }


}
