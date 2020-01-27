package com.tenserflow.therapist.Webservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.Utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rating_Api {
    Context context;
    String review_coment,bookingId,ratingVal;
    OnRating callback;
    public void rating(Context context,String ratingVal, String review_coment, String bookingId,OnRating onRating) {
        this.context=context;
        this.ratingVal=ratingVal;
        this.review_coment=review_coment;
        this.bookingId=bookingId;
        this.callback=onRating;



        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.rating(Global.token,bookingId,bookingId,ratingVal,review_coment);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                    callback.onRatingSuccess(response.body().toString());
                else
                    callback.onRatingFailure();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                callback.onRatingFailure();
            }
        });
    }

    public interface OnRating
    {
        void onRatingSuccess(String res);
        void onRatingFailure();
    }

}
