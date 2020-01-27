package com.tenserflow.therapist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Window;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.BookingHistoryApi;
import com.tenserflow.therapist.adapter.BookingHistoryAdapter;
import com.tenserflow.therapist.fragment.BookingHistory;
import com.tenserflow.therapist.model.Booking_history.Booking_History;
import com.tenserflow.therapist.model.Booking_history.HistoryArray;

import java.util.ArrayList;

public class CustomDilaogCancelled extends Dialog {
    Context cnxt;
    public CustomDilaogCancelled(Context context) {
        super(context);
        this.cnxt=context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_cancelled);

     }

    @Override
    protected void onStop() {
       // super.onStop();
        if (!InternetConnectivity.isConnected(cnxt)) {
            DialogHelperClass.getConnectionError(cnxt).show();
        } else {
            getBookingHstoryApi();
        }




    }


    private void getBookingHstoryApi() {
        BookingHistoryApi bookingHistoryApi = new BookingHistoryApi();
        bookingHistoryApi.bookHistory(cnxt, new BookingHistoryApi.BookingHistoryCallBack() {

            @Override
            public void onBookingHistoryStatus(Booking_History result) {
                ProgressDialog.getInstance().dismiss();
                try {

                    if (result.getStatus().equalsIgnoreCase("1")) {
                        String msg = result.getMesaage();

                        Global.changeFragment(cnxt,new BookingHistory(),"save");




                    }
                 }
                   catch (Exception e) {
                        e.printStackTrace();
                        }



            }

            @Override
            public void onBookingHistoryFailure() {

            }
        });





    }
    }
