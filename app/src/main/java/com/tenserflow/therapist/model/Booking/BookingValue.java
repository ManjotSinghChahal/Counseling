package com.tenserflow.therapist.model.Booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class BookingValue {



    @SerializedName("Booking_id")
    @Expose
    private Integer bookingId;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }


}