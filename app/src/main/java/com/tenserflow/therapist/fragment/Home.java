package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.LinearLayoutManagerWithSmoothScroller;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.HomeListing_Api;
import com.tenserflow.therapist.adapter.CustomDrawerAdapter;
import com.tenserflow.therapist.adapter.DataModelAdapter;
import com.tenserflow.therapist.adapter.HomeAdapter;
import com.tenserflow.therapist.model.HomeListing.HomeListing;
import com.tenserflow.therapist.model.HomeListing.Listing;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {


    @BindView(R.id.recyclerview_home)
    RecyclerView recyclerviewHome;
    Unbinder unbinder;

    HomeAdapter mAdapter;
    @BindView(R.id.relTopHome)
    RelativeLayout relTopHome;
    RecyclerView.LayoutManager mLayoutManager;
    private List<Listing> mUsers = new ArrayList<>();
    DataModelAdapter dataModelAdapter;
    List<Listing> listingFinal;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);


        setNavBar();
        changeTitle();


        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewHome.setLayoutManager(mLayoutManager);
        recyclerviewHome.setItemAnimator(new DefaultItemAnimator());*/

        mLayoutManager = new LinearLayoutManagerWithSmoothScroller(getActivity());
        recyclerviewHome.setLayoutManager(mLayoutManager);


        if (!InternetConnectivity.isConnected(getActivity())) {
            DialogHelperClass.getConnectionError(getActivity()).show();
        } else {

            ProgressDialog.getInstance().show(getActivity());

            HomeListing_Api homeListing_api = new HomeListing_Api();
            homeListing_api.getHomeListing(getActivity(), new HomeListing_Api.GetHomeListingCallBack() {
                @Override
                public void onGetHomeListingStatus(HomeListing jsonObjectRes) {


                    ProgressDialog.getInstance().dismiss();


                    try {


                        Snackbar snackbar = Snackbar.make(relTopHome, jsonObjectRes.getMesaage(), Snackbar.LENGTH_SHORT);
                        snackbar.show();


                        if (jsonObjectRes.getStatus().equalsIgnoreCase("1")) {


                              mAdapter = new HomeAdapter(getActivity(), jsonObjectRes.getListing());
                              recyclerviewHome.setAdapter(mAdapter);


                            //-----------for showing progress loader---------------------
                           /* if (jsonObjectRes.getListing().size()<=10)
                            {
                                mAdapter = new HomeAdapter(getActivity(), jsonObjectRes.getListing());
                                recyclerviewHome.setAdapter(mAdapter);
                            }

                            else
                            {
                                callHomeAdapter(jsonObjectRes.getListing());
                            }*/

                            try {
                                recyclerviewHome.smoothScrollToPosition(HelperPreferences.SELECTED_ITEM_HOME + 1);
                            } catch (Exception e) {
                            }


                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onGetHomeListingFailure() {

                    ProgressDialog.getInstance().dismiss();
                    DialogHelperClass.getSomethingError(getActivity()).show();
                }
            });

        }


        return view;
    }

    private void callHomeAdapter(List<Listing> listing) {

        listingFinal = new ArrayList<>();
        listingFinal.addAll(listing);


         if (listingFinal.size()>10)
         {
             for (int i = 0; i < 10; i++) {
                 mUsers.add(listingFinal.get(i));
             }
         }


        dataModelAdapter = new DataModelAdapter(getActivity(), recyclerviewHome,mUsers);
        recyclerviewHome.setAdapter(dataModelAdapter);


        dataModelAdapter.setOnLoadMoreListener(new DataModelAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                mUsers.add(null);
                dataModelAdapter.notifyItemInserted(mUsers.size() - 1);

                //Load more data for reyclerview
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("haint", "Load More 2");
                        //Remove loading item
                        mUsers.remove(mUsers.size() - 1);
                        dataModelAdapter.notifyItemRemoved(mUsers.size());
                        //Load data
                        int index = mUsers.size();
                        int end = index + 10;

                        if (listingFinal.size()>=end)
                        { }
                        else
                        {
                          int t =  end-listingFinal.size();
                          end = end - t;
                        }


                     if (mUsers.size()!=listingFinal.size())
                     {

                         for (int i = index; i < end; i++) {
                            Listing user = new Listing();
                           /* user.setName("Name " + i);
                            user.setEmail("alibaba" + i + "@gmail.com");*/
                            mUsers.add(listingFinal.get(i));
                        }

                        dataModelAdapter.notifyDataSetChanged();
                        dataModelAdapter.setLoaded();
                     }
                    }
                }, 2000);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Home");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).lockDrawer(false);


    }


    public void setNavBar() {

        ArrayList<String> dataList = new ArrayList<String>();
        if (Global.enterWayVar.equalsIgnoreCase("simple")) {

            dataList.add("Home");
            dataList.add("Introduction");
            dataList.add("Legal(Informed Consent)");
            dataList.add("Alerts");
            dataList.add("HIPPA");
            dataList.add("Profile");
            dataList.add("Change Password");
            dataList.add("Chat");
            dataList.add("Contact");
            dataList.add("Logout");
        } else {
            dataList.add("Home");
            dataList.add("Introduction");
            dataList.add("Legal(Informed Consent)");
            dataList.add("Alerts");
            dataList.add("HIPPA");
            dataList.add("Profile");
            dataList.add("Chat");
            dataList.add("Contact");
            dataList.add("Logout");
        }


        ((MainActivity) getActivity()).navigationDrawerList.setAdapter(new CustomDrawerAdapter(
                getActivity(),
                R.layout.custom_drawer_item,
                dataList, ((MainActivity) getActivity()).navigationDrawerList, ((MainActivity) getActivity()).drawerLayout, "1"));


    }



}
