package com.example.mareu.events;


import com.example.mareu.model.Places;

/**
 * Event fired when a user deletes a place
 */
public class DeletePlaceEvent {

    /**
     * Meeting to delete
     */
    public Places places;

    /**
     * Constructor.
     *
     * @param places
     */
    public DeletePlaceEvent(Places places) {
        this.places = places;
    }

}
