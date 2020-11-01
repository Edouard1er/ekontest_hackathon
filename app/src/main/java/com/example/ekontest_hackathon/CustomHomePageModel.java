package com.example.ekontest_hackathon;

class CustomHomePageModel {
    private String periodTime;
    private String relationType;
    private String subject;
    private String amountTotal;
    private String perHour;

    public String getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(String periodTime) {
        this.periodTime = periodTime;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(String amountTotal) {
        this.amountTotal = amountTotal;
    }

    public String getPerHour() {
        return perHour;
    }

    public void setPerHour(String perHour) {
        this.perHour = perHour;
    }

    public CustomHomePageModel(String periodTime, String relationType, String subject, String amountTotal, String perHour) {
        this.periodTime = periodTime;
        this.relationType = relationType;
        this.subject = subject;
        this.amountTotal = amountTotal;
        this.perHour = perHour;
    }
}
