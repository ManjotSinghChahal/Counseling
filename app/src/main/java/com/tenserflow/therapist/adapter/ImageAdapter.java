package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.ImageViewerActivity;
import com.tenserflow.therapist.model.Detail.ImageArray;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Admin on 12/21/2018.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    Unbinder unbinder;
    Context context;
    ArrayList<ImageArray> imageArray;


    public ImageAdapter(FragmentActivity activity, ArrayList<ImageArray> imageArrays) {
        this.context = activity;
        this.imageArray = imageArrays;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Glide.with(context).load(imageArray.get(position).getImage()).apply(new RequestOptions()).into(holder.imageAll);


        }

    @Override
    public int getItemCount() {
        return imageArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_all) ImageView imageAll;
        @OnClick(R.id.cardview_image_detail)
        public void onViewClicked() {

            // ImageViewerActivity.start(context, "http://therapy.gangtask.com/public/images/default.jpg",Global.KEY_IMAGE_URL);
            ImageViewerActivity.start(context, imageArray.get(getLayoutPosition()).getImage(), Global.KEY_IMAGE_URL);

        }


        public ViewHolder(View itemView) {

            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
