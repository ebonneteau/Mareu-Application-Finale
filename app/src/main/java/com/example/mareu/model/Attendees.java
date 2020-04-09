package com.example.mareu.model;


import java.util.Objects;

public class Attendees {


    /**
     * Identifier
     */
    private Integer id;

    /**
     * Full name
     */
    private String mailAddress;


    /**
     * Constructor
     *
     * @param id
     * @param mailAddress
     */
    public Attendees(Integer id, String mailAddress) {
        this.id = id;
        this.mailAddress = mailAddress;


    }

    public Integer getId() {
        return id;
    }

    //Added method to display list of list in a string format
    @Override
    public String toString() {
        return mailAddress;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    //For later usage
    public void setmailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendees attendees = (Attendees) o;
        return Objects.equals( id, attendees.id );
    }

    @Override
    public int hashCode() {
        return Objects.hash( id );
    }
}
