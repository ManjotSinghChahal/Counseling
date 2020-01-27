package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.BookingHistoryApi;
import com.tenserflow.therapist.adapter.BookingHistoryAdapter;
import com.tenserflow.therapist.model.Booking_history.Booking_History;
import com.tenserflow.therapist.model.Booking_history.HistoryArray;
import com.tenserflow.therapist.model.Booking_history.RecordBookingHistory;
import java.util.ArrayList;
import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingHistory extends Fragment {


    @BindView(R.id.recyclerview_bookingHis)
    RecyclerView recyclerviewBookingHis;
    Unbinder unbinder;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rel_bookingHistory)
    RelativeLayout relBookingHistory;
   ArrayList<HistoryArray> historyArray;

    BookingHistoryAdapter bookingHistoryAdapter;

    public BookingHistory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_history, container, false);
        unbinder = ButterKnife.bind(this, view);

        changeTitle();

        historyArray = new ArrayList<>();
        bookingHistoryAdapter = new BookingHistoryAdapter(getActivity(), historyArray);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewBookingHis.setLayoutManager(mLayoutManager);
        recyclerviewBookingHis.setItemAnimator(new DefaultItemAnimator());
        recyclerviewBookingHis.setAdapter(bookingHistoryAdapter);

        if (!InternetConnectivity.isConnected(getActivity())) {
            DialogHelperClass.getConnectionError(getActivity()).show();
        } else {
            ProgressDialog.getInstance().show(getActivity());
            getBookingHstoryApi();
        }




        return view;
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Booking History & Alerts");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).lockDrawer(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getBookingHstoryApi() {
        BookingHistoryApi bookingHistoryApi = new BookingHistoryApi();
        bookingHistoryApi.bookHistory(getActivity(), new BookingHistoryApi.BookingHistoryCallBack() {
            @Override
            public void onBookingHistoryStatus(Booking_History result) {

                ProgressDialog.getInstance().dismiss();
                try {

                    if (result.getStatus().equalsIgnoreCase("1"))
                    {

                        String msg = result.getMesaage();
                        Snackbar.make(relBookingHistory,msg,Snackbar.LENGTH_SHORT);

                        historyArray.clear();
                        historyArray.addAll(result.getRecord().getHistoryArray());
                        bookingHistoryAdapter.notifyDataSetChanged();

                        try {
                            recyclerviewBookingHis.smoothScrollToPosition(HelperPreferences.SELECTED_ITEM_BOOKING_HISTORY + 1);
                        } catch (Exception e) {
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            }

            @Override
            public void onBookingHistoryFailure() {
                ProgressDialog.getInstance().dismiss();
                DialogHelperClass.getSomethingError(getActivity()).show();
            }


        });
    }
}
