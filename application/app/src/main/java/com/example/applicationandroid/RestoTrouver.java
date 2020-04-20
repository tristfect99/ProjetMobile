package com.example.applicationandroid;

import java.io.Serializable;

public class RestoTrouver implements Serializable {
    private String nomResto;
    private String note;

    private boolean active;

    public RestoTrouver(String nomResto, String note)  {
        this.nomResto= nomResto;
        this.note = note;
        this.active= true;
    }

    public RestoTrouver(String nomResto, String note, boolean active)  {
        this.nomResto= nomResto;
        this.note = note;
        this.active= active;
    }

    public String getUserType() {
        return note;
    }

    public void setUserType(String note) {
        this.note = note;
    }

    public String getUserName() {
        return nomResto;
    }

    public void setUserName(String nomResto) {
        this.nomResto = nomResto;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return this.nomResto +" note: "+ this.note+"/5";
    }
}
