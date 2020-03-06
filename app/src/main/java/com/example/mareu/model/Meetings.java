package com.example.mareu.model;



import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Meetings {


    /** Identifier */
    private Integer id;

    /** Full name */
    private String subject;

    /** Time */
    private String time;

    /** Place */
    private String place;
    private List<Attendees> attendees;

    public Meetings(Integer id, String subject, String time, String place, List<Attendees> attendees) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.attendees = attendees;
    }

    /**
     * Constructor
     * @param id
     * @param subject
     * @param time
     * @param place
     */
    public Meetings(Integer id, String subject, String time, String place ) {
        this.id = id;
        this.subject = subject;
        this.time = time;
        this.place = place;
        this.attendees = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
