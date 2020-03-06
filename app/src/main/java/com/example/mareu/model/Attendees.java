package com.example.mareu.model;



import java.util.Objects;

public class Attendees {


    /** Identifier */
    private Integer id;

    /** Full name */
    private String mailAddress;





    /**
     * Constructor
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

    public void setId(Integer id) {
        this.id = id;
    }

    public String getmailAddress() {
        return mailAddress;
    }

    public void setmailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Attendees attendees = (Attendees) o;
        return Objects.equals(id, attendees.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}