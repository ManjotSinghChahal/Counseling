package com.tenserflow.therapist.Webservice;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 12/13/2018.
 */

public class ForgotPassword_Api {



    Context context;
    String username, password;
    RelativeLayout relTopSignup;


    public void forgot(Context registerScreen,final String password, final RelativeLayout relTopSignup,final GetForgotPasswordCallBack callback) {
        this.context = registerScreen;
        this.password = password;
        this.relTopSignup = relTopSignup;




        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.forgot(context.getString(R.string.auth_value),  password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                callback.onGetForgotPasswordStatus(response.body().toString());
                else
                    callback.onGetForgotPasswordFailure();

                }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                DialogHelperClass.getSomethingError(context).show();
                callback.onGetForgotPasswordFailure();



            }
        });




    }

    public interface GetForgotPasswordCallBack {
        void onGetForgotPasswordStatus(String jsonObject);
        void onGetForgotPasswordFailure();
    }

}