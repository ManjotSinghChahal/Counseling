package com.tenserflow.therapist.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.fragment.Detail;
import com.tenserflow.therapist.model.HomeListing.Listing;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Admin on 12/13/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    List<Listing> listing;


    public HomeAdapter(FragmentActivity activity, List<Listing> listings) {
        this.context = activity;
        this.listing = listings;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_adapter, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.therapyCatName.setText(listing.get(position).getTherapyName());
        holder.txtviewDescHome.setText(listing.get(position).getDescription());



        if (position==listing.size()-1){
        holder.itemView.setPadding(0,0,0, (int) context.getResources().getDimension(R.dimen._16sdp));}

        try {
            holder.rtBRating.setRating(Float.parseFloat(listing.get(position).getRatings()));
        }catch (Exception e){

        }

      //  holder.therapyPrice.setText("$"+listing.get(position).getPrice());
        Glide.with(context).load(listing.get(position).getImage()).apply(new RequestOptions()).into(holder.thearpyImage);



    }

    @Override
    public int getItemCount() {
        return listing.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cardview_home)
        CardView cardviewHome;
        @BindView(R.id.thearpy_image)
        ImageView thearpyImage;
        @BindView(R.id.txtview_desc_home)
        TextView txtviewDescHome;
        @BindView(R.id.therapy_catName)
        TextView therapyCatName;
        /*@BindView(R.id.therapy_price)
        TextView therapyPrice;*/
        @BindView(R.id.rtB_rating)
        RatingBar rtBRating;

        Unbinder unbinder;

        @OnClick(R.id.cardview_home)
        public void onViewClicked() {

            HelperPreferences.SELECTED_ITEM_HOME =getAdapterPosition();


            Detail detail = new Detail();
            Bundle bundle = new Bundle();
            bundle.putString("therapy_id",listing.get(getAdapterPosition()).getTherapyId());
            bundle.putString("therapy_desc",listing.get(getAdapterPosition()).getDescription());
            bundle.putString("therapy_name",listing.get(getAdapterPosition()).getTherapyName());
           // bundle.putString("therapy_price",listing.get(getAdapterPosition()).getPrice());
            bundle.putString("therapy_rating",listing.get(getAdapterPosition()).getRatings());
            bundle.putString("category_name",listing.get(getAdapterPosition()).getCategoryName());
            bundle.putString("category_id",listing.get(getAdapterPosition()).getCategoryId());
            detail.setArguments(bundle);
            Global.changeFragment(context, detail, "save");
        }


        public ViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

        }
    }

}
