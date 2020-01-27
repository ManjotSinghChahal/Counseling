package com.tenserflow.therapist.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Webservice.Payment_Api;
import com.tenserflow.therapist.Webservice.Service;
import com.tenserflow.therapist.fragment.Home;
import com.tenserflow.therapist.model.PersonalDetail.PersonalDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

import retrofit2.Call;

import static com.tenserflow.therapist.view.activity.PayPalPaymentAct.PAYPAL_REQUEST_CODE;


/**
 * Created by Arvind on 01-Dec-16.
 */
public class Payment extends DialogFragment {
Context context;
String amount="10";
String booking_id;

   //  public static final String PAYPAL_CLIENT_ID = "AUqO0W75Uai4v85lTSf83y3nmwJv-0O4GR0Yf2WY9MMAal14-BY4dR83aS3ezTl9YCUi1Gnh7WDeqiIA";  // last call applive
  //   public static final String PAYPAL_CLIENT_ID = "AWbiee-itPAKrDs5kmU80VfNNcu8plO1yznZORMnHqqaQSgRvFzehfBHfUzOnZZWnYe0IUVrFAT5xEUe";  // last call app sandbox
       public static final String PAYPAL_CLIENT_ID = "AdUgx4IZXw89m-i5WCwiky9n5r4zMK97kWHgWI8oAGti-mmKdid_4tHf8858fBQPo7k3I2oOpBx6cnIj";  // therapist sandbox
    public Payment(FragmentActivity activity, String amount,String booking_id) {
        this.context =activity;
        this.amount=amount;
        this.booking_id=booking_id;
    }
    public Payment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final RelativeLayout root = new RelativeLayout(getActivity());
        root.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.payment_screen, container, false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


       // start the payment class

        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);



        if (amount!=null && !amount.equalsIgnoreCase("") && !amount.equalsIgnoreCase("0"))
           getPayment();
        else
        {
          //  amount = "50";
            getPayment();
        }




        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();

        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Window window = getDialog().getWindow();
        ViewGroup.LayoutParams attributes = window.getAttributes();
        //must setBackgroundDrawable(TRANSPARENT) in onActivityCreated()
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }




    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .acceptCreditCards(true)
            .clientId(PAYPAL_CLIENT_ID);

    private void getPayment() {


        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", "Counseling Charges",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(getActivity(), PaymentActivity.class);

        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);


        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {

            dismiss();


            //If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {
                //Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                //if confirmation is not null
                if (confirm != null) {
                    //Getting the payment details
                    String paymentDetails = confirm.toJSONObject().toString();
                    Log.e("paymentDetails",paymentDetails);

                    Toast.makeText(context,  "Transaction Successful", Toast.LENGTH_SHORT).show();


                    try {

                        JSONObject jsonObject = new JSONObject(paymentDetails);

                        if (jsonObject.has("response"))
                        {
                           String res =  jsonObject.getString("response");
                            JSONObject jsonObject1 = new JSONObject(res);
                            String create_time = jsonObject1.getString("create_time");
                            String trans_id = jsonObject1.getString("id");
                            String state = jsonObject1.getString("state");

                            if (state.equalsIgnoreCase("approved"))
                            {
                                Payment_Api payment_api = new Payment_Api();
                                payment_api.payment(getActivity(),booking_id,trans_id,amount,create_time);

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }





                    //Starting a new activity for the payment details and also putting the payment details with intent
                   // store payment details in shared pref and clear on payment api success method
/*                        sharedPreferences = getActivity().getSharedPreferences(GlobalVariables.Sharedpreference, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(GlobalVariables.payment_Status, "paid");
                        editor.putString(GlobalVariables.user_id, userid);
                        editor.putString(GlobalVariables.username, username);
                        editor.commit();
                        Intent i = new Intent(getActivity(), Home.class);
                        startActivity(i);
                        MDToast mdToast = MDToast.makeText(getActivity(), "Payment Done", Toast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
                        mdToast.show();
                        payment_api();*/

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.e("paymentExample", "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.e("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        }
    }



}
