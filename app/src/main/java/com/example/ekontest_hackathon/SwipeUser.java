package com.example.ekontest_hackathon;

import android.widget.ImageView;

class SwipeUser {
    private String nomFreelancer;
    private String nombreCours;
    private int ratingBarNumber;
    private int icon;

    public SwipeUser() {
    }

    public SwipeUser(String nomFreelancer, String nombreCours, int ratingBarNumber, int icon) {
        this.nomFreelancer = nomFreelancer;
        this.nombreCours = nombreCours;
        this.ratingBarNumber = ratingBarNumber;
        this.icon=icon;
    }

    public String getNomFreelancer() {
        return nomFreelancer;
    }

    public void setNomFreelancer(String nomFreelancer) {
        this.nomFreelancer = nomFreelancer;
    }

    public String getNombreCours() {
        return nombreCours;
    }

    public void setNombreCours(String nombreCours) {
        this.nombreCours = nombreCours;
    }

    public int getRatingBarNumber() {
        return ratingBarNumber;
    }

    public void setRatingBarNumber(int ratingBarNumber) {
        this.ratingBarNumber = ratingBarNumber;
    }
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
