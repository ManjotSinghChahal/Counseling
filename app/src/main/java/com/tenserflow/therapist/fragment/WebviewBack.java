package com.tenserflow.therapist.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebviewBack extends Fragment {


    @BindView(R.id.webview_introB)
    WebView webviewIntroB;
    Unbinder unbinder;

    public WebviewBack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_webview_back, container, false);
        unbinder = ButterKnife.bind(this, view);


        Bundle bundle = getArguments();
        if (bundle!=null)
        {
            if (bundle.containsKey("keyEnter"))
            {
                WebSettings webSettings = webviewIntroB.getSettings();
                webSettings.setJavaScriptEnabled(true);
               // webviewIntroB.loadUrl("http://therapy.gangtask.com/policy.php");

                String key = bundle.getString("keyEnter");
                if (key.equalsIgnoreCase("Cancellation Policy"))
                {
                    changeTitle("Cancellation Policy");
                    webviewIntroB.loadData(Global.CANCELLATION_POLICY, "text/html", "UTF-8");
                }
                else if (key.equalsIgnoreCase("Informed Consent"))
                {
                    changeTitle("Informed Consent");
                    webviewIntroB.loadData(Global.INFORMED_CONSENT, "text/html", "UTF-8");
                }
            }
        }







        return view;
    }


    private void changeTitle(String str) {

        ((MainActivity) getActivity()).userNameMainOptions.setText(str);
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
}
