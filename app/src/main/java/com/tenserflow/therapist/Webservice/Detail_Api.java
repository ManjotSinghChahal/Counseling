package com.tenserflow.therapist.Webservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.Detail.DetailListing;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Api {


    Context context;
    String category_id,therapy_id;


    public void detail_Api(Context registerScreen, String category_id,String therapy_id,final DetailCallBack callBack) {
        this.context = registerScreen;
        this.category_id = category_id;
        this.therapy_id = therapy_id;



        Service service = RetrofitClient.getClient().create(Service.class);
        Call<DetailListing> call = service.detailListing(Global.token,category_id,therapy_id);
        call.enqueue(new Callback<DetailListing>() {
            @Override
            public void onResponse(Call<DetailListing> call, Response<DetailListing> response) {

                if (response.isSuccessful())
                    callBack.onDetailStatus(response.body());
                else
                    callBack.onDetailFailure();


            }

            @Override
            public void onFailure(Call<DetailListing> call, Throwable t) {
                Log.e("vcedvcrd",t.getMessage());
                callBack.onDetailFailure();

              }
        });


    }

    public interface DetailCallBack {
        void onDetailStatus(DetailListing jsonObject);
        void onDetailFailure();
    }
}
