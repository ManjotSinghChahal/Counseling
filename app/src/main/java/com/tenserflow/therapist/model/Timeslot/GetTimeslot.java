package com.tenserflow.therapist.model.Timeslot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetTimeslot {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String mesaage;
    @SerializedName("listing")
    @Expose
    private List<TimeslotLists> listing = null;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMesaage() {
        return mesaage;
    }

    public void setMesaage(String mesaage) {
        this.mesaage = mesaage;
    }

    public List<TimeslotLists> getListing() {
        return listing;
    }

    public void setListing(List<TimeslotLists> listing) {
        this.listing = listing;
    }

}



