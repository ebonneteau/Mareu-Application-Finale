package com.example.mareu.service;




import com.example.mareu.model.Attendees;

import java.util.List;


/**
 * Neighbour API client
 */
public interface ReuApiService {

    /**
     * Get all my Neighbours
     *
     * @return {@link List}
     */
    List<Attendees> getAttendees();




    /**
     * Deletes a neighbour
     */
    void deleteAttendees(Attendees attendees);





    void addAttendees();


}


