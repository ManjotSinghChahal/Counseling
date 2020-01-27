package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUs extends Fragment {


    public ContactUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        changeTitle();

        return view;
    }

    private  void changeTitle()
    {

        ((MainActivity)getActivity()).userNameMainOptions.setText("Contact Us");
        ((MainActivity)getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity)getActivity()).relBackMainactivity.setVisibility(View.GONE);
        ((MainActivity)getActivity()).relMenuMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity)getActivity()).lockDrawer(false);
    }

}
