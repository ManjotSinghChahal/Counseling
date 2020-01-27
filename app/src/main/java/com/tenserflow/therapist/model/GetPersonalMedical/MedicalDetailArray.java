package com.tenserflow.therapist.model.GetPersonalMedical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicalDetailArray {

    @SerializedName("medical_status")
    @Expose
    private String medicalStatus;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("sub_cat")
    @Expose
    private String subCat;
    @SerializedName("therapy_id")
    @Expose
    private String therapyId;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("father_name")
    @Expose
    private String fatherName;
    @SerializedName("mother_name")
    @Expose
    private String motherName;
    @SerializedName("Father_Mental_health_issue")
    @Expose
    private String fatherMentalHealthIssue;
    @SerializedName("Mother_Mental_health_issue")
    @Expose
    private String motherMentalHealthIssue;
    @SerializedName("any_medical")
    @Expose
    private String anyMedical;
    @SerializedName("diagonosis")
    @Expose
    private String diagonosis;
    @SerializedName("issues_message")
    @Expose
    private String issuesMessage;
    @SerializedName("theraphy_goal")
    @Expose
    private String theraphyGoal;

    public String getMedicalStatus() {
        return medicalStatus;
    }

    public void setMedicalStatus(String medicalStatus) {
        this.medicalStatus = medicalStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getTherapyId() {
        return therapyId;
    }

    public void setTherapyId(String therapyId) {
        this.therapyId = therapyId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherMentalHealthIssue() {
        return fatherMentalHealthIssue;
    }

    public void setFatherMentalHealthIssue(String fatherMentalHealthIssue) {
        this.fatherMentalHealthIssue = fatherMentalHealthIssue;
    }

    public String getMotherMentalHealthIssue() {
        return motherMentalHealthIssue;
    }

    public void setMotherMentalHealthIssue(String motherMentalHealthIssue) {
        this.motherMentalHealthIssue = motherMentalHealthIssue;
    }

    public String getAnyMedical() {
        return anyMedical;
    }

    public void setAnyMedical(String anyMedical) {
        this.anyMedical = anyMedical;
    }

    public String getDiagonosis() {
        return diagonosis;
    }

    public void setDiagonosis(String diagonosis) {
        this.diagonosis = diagonosis;
    }

    public String getIssuesMessage() {
        return issuesMessage;
    }

    public void setIssuesMessage(String issuesMessage) {
        this.issuesMessage = issuesMessage;
    }

    public String getTheraphyGoal() {
        return theraphyGoal;
    }

    public void setTheraphyGoal(String theraphyGoal) {
        this.theraphyGoal = theraphyGoal;
    }

}