package com.example.ekontest_hackathon;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

class InfoAcademicModel implements Parcelable {
    private String level;
    private String ecole;
    private String filiere;
    private String diplome;
    private String startYear;
    private String endYear;

    public InfoAcademicModel(String level, String ecole, String filiere, String diplome, String startYear, String endYear) {
       this.level=level;
        this.ecole = ecole;
        this.filiere = filiere;
        this.diplome = diplome;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    public InfoAcademicModel() {
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.level);
        dest.writeString(this.ecole);
        dest.writeString(this.filiere);
        dest.writeString(this.diplome);
        dest.writeString(this.startYear);
        dest.writeString(this.endYear);
    }
    public InfoAcademicModel(Parcel in) {
        this.level = in.readString();
        this.ecole = in.readString();
        this.filiere = in.readString();
        this.diplome = in.readString();
        this.startYear = in.readString();
        this.endYear = in.readString();

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InfoAcademicModel createFromParcel(Parcel in) {
            return new InfoAcademicModel(in);
        }

        public InfoAcademicModel[] newArray(int size) {
            return new InfoAcademicModel[size];
        }
    };
}
