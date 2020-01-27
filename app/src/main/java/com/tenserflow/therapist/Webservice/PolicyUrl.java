package com.tenserflow.therapist.Webservice;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.Utils.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PolicyUrl {

    Context context;

    public void policyUrl(Context context) {
        this.context=context;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.policy_url();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.isSuccessful())
                {


                    try {
                        Log.e("response_ploicy_url",response.body().toString());
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1"))
                        {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("details");
                            String Cancellation_Policy = jsonObject1.getString("Cancellation_Policy");
                            String Privacy_Policy = jsonObject1.getString("Privacy_Policy");
                            String Terms = jsonObject1.getString("terms&conditions");
                            String Informed_consent = jsonObject1.getString("Informed_consent");

                            Global.CANCELLATION_POLICY = Cancellation_Policy;
                            Global.PRIVACY_POLICY = Privacy_Policy;
                            Global.TERMS_CONDITIONS = Terms;
                            Global.INFORMED_CONSENT = Informed_consent;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


}
