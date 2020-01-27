package com.tenserflow.therapist.model.Detail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionArray {


    @SerializedName("Individual_1_Session")
    @Expose
    private String individual1Session;
    @SerializedName("Individual_6_Session")
    @Expose
    private String individual6Session;
    @SerializedName("Group_1_Session")
    @Expose
    private String group1Session;
    @SerializedName("Group_6_Session")
    @Expose
    private String group6Session;

    public String getIndividual1Session() {
        return individual1Session;
    }

    public void setIndividual1Session(String individual1Session) {
        this.individual1Session = individual1Session;
    }

    public String getIndividual6Session() {
        return individual6Session;
    }

    public void setIndividual6Session(String individual6Session) {
        this.individual6Session = individual6Session;
    }

    public String getGroup1Session() {
        return group1Session;
    }

    public void setGroup1Session(String group1Session) {
        this.group1Session = group1Session;
    }

    public String getGroup6Session() {
        return group6Session;
    }

    public void setGroup6Session(String group6Session) {
        this.group6Session = group6Session;
    }

}

