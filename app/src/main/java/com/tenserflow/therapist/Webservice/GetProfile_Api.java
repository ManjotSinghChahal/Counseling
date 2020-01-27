package com.tenserflow.therapist.Webservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.Utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Manjot on 12/14/2018.
 */

public class GetProfile_Api {
    Context context;


    String enterStatus;

    public void getProfile(Context registerScreen , String enter_Status, final GetProfileCallBack callBack) {
        this.context = registerScreen;
        this.enterStatus = enter_Status;

        if (!enterStatus.equalsIgnoreCase("Main"))
        {


            com.tenserflow.therapist.Utils.ProgressDialog.getInstance().show(context);
        }



        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.getProfile(Global.token);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (!enterStatus.equalsIgnoreCase("Main"))
                {
                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();
                }

                if (response.isSuccessful())
                  callBack.onGetProfileStatus(response.body().toString());



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {


                if (!enterStatus.equalsIgnoreCase("Main"))
                {
                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();
                }
              //  DialogHelperClass.getSomethingError(context).show();
               callBack.onGetProfileFailure();




            }
        });




    }
    public interface GetProfileCallBack {
        void onGetProfileStatus(String jsonObject);
        void onGetProfileFailure();
    }


}
