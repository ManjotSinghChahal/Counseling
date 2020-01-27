package com.tenserflow.therapist.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.HelperPreferences;
import com.tenserflow.therapist.fragment.BookingDetail;
import com.tenserflow.therapist.fragment.BookingHistory;
import com.tenserflow.therapist.fragment.ChangePassword;
import com.tenserflow.therapist.fragment.Chat;
import com.tenserflow.therapist.fragment.ContactUs;
import com.tenserflow.therapist.fragment.Home;
import com.tenserflow.therapist.fragment.Profile;
import com.tenserflow.therapist.fragment.WebviewIntro;
import com.tenserflow.therapist.view.activity.Login;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 12/12/2018.
 */

public class CustomDrawerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> drawerItemList;
    int layoutResID;
   boolean[] arrayList;
   ListView navigationDrawerList;
   DrawerLayout drawerLayout;
    GoogleApiClient mGoogleApiClient;
    public static View view;
    String str_;
    TextView   textviewDrawerItem;
    int count= 0;
    DrawerItemHolder drawerHolder;

    public CustomDrawerAdapter(Context context, int layoutResourceID, List<String> listItems,ListView navigationDrawerList,DrawerLayout drawerLayout,String str) {
        super(context, layoutResourceID, listItems);
        this.context = context;
        this.drawerItemList = listItems;
        this.layoutResID = layoutResourceID;
        this.navigationDrawerList = navigationDrawerList;
        this.drawerLayout = drawerLayout;
        this.str_ = str;

        arrayList = new boolean[drawerItemList.size()];


        for (int f=0;f<drawerItemList.size();f++)
        {
            arrayList[f]=false;
        }
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        // TODO Auto-generated method stub


         view = convertView;

        if (view == null) {

            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            drawerHolder = new DrawerItemHolder();

            view = inflater.inflate(layoutResID, parent, false);

            drawerHolder.ItemName = (TextView)view.findViewById(R.id.drawer_itemName);
            drawerHolder.rel_root = (RelativeLayout)view.findViewById(R.id.rel_root);
            textviewDrawerItem =(TextView)view.findViewById(R.id.drawer_itemName);
            textviewDrawerItem.setText(drawerItemList.get(position));

            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(context)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
            mGoogleApiClient.connect();



            drawerHolder.rel_root.setTag(position);
            view.setTag(drawerHolder);

        } else {
            drawerHolder = (DrawerItemHolder) view.getTag();
        }



        //----------------to change textColor of nav bar---------------------------------
        if (count==position)
            drawerHolder.ItemName.setTextColor(Color.parseColor("#0BCBDC"));
        else
           drawerHolder.ItemName.setTextColor(Color.parseColor("#999696"));




        drawerHolder.rel_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //----------------to change textColor on every nav bar item click except logout click-----------
                if (position!=drawerItemList.size()-1)
                {
                    count = (int) view.getTag();
                    notifyDataSetChanged();
                }


                 try {
                    replaceScreen(String.valueOf(drawerItemList.get(position)));
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }
        });







        return view;
    }



    private void logout()

    {
        if (Global.enterWayVar.equalsIgnoreCase("socialf"))
        {

            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null) {
                LoginManager.getInstance().logOut();
            }
        }
        else if (Global.enterWayVar.equalsIgnoreCase("socialg"))
        {
            if (mGoogleApiClient.isConnected()) {


                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(Status status) {
                                context.startActivity(new Intent(context, Login.class));
                                ((MainActivity)context).finish();

                            }
                        });
            }
        }



       String rememberME = HelperPreferences.get(context).getString("rememberME");
        if (rememberME!=null && !rememberME.equalsIgnoreCase("") && rememberME.equalsIgnoreCase("checked"))
        {
            HelperPreferences.get(context).saveString("logout_status","logout");
            context.startActivity(new Intent(context, Login.class));
            ((Activity)context).finish();
        }
        //----------------clearing-------------------------------
        else
        {
            HelperPreferences.get(context).clear();
            context.startActivity(new Intent(context, Login.class));
            ((Activity)context).finish();
        }



    }

    private static class DrawerItemHolder {
        TextView ItemName;
        RelativeLayout rel_root;

    }


    private void replaceScreen(String text) throws Exception
    {

        if (text.equalsIgnoreCase("Logout"))
        {
            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setTitle("Do you want to Logout?");
            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Your action here
                    logout();

                }
            });

            alert.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

            alert.show();


        }
        else if (text.equalsIgnoreCase("Home"))
        {

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Home());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Profile"))
        {

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Profile());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Change Password"))
        {

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new ChangePassword());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Introduction"))
        {

            WebviewIntro webviewIntro = new WebviewIntro();

            Bundle bundle = new Bundle();
            bundle.putString("keyEnter","Introduction");
            webviewIntro.setArguments(bundle);

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, webviewIntro);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Legal(Informed Consent)"))
        {

            WebviewIntro webviewIntro = new WebviewIntro();

            Bundle bundle = new Bundle();
            bundle.putString("keyEnter","Legal(Informed Consent)");
            webviewIntro.setArguments(bundle);

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, webviewIntro);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("HIPPA"))
        {

            WebviewIntro webviewIntro = new WebviewIntro();

            Bundle bundle = new Bundle();
            bundle.putString("keyEnter","HIPPA");
            webviewIntro.setArguments(bundle);

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, webviewIntro);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Contact"))
        {

            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new ContactUs());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Alerts"))
        {


            FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new BookingHistory());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else if (text.equalsIgnoreCase("Chat"))
        {

           /* FragmentManager fragmentManager =((MainActivity)context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new Chat());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/

        }

        drawerLayout.closeDrawers();


    }

}