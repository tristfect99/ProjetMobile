package com.example.applicationandroid;

public class CoteRestaurant {
    String googleID;
    Float cote;

    public CoteRestaurant(String googleID, Float cote) {
        this.googleID = googleID;
        this.cote = cote;
    }

    public CoteRestaurant() {
    }

    public String getGoogleID() {
        return googleID;
    }

    public Float getCote() {
        return cote;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }

    public void setCote(Float cote) {
        this.cote = cote;
    }
}
