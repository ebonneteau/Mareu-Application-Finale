package com.example.mareu.model;



import java.util.Objects;

public class Meetings {


    /** Identifier */
    private Integer id;

    /** Full name */
    private String name;

    /** Time */
    private String time;

    /** Place */
    private String place;

    /**
     * Constructor
     * @param id
     * @param name
     * @param time
     * @param place
     */
    public Meetings(Integer id, String name, String time, String place ) {
        this.id = id;
        this.name = name;
        this.time = time;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
        Meetings attendees = (Meetings) o;
        return Objects.equals(id, attendees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
