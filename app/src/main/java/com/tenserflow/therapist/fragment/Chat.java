package com.tenserflow.therapist.fragment;


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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.adapter.ChatAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {


    @BindView(R.id.recyclerview_chat)
    RecyclerView recyclerviewChat;
    Unbinder unbinder;

    ChatAdapter chatAdapter;
    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.rel_send_btn)
    RelativeLayout relSendBtn;
    @BindView(R.id.rel_sendMessage)
    RelativeLayout relSendMessage;

    ArrayList<HashMap<String, String>> chatList = new ArrayList<>();
    @BindView(R.id.edt_message)
    EditText edtMessage;
    @BindView(R.id.relTop_chatfragment)
    RelativeLayout relTopChatfragment;

    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        unbinder = ButterKnife.bind(this, view);

        chatList = new ArrayList<>();

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("receive_msg","Thanks for using Therapist App. Please tell me how can i help you.");
        hashMap.put("Id","1");
        chatList.add(hashMap);

       /* HashMap<String,String> hashMap1 = new HashMap<>();
        hashMap1.put("send_msg","Willing to take.");
        hashMap1.put("Id","0");
        chatList.add(hashMap1);*/


        changeTitle();

        chatAdapter = new ChatAdapter(getActivity(),chatList);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerviewChat.setLayoutManager(mLayoutManager);
        recyclerviewChat.setItemAnimator(new DefaultItemAnimator());
        recyclerviewChat.setAdapter(chatAdapter);


        return view;
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Chat");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).lockDrawer(false );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rel_send_btn, R.id.rel_sendMessage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_send_btn:

                if (edtMessage.getText().toString().trim().equalsIgnoreCase(""))
                {
                    Toast.makeText(getActivity(),  "Please enter message", Toast.LENGTH_SHORT).show();
                }
                else {

                   /* if (chatList.size()==7)
                    {
                        HashMap<String,String> hashMap = new HashMap<>();
                        hashMap.put("receive_msg","Physical therapy is the world. It discounts the permanent customer. It also offers life time validity to special customers. Every one can apply for special prime memeber ship.");
                        hashMap.put("Id","1");
                        chatList.add(hashMap);
                    }*/

                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("send_msg",edtMessage.getText().toString().trim());
                    hashMap.put("Id","0");
                    chatList.add(hashMap);

                    edtMessage.setText("");

                    chatAdapter.notifyDataSetChanged();
                    try {
                        recyclerviewChat.scrollToPosition(chatList.size()-1);
                    }catch (Exception e){
                    }

                }



                break;
            case R.id.rel_sendMessage:
                break;
        }
    }
}
