package com.tenserflow.therapist.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
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
import com.tenserflow.therapist.Utils.VideoViewActivity;
import com.tenserflow.therapist.model.Detail.VideoArray;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Admin on 12/20/2018.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    Unbinder unbinder;
    Context context;
    ArrayList<VideoArray> videoArray;
    Bitmap bitmap;

    public VideoAdapter(FragmentActivity activity, ArrayList<VideoArray> videoArrays) {
        this.context = activity;
        this.videoArray = videoArrays;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Glide.with(context).load(videoArray.get(position).getVideo())
                .apply(new RequestOptions())
                .into(holder.videoThumbnail);


    }

    @Override
    public int getItemCount() {
        return videoArray.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_thumbnail)
        ImageView videoThumbnail;

        @OnClick(R.id.cardview_video_detail)
        public void onViewClicked() {

            VideoViewActivity.start(context, videoArray.get(getAdapterPosition()).getVideo(), Global.KEY_VIDEO_URL);
        }

        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }


}
