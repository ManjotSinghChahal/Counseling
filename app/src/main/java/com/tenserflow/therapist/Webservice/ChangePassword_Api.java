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
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
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
 * Created by Admin on 12/14/2018.
 */

public class ChangePassword_Api {



    Context context;
    String old_password, new_password;
    RelativeLayout relChangePassword;


    public void changePassword(Context registerScreen,final String old_password,final String new_password, final RelativeLayout relChangePassword,final  GetChangePasswordCallBack callback) {
        this.context = registerScreen;
        this.old_password = old_password;
        this.new_password = new_password;
        this.relChangePassword = relChangePassword;



        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.change_password(Global.token,  old_password,new_password);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                callback.onGetChangePasswordStatus(response.body().toString());
                else
                    callback.onGetChangePasswordFailure();







            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                callback.onGetChangePasswordFailure();

            }
        });




    }

    public interface GetChangePasswordCallBack {
        void onGetChangePasswordStatus(String jsonObject);
        void onGetChangePasswordFailure();
    }

}
