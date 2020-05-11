package com.example.applicationandroid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RestoTrouver implements Serializable {
    private String nom;
    private String adresse;
    private String note;
    private String latitude;
    private String longitude;
    private String googleID;

    private boolean active;

    /*public RestoTrouver(String nom, String adresse, String note, String latitude, String longitude)  {
        this.nom= nom;
        this.adresse = adresse;
        this.note = note;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active= true;
    }

    public RestoTrouver(String nom, String adresse, String note,String latitude, String longitude, boolean active)  {
        this.nom= nom;
        this.adresse = adresse;
        this.note = note;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active= active;
    }*/

    public RestoTrouver(String nom, String adresse, String note, String latitude, String longitude, String googleID) {
        this.nom = nom;
        this.adresse = adresse;
        this.note = note;
        this.latitude = latitude;
        this.longitude = longitude;
        this.googleID = googleID;
        this.active= true;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.nom +" note: "+ this.note+"/5";
    }

    public String getGoogleID() {
        return googleID;
    }

    public void setGoogleID(String googleID) {
        this.googleID = googleID;
    }
}
