package com.example.mareu.service;



import com.example.mareu.model.Places;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


abstract class DummyReuGenerator {

    static List<Places> LIST_PLACES = Arrays.asList(
            new Places(1,"Mario"),
            new Places(2, "Luigi"),
            new Places(3, "Athena"),
            new Places(4, "Monica"),
            new Places(5, "Todd"),
            new Places(6, "Maria"),
            new Places(7, "Mercury"),
            new Places(8, "Atlanta"),
            new Places(9, "Chicago"),
            new Places(10, "Novea")

    );

    static List<Places> generatePlaces() {

        return new ArrayList<>(LIST_PLACES);
    }


}
