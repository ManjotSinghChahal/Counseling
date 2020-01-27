package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.fragment.Detail;
import com.tenserflow.therapist.model.HomeListing.Listing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DataModelAdapter extends RecyclerView.Adapter < RecyclerView.ViewHolder >
{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener mOnLoadMoreListener;
    private boolean isLoading;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private List<Listing> listing = new ArrayList<>();
    Context context;
    RecyclerView recyclerviewHome;


    public DataModelAdapter(Context activity, RecyclerView recyclerviewHome, List<Listing> listing)
    {
        this.context= activity;
        this.recyclerviewHome= recyclerviewHome;
        this.listing= listing;

        Log.e("BFrbvff",listing.size()+"  hhh");

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerviewHome.getLayoutManager();

        recyclerviewHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });
    }


    class UserViewHolder extends RecyclerView.ViewHolder {

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


        public UserViewHolder (View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);

        }

    }
     class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    @Override public int getItemViewType(int position)
    {
        return listing.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_item, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
          //  Listing user = listing.get(position);
            UserViewHolder userViewHolder = (UserViewHolder) holder;

            userViewHolder.therapyCatName.setText(listing.get(position).getTherapyName()+" hjhj");
            userViewHolder.txtviewDescHome.setText(listing.get(position).getDescription());

            Log.e("bvtrgf",position+" jj");
            Log.e("bvtrgf11111111111",listing.size()+" jj");

            if (position==listing.size()-1){
                holder.itemView.setPadding(0,0,0, (int) context.getResources().getDimension(R.dimen._16sdp));}

            try {
                userViewHolder.rtBRating.setRating(Float.parseFloat(listing.get(position).getRatings()));
            }catch (Exception e){

            }

            Glide.with(context).load(listing.get(position).getImage()).apply(new RequestOptions()).into(userViewHolder.thearpyImage);


          //  userViewHolder.tvName.setText(user.getTherapyName());
         //   userViewHolder.tvEmailId.setText(user.getEmail());


        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }
    @Override
    public int getItemCount() {
        return listing == null ? 0 : listing.size();
    }
    public void setLoaded() {
        isLoading = false;
    }

    public interface OnLoadMoreListener
    {
        void onLoadMore();

    }

}