package com.tenserflow.therapist.model.HomeListing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeListing {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mesaage")
    @Expose
    private String mesaage;
    @SerializedName("listing")
    @Expose
    private List<Listing> listing = null;

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

    public List<Listing> getListing() {
        return listing;
    }

    public void setListing(List<Listing> listing) {
        this.listing = listing;
    }
}

