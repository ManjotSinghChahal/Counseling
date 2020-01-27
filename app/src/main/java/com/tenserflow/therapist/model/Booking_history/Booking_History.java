package com.tenserflow.therapist.model.Booking_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking_History {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("mesaage")
    @Expose
    private String mesaage;
    @SerializedName("record")
    @Expose
    private RecordBookingHistory record;

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

    public RecordBookingHistory getRecord() {
        return record;
    }

    public void setRecord(RecordBookingHistory record) {
        this.record = record;
    }
}




