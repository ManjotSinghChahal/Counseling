package com.tenserflow.therapist.model.Booking_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HistoryArray {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("booking_Session")
    @Expose
    private String bookingSession;
    @SerializedName("Therapy_name")
    @Expose
    private String therapyName;
    @SerializedName("Booking_Status")
    @Expose
    private String bookingStatus;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingSession() {
        return bookingSession;
    }

    public void setBookingSession(String bookingSession) {
        this.bookingSession = bookingSession;
    }

    public String getTherapyName() {
        return therapyName;
    }

    public void setTherapyName(String therapyName) {
        this.therapyName = therapyName;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}