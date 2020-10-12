package com.example.ekontest_hackathon;

public class FreelancerModel extends StudentModel {
    private ProfilModel profilModel;
    private AvisModel avisModel;

    public FreelancerModel(ProfilModel profilModel, AvisModel avisModel) {
        super();
        this.profilModel = profilModel;
        this.avisModel = avisModel;
    }

    public FreelancerModel() {
        super();

    }

    public void test(){
        FreelancerModel freelancerModel= new FreelancerModel();

    }
}
