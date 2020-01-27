package com.tenserflow.therapist.model.PersonalMedicalDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

 public class Record {

    @SerializedName("PersonalDetail_Array")
    @Expose
    private List<PersonalDetailArray> personalDetailArray = null;
    @SerializedName("MedicalDetail_Array")
    @Expose
    private List<MedicalDetailArray> medicalDetailArray = null;

    public List<PersonalDetailArray> getPersonalDetailArray() {
        return personalDetailArray;
    }

    public void setPersonalDetailArray(List<PersonalDetailArray> personalDetailArray) {
        this.personalDetailArray = personalDetailArray;
    }

    public List<MedicalDetailArray> getMedicalDetailArray() {
        return medicalDetailArray;
    }

    public void setMedicalDetailArray(List<MedicalDetailArray> medicalDetailArray) {
        this.medicalDetailArray = medicalDetailArray;
    }

}