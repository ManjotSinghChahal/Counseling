package com.tenserflow.therapist.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


 class ChatAdapter1/* extends RecyclerView.Adapter*/ {}

/*
    List<ChatMessageModel> messagelist;
    Context context;
    private RecyclerViewClickListener onBottomReachedListener;
    private WebService service;
    private StripeApp StripeAppmApp;
    private int currentFlag = 0;
    private String user_id= "";
    private String reciever_d= "";

    String friendname;
    public static class SendMessageViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userImage;
        LinearLayout ll_sendchat;
        TextView txtMessage, txtMsgTime;
        ImageView read_tick;

        public SendMessageViewHolder(View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.img_user);
            this.read_tick = itemView.findViewById(R.id.sender_tick);
            this.txtMessage = itemView.findViewById(R.id.txt_message);
            this.txtMsgTime = itemView.findViewById(R.id.txt_msg_time);
            this.ll_sendchat = itemView.findViewById(R.id.ll_sendmsg);
        }
    }


    public static class RecievedMessageViewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        TextView txtMessage, txtMsgTime;
        LinearLayout ll_recmsg;
        public RecievedMessageViewHolder(View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.img_user);
            this.txtMessage = itemView.findViewById(R.id.txt_message);
            this.txtMsgTime = itemView.findViewById(R.id.txt_msg_time);
            this.ll_recmsg = itemView.findViewById(R.id.ll_recmsg);

        }
    }


    public static class TodayViewHolder extends RecyclerView.ViewHolder {

        TextView today;

        public TodayViewHolder(View itemView) {
            super(itemView);

            this.today = itemView.findViewById(R.id.today);


        }
    }


    public static class ImageSentViewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        ImageView imgSent;

        public ImageSentViewHolder(View itemView) {
            super(itemView);

            this.userImage = itemView.findViewById(R.id.img_user);
            this.imgSent = itemView.findViewById(R.id.img_sent_image);

        }
    }

    public static class ImageReceivedViewHolder extends RecyclerView.ViewHolder {


        CircleImageView userImage;
        ImageView imgSent;

        public ImageReceivedViewHolder(View itemView) {
            super(itemView);
            this.userImage = itemView.findViewById(R.id.img_user);
            this.imgSent = itemView.findViewById(R.id.img_received_image);

        }
    }

    public static class MakeOfferViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName, txtItemQuantity,dollarsign, txtItemCost, txtCanceled,aftertext_itemsendername,texitem_sendername,aftertxtItemName, aftertxtItemQuantity, aftertxtItemCost;
        Button btnAccept, btnCanceld, btnPay;
        LinearLayout liOffer, liPay, liOfferStatus,upperchat_layout,beforeaccept,afteraccept;


        public MakeOfferViewHolder(View itemView) {
            super(itemView);

            this.aftertxtItemName = (TextView) itemView.findViewById(R.id.aftertxt_item_name);
            this.dollarsign = (TextView) itemView.findViewById(R.id.dollar_sign);
            this.aftertxtItemQuantity = (TextView) itemView.findViewById(R.id.aftertxt_item_quantity);
            this.aftertxtItemCost = (TextView) itemView.findViewById(R.id.aftertxt_item_cost);
            this.aftertext_itemsendername = (TextView) itemView.findViewById(R.id.aftertxt_item_sender_name);
            this.txtItemName = (TextView) itemView.findViewById(R.id.txt_item_name);
            this.texitem_sendername = (TextView) itemView.findViewById(R.id.txt_item_sender_name);
            this.txtItemQuantity = (TextView) itemView.findViewById(R.id.txt_item_quantity);
            this.txtItemCost = (TextView) itemView.findViewById(R.id.txt_item_cost);
            this.txtCanceled = (TextView) itemView.findViewById(R.id.txt_offer_status);
            this.btnAccept = itemView.findViewById(R.id.btn_item_accept);
            this.btnPay = itemView.findViewById(R.id.btn_pay);
            this.btnCanceld = itemView.findViewById(R.id.btn_item_cancel);
            this.liOffer = itemView.findViewById(R.id.li_confirm_order);
            this.liPay = itemView.findViewById(R.id.li_pay_order);
            this.liOfferStatus = itemView.findViewById(R.id.li_offer_status);
            this.upperchat_layout = itemView.findViewById(R.id.uppperchatlayout);
            this.beforeaccept = itemView.findViewById(R.id.beforeacpt__offer__layout);
            this.afteraccept = itemView.findViewById(R.id.afteracpt_offerlayout);

        }
    }

    private TextView txtReview;
    public ChatAdapter(List<ChatMessageModel> messageList, Context context, TextView txtReview) {

        this.messagelist = messageList;
        this.context = context;
        this.txtReview=txtReview;
        onBottomReachedListener = new ChatActivity();
        service = Global.WebServiceConstants.getRetrofitinstance();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case SAConstants.Keys.TYPE_SEND:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_sent, parent, false);
                return new SendMessageViewHolder(view);
            case SAConstants.Keys.TYPE_RECEIVED:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_recieved, parent, false);
                return new RecievedMessageViewHolder(view);
            case SAConstants.Keys.TYPE_MAKEOFFER:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_make_offer, parent, false);
                return new MakeOfferViewHolder(view);
            case SAConstants.Keys.TYPE_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_send_image, parent, false);
                return new ImageSentViewHolder(view);
            case SAConstants.Keys.TYPE_RECIEVED_IMAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_received_image, parent, false);
                return new ImageReceivedViewHolder(view);

            case 101:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_today_layout, parent, false);
                return new TodayViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

          friendname = ((ChatActivity)context).txtUserName.getText().toString();
        user_id = messagelist.get(position).getSenderId();
        reciever_d = messagelist.get(position).getReceiverId();

        if (messagelist.get(position).isToday_boolean())
        {
            ((TodayViewHolder)holder).today.setText(messagelist.get(position).getToday());
        }
        else
        {

            if (messagelist.get(position).getSenderId().equalsIgnoreCase(HelperPreferences.get(context).getString(UID))) {

                if (messagelist.get(position).getType().equalsIgnoreCase("o")) {

                    handleOfferData(holder, position);

                } else if (messagelist.get(position).getType().equalsIgnoreCase("img")) {

                    handleSentImageData(holder, position);

                } else {



                    handleSentMessageData(holder, position);
                }
            } else {
                if (messagelist.get(position).getType().equalsIgnoreCase("o")) {

                    handleOfferData(holder, position);

                } else if (messagelist.get(position).getType().equalsIgnoreCase("img")) {

                    handleRecievedImageData(holder, position);

                } else {
                    handleRecievMessageData(holder, position);
                }
            }

        }



        if (position == messagelist.size() - 1) {

            onBottomReachedListener.onBottomReached(true);

        } else {

            onBottomReachedListener.onBottomReached(false);

        }
//        notifyDataSetChanged();
    }



    private void handleRecievMessageData(RecyclerView.ViewHolder holder, int position)
    {

        if (position==0)
        {
            ((RecievedMessageViewHolder) holder).ll_recmsg.setBackgroundResource(R.drawable.recchat1);


        }


        else
        {

            if (position+1<messagelist.size())
            {

                if (!messagelist.get(position-1).getReceiverId().equals(reciever_d))
                {
                    ((RecievedMessageViewHolder) holder).ll_recmsg.setBackgroundResource(R.drawable.recchat1);
                }
                else if (!messagelist.get(position+1).getReceiverId().equals(reciever_d))
                {
                    ((RecievedMessageViewHolder) holder).ll_recmsg.setBackgroundResource(R.drawable.recchat3);
                }
                else
                {
                    ((RecievedMessageViewHolder) holder).ll_recmsg.setBackgroundResource(R.drawable.recchat2);
                }

            }
            else
            {
                ((RecievedMessageViewHolder) holder).ll_recmsg.setBackgroundResource(R.drawable.recchat3);
            }




        }

        ((RecievedMessageViewHolder) holder).txtMessage.setText(messagelist.get(position).getMessage());
        ((RecievedMessageViewHolder) holder).userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultingIntent = new Intent(context, MainActivity.class);
                resultingIntent.putExtra(Chat_User_Data, messagelist.get(position).getSenderId());
                ((ChatActivity) context).startActivity(resultingIntent);
            }
        });



    }

    private void handleSentMessageData(RecyclerView.ViewHolder holder, int position) {


        if (messagelist.get(position).getMessage_read_status()!=null)
        {
            Log.e("ReadStatus" ,messagelist.get(position).getMessage_read_status());

            if (messagelist.get(position).getMessage_read_status().equalsIgnoreCase("Y"))
            {
                ((SendMessageViewHolder) holder).read_tick.setImageResource(R.drawable.red_tick);
            }
            else
            {
                ((SendMessageViewHolder) holder).read_tick.setImageResource(R.drawable.grey_tick);
            }
        }

            if (position==0)
            {
                ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat1);

            }


            else
            {

                if (position+1<messagelist.size())
                {
                    if (!messagelist.get(position-1).getSenderId().equals(user_id))
                    {
                        ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat1);
                    }
                    else if (!messagelist.get(position+1).getSenderId().equals(user_id))
                    {
                        ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat3);
                    }
                    else
                    {
                        ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat2);
                    }

                }
                else
                {
                    ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat3);
                }




            }
      */
/*  if(currentFlag==0)
        {
            ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat1);
        }
        else if(currentFlag==2)
        {
            ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat3);

        }
        else
        {
            ((SendMessageViewHolder) holder).ll_sendchat.setBackgroundResource(R.drawable.sendchat2);


        }*//*
*/
/*



       // if (messagelist.get(position).getSenderId().equals(messagelist.get(getItemCount())))


        ((SendMessageViewHolder) holder).txtMessage.setText(messagelist.get(position).getMessage());
        Glide.with(context).load(messagelist.get(position).getSenderImage()).apply(Global.getGlideOptions())
                .into(((SendMessageViewHolder) holder).userImage);
//        ((SendMessageViewHolder) holder).userImage.setImageResource(messagelist.get(position).getImageId());
        ((SendMessageViewHolder) holder).txtMsgTime.setText(formateDate(Global.convertUTCToLocal(messagelist.get(position).getCreatedAt())));

    }

    private void handleSentImageData(RecyclerView.ViewHolder holder, int position) {


        Glide.with(context).load(messagelist.get(position).getImageUrl()).apply(Global.getGlideOptions())
                .into(((ImageSentViewHolder) holder).imgSent);
        Glide.with(context).load(messagelist.get(position).getSenderImage()).apply(Global.getGlideOptions())
                .into(((ImageSentViewHolder) holder).userImage);
        ((ImageSentViewHolder) holder).imgSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewerActivity.start(context, messagelist.get(position).getImageUrl());
            }
        });
    }

    private void handleRecievedImageData(RecyclerView.ViewHolder holder, int position) {
        Glide.with(context).load(messagelist.get(position).getImageUrl()).apply(Global.getGlideOptions())
                .into(((ImageReceivedViewHolder) holder).imgSent);
        Glide.with(context).load(messagelist.get(position).getSenderImage()).apply(Global.getGlideOptions())
                .into(((ImageReceivedViewHolder) holder).userImage);
        ((ImageReceivedViewHolder) holder).imgSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewerActivity.start(context, messagelist.get(position).getImageUrl());
            }
        });
        ((ImageReceivedViewHolder) holder).userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultingIntent = new Intent(context, MainActivity.class);
                resultingIntent.putExtra(Chat_User_Data, messagelist.get(position).getSenderId());
                ((ChatActivity) context).startActivity(resultingIntent);
            }
        });
    }

    private void StripConnect(MakeOfferViewHolder holder, int position) {
        StripeAppmApp = new StripeApp(context, "geniusAppDeveloper", "ca_EWK1BYRqruSX1X92DbtLY8UiV46ADGoC",
                "sk_test_HDkDbhty58uz3aaJi2TDllrR", "https://developer.android.com", "read_write");
//        mStripeButton = (StripeButton) findViewById(R.id.btnStripeConnect);
        StripeButton btnStripeConnect = new StripeButton(context);
        btnStripeConnect.setStripeApp(StripeAppmApp);
        btnStripeConnect.setConnectMode(StripeApp.CONNECT_MODE.DIALOG);
        btnStripeConnect.addStripeConnectListener(new StripeConnectListener() {

            @Override
            public void onConnected() {
                Log.e("Connected_as", "onConnected: " + StripeAppmApp.getUserId());
                if (StripeAppmApp.getUserId() != null) {

                    new ApisHelper().linkStripApi(context, StripeAppmApp.getUserId(), new ApisHelper.StripeConnectCallback() {
                        @Override
                        public void onStripeConnectSuccess(String msg) {
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            HelperPreferences.get(context).saveString(API_ACCESS_TOKEN, StripeAppmApp.getUserId());
                            acceptDeclineApi((holder), messagelist.get(position).getOfferId(), "a", position);
//                            txtConnectionStatus.setVisibility(View.GONE);
                        }

                        @Override
                        public void onStripeConnectFailure() {
                            Toast.makeText(context, "Unable to link account with stripe at this movements", Toast.LENGTH_SHORT).show();
            *//*
*/
/*                Snackbar.make(context.getWindow().getDecorView(), , Snackbar.LENGTH_SHORT)
                                    .setAction("", null).show();*//*
*/
/*

                        }
                    });
                }
//                tvSummary.setText("Connected as " + StripeAppmApp.getAccessToken());
            }

            @Override
            public void onDisconnected() {
                Log.e("Disconnected", "Disconnected: ");
//                tvSummary.setText("Disconnected");
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }

        });
        btnStripeConnect.performClick();

    }

    private void handleOfferData(RecyclerView.ViewHolder holder, int position) {

        ((MakeOfferViewHolder) holder).txtItemName.setText(messagelist.get(position).getProductName());
        try {
            if (!TextUtils.isEmpty(messagelist.get(position).getQuantity())) {
                ((MakeOfferViewHolder) holder).txtItemQuantity.setText(messagelist.get(position).getQuantity());
            } else {
                ((MakeOfferViewHolder) holder).txtItemQuantity.setText("1");
            }
        } catch (Exception e) {
            ((MakeOfferViewHolder) holder).txtItemQuantity.setText("1");
        }
        ((MakeOfferViewHolder) holder).txtItemCost.setText(messagelist.get(position).getProductCost());
        Log.e("status: ",  messagelist.get(position).getStatus());
        if (messagelist.get(position).getSenderId().equalsIgnoreCase(HelperPreferences.get(context).getString(UID))) {
            showOfferStatusSenderSide(((MakeOfferViewHolder) holder), messagelist.get(position).getStatus(), position);
            ((MakeOfferViewHolder) holder).btnPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PaymentDialog.create(context, messagelist.get(position).getOfferId(), messagelist.get(position).getReceiverId(), messagelist.get(position).getProductId(), "", "", new PaymentDialog.PaymentCallBack() {
                        @Override
                        public void onPaymentSuccess() {
                            messagelist.get(position).setStatus("s");
                            notifyDataSetChanged();
                            if(txtReview!=null){
                                txtReview.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onPaymentFail(String message) {
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelDialog() {

                        }
                    }).show();
                }
            });

        } else {
            showOfferStatusReceiverSide(((MakeOfferViewHolder) holder), messagelist.get(position).getStatus(), position);
            ((MakeOfferViewHolder) holder).btnCanceld.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    acceptDeclineApi(((MakeOfferViewHolder) holder), messagelist.get(position).getOfferId(), "r", position);
                }
            });
            ((MakeOfferViewHolder) holder).btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e( "onClick: ","ff"+ HelperPreferences.get(context).getString(API_ACCESS_TOKEN));

                    acceptDeclineApi(((MakeOfferViewHolder) holder), messagelist.get(position).getOfferId(), "a", position);

//                    HelperPreferences.get(context).remove(API_ACCESS_TOKEN);
                    *//*
*/
/*if (!TextUtils.isEmpty(HelperPreferences.get(context).getString(API_ACCESS_TOKEN))) {
                        acceptDeclineApi(((MakeOfferViewHolder) holder), messagelist.get(position).getOfferId(), "a", position);
                    } else {
                        S_Dialogs.getStipeConnectDialog(context, ((dialog, which) -> {
                            StripConnect((MakeOfferViewHolder) holder, position);
                        })).show();
                    }*//*
*/
/*
                }
            });
        }


    }


    @Override
    public int getItemViewType(int position) {



            if (messagelist.get(position).getSenderId().equalsIgnoreCase(HelperPreferences.get(context).getString(UID))) {


                if (messagelist.get(position).isToday_boolean())
                {
                    return 101;
                }

                if (messagelist.get(position).getType().equalsIgnoreCase("o")) {

                    return SAConstants.Keys.TYPE_MAKEOFFER;

                } else if (messagelist.get(position).getType().equalsIgnoreCase("img")) {

                    return SAConstants.Keys.TYPE_IMAGE;

                } else {
                    return SAConstants.Keys.TYPE_SEND;
                }
            } else {

                if (messagelist.get(position).isToday_boolean())
                {
                    return 101;
                }

                if (messagelist.get(position).getType().equalsIgnoreCase("o")) {

                    return SAConstants.Keys.TYPE_MAKEOFFER;

                } else if (messagelist.get(position).getType().equalsIgnoreCase("img")) {

                    return SAConstants.Keys.TYPE_RECIEVED_IMAGE;

                } else {
                    return SAConstants.Keys.TYPE_RECEIVED;
                }
            }





    }

    @Override
    public int getItemCount() {
        return messagelist.size();
    }

  *//*



}

*/
