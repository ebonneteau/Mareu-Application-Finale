package com.example.mareu.service;


import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;

import java.util.List;


/**
 * "Ma Réu" API client
 */
public interface ReuApiService {

    /**
     * Get all Places
     *
     * @return {@link List}
     */
    List<Places> getPlaces();

    /**
     * Get all Meetings
     *
     * @return {@link List}
     */
    List<Meetings> getMeetings();

    /**
     * Menu actions
     */
    List<Meetings> getMeetingsByTime();

    List<Meetings> getMeetingsByPlace();

    /**
     * Other actions
     */


    void deletePlace(Places places);

    void deleteMeeting(Meetings meetings);

    void addMeeting(Meetings meetings);


}


