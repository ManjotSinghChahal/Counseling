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

import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.Detail.DetailListing;
import com.tenserflow.therapist.model.GetPersonalMedical.GetPersonalMedicalDetail;
import com.tenserflow.therapist.model.PersonalMedicalDetail.PersonalMedical;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalMedical_Api {

    Context context;
    String therapy_Id;
    PersonalMedicalCallBack callBack;



    public void medical_personal(Context registerScreen, String therapy_id,PersonalMedicalCallBack callBack_) {
        this.context = registerScreen;
        this.therapy_Id = therapy_id;
        this.callBack = callBack_;



        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.detailPersonalMediacal(Global.token,therapy_Id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful())
                    callBack.onPersonalMedicalStatus(response.body().toString());
                else
                    callBack.onPersonalMedicalFailure();


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                callBack.onPersonalMedicalFailure();


            }
        });


    }

    public interface PersonalMedicalCallBack {
        void onPersonalMedicalStatus(String jsonObject);

        void onPersonalMedicalFailure();
    }
}

