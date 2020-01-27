package com.tenserflow.therapist.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.CustomSpinnerSubcat;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.Detail_Api;
import com.tenserflow.therapist.adapter.ImageAdapter;
import com.tenserflow.therapist.adapter.ReviewAdapter;
import com.tenserflow.therapist.adapter.VideoAdapter;
import com.tenserflow.therapist.model.Detail.DetailListing;
import com.tenserflow.therapist.model.Detail.ImageArray;
import com.tenserflow.therapist.model.Detail.ReviewsArray;
import com.tenserflow.therapist.model.Detail.SubCategoryArray;
import com.tenserflow.therapist.model.Detail.VideoArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail extends Fragment {


    @BindView(R.id.recyclerview_review_detail)
    RecyclerView recyclerviewReviewDetail;
    Unbinder unbinder;


    @BindView(R.id.recyclerview_videos)
    RecyclerView recyclerviewVideos;
    @BindView(R.id.recyclerview_images)
    RecyclerView recyclerviewImages;
    @BindView(R.id.spinner_subCatDetail)
    Spinner spinnerSubCatDetail;
    @BindView(R.id.rel_lay_Detail)
    RelativeLayout relLayDetail;
    @BindView(R.id.therapyType_detail)
    TextView therapyTypeDetail;
   /* @BindView(R.id.therapyPrice_detail)
    TextView therapyPriceDetail;*/
    @BindView(R.id.therapyDesc_detail)
    TextView therapyDescDetail;
    @BindView(R.id.rtB_ratingDetail)
    RatingBar rtBRatingDetail;
    @BindView(R.id.txtview_noReview)
    TextView txtviewNoReview;
    @BindView(R.id.rel_layout_viewAll)
    RelativeLayout relLayoutViewAll;
    @BindView(R.id.txtview_noVideos)
    TextView txtviewNoVideos;
    @BindView(R.id.txtview_noImages)
    TextView txtviewNoImages;
    @BindView(R.id.catgoryName_detail)
    TextView catgoryNameDetail;
    private ArrayList<ReviewsArray> reviewsArrays;
    private ArrayList<SubCategoryArray> subCatArrays;
    private ArrayList<ImageArray> imageArrays;
    private ArrayList<VideoArray> videoArrays;

    CustomSpinnerSubcat customAdapter;
    ImageAdapter imageAdapter;
    ReviewAdapter reviewAdapter;
    VideoAdapter videoAdapter;
    String therapy_id = "", therapy_name = "", therapy_desc = "", therapy_price = "", therapy_rating = "", subCatId = "";
    String individual_1_session = "", individual_6_session = "", group_1_session = "", group_6_session = "", category_name = "",
            category_id = "";

    public Detail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        unbinder = ButterKnife.bind(this, view);


        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();
        if (bundle.containsKey("therapy_id")) {
            therapy_id = bundle.getString("therapy_id");
            therapy_name = bundle.getString("therapy_name");
            therapy_desc = bundle.getString("therapy_desc");
          //  therapy_price = bundle.getString("therapy_price");
            therapy_rating = bundle.getString("therapy_rating");
            category_name = bundle.getString("category_name");
            category_id = bundle.getString("category_id");

            catgoryNameDetail.setText(category_name);



            if (!InternetConnectivity.isConnected(getActivity())) {
                DialogHelperClass.getConnectionError(getActivity()).show();
            } else {
                detailApi();
            }

        }

        changeTitle();


        //----------------------review adapter---------------------------------------------------
        reviewsArrays = new ArrayList<>();

        if (reviewsArrays.size() == 0) {
            txtviewNoReview.setVisibility(View.VISIBLE);
            relLayoutViewAll.setVisibility(View.GONE);
        } else {
            txtviewNoReview.setVisibility(View.GONE);
            relLayoutViewAll.setVisibility(View.VISIBLE);
        }


        reviewAdapter = new ReviewAdapter(getActivity(), reviewsArrays);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewReviewDetail.setLayoutManager(mLayoutManager);
        recyclerviewReviewDetail.setItemAnimator(new DefaultItemAnimator());
        recyclerviewReviewDetail.setAdapter(reviewAdapter);


        //--------------------Video Adapter-------------------------------------
        videoArrays = new ArrayList<>();

        if (videoArrays.size() == 0)
            txtviewNoVideos.setVisibility(View.VISIBLE);
        else
            txtviewNoVideos.setVisibility(View.GONE);

        videoAdapter = new VideoAdapter(getActivity(), videoArrays);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewVideos.setLayoutManager(layoutManager);
        recyclerviewVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerviewVideos.setAdapter(videoAdapter);

        //----------------------Image Adapter------------------------------------------------
        imageArrays = new ArrayList<>();

        if (imageArrays.size() == 0)
            txtviewNoImages.setVisibility(View.VISIBLE);
        else
            txtviewNoImages.setVisibility(View.GONE);

        imageAdapter = new ImageAdapter(getActivity(), imageArrays);
        RecyclerView.LayoutManager layoutManager_img = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewImages.setLayoutManager(layoutManager_img);
        recyclerviewImages.setItemAnimator(new DefaultItemAnimator());
        recyclerviewImages.setAdapter(imageAdapter);

        //------------------------------spinner---------------------------------------------
        subCatArrays = new ArrayList<>();
        /*SubCategoryArray subCategoryArray = new SubCategoryArray();
        subCategoryArray.setSubCategoryName("Select Sub Category");
        subCatArrays.add(subCategoryArray);*/


        customAdapter = new CustomSpinnerSubcat(getActivity(), subCatArrays);
        spinnerSubCatDetail.setAdapter(customAdapter);


        spinnerSubCatDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                try {

                    subCatId = subCatArrays.get(i).getId().toString();

                    therapyTypeDetail.setText(Global.capitalize(subCatArrays.get(i).getName()));




                    //----------------------review----------------------------------------------
                    reviewsArrays.clear();
                    reviewsArrays.addAll(subCatArrays.get(i).getReviewsArray());

                    if (subCatArrays.get(i).getReviewsArray().size() == 0) {
                        relLayoutViewAll.setVisibility(View.GONE);
                        txtviewNoReview.setVisibility(View.VISIBLE);
                    } else {
                        relLayoutViewAll.setVisibility(View.VISIBLE);
                        txtviewNoReview.setVisibility(View.GONE);
                    }
                    reviewAdapter.notifyDataSetChanged();

                    //-----------------------images----------------------------------------------------
                    imageArrays.clear();
                    imageArrays.addAll(subCatArrays.get(i).getImages());

                    if (subCatArrays.get(i).getImages().size() == 0)
                        txtviewNoImages.setVisibility(View.VISIBLE);
                    else
                        txtviewNoImages.setVisibility(View.GONE);

                    imageAdapter.notifyDataSetChanged();

                    //----------------------videos------------------------------------------------------
                    videoArrays.clear();
                    videoArrays.addAll(subCatArrays.get(i).getVideos());

                    if (subCatArrays.get(i).getVideos().size() == 0)
                        txtviewNoVideos.setVisibility(View.VISIBLE);
                    else
                        txtviewNoVideos.setVisibility(View.GONE);

                    videoAdapter.notifyDataSetChanged();


                    //--------------------------------------------------------------------------------

                    if (subCatArrays.get(i).getDescription()!=null && !subCatArrays.get(i).getDescription().equalsIgnoreCase(""))
                    {
                        therapyDescDetail.setText(subCatArrays.get(i).getDescription());
                        Global.makeTextViewResizable(therapyDescDetail, 2, "..See More", true);
                    }
                    else
                        therapyDescDetail.setText("No Description");



                    if (subCatArrays.get(i).getPrice()!=null && !subCatArrays.get(i).getPrice().equalsIgnoreCase(""))
                   // therapyPriceDetail.setText("$" + subCatArrays.get(i).getPrice());
                    try {
                        if (subCatArrays.get(i).getRatings()!=null && !subCatArrays.get(i).getRatings().equalsIgnoreCase(""))
                        rtBRatingDetail.setRating(Float.parseFloat(subCatArrays.get(i).getRatings()));
                    } catch (Exception e) {
                    }

                } catch (Exception e) {
                  Log.e("ErrorGet",e.getMessage());
                }

                //---------------------------------------------------------------------------------------

                if (subCatArrays.get(i).getSessionArray().size()!=0)
                {
                    individual_1_session = subCatArrays.get(i).getSessionArray().get(0).getIndividual1Session();
                    individual_6_session = subCatArrays.get(i).getSessionArray().get(0).getIndividual6Session();
                    group_1_session = subCatArrays.get(i).getSessionArray().get(0).getGroup1Session();
                    group_6_session = subCatArrays.get(i).getSessionArray().get(0).getGroup6Session();
                }



                //--------------------------------------------------------------------------------------------


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText(therapy_name);
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).lockDrawer(true);


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.txtview_book_now, R.id.rel_layout_viewAll})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.txtview_book_now:

              /*  if (subCatId == null || subCatId.equalsIgnoreCase("")) {
                    Snackbar.make(relLayDetail, "Select Sub Category", Snackbar.LENGTH_SHORT).show();
                } else {*/



                    BookingDetail bookingDetail = new BookingDetail();
                    Bundle bundleList = new Bundle();

                    bundleList.putString("therapy_id", therapy_id);
                    bundleList.putString("subCatId", subCatId);

                    bundleList.putString("individual_1_session", individual_1_session);
                    bundleList.putString("individual_6_session", individual_6_session);
                    bundleList.putString("group_1_session", group_1_session);
                    bundleList.putString("group_6_session", group_6_session);

                    bookingDetail.setArguments(bundleList);
                    Global.changeFragment(getActivity(), bookingDetail, "save");
             //   }

                break;

            case R.id.rel_layout_viewAll:
                ReviewAll reviewAll = new ReviewAll();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("review_list", reviewsArrays);

                reviewAll.setArguments(bundle);
                Global.changeFragment(getActivity(), reviewAll, "save");
                break;


        }
    }


    private void detailApi() {

       ProgressDialog.getInstance().show(getActivity());

        Detail_Api detail_api = new Detail_Api();
        detail_api.detail_Api(getActivity(), category_id,therapy_id, new Detail_Api.DetailCallBack() {
            @Override
            public void onDetailStatus(DetailListing res) {

               ProgressDialog.getInstance().dismiss();

               try {
                   Snackbar.make(relLayDetail, res.getMesaage(), Snackbar.LENGTH_SHORT);
               }catch (Exception e){

               }


                if (res.getStatus().equalsIgnoreCase("1")) {


                   //------------------------subcategory----------------------------------------
                    subCatArrays.clear();

                    if (res.getRecord().getSubCategoryArray().size()!=0)
                    {
                        subCatArrays.addAll(res.getRecord().getSubCategoryArray());
                        customAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("No Record Found");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Your action here
                                dialog.dismiss();
                                getActivity().onBackPressed();


                            }
                        });
                        alert.show();
                    }






                } else {
                    if (subCatArrays.size()==0) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("No Record Found");
                        alert.setCancelable(false);
                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Your action here
                                dialog.dismiss();
                                getActivity().onBackPressed();


                            }
                        });
                        alert.show();

                    }
                }

            }

            @Override
            public void onDetailFailure() {

                ProgressDialog.getInstance().dismiss();
                DialogHelperClass.getSomethingError(getActivity()).show();
            }
        });


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
