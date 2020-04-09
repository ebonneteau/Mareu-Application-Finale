package com.example.mareu.events;


import com.example.mareu.model.Meetings;

/**
 * Event fired when a user deletes a meeting
 */
public class DeleteMeetingEvent {

    /**
     * Meeting to delete
     */
    public Meetings meetings;

    /**
     * Constructor.
     *
     * @param meetings
     */
    public DeleteMeetingEvent(Meetings meetings) {
        this.meetings = meetings;
    }

}
