package com.example.ekontest_hackathon;

class CustomAllAvisModel {
    private String nom, prenom, commentaire, date;
    private int numStar;

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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumStar() {
        return numStar;
    }

    public void setNumStar(int numStar) {
        this.numStar = numStar;
    }

    public CustomAllAvisModel(String nom, String prenom, String commentaire, String date, int numStar) {
        this.nom = nom;
        this.prenom = prenom;
        this.commentaire = commentaire;
        this.date = date;
        this.numStar = numStar;
    }
}
