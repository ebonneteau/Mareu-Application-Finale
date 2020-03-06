package com.example.mareu.model;



import java.util.Objects;

public class Places {


    /** Identifier */
    private Integer id;

    /** Full name */
    private String place;





    /**
     * Constructor
     * @param id
     * @param place
     */
    public Places(Integer id, String place) {
        this.id = id;
        this.place = place;


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        Places attendees = (Places) o;
        return Objects.equals(id, attendees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
