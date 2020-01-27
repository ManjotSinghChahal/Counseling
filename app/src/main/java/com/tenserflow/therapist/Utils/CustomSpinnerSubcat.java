package com.tenserflow.therapist.Utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.model.Detail.SubCategoryArray;

import java.util.ArrayList;

public class CustomSpinnerSubcat extends BaseAdapter {
    LayoutInflater inflter;
    Context context;
    ArrayList<SubCategoryArray> arrayLists;
    public CustomSpinnerSubcat(FragmentActivity activity, ArrayList<SubCategoryArray> arrayLists) {
        this.context=activity;
        this.arrayLists=arrayLists;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return arrayLists.size();
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
        try {

            if (arrayLists.get(i).getName()!=null & !arrayLists.get(i).getName().equalsIgnoreCase(""))
                txtview.setText(Global.capitalize(arrayLists.get(i).getName()));


        }catch (Exception e){
        }


        return view;
    }



}
