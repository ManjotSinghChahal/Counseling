package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tenserflow.therapist.R;

import java.util.ArrayList;

public class CustomSpinnerTimeslot extends BaseAdapter {
    LayoutInflater inflter;
    Context context;
    ArrayList<String> arrayList;
    ArrayList<String> timeslot_id;
    public CustomSpinnerTimeslot(FragmentActivity activity, ArrayList<String> arrayLists,ArrayList<String> timeslot_id) {
        this.context=activity;
        this.arrayList=arrayLists;
        this.timeslot_id=timeslot_id;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view_, ViewGroup viewGroup) {
        View  view = inflter.inflate(R.layout.custom_spinner, null);

        TextView txtview = view.findViewById(R.id.txtview);
        txtview.setText(arrayList.get(i));

        return view;
    }

}

