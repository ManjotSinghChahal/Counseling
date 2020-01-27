package com.tenserflow.therapist.model.Booking_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecordBookingHistory {


    @SerializedName("HistoryArray")
    @Expose
    private List<HistoryArray> historyArray = null;

    public List<HistoryArray> getHistoryArray() {
        return historyArray;
    }

    public void setHistoryArray(List<HistoryArray> historyArray) {
        this.historyArray = historyArray;
    }
}
