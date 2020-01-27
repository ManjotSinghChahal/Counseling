package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.model.Detail.ReviewsArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ReviewAllAdapter extends RecyclerView.Adapter<ReviewAllAdapter.ViewHolder> {
    Context context;
    List<ReviewsArray> reviewList;


    public ReviewAllAdapter(FragmentActivity activity, List<ReviewsArray> review_List) {
        this.context = activity;
        this.reviewList = review_List;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_all_adapter, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


         holder.txtviewReviewAllName.setText(Global.capitalize(reviewList.get(position).getReviewUserName()));
         holder.reviewAllDesc.setText(reviewList.get(position).getReviewDescription());
         Global.makeTextViewResizable(holder.reviewAllDesc, 2, "..See More", true);

         if (reviewList.get(position).getReviewsCount().equalsIgnoreCase("") || reviewList.get(position).getReviewsCount().equalsIgnoreCase("0"))
         holder.reviewAllCount.setText("0 Review");
        else  if (reviewList.get(position).getReviewsCount().equalsIgnoreCase("1"))
            holder.reviewAllCount.setText(reviewList.get(position).getReviewsCount()+" Review");
        else
             holder.reviewAllCount.setText(reviewList.get(position).getReviewsCount()+" Reviews");

        String name = reviewList.get(position).getReviewUserName();
        if (name.length()>0)
        {
            char first = name.charAt(0);
            holder.reviewAllUName.setText(String.valueOf(first).toUpperCase());
        }
        else
        {
            holder.reviewAllUName.setText(name);
        }


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;
        @BindView(R.id.reviewAll_uName)
        TextView reviewAllUName;
        @BindView(R.id.txtview_reviewAll_name)
        TextView txtviewReviewAllName;
        @BindView(R.id.reviewAll_count)
        TextView reviewAllCount;
        @BindView(R.id.reviewAll_desc)
        TextView reviewAllDesc;
        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }
}
