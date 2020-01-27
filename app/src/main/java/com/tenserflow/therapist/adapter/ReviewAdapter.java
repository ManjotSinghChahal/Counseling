package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.model.Detail.ReviewsArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Admin on 12/17/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    Context context;
    List<ReviewsArray> reviewsArray;
    int sizeOfArray;

   /* public ReviewAdapter()
    {}*/


    public ReviewAdapter(FragmentActivity activity, List<ReviewsArray> reviews_Array) {
        this.context = activity;
        this.reviewsArray = reviews_Array;


    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_adapter, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        if (position <= 3) {
            String name = reviewsArray.get(position).getReviewUserName();
            holder.txtviewReviewName.setText(reviewsArray.get(position).getReviewUserName());


            if (name.length() > 0) {
                char first = name.charAt(0);
                holder.txtviewUName.setText(String.valueOf(first).toUpperCase());
            } else {
                holder.txtviewUName.setText(name);
            }


            holder.txtviewReviewName.setText(Global.capitalize(reviewsArray.get(position).getReviewUserName()));

            if (reviewsArray.get(position).getReviewsCount().equalsIgnoreCase("") || reviewsArray.get(position).getReviewsCount().equalsIgnoreCase("0"))
                holder.reviewCount.setText("0 Review");
            else if (reviewsArray.get(position).getReviewsCount().equalsIgnoreCase("1"))
                holder.reviewCount.setText(reviewsArray.get(position).getReviewsCount() + " Review");
            else
                holder.reviewCount.setText(reviewsArray.get(position).getReviewsCount() + " Reviews");
        }
    }

    @Override
    public int getItemCount() {
        if (reviewsArray.size() > 3) {
            return 3;
        } else {
            return reviewsArray.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtviewUName)
        TextView txtviewUName;
        @BindView(R.id.txtview_review_name)
        TextView txtviewReviewName;
        @BindView(R.id.review_count)
        TextView reviewCount;
        Unbinder unbinder;

        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
