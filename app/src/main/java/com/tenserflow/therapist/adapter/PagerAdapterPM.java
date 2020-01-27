package com.tenserflow.therapist.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.tenserflow.therapist.R;

public class PagerAdapterPM extends PagerAdapter {
 Context context;
    private int mCurrentPosition = -1;
    public PagerAdapterPM(FragmentActivity activity) {
        this.context=activity;
    }

    public Object instantiateItem(ViewGroup viewGroup, int position) {

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.id.lin_layout_personal;
                break;
            case 1:
                resId = R.id.lin_layout_medical;
                break;
        }
        return viewGroup.findViewById(resId);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Personal";

        }else {
            return "Medical";
        }

    }




    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }





}
