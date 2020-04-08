package com.example.mareu.service;

import com.example.mareu.DI.DI;
import com.example.mareu.model.Places;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PlacesServiceTest {
    private ReuApiService service;

    @Before
    public void setUp() throws Exception {
        service = DI.getReuApiService();

    }

    @Test
    public void getPlacesWithSuccess() {
        //Given the list of places
        List<Places> places = service.getPlaces();
        // When comparing generated list with original one
        List<Places> expectedPlacesList = DummyPlacesGenerator.LIST_PLACES;
        //Then assert the generated list contains same number of items than the original one.
        assertThat(places, IsIterableContainingInAnyOrder.containsInAnyOrder
                ( Objects.requireNonNull( expectedPlacesList.toArray() ) ));

    }

    @Test
    public void deletePlacesWithSuccess() {
        //Given place at position 1
        Places placeToDelete = service.getPlaces().get(0);

        //When deleting this place
        service.deletePlace(placeToDelete);
        //Then this list does not contain this object anymore
        assertFalse( service.getPlaces().contains(placeToDelete));

    }
}