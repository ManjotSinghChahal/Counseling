package com.tenserflow.therapist.Webservice;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.Utils.Payment;
import com.tenserflow.therapist.Utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Payment_Api {
    Context context;
    String booking_id, trans_id, amount, date;

    public void payment(final Context context, String booking_id, String trans_id, String amount, String date) {

        this.context = context;
        this.booking_id = booking_id;
        this.trans_id = trans_id;
        this.amount = amount;
        this.date = date;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.payment(Global.token, booking_id, trans_id, amount, date);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (response.isSuccessful()) {
                            try {

                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                String status = jsonObject.getString("status");
                                String message = jsonObject.getString("message");
                                Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
                                if (status.equalsIgnoreCase("1")) {

                                    Intent intent = new Intent(context, MainActivity.class);
                                    intent.putExtra("pay", "paymenscreen");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
                                } else {

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }, Snackbar.LENGTH_LONG + 1500);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // run api again with data stored in shared pref
            }
        });
    }
}
