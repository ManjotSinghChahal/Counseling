package com.tenserflow.therapist.model.Booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tenserflow.therapist.fragment.BookingDetail;

public class BookingListing
{
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("Booking_detail")
    @Expose
    private BookingDetail bookingDetail;

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

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }


}



