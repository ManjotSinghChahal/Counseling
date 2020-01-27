package com.tenserflow.therapist.model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailListing {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mesaage")
    @Expose
    private String mesaage;
    @SerializedName("record")
    @Expose
    private Record record;

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

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

}










