package com.example.mareu.events;

import com.example.mareu.model.Attendees;


/**
 * Event fired when a user deletes a Neighbour
 */
public class DeleteAttendeesEvent {

    /**
     * Meeting to delete
     */
    public Attendees attendees;

    /**
     * Constructor.
     * @param attendees
     */
    public DeleteAttendeesEvent(Attendees attendees) {
        this.attendees = attendees;
    }

}
