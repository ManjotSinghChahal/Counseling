package com.tenserflow.therapist.Webservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.Booking.BookingListing;
import com.tenserflow.therapist.model.HomeListing.HomeListing;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookingDetail_Api {

    Context context;
    String therapyType, therapyPackage, subCat, therapy_id;
    ArrayList<HashMap<String, String>> hashMapDateTime;
    // ArrayList<String> arrayListImgs;
    String dateVal = "";
    //  MultipartBody.Part[] ImagesParts_lic;
    MultipartBody.Part ImagesParts_ins, ImagesParts_lic;
    ArrayList<HashMap<String, String>> hashMapImg = new ArrayList<>();
    String ins_img, lic_img;


    public void bookingDetail(final Context context, String sub_Cat, String therapyid, String therapy_type, String therapy_package, ArrayList<HashMap<String, String>> hashMap_dateTime, ArrayList<HashMap<String, String>> hashMapImg_, String ins_img, String lic_img, final GetBookingDetailCallBack callback) {
        this.context = context;
        this.therapyType = therapy_type;
        this.therapyPackage = therapy_package;
        this.hashMapDateTime = hashMap_dateTime;
        this.hashMapImg = hashMapImg_;
        this.subCat = sub_Cat;
        this.therapy_id = therapyid;
        this.ins_img = ins_img;
        this.lic_img = lic_img;


        // ImagesParts_lic = new MultipartBody.Part[];


        for (int i = 0; i < hashMapDateTime.size(); i++) {

            if (i==0)
            {
                String slot_time = hashMapDateTime.get(i).get("slot_time_id");
                String resD = hashMapDateTime.get(i).get("date");
                dateVal =   slot_time+"$"+resD;
            }
           else   if (i < hashMapDateTime.size() - 1) {
             //   String resST = hashMapDateTime.get(i).get("s_time");
            //    String resET = hashMapDateTime.get(i).get("e_time");
             //   String slot_time = hashMapDateTime.get(i).get("slot_time");
                String resD = hashMapDateTime.get(i).get("date");
                String slot_time = hashMapDateTime.get(i).get("slot_time_id");

              //  dateVal = dateVal + resD + "$" + resST + "/" + resET + ",";
              //  dateVal = dateVal + resD + "$" + slot_time + ",";
                dateVal = dateVal+"," +slot_time+"$"+resD;
            } else {
             //   String resST = hashMapDateTime.get(i).get("s_time");
             //   String resET = hashMapDateTime.get(i).get("e_time");
             //   String slot_time = hashMapDateTime.get(i).get("slot_time");
                String resD = hashMapDateTime.get(i).get("date");
                String slot_time = hashMapDateTime.get(i).get("slot_time_id");

                dateVal = dateVal +","+  slot_time+"$"+resD;
            }
        }










        RequestBody sub_CatRB = RequestBody.create(MediaType.parse("text/plain"), sub_Cat);
        RequestBody therapyidRB = RequestBody.create(MediaType.parse("text/plain"), therapyid);
        RequestBody therapy_typeRB = RequestBody.create(MediaType.parse("text/plain"), therapy_type);
        RequestBody therapyPackageRB = RequestBody.create(MediaType.parse("text/plain"), therapyPackage);
        RequestBody timeslotRB = RequestBody.create(MediaType.parse("text/plain"), dateVal);


        //----------insurance------------------------
        File file_ins = new File(ins_img);
        RequestBody imgBody_ins = RequestBody.create(MediaType.parse("image/*"), file_ins);
        ImagesParts_ins = MultipartBody.Part.createFormData("upload_document", file_ins.getName(), imgBody_ins);

        //-----------license--------------------------
        File file_lic = new File(lic_img);
        RequestBody imgBody_lic = RequestBody.create(MediaType.parse("image/*"), file_lic);
        ImagesParts_lic = MultipartBody.Part.createFormData("licence", file_lic.getName(), imgBody_lic);




        /* for (int j=0;j<hashMapImg.size();j++)
             {
             File file = new File(hashMapImg.get(j).get("image"));
             RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
             ImagesParts[j] = MultipartBody.Part.createFormData("images", file.getName(), imgBody);

             }
       */


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<JsonObject> call = service.bookingListing(Global.token, sub_CatRB, therapyidRB, therapy_typeRB, therapyPackageRB, timeslotRB, ImagesParts_ins,ImagesParts_lic);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful())
                    callback.onGetBookingDetailStatus(response.body().toString());
                else
                {
                    callback.onGetBookingDetailFailure();
                   /* try {

                        Log.e("errorMessage",response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                }



            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("ErrorMessage",t.getMessage());
                Toast.makeText(context,t.getMessage()+"",Toast.LENGTH_SHORT);
                callback.onGetBookingDetailFailure();

            }
        });


    }


    public interface GetBookingDetailCallBack {
        void onGetBookingDetailStatus(String jsonObject);

        void onGetBookingDetailFailure();
    }

}
