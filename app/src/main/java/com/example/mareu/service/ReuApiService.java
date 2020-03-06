package com.example.mareu.service;






import com.example.mareu.model.Attendees;
import com.example.mareu.model.Meetings;
import com.example.mareu.model.Places;

import java.util.List;


/**
 * Neighbour API client
 */
public interface ReuApiService {

    /**
     * Get all Places
     *
     * @return {@link List}
     */
    List<Places> getPlaces();

    List<Meetings> getMeetings();




    /**
     * Deletes attendee
     */
    void deleteAttendees(Attendees attendees);

    void deleteMeeting (Meetings meetings);




    void addAttendees();


}


