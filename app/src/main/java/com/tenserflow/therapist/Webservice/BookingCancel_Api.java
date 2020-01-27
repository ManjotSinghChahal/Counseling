package com.tenserflow.therapist.Webservice;

import android.content.Context;
import android.content.Intent;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingCancel_Api {
    Context context;
    String bookingId;
    OnBookingCancel callback;
    public void bookingCancel(final Context context, String bookingId, OnBookingCancel onBookingCancel) {
        this.context=context;
        this.bookingId=bookingId;
        this.callback=onBookingCancel;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.bookingCancel(Global.token,bookingId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                    callback.OnBookingCancelSuccess(response.body().toString());
                else
                    callback.OnBookingCancelFailure();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.OnBookingCancelFailure();
            }
        });
    }

    public interface OnBookingCancel
    {
        void OnBookingCancelSuccess(String res);
        void OnBookingCancelFailure();

    }

}
