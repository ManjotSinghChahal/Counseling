package com.tenserflow.therapist.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.Rating_Api;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogAccepted extends Dialog {


    @BindView(R.id.ratingBar)
    AppCompatRatingBar ratingBar;
    @BindView(R.id.edt_review)
    EditText edtReview;
    Context context;
    String ratingVal = "0.0";
    String bookingId;

    public CustomDialogAccepted(Context context, String bookingId) {
        super(context);
        this.context = context;
        this.bookingId = bookingId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_accepted);

        ButterKnife.bind(this);


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                try {
                    ratingVal = String.valueOf(rating);
                } catch (Exception e) {
                }


            }
        });

    }

    @OnClick({R.id.txtview_reviewSubmit,R.id.lin_commentReview})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.txtview_reviewSubmit:

                if (edtReview.getText().toString().trim().equalsIgnoreCase(""))
                    Toast.makeText(context, "Please add comment", Toast.LENGTH_SHORT).show();
                else {
                    Global.softKeyboardToggle((Activity) context);

                    if (!InternetConnectivity.isConnected(context)) {
                        DialogHelperClass.getConnectionError(context).show();
                    } else {
                        ProgressDialog.getInstance().show(context);
                        ratingApi();
                    }
                }
                break;

            case R.id.lin_commentReview:
                Global.softKeyboardToggle((Activity) context);
                break;

        }

    }

    private void ratingApi() {
        Rating_Api rating_api = new Rating_Api();
        rating_api.rating(context, ratingVal, edtReview.getText().toString().trim(), bookingId, new Rating_Api.OnRating() {
            @Override
            public void onRatingSuccess(String res) {

                ProgressDialog.getInstance().dismiss();

                try {

                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    Toast.makeText(context, message + "", Toast.LENGTH_SHORT).show();
                    dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onRatingFailure() {
                ProgressDialog.getInstance().dismiss();
                DialogHelperClass.getSomethingError(context).show();
            }
        });

    }

    @Override
    protected void onStop() {
      //  super.onStop();

      //  Global.hideKeyboard((Activity)context);
    }
}
