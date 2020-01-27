package com.tenserflow.therapist.model.GetPersonalMedical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPersonalMedicalDetail {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Personal_details")
    @Expose
    private PersonalDetailArray personalDetails;
    @SerializedName("Medical_details")
    @Expose
    private MedicalDetailArray medicalDetails;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PersonalDetailArray getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetailArray personalDetails) {
        this.personalDetails = personalDetails;
    }

    public MedicalDetailArray getMedicalDetails() {
        return medicalDetails;
    }

    public void setMedicalDetails(MedicalDetailArray medicalDetails) {
        this.medicalDetails = medicalDetails;
    }
}