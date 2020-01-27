package com.tenserflow.therapist.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.fragment.Detail;

public class CustomDialog extends Dialog {
    TextView close_customDialog;
    Context cnxt;
    String individual_therapy_fee,perPrice,sixPrice;
    TextView txtviewTherapyType,txtview_perPrice,txtview_sixPrice;
    public CustomDialog(Context context, String individual_therapy_fee_, String perPrice_, String sixPrice_) {
        super(context);
        this.cnxt=context;
        this.individual_therapy_fee=individual_therapy_fee_;
        this.perPrice=perPrice_;
        this.sixPrice=sixPrice_;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

        close_customDialog = findViewById(R.id.close_customDialog);
        txtviewTherapyType = findViewById(R.id.txtviewTherapyType);
        txtview_perPrice = findViewById(R.id.txtview_perPrice);
        txtview_sixPrice = findViewById(R.id.txtview_sixPrice);

        txtviewTherapyType.setText(individual_therapy_fee);

        if (perPrice!=null && !perPrice.equalsIgnoreCase(""))
         txtview_perPrice.setText("$"+perPrice);
        if (sixPrice!=null && !sixPrice.equalsIgnoreCase(""))
         txtview_sixPrice.setText("$"+sixPrice);

        close_customDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               dismiss();


            }
        });


    }
}
