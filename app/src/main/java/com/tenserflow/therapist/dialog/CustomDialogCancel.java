package com.tenserflow.therapist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.BookingCancel_Api;

import org.json.JSONException;
import org.json.JSONObject;

public class CustomDialogCancel extends Dialog {
    Context cnxt;
    String bookingId;
    public CustomDialogCancel(Context context, String bookingId) {
        super(context);
        this.cnxt=context;
        this.bookingId=bookingId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_cancel);

        TextView textView = findViewById(R.id.txtview_AgreeCancel);
        TextView txtview_cancelPolicy = findViewById(R.id.txtview_cancelPolicy);
        final CheckBox checkbox_dialogCancel = findViewById(R.id.checkbox_dialogCancel);
        TextView submit_customDialogCancel = findViewById(R.id.submit_customDialogCancel);

        /*Spannable wordPolicy = new SpannableString("Please read and agree to the \n");
        wordPolicy.setSpan(new ForegroundColorSpan(cnxt.getResources().getColor(R.color.colorAqua)), 0, wordPolicy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(wordPolicy);

        Spannable wordTwoPolicy = new SpannableString("cancellation policy");
        wordTwoPolicy.setSpan(new ForegroundColorSpan(cnxt.getResources().getColor(R.color.colorRed)), 0, wordTwoPolicy.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(wordTwoPolicy);*/

        Spannable word = new SpannableString("Note: ");
        word.setSpan(new ForegroundColorSpan(cnxt.getResources().getColor(R.color.colorBlack)), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtview_cancelPolicy.setText(word);

        Spannable wordTwo = new SpannableString("If you cancel an appointment with less than 24 hours, you will be charged");
        wordTwo.setSpan(new ForegroundColorSpan(cnxt.getResources().getColor(R.color.colorGrey)), 0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtview_cancelPolicy.append(wordTwo);

        Spannable wordThree = new SpannableString(" $50");
        wordThree.setSpan(new ForegroundColorSpan(cnxt.getResources().getColor(R.color.colorBlack)), 0, wordThree.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtview_cancelPolicy.append(wordThree);

        submit_customDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkbox_dialogCancel.isChecked())
                {
                    Toast.makeText(cnxt,  "Please agree to the cancellation policy.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    dismiss();

                    if (!InternetConnectivity.isConnected(cnxt)) {
                        DialogHelperClass.getConnectionError(cnxt).show();
                    } else {
                        ProgressDialog.getInstance().show(cnxt);
                        BookingcancelApi();
                    }


                }



            }
        });

    }

    private void BookingcancelApi()
    {

        BookingCancel_Api bookingCancel_api = new BookingCancel_Api();
        bookingCancel_api.bookingCancel(cnxt,bookingId, new BookingCancel_Api.OnBookingCancel() {
            @Override
            public void OnBookingCancelSuccess(String res) {

                ProgressDialog.getInstance().dismiss();
                try {

                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");
                    String msg = jsonObject.getString("message");
                    Toast.makeText(cnxt, msg+"", Toast.LENGTH_SHORT).show();

                    if (status.equalsIgnoreCase("1"))
                    {
                    CustomDilaogCancelled customDialog = new CustomDilaogCancelled(cnxt);
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    // customDialog.setCancelable(false);
                    customDialog.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void OnBookingCancelFailure() {
                ProgressDialog.getInstance().dismiss();
                DialogHelperClass.getSomethingError(cnxt).show();
            }
        });

    }
}
