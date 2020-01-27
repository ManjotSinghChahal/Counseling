package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class SessionRecord extends Fragment {
    String sessionCount;
    @BindView(R.id.cardview_booking_history)
    CardView cardviewBookingHistory;
    @BindView(R.id.cardview_booking_history1)
    CardView cardviewBookingHistory1;
    @BindView(R.id.cardview_booking_history2)
    CardView cardviewBookingHistory2;
    @BindView(R.id.cardview_booking_history3)
    CardView cardviewBookingHistory3;
    @BindView(R.id.cardview_booking_history4)
    CardView cardviewBookingHistory4;
    @BindView(R.id.cardview_booking_history5)
    CardView cardviewBookingHistory5;
    Unbinder unbinder;

    public SessionRecord() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_session_record, container, false);
        unbinder = ButterKnife.bind(this, view);
        changeTitle();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("sessionCount")) {
                sessionCount = bundle.getString("sessionCount");
                if (sessionCount.equalsIgnoreCase("1 Session"))
                {
                    cardviewBookingHistory.setVisibility(View.VISIBLE);
                    cardviewBookingHistory1.setVisibility(View.GONE);
                    cardviewBookingHistory2.setVisibility(View.GONE);
                    cardviewBookingHistory3.setVisibility(View.GONE);
                    cardviewBookingHistory4.setVisibility(View.GONE);
                    cardviewBookingHistory5.setVisibility(View.GONE);
                }
                else
                {
                    cardviewBookingHistory.setVisibility(View.VISIBLE);
                    cardviewBookingHistory1.setVisibility(View.VISIBLE);
                    cardviewBookingHistory2.setVisibility(View.VISIBLE);
                    cardviewBookingHistory3.setVisibility(View.VISIBLE);
                    cardviewBookingHistory4.setVisibility(View.VISIBLE);
                    cardviewBookingHistory5.setVisibility(View.VISIBLE);
                }


            }
        }



        return view;
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Session Record");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).lockDrawer(true);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
