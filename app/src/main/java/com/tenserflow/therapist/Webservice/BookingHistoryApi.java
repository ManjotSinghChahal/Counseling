package com.tenserflow.therapist.Webservice;

import android.content.Context;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.Utils.RetrofitClient;
import com.tenserflow.therapist.model.Booking_history.Booking_History;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingHistoryApi {
    Context context;
    BookingHistoryCallBack callBack;
    public void bookHistory(Context context,BookingHistoryCallBack bookingDetailCallBack) {
        this.context=context;
        this.callBack=bookingDetailCallBack;


        Service service = RetrofitClient.getClient().create(Service.class);
        Call<Booking_History> call = service.bookingHistory(Global.token);
        call.enqueue(new Callback<Booking_History>() {
            @Override
            public void onResponse(Call<Booking_History> call, Response<Booking_History> response) {

                if (response.isSuccessful())
                    callBack.onBookingHistoryStatus(response.body());
                else
                    callBack.onBookingHistoryFailure();


            }

            @Override
            public void onFailure(Call<Booking_History> call, Throwable t) {

                callBack.onBookingHistoryFailure();

            }
        });


    }

    public interface BookingHistoryCallBack {
        void onBookingHistoryStatus(Booking_History booking);

        void onBookingHistoryFailure();
    }
}
