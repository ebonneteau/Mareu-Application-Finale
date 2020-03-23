package com.example.mareu.service;






import com.example.mareu.model.Attendees;
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
    List<Meetings> getMeetings();
    List<Attendees> getAttendees();


    /**
     * Other actions
     */
    void deleteAttendees(Attendees attendees);

    void deletePlace (Places places);

    void deleteMeeting (Meetings meetings);

    void addMeeting (Meetings meetings);

    void addAttendees(Attendees attendees);


}


