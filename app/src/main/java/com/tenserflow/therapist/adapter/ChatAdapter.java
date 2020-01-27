package com.tenserflow.therapist.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tenserflow.therapist.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ChatAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<HashMap<String, String>> chatList = new ArrayList<>();
    public static final int TYPE_SEND = 0;
    public static final int TYPE_RECEIVE = 1;


    public ChatAdapter(Context activity, ArrayList<HashMap<String, String>> chatList) {
        this.context = activity;
        this.chatList = chatList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

        switch (viewType) {
            case TYPE_SEND:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_sender_layout, parent, false);
                return new SenderViewHolder(view);
            case TYPE_RECEIVE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_receiver_layout, parent, false);
                return new ReceiverViewHolder(view);

        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        if (i == 0)
            viewHolder.itemView.setPadding(0, (int)context.getResources().getDimension(R.dimen._12sdp), 0, 0);


        if (viewHolder instanceof SenderViewHolder)
        {
            ((SenderViewHolder) viewHolder).relSendMessage.setVisibility(View.VISIBLE);
            ((SenderViewHolder) viewHolder).txtviewSender.setText(chatList.get(i).get("send_msg"));
        }
        else
        {
            ((ReceiverViewHolder) viewHolder).relReceiveMessage.setVisibility(View.VISIBLE);
            ((ReceiverViewHolder) viewHolder).txtviewReceiver.setText(chatList.get(i).get("receive_msg"));
        }


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;
        @BindView(R.id.rel_sendMessage)
        RelativeLayout relSendMessage;
        @BindView(R.id.txtview_sender)
        TextView txtviewSender;

        public SenderViewHolder(View itemView) {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        Unbinder unbinder;

        @BindView(R.id.rel_receiveMessage)
        RelativeLayout relReceiveMessage;
        @BindView(R.id.txtview_receiver)
        TextView txtviewReceiver;

        public ReceiverViewHolder(View itemView)  {
            super(itemView);
            unbinder = ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (chatList.get(position).get("Id"))
        {
            case "0": return TYPE_SEND;
            case "1": return TYPE_RECEIVE;
            default: return -1;
        }



    }

}
