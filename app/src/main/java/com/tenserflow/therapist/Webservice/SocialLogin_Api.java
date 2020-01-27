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
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.Utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admin on 12/13/2018.
 */

public class SocialLogin_Api {



    Context context;
    String password, social_id, name, email,type,photo_url_,last_name;
    RelativeLayout relTopSignup;


    public void social_login(Context registerScreen, final String social_id, final String type, final String name,final String last_name, final String email, final RelativeLayout relTopSignup,String photo_url) {
        this.context = registerScreen;
        this.social_id = social_id;
        this.type = type;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.relTopSignup = relTopSignup;
        this.photo_url_ = photo_url;

        com.tenserflow.therapist.Utils.ProgressDialog.getInstance().show(context);




        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.social_login(context.getString(R.string.auth_value),  social_id,type,name,last_name, email,"","","","",photo_url_,"","","link");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();

                if (response.isSuccessful())
                {



                    try {


                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1")) {
                            String message = jsonObject.getString("message");
                            String user_id = jsonObject.getString("user_id");
                            String token = jsonObject.getString("token");

                            Global.token = token;
                            Global.user_id = user_id;
                            Global.enterWayVar = "social" + type;



                            HelperPreferences.get(context).saveString("user_id",user_id);
                            HelperPreferences.get(context).saveString("token",token);
                            HelperPreferences.get(context).saveString("enter_way","social" + type);

                            HelperPreferences.get(context).saveString("rememberME","unchecked");
                            HelperPreferences.get(context).saveString("logout_status","login");

                            Intent intent = new Intent(context, MainActivity.class);
                            intent.putExtra("statusEntery", "Login");
                            intent.putExtra("user_id", Global.user_id);
                            intent.putExtra("token", Global.token);
                            context.startActivity(intent);
                            ((Activity) context).finish();


                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                {
                    try {
                        Log.e("errorMessage",response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    DialogHelperClass.getSomethingError(context).show();
                }






            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("errorMessage",t.getMessage());
                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();
                DialogHelperClass.getSomethingError(context).show();

            }
        });


    }

}
