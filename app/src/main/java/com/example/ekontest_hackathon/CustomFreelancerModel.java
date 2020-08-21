package com.example.ekontest_hackathon;

class CustomFreelancerModel {
    private String nom;
    private String prenom;
    int image;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public CustomFreelancerModel(String nom, String prenom, int image) {
        this.nom = nom;
        this.prenom = prenom;
        this.image = image;
    }
}
