package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.adapter.ReviewAllAdapter;
import com.tenserflow.therapist.model.Detail.ReviewsArray;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewAll extends Fragment {


    @BindView(R.id.recyclerview_reviewAll)
    RecyclerView recyclerviewReviewAll;
    Unbinder unbinder;

    ReviewAllAdapter reviewAllAdapter;

    public ReviewAll() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review_all, container, false);
        unbinder = ButterKnife.bind(this, view);


        Bundle bundle = getArguments()!=null?getArguments():new Bundle();
        if (bundle.containsKey("review_list"))
        {

            List<ReviewsArray> reviewList = bundle.getParcelableArrayList("review_list");

            //----------------------review adapter---------------------------------------------------
            reviewAllAdapter = new ReviewAllAdapter(getActivity(),reviewList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerviewReviewAll.setLayoutManager(mLayoutManager);
            recyclerviewReviewAll.setItemAnimator(new DefaultItemAnimator());
            recyclerviewReviewAll.setAdapter(reviewAllAdapter);


        }





        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.recyclerview_reviewAll)
    public void onViewClicked() {
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Reviews");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).lockDrawer(true);


    }

    @Override
    public void onResume() {
        super.onResume();

        changeTitle();


    }




}
