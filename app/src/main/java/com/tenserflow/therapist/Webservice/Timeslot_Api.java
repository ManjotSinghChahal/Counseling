package com.tenserflow.therapist.Webservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.Timeslot.GetTimeslot;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Timeslot_Api {

    Context context;
    String thearapy_id,subCatId,date;
    OnGetTimeSlot onGetTimeSlot;




    public void timeslot(final Context context, String thearapy_id, String subCatId, String date, final OnGetTimeSlot onGetTimeSlot) {
        this.context = context;
        this.thearapy_id = thearapy_id;
        this.subCatId = subCatId;
        this.date = date;
        this.onGetTimeSlot = onGetTimeSlot;

        com.tenserflow.therapist.Utils.ProgressDialog.getInstance().show(context);

        Service service = RetrofitClient.getClient().create(Service.class);
        Call<GetTimeslot> call = service.timeslot(Global.token,thearapy_id,/*subCatId,*/date);
        call.enqueue(new Callback<GetTimeslot>() {
            @Override
            public void onResponse(Call<GetTimeslot> call, Response<GetTimeslot> response) {

                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();

                if (response.isSuccessful())
                {
                    onGetTimeSlot.onGetTimeslotSuccess(response.body());
                    }

                else
                {
                    onGetTimeSlot.onGetTimeslotFailure();
                }




            }
            @Override
            public void onFailure(Call<GetTimeslot> call, Throwable t) {
                com.tenserflow.therapist.Utils.ProgressDialog.getInstance().dismiss();
                 onGetTimeSlot.onGetTimeslotFailure();
            }
        });




    }

    public interface OnGetTimeSlot
    {
        void onGetTimeslotSuccess(GetTimeslot res);
        void onGetTimeslotFailure();

    }

}