package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.dialog.CustomDialogAccepted;
import com.tenserflow.therapist.dialog.CustomDialogCancel;
import com.tenserflow.therapist.fragment.SessionRecord;
import com.tenserflow.therapist.model.Booking_history.HistoryArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.tenserflow.therapist.Utils.HelperPreferences.SELECTED_ITEM_BOOKING_HISTORY;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.ViewHolder> {
    Context context;
    RelativeLayout relBookingHistory;
    ArrayList<HistoryArray> historyArray = new ArrayList<>();

    public BookingHistoryAdapter() {


    }
    public BookingHistoryAdapter(Context activity, ArrayList<HistoryArray> historyArray) {
        this.context = activity;
        this.historyArray = historyArray;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.booking_history_adpter, parent, false);


        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


        viewHolder.therapyNameBH.setText(historyArray.get(i).getTherapyName());
        viewHolder.txtThearDescBH.setText(historyArray.get(i).getDescription());
        if (historyArray.get(i).getPrice()!=null && !historyArray.get(i).getPrice().equalsIgnoreCase(""))
        viewHolder.therapyPriceBH.setText("$"+historyArray.get(i).getPrice());


        if (i == historyArray.size()-1)
            viewHolder.itemView.setPadding(0, 0, 0, (int) context.getResources().getDimension(R.dimen._12sdp));

        //---------------------------displaying booking status------------------------------------------------------------
        if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("A")) {
            viewHolder.orderSatatusBookingHis.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.orderSatatusBookingHis.setText("Accepted");
        } else if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("R")) {
            viewHolder.orderSatatusBookingHis.setTextColor(context.getResources().getColor(R.color.colorRed));
            viewHolder.orderSatatusBookingHis.setText("Rejected");
        } else if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("C")) {
            viewHolder.orderSatatusBookingHis.setTextColor(context.getResources().getColor(R.color.colorYellow));
            viewHolder.orderSatatusBookingHis.setText("Completed");
        } else if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("P")) {
            viewHolder.orderSatatusBookingHis.setTextColor(context.getResources().getColor(R.color.colorAqua));
            viewHolder.orderSatatusBookingHis.setText("Pending");
        }

        //-----------------displaying review and cancel btn alternatively---------------------------------------
        if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("C")) {
            final int sdk = Build.VERSION.SDK_INT;
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtReviewCancelBH.setText("Reviews");
                viewHolder.txtReviewCancelBH.setTextColor(context.getResources().getColor(R.color.colorAqua));
                viewHolder.relReviewcancelBH.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_corners_aqua_thin));
            } else {
                viewHolder.txtReviewCancelBH.setText("Reviews");
                viewHolder.txtReviewCancelBH.setTextColor(context.getResources().getColor(R.color.colorAqua));
                viewHolder.relReviewcancelBH.setBackground(ContextCompat.getDrawable(context, R.drawable.round_corners_aqua_thin));
            }
        } else {
            final int sdk = Build.VERSION.SDK_INT;
            if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.txtReviewCancelBH.setText("Cancel");
                viewHolder.txtReviewCancelBH.setTextColor(context.getResources().getColor(R.color.colorRed));
                viewHolder.relReviewcancelBH.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.round_corners_red));
            } else {
                viewHolder.txtReviewCancelBH.setText("Cancel");
                viewHolder.txtReviewCancelBH.setTextColor(context.getResources().getColor(R.color.colorRed));
                viewHolder.relReviewcancelBH.setBackground(ContextCompat.getDrawable(context, R.drawable.round_corners_red));
            }
        }
        //---------------------hiding review and cancel on reject status------------------------------------------------
        if (historyArray.get(i).getBookingStatus().equalsIgnoreCase("R"))
             viewHolder.relReviewcancelBH.setVisibility(View.GONE);
        else
             viewHolder.relReviewcancelBH.setVisibility(View.VISIBLE);


        //------------------------------------------------------------------------------------------------------------


    }

    @Override
    public int getItemCount() {
        return historyArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;
        @BindView(R.id.cardview_booking_history)
        CardView cardviewBookingHistory;
        @BindView(R.id.orderSatatus_bookingHis)
        TextView orderSatatusBookingHis;
        @BindView(R.id.rel_BH_therapyName)
        RelativeLayout relBHTherapyName;
        @BindView(R.id.rel_reviewcancel_BH)
        RelativeLayout relReviewcancelBH;
        @BindView(R.id.txt_reviewCancel_BH)
        TextView txtReviewCancelBH;
        @BindView(R.id.therapyName_BH)
        TextView therapyNameBH;
        @BindView(R.id.therapyPrice_BH)
        TextView therapyPriceBH;
        @BindView(R.id.txt_thearDesc_BH)
        TextView txtThearDescBH;

        @OnClick({R.id.rel_arrowNext_BH, R.id.orderSatatus_bookingHis, R.id.txt_reviewCancel_BH})
        public void onViewClicked(View view) {

            switch (view.getId()) {

                case R.id.rel_arrowNext_BH:

                    SELECTED_ITEM_BOOKING_HISTORY=getAdapterPosition();

                    Bundle bundle = new Bundle();
                    bundle.putString("sessionCount",historyArray.get(getAdapterPosition()).getBookingSession());
                    SessionRecord sessionRecord = new SessionRecord();
                    sessionRecord.setArguments(bundle);
                    Global.changeFragment(context, sessionRecord, "save");
                    break;

                case R.id.orderSatatus_bookingHis:



                    break;
                case R.id.txt_reviewCancel_BH:

                    //------------------showing review button---------------------------------------
                    if (historyArray.get(getAdapterPosition()).getBookingStatus().equalsIgnoreCase("C"))
                    {
                        CustomDialogAccepted customDialogAccepted = new CustomDialogAccepted(context,historyArray.get(getAdapterPosition()).getBookingId());
                        customDialogAccepted.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        // customDialog.setCancelable(false);
                        customDialogAccepted.show();
                    }
                    //----------------showing cancel button-----------------------------------------
                    else
                    {
                        CustomDialogCancel customDialog = new CustomDialogCancel(context,historyArray.get(getAdapterPosition()).getBookingId());
                        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        // customDialog.setCancelable(false);
                        customDialog.show();


                    }



                    break;

            }

        }


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }


}
