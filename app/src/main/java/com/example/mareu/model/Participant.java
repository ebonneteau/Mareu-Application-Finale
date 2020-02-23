package com.example.mareu.model;

import java.util.Date;
import java.util.Objects;

public class Participant {


    /** Identifier */
    private Integer id;

    /** Full name */
    private String name;

    /** Date */
    private Date date;

    /** Place */
    private String place;

    /**
     * Constructor
     * @param id
     * @param name
     * @param date
     * @param place
     */
    public Participant(Integer id, String name, Date date,String place ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant participant = (Participant) o;
        return Objects.equals(id, participant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
