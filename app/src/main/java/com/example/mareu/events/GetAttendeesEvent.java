package com.example.mareu.events;


import com.example.mareu.model.Attendees;

/**
 * Event fired when a user wants to add a Neighbour object into Favorite List (second callBack addFavoriteEvent)
 */
public class GetAttendeesEvent {
    public Attendees attendees;


    /**
     * Neighbour to get
     */
    public GetAttendeesEvent(Attendees attendees) {
        this.attendees = attendees;


    }


}
