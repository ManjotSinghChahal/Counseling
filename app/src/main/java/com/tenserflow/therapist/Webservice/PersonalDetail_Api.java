package com.tenserflow.therapist.Webservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.google.gson.JsonObject;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.HomeListing.HomeListing;
import com.tenserflow.therapist.model.PersonalDetail.PersonalDetail;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalDetail_Api {
    Context context;
    String subCat_id,thearpy_id,name_per,address_per,city_per,state_per,date_per,age_per,
           email_per,cellphoneCC_per,cellphone_per,homephoneCC_per,homephone_per,countrycode_cell,countrycode_home,country_name;
    GetPersonalDetailCallBack callBack;



    public void personalDetail(FragmentActivity activity, String sub_Cat_id, String thearpy_Id, String name, String address, String city, String state, String date, String age, String email, String cellPhoneCC, String cellPhone, String homePhoneCC, String homePhone,String countryCode_cell,String countryCode_home,String countryName,GetPersonalDetailCallBack callback) {
      this.context=activity;
      this.subCat_id=sub_Cat_id;
      this.thearpy_id=thearpy_Id;

      this.name_per=name;
      this.address_per=address;
      this.city_per=city;
      this.state_per=state;
      this.date_per=date;
      this.age_per=age;
      this.email_per=email;
      this.cellphoneCC_per=cellPhoneCC;
      this.cellphone_per=cellPhone;
      this.homephoneCC_per=homePhoneCC;
      this.homephone_per=homePhone;
      this.countrycode_cell=countryCode_cell;
      this.countrycode_home=countryCode_home;
      this.country_name=countryName;
      this.callBack=callback;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<PersonalDetail> call = service.personalDetail(Global.token,  subCat_id,thearpy_id,name_per,address_per,city_per,state_per,date_per,age_per,email_per,cellPhone,homePhone,countryCode_cell,countryCode_home,country_name);
        call.enqueue(new Callback<PersonalDetail>() {
            @Override
            public void onResponse(Call<PersonalDetail> call, Response<PersonalDetail> response) {

                if (response.isSuccessful())
                    callBack.onGetPersonalDetailStatus(response.body());
                else
                    callBack.onGetPersonalDetailFailure();

            }

            @Override
            public void onFailure(Call<PersonalDetail> call, Throwable t) {
                callBack.onGetPersonalDetailFailure();
                }
        });




    }

    public interface GetPersonalDetailCallBack {
        void onGetPersonalDetailStatus(PersonalDetail jsonObject);
        void onGetPersonalDetailFailure();
    }


}
