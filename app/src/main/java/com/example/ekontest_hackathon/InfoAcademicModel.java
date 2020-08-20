package com.example.ekontest_hackathon;

class InfoAcademicModel {
    private int etudeNumber;
    private String ecole;
    private String filiere;
    private String diplome;
    private String startYear;
    private String endYear;

    public int getEtudeNumber() {
        return etudeNumber;
    }

    public void setEtudeNumber(int etudeNumber) {
        this.etudeNumber = etudeNumber;
    }

    public String getEcole() {
        return ecole;
    }

    public void setEcole(String ecole) {
        this.ecole = ecole;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getDiplome() {
        return diplome;
    }

    public void setDiplome(String diplome) {
        this.diplome = diplome;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public InfoAcademicModel(int etudeNumber, String ecole, String filiere, String diplome, String startYear, String endYear) {
        this.etudeNumber = etudeNumber;
        this.ecole = ecole;
        this.filiere = filiere;
        this.diplome = diplome;
        this.startYear = startYear;
        this.endYear = endYear;
    }
}
