package com.tenserflow.therapist.Webservice;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Window;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.MedicalDetail.MedicalDetail;
import com.tenserflow.therapist.model.PersonalDetail.PersonalDetail;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicalDetail_Api {
    Context context;
    String sub_Cat,therapy_Id,contact,father,fatherHealthIssue,mother,motherHealthIssue,diagonosis,issues,goals,country_code_preferred;
    GetMedicalDetailCallBack callBack;


    public void medicalDetail(FragmentActivity activity, String sub_cat, String therapy_id, String contacts, String fathers, String fatherHealthIssues, String mothers, String motherHealthIssues,
                              String diagonosiss, String issuess,String country_codePreferred, String goalss,GetMedicalDetailCallBack callback) {
    this.context=activity;

    this.sub_Cat=sub_cat;
    this.therapy_Id=therapy_id;

    this.contact=contacts;
    this.father=fathers;
    this.fatherHealthIssue=fatherHealthIssues;
    this.mother=mothers;
    this.motherHealthIssue=motherHealthIssues;
    this.diagonosis=diagonosiss;
    this.issues=issuess;
    this.goals=goalss;
    this.country_code_preferred=country_codePreferred;
    this.callBack=callback;




        Service service = RetrofitClient.getClient().create(Service.class);
        Call<MedicalDetail> call = service.medicalDetail(Global.token,  sub_Cat,therapy_Id,contact,father,fatherHealthIssue,mother,motherHealthIssue,diagonosis,issues,goals,"no",country_code_preferred);
        call.enqueue(new Callback<MedicalDetail>() {
            @Override
            public void onResponse(Call<MedicalDetail> call, Response<MedicalDetail> response) {

                if (response.isSuccessful())
                    callBack.onGetMedicalDetailStatus(response.body());
                else
                    callBack.onGetMedicalDetailFailure();

                }

            @Override
            public void onFailure(Call<MedicalDetail> call, Throwable t) {

             callBack.onGetMedicalDetailFailure();

             }
        });




    }

    public interface GetMedicalDetailCallBack {
        void onGetMedicalDetailStatus(MedicalDetail jsonObject);
        void onGetMedicalDetailFailure();
    }


}
