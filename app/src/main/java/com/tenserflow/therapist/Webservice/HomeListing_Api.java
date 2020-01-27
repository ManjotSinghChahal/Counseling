package com.tenserflow.therapist.Webservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.HomeListing.HomeListing;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeListing_Api {

    Context context;

    GetHomeListingCallBack callBack;




    public void getHomeListing(FragmentActivity activity, final GetHomeListingCallBack callBacks) {

        this.context = activity;
        this.callBack = callBacks;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<HomeListing> call = service.homelisting(Global.token);
        call.enqueue(new Callback<HomeListing>() {
            @Override
            public void onResponse(Call<HomeListing> call, Response<HomeListing> response) {

                if (response.isSuccessful())
                callBack.onGetHomeListingStatus(response.body());
                else
                    callBack.onGetHomeListingFailure();


            }
            @Override
            public void onFailure(Call<HomeListing> call, Throwable t) {

                callBack.onGetHomeListingFailure();

                }
        });




    }



    public interface GetHomeListingCallBack {
        void onGetHomeListingStatus(HomeListing jsonObject);
        void onGetHomeListingFailure();
    }

}
