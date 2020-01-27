package com.tenserflow.therapist.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.Payment;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.BookingDetail_Api;
import com.tenserflow.therapist.Webservice.PersonalMedical_Api;
import com.tenserflow.therapist.Webservice.Timeslot_Api;
import com.tenserflow.therapist.adapter.CustomSpinnerTimeslot;
import com.tenserflow.therapist.adapter.Date_time_Adapter;
import com.tenserflow.therapist.adapter.ImageIdentityAdapter;
import com.tenserflow.therapist.dialog.CustomDialog;
import com.tenserflow.therapist.model.Timeslot.GetTimeslot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookingDetail extends Fragment {


    @BindView(R.id.recyclerview_date_time_bd)
    RecyclerView recyclerviewDateTimeBd;
    Unbinder unbinder;
    @BindView(R.id.recyclerview_image_identity)
    RecyclerView recyclerviewImageIdentity;


    RecyclerView.LayoutManager layoutManager_img_identity;
    ImageIdentityAdapter imageIdentityAdapter;
    Date_time_Adapter date_time_adapter;

    byte[] byteArray;
    String img_path;

    ArrayList<String> arrayListLicImgs = new ArrayList<>();
    ArrayList<String> arrayListInsImgs = new ArrayList<>();
    ArrayList<String> arrayListBothImgs = new ArrayList<>();
    ArrayList<HashMap<String, String>> hashMapDateTime = new ArrayList<>();
    ArrayList<HashMap<String, String>> hashMapImg = new ArrayList<>();

    @BindView(R.id.txtview_date)
    TextView txtviewDate;


    @BindView(R.id.txtview_starttime_bd)
    TextView txtviewStarttimeBd;
    @BindView(R.id.rel_starttime_booking_detail)
    RelativeLayout relStarttimeBookingDetail;
    @BindView(R.id.txtview_endtime_bd)
    TextView txtviewEndtimeBd;
    @BindView(R.id.rel_top_bd)
    RelativeLayout relTopBd;
    @BindView(R.id.radioBtn_individual)
    RadioButton radioBtnIndividual;
    @BindView(R.id.radioBtn_group)
    RadioButton radioBtnGroup;
    @BindView(R.id.radioBtn_1session)
    RadioButton radioBtn1session;
    @BindView(R.id.radioBtn_6session)
    RadioButton radioBtn6session;
    @BindView(R.id.check_agree_bd)
    CheckBox checkAgreeBd;
    @BindView(R.id.rel_add_date_time)
    RelativeLayout relAddDateTime;
    @BindView(R.id.spinner_timeslot)
    Spinner spinnerTimeslot;

    private int mHour, mMinute;
    String date = "";
    String startTime = "";
    String endTime = "";
    String therapyType = "";
    String therapyPackage = "";
    String am_pm = "";
    String subCatId, therapy_id;
    String individual_1_session = "", individual_6_session = "", group_1_session = "", group_6_session = "";

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_SELECT_IMAGE = 101;
    String photoSelSatatus = "";

    String personal_status = "", medical_status = "";


    Date timeCompareStart;
    Date timeCompareEnd;
    String compareTimeStart = "";
    String compareTimeEnd = "";
    String inputFormat = "HH:mm";
    SimpleDateFormat inputParser = new SimpleDateFormat(inputFormat, Locale.US);
    String amount = "";
    ArrayList<String> arrayListTimeslot;
    ArrayList<String> timeslot_id;


    String lic_img = "";
    String ins_img = "";
    String booking_id = "";
    CustomSpinnerTimeslot customAdapterTimeslot;

    String selectedStartTime="",selectedEndTime="",selectedTimeslot="",selected_timeslot_id="";


    public BookingDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_detail, container, false);
        unbinder = ButterKnife.bind(this, view);


        Bundle bundle = getArguments() != null ? getArguments() : new Bundle();

        if (bundle.containsKey("therapy_id")) {
            subCatId = bundle.getString("subCatId");
            therapy_id = bundle.getString("therapy_id");

            individual_1_session = bundle.getString("individual_1_session");
            individual_6_session = bundle.getString("individual_6_session");
            group_1_session = bundle.getString("group_1_session");
            group_6_session = bundle.getString("group_6_session");


        }

        getPersonalMedical();


        //-------------------------timeslot spinner--------------------------------------------
        arrayListTimeslot = new ArrayList<>();
        timeslot_id = new ArrayList<>();
        /*SubCategoryArray subCategoryArray = new SubCategoryArray();
        subCategoryArray.setSubCategoryName("Select Sub Category");
        subCatArrays.add(subCategoryArray);*/



        arrayListTimeslot.add("Select Time-Slots");
        timeslot_id.add("0");
        customAdapterTimeslot = new CustomSpinnerTimeslot(getActivity(), arrayListTimeslot,timeslot_id);
        spinnerTimeslot.setAdapter(customAdapterTimeslot);

        spinnerTimeslot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTimeslot = arrayListTimeslot.get(i);



                if (timeslot_id.size()>0)
                selected_timeslot_id = timeslot_id.get(i);



                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


                //---------------------------------------------------------------------------------------


        changeTitle();

        //----------------------Date & Time Adapter------------------------------------------------
        date_time_adapter = new Date_time_Adapter(getActivity(), hashMapDateTime, new Date_time_Adapter.onDateTimeCallBack() {
            @Override
            public void onDateTimeSuccess() {

            }
        });
        RecyclerView.LayoutManager layoutManager_img = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewDateTimeBd.setLayoutManager(layoutManager_img);
        recyclerviewDateTimeBd.setItemAnimator(new DefaultItemAnimator());
        recyclerviewDateTimeBd.setAdapter(date_time_adapter);

        //----------------------Image Adapter------------------------------------------------
        imageIdentityAdapter = new ImageIdentityAdapter(getActivity(), hashMapImg);
        layoutManager_img_identity = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerviewImageIdentity.setLayoutManager(layoutManager_img_identity);
        recyclerviewImageIdentity.setItemAnimator(new DefaultItemAnimator());
        recyclerviewImageIdentity.setAdapter(imageIdentityAdapter);


        return view;
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Booking Detail");
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

    @OnClick({R.id.img_license_upload, R.id.img_insurance_upload, R.id.rel_add_date_time, R.id.rel_starttime_booking_detail, R.id.relLay_about_client, R.id.rel_date_booking_detail, R.id.rel_endtime_booking_detail, R.id.radioBtn_individual,
            R.id.radioBtn_group, R.id.radioBtn_1session, R.id.radioBtn_6session, R.id.rel_lay_payNow, R.id.txtview_cancellationPolicy_bd, R.id.txtview_informed_consent_bd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_license_upload:
                photoSelSatatus = "license";
                insertDummyContactWrapper();
                break;
            case R.id.img_insurance_upload:
                photoSelSatatus = "insurance";
                insertDummyContactWrapper();
                break;
            case R.id.rel_add_date_time:


                if (!radioBtn1session.isChecked() && !radioBtn6session.isChecked()) {
                    Snackbar.make(relTopBd, "Please choose Session Package", Snackbar.LENGTH_SHORT).show();
                } else {


                    if (date.equalsIgnoreCase(""))
                        Snackbar.make(relTopBd, "Please select Date", Snackbar.LENGTH_SHORT).show();
                   /* else if (startTime.equalsIgnoreCase(""))
                        Snackbar.make(relTopBd, "Please select Start Time", Snackbar.LENGTH_SHORT).show();
                    else if (endTime.equalsIgnoreCase(""))
                        Snackbar.make(relTopBd, "Please select End Time", Snackbar.LENGTH_SHORT).show();*/
                    else if (selectedTimeslot.equalsIgnoreCase("Select Time-Slots"))
                        Snackbar.make(relTopBd, "Please select Timeslot", Snackbar.LENGTH_SHORT).show();
                    else {

                        if (radioBtn1session.isChecked() && hashMapDateTime.size() == 1) {
                            Snackbar.make(relTopBd, "You have selected only one session that's why multiple dates addition is not allowed", Snackbar.LENGTH_SHORT).show();
                        } else if (radioBtn6session.isChecked() && hashMapDateTime.size() == 6) {
                            Snackbar.make(relTopBd, "You can select only 6 time slots", Snackbar.LENGTH_SHORT).show();
                        } else {


                            //----------checking to avoide 2 same time slot--------------------------------
                          String s="false";
                          for (int j=0;j<hashMapDateTime.size();j++)
                          {
                              if (hashMapDateTime.get(j).get("date").equalsIgnoreCase(date) && hashMapDateTime.get(j).get("slot_time").equalsIgnoreCase(selectedTimeslot))
                               s="true";
                          }



                          if (s.equalsIgnoreCase("false"))
                          {
                              HashMap<String, String> hashMap = new HashMap<>();
                              hashMap.put("date", date);
                              // hashMap.put("s_time", startTime);
                              //  hashMap.put("e_time", endTime);

                              hashMap.put("slot_time", selectedTimeslot);
                              hashMap.put("slot_time_id", selected_timeslot_id);




                              hashMapDateTime.add(hashMap);
                              date_time_adapter.notifyDataSetChanged();





                          //    arrayListTimeslot.clear();
                           //   arrayListTimeslot.add("Select Timeslot");
                          ///    customAdapterTimeslot.notifyDataSetChanged();

                            //  clearDateTime();

                          }
                          else
                          {
                              Toast.makeText(getActivity(),  "Please select different Timeslot", Toast.LENGTH_SHORT).show();
                          }




                        }


                    }
                }

                break;

            case R.id.rel_starttime_booking_detail:
                if (date.equalsIgnoreCase("")) {
                    Snackbar.make(relTopBd, "Please choose Date First", Snackbar.LENGTH_SHORT).show();
                } else
                    selectTime("start");
                break;

            case R.id.rel_endtime_booking_detail:
                if (startTime.equalsIgnoreCase("")) {
                    Snackbar.make(relTopBd, "Please choose Start Time First", Snackbar.LENGTH_SHORT).show();
                } else
                    selectTime("end");
                break;


            case R.id.relLay_about_client:

                AboutClient aboutClient = new AboutClient();
                Bundle bundle = new Bundle();
                bundle.putString("therapy_id", therapy_id);
                bundle.putString("subCatId", subCatId);
                aboutClient.setArguments(bundle);
                Global.changeFragment(getActivity(), aboutClient, "save");

                break;

            case R.id.rel_date_booking_detail:
                if (!radioBtn1session.isChecked() && !radioBtn6session.isChecked())
                    Snackbar.make(relTopBd, "Please choose Session Package", Snackbar.LENGTH_SHORT).show();
                else
                    selectDate();
                break;

            case R.id.radioBtn_individual:

                radioBtnIndividual.setChecked(true);
                radioBtnGroup.setChecked(false);
                therapyType = "individual";

                break;

            case R.id.radioBtn_group:

                radioBtnIndividual.setChecked(false);
                radioBtnGroup.setChecked(true);
                therapyType = "group";

                break;

            case R.id.radioBtn_1session:

                hashMapDateTime.clear();
                date_time_adapter.notifyDataSetChanged();

                clearDateTime();

                showPriceDialog("1session");
                break;

            case R.id.radioBtn_6session:

                hashMapDateTime.clear();
                date_time_adapter.notifyDataSetChanged();

                clearDateTime();

                showPriceDialog("6session");
                break;
            case R.id.txtview_cancellationPolicy_bd:
                WebviewBack webviewIntro = new WebviewBack();

                Bundle bundleC = new Bundle();
                bundleC.putString("keyEnter", "Cancellation Policy");
                webviewIntro.setArguments(bundleC);

                Global.changeFragment(getActivity(), webviewIntro, "save");

                break;
            case R.id.txtview_informed_consent_bd:
                WebviewBack webviewIntroI = new WebviewBack();
                Bundle bundleI = new Bundle();
                bundleI.putString("keyEnter", "Informed Consent");
                webviewIntroI.setArguments(bundleI);

                Global.changeFragment(getActivity(), webviewIntroI, "save");

                break;

            case R.id.rel_lay_payNow:

                 lic_img = "";
                 ins_img = "";


                //----------------for image array---------------------
                for (int g = 0; g < hashMapImg.size(); g++) {
                    if (hashMapImg.get(g).containsValue("license"))
                        lic_img = hashMapImg.get(g).get("image");
                    else if (hashMapImg.get(g).containsValue("insurance"))
                        ins_img = hashMapImg.get(g).get("image");
                }

                //--------------------date & time array--------------------------
                int timeArrayCount = 0;
                if (radioBtn1session.isChecked())
                    timeArrayCount = 1;
                else
                    timeArrayCount = 6;


                if (therapyType.equalsIgnoreCase(""))
                    Snackbar.make(relTopBd, "Please choose Therapy Type", Snackbar.LENGTH_SHORT).show();
                else if (therapyPackage.equalsIgnoreCase(""))
                    Snackbar.make(relTopBd, "Please choose Session Package", Snackbar.LENGTH_SHORT).show();
                else if (hashMapDateTime.size() == 0)
                    Snackbar.make(relTopBd, "Please add Date & Time", Snackbar.LENGTH_SHORT).show();
                else if (hashMapDateTime.size() != 0 && timeArrayCount != hashMapDateTime.size())
                    Snackbar.make(relTopBd, "You have selected  six sessions so you need to add 6 time slots", Snackbar.LENGTH_SHORT).show();
                else if (lic_img==null  || lic_img.equalsIgnoreCase("")) {
                    Snackbar.make(relTopBd, "Please Upload Licence image", Snackbar.LENGTH_SHORT).show();
                } else if (ins_img==null  || ins_img.equalsIgnoreCase("")) {
                    Snackbar.make(relTopBd, "Please Upload Insurance Card image", Snackbar.LENGTH_SHORT).show();
                } else if (!checkAgreeBd.isChecked())
                    Snackbar.make(relTopBd, "You need to agree our Cancellation Policy & Informed Consent", Snackbar.LENGTH_SHORT).show();
                else if (personal_status.equalsIgnoreCase("") || personal_status.equalsIgnoreCase("1"))
                    Snackbar.make(relTopBd, "Please add Personal information in About Client", Snackbar.LENGTH_SHORT).show();
                else if (medical_status.equalsIgnoreCase("") || medical_status.equalsIgnoreCase("1"))
                    Snackbar.make(relTopBd, "Please add Medical information in About Client", Snackbar.LENGTH_SHORT).show();

                else {


                    if (radioBtnIndividual.isChecked()) {
                        if (radioBtn1session.isChecked())
                            amount = individual_1_session;
                        else
                            amount = individual_6_session;
                    } else {
                        if (radioBtn1session.isChecked())
                            amount = group_1_session;
                        else
                            amount = group_6_session;
                    }


                    if (!InternetConnectivity.isConnected(getActivity())) {
                        DialogHelperClass.getConnectionError(getActivity()).show();
                    } else {

                        ProgressDialog.getInstance().show(getActivity());

                        BookingDetail_Api bookingDetail_api = new BookingDetail_Api();
                        bookingDetail_api.bookingDetail(getActivity(), subCatId, therapy_id, therapyType, therapyPackage, hashMapDateTime, hashMapImg,ins_img,lic_img, new BookingDetail_Api.GetBookingDetailCallBack() {
                            @Override
                            public void onGetBookingDetailStatus(String res) {

                                ProgressDialog.getInstance().dismiss();

                                try {

                                    JSONObject jsonObject = new JSONObject(res);

                                    final String status = jsonObject.get("status").toString();
                                    String message = jsonObject.get("message").toString();
                                    Snackbar.make(relTopBd, message, Snackbar.LENGTH_SHORT).show();


                                    if (status.equalsIgnoreCase("1")) {
                                        JSONObject jsonObject1 = jsonObject.getJSONObject("Booking_detail");
                                        booking_id = jsonObject1.get("Booking_id").toString();




                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {

                                            if (status.equalsIgnoreCase("1")) {
                                                Payment dialogFragment = new Payment(getActivity(), amount,booking_id);
                                                dialogFragment.show(getActivity().getFragmentManager(), "Payment");

                                            }

                                        }
                                    }, Snackbar.LENGTH_LONG + 1500);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }


                            @Override
                            public void onGetBookingDetailFailure() {

                                ProgressDialog.getInstance().dismiss();
                            }
                        });
                    }
                }

        }
    }


    //************************************************Camera Permission**********************************************
    @TargetApi(Build.VERSION_CODES.M)
    private void insertDummyContactWrapper() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 11);
        } else
            selectImage();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();

                } else {
                    // Permission Denied
                    Toast.makeText(getActivity(), "CAMERA Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    // **********************image picker gallery or camera********************
    private void selectImage() {


        String[] colors = {"Take  photo", "Choose photo"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Action");

        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]

                if (which == 0) {
                    takePhotoFromCamera();
                } else if (which == 1) {
                    choosePhotoFromGallary();
                }

            }
        });
        builder.show();


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            Uri tempUri = getImageUri(this.getActivity(), imageBitmap);
            img_path = getRealPathFromURI(tempUri);*/

            //  hashMapImg.add("license",)


            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("image", img_path);
            hashMap.put("type", photoSelSatatus);

            if (hashMapImg.size() != 0) {

                for (int j = 0; j < hashMapImg.size(); j++) {


                    if (!hashMapImg.get(j).containsValue(photoSelSatatus)) {
                    } else {
                        hashMapImg.remove(j);
                    }
                }
            } else {

            }
            hashMapImg.add(hashMap);


            imageIdentityAdapter.notifyDataSetChanged();


        }

        if (requestCode == REQUEST_SELECT_IMAGE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), contentURI);
                    Uri tempUri = getImageUri(getActivity(), bitmap);

                    img_path = getRealPathFromURI(tempUri);


                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("image", img_path);
                    hashMap.put("type", photoSelSatatus);

                    if (hashMapImg.size() != 0) {

                        for (int j = 0; j < hashMapImg.size(); j++) {


                            if (!hashMapImg.get(j).containsValue(photoSelSatatus)) {
                                Log.e("AddData", "onActivityResult: ");
                            } else {
                                Log.e("AddDataRemove", "onActivityResult: ");
                                hashMapImg.remove(j);
                            }
                        }
                    } else {

                    }
                    hashMapImg.add(hashMap);


                    imageIdentityAdapter.notifyDataSetChanged();
                    //      Glide.with(this).load(new File(img_path)).apply(new RequestOptions()).into(circleviewNav);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void takePhotoFromCamera() {
       /* Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);*/

        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                // Error occurred while creating the File


            }


            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", photoFile);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
                    pictureIntent.setClipData(ClipData.newRawUri("", photoURI));
                    pictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
                startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
            }
        }
    }

    private void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, REQUEST_SELECT_IMAGE);
    }


    private void selectDate() {

        Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtviewDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        txtviewDate.setTextColor(Color.parseColor("#1a1b1b"));

                        String date_ = year + "-" + (monthOfYear + 1) + "-" +dayOfMonth ;


                        if (!date.equalsIgnoreCase(date_)) {
                            startTime = "";
                            compareTimeStart = "";
                            txtviewStarttimeBd.setText("Start time");
                            txtviewStarttimeBd.setTextColor(getResources().getColor(R.color.colorGrey));
                            endTime = "";
                            compareTimeEnd = "";
                            txtviewEndtimeBd.setText("End time");
                            txtviewEndtimeBd.setTextColor(getResources().getColor(R.color.colorGrey));
                        }

                    //    date = date_;

                        String dd="",mm="";


                        if (dayOfMonth<10)
                          dd = "0"+dayOfMonth;
                        else
                            dd=dayOfMonth+"";

                        if ((monthOfYear+1)<10)
                            mm= "0"+(monthOfYear+1);
                        date = year +"-"+mm+"-"+dd+"";

                        Timeslot_Api timeslot_api = new Timeslot_Api();
                        timeslot_api.timeslot(getActivity(),therapy_id,subCatId,date, new Timeslot_Api.OnGetTimeSlot() {
                            @Override
                            public void onGetTimeslotSuccess(GetTimeslot res) {

                                try {


                                if (res.getStatus().equalsIgnoreCase("1"))
                                {
                                    arrayListTimeslot.clear();
                                    arrayListTimeslot.add("Select Time-Slots");
                                    timeslot_id.clear();
                                    timeslot_id.add("0");
                                    for (int i=0;i<res.getListing().size();i++)
                                    {
                                        arrayListTimeslot.add(res.getListing().get(i).getStartTime()+" - "+res.getListing().get(i).getEndTime());
                                        try {
                                            timeslot_id.add(String.valueOf(res.getListing().get(i).getId()));
                                        }catch (Exception e){

                                        }

                                    }
                                 //   customAdapterTimeslot = new CustomSpinnerTimeslot(getActivity(), arrayListTimeslot,timeslot_id);
                                //    spinnerTimeslot.setAdapter(customAdapterTimeslot);

                                    spinnerTimeslot.setSelection(0);
                                    customAdapterTimeslot.notifyDataSetChanged();
                                }
                                else
                                {
                                    arrayListTimeslot.clear();
                                    arrayListTimeslot.add("Select Time-Slots");

                                    timeslot_id.clear();

                                    spinnerTimeslot.setSelection(0);
                                    customAdapterTimeslot.notifyDataSetChanged();

                                    Toast.makeText(getActivity(), res.getMesaage()+ "", Toast.LENGTH_SHORT).show();
                                }

                                }catch (Exception e){

                                }

                            }

                            @Override
                            public void onGetTimeslotFailure() {

                            }
                        });


                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        datePickerDialog.show();
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 10000);

    }

    private void selectTime(final String time) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        if (time.equalsIgnoreCase("start"))
                            compareTimeStart = String.valueOf(hourOfDay + ":" + minute);
                        else
                            compareTimeEnd = String.valueOf(hourOfDay + ":" + minute);

                        //---------------------------------------------------------------


                        int hrs = 0;
                        if (hourOfDay < 12) {
                            hrs = hourOfDay;
                            am_pm = "AM";

                        } else {
                            am_pm = "PM";
                            hrs = hourOfDay - 12;
                        }

                        if (time.equalsIgnoreCase("start")) {

                            if (hrs < 10) {

                                if (minute < 10) {
                                    txtviewStarttimeBd.setText("0" + hrs + ":" + "0" + minute + " " + am_pm);
                                    txtviewStarttimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    startTime = "0" + hrs + ":" + "0" + minute + " " + am_pm;
                                } else {
                                    txtviewStarttimeBd.setText("0" + hrs + ":" + minute + " " + am_pm);
                                    txtviewStarttimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    startTime = "0" + hrs + ":" + minute + " " + am_pm;
                                }


                            } else {

                                if (minute < 10) {

                                    txtviewStarttimeBd.setText(hrs + ":" + "0" + minute + " " + am_pm);
                                    txtviewStarttimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    startTime = hrs + ":" + "0" + minute + " " + am_pm;
                                } else {
                                    txtviewStarttimeBd.setText(hrs + ":" + minute + " " + am_pm);
                                    txtviewStarttimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    startTime = hrs + ":" + minute + " " + am_pm;
                                }


                            }


                        } else {

                            if (hrs < 10) {


                                if (minute < 10) {
                                    txtviewEndtimeBd.setText("0" + hrs + ":" + "0" + minute + " " + am_pm);
                                    txtviewEndtimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    endTime = "0" + hrs + ":" + "0" + minute + " " + am_pm;
                                } else {
                                    txtviewEndtimeBd.setText("0" + hrs + ":" + minute + " " + am_pm);
                                    txtviewEndtimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    endTime = "0" + hrs + ":" + minute + " " + am_pm;

                                }


                            } else {

                                if (minute < 10) {
                                    txtviewEndtimeBd.setText(hrs + ":" + "0" + minute + " " + am_pm);
                                    txtviewEndtimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    endTime = hrs + ":" + "0" + minute + " " + am_pm;
                                } else {
                                    txtviewEndtimeBd.setText(hrs + ":" + minute + " " + am_pm);
                                    txtviewEndtimeBd.setTextColor(Color.parseColor("#1a1b1b"));
                                    endTime = hrs + ":" + minute + " " + am_pm;

                                }


                            }
                        }

                        //----------------------------time comparison(start and end time)------------------------------------------
                        if (compareTimeStart != null && !compareTimeStart.equals("") && compareTimeEnd != null && !compareTimeEnd.equals("")) {

                            try {
                                timeCompareStart = parseDate(compareTimeStart);
                                timeCompareEnd = parseDate(compareTimeEnd);

                                if (timeCompareStart.before(timeCompareEnd)) {
                                    //  Snackbar.make(relTopBd, "Success", Snackbar.LENGTH_SHORT).show();
                                } else {
                                    Snackbar.make(relTopBd, "End time should not be before start time", Snackbar.LENGTH_SHORT).show();

                                    endTime = "";
                                    compareTimeEnd = "";
                                    txtviewEndtimeBd.setText("End time");
                                    txtviewEndtimeBd.setTextColor(getResources().getColor(R.color.colorGrey));

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        //----------------end compariosn------------------------------------------------------------


                        //**************************to select only future time of current date in start time case************************************

                        if (time.equalsIgnoreCase("start")) {

                            //-------------get current date in formatted form--------------------------
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                            String formattedDate = df.format(c);
                            try {

                                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                                Date dateCurrent = sdf.parse(formattedDate);
                                Date dateSelected = sdf.parse(date);

                                if (dateSelected.after(dateCurrent))
                                    Log.e("Response: Timing", "future date");

                                else {
                                    Log.e("Response: Timing", "today date");

                                    if (hourOfDay <= mHour && minute <= mMinute) {

                                        Snackbar.make(relTopBd, "Please choose Future time of Today", Snackbar.LENGTH_SHORT).show();

                                        startTime = "";
                                        compareTimeStart = "";
                                        txtviewStarttimeBd.setText("Start time");
                                        txtviewStarttimeBd.setTextColor(getResources().getColor(R.color.colorGrey));

                                    } else {

                                    }


                                }


                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();


        //----------------------------check for timeslot--------------------------------------------


    }


    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //  getActivity().onBackPressed();

    }


    private void showPriceDialog(String sessionPrice) {


        if (radioBtnIndividual.isChecked()) {
            if (sessionPrice.equalsIgnoreCase("6session")) {
                radioBtn1session.setChecked(false);
                radioBtn6session.setChecked(true);
            } else {
                radioBtn1session.setChecked(true);
                radioBtn6session.setChecked(false);
            }

            therapyPackage = sessionPrice;

            CustomDialog customDialog = new CustomDialog(getActivity(), "Individual Therapy Fee", individual_1_session, individual_6_session);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialog.setCancelable(false);
            customDialog.show();
        } else if (radioBtnGroup.isChecked()) {
            if (sessionPrice.equalsIgnoreCase("6session")) {
                radioBtn1session.setChecked(false);
                radioBtn6session.setChecked(true);
            } else {
                radioBtn1session.setChecked(true);
                radioBtn6session.setChecked(false);
            }

            therapyPackage = sessionPrice;

            CustomDialog custom_Dialog = new CustomDialog(getActivity(), "Group Therapy Fee", group_1_session, group_6_session);
            custom_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            custom_Dialog.setCancelable(false);
            custom_Dialog.show();
        } else {
            Snackbar.make(relTopBd, "Please choose Therapy Type", Snackbar.LENGTH_SHORT).show();
            radioBtn1session.setChecked(false);
            radioBtn6session.setChecked(false);
        }

    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        img_path = image.getAbsolutePath();
        return image;
    }


    private void clearDateTime() {
        date = "";
        startTime = "";
        endTime = "";
        txtviewDate.setText("Select date ");
        txtviewStarttimeBd.setText("Start time");
        txtviewEndtimeBd.setText("End time");

        txtviewDate.setTextColor(getResources().getColor(R.color.colorGrey));
        txtviewStarttimeBd.setTextColor(getResources().getColor(R.color.colorGrey));
        txtviewEndtimeBd.setTextColor(getResources().getColor(R.color.colorGrey));
    }

    private void getPersonalMedical() {

        PersonalMedical_Api personalMedical_api = new PersonalMedical_Api();
        personalMedical_api.medical_personal(getActivity(), therapy_id, new PersonalMedical_Api.PersonalMedicalCallBack() {
            @Override
            public void onPersonalMedicalStatus(String res) {


                try {

                    JSONObject jsonObject = new JSONObject(res);
                    String status = jsonObject.getString("status");


                    if (status.equalsIgnoreCase("1")) {


                        //------------------personal details------------------------------------
                        Object object = jsonObject.get("Personal_details");
                        if (object instanceof JSONArray) {
                            JSONArray jsonArray_personal = jsonObject.getJSONArray("Personal_details");


                        } else {
                            JSONObject jsonObject_personal = jsonObject.getJSONObject("Personal_details");

                            personal_status = jsonObject_personal.getString("personal_status");
                            String user_id = jsonObject_personal.getString("user_id");
                            String sub_cat = jsonObject_personal.getString("sub_cat");
                            String therapy_id = jsonObject_personal.getString("therapy_id");
                            String name = jsonObject_personal.getString("name");
                            String address = jsonObject_personal.getString("address");
                            String city = jsonObject_personal.getString("city");
                            String state = jsonObject_personal.getString("state");
                            String dob = jsonObject_personal.getString("dob");
                            String age = jsonObject_personal.getString("age");
                            String email = jsonObject_personal.getString("email");
                            String country_name = jsonObject_personal.getString("country_name");
                            String country_code_C = jsonObject_personal.getString("country_code_C");
                            String cell_phone = jsonObject_personal.getString("cell_phone");
                            String country_code_H = jsonObject_personal.getString("country_code_H");
                            String home_phone = jsonObject_personal.getString("home_phone");


                        }


                        //----------------medical details-----------------------------------------

                        Object object1 = jsonObject.get("Medical_details");
                        if (object1 instanceof JSONArray) {
                            JSONArray jsonArray_medical = jsonObject.getJSONArray("Medical_details");


                        } else {
                            JSONObject jsonObject_medical = jsonObject.getJSONObject("Medical_details");
                            medical_status = jsonObject_medical.getString("medical_status");
                            String user_id = jsonObject_medical.getString("user_id");
                            String sub_cat = jsonObject_medical.getString("sub_cat");
                            String therapy_id = jsonObject_medical.getString("therapy_id");
                            String country_code = jsonObject_medical.getString("country_code");
                            String contact_number = jsonObject_medical.getString("contact_number");
                            String father_name = jsonObject_medical.getString("father_name");
                            String mother_name = jsonObject_medical.getString("mother_name");
                            String Father_Mental_health_issue = jsonObject_medical.getString("Father_Mental_health_issue");
                            String Mother_Mental_health_issue = jsonObject_medical.getString("Mother_Mental_health_issue");
                            String any_medical = jsonObject_medical.getString("any_medical");
                            String diagonosis = jsonObject_medical.getString("diagonosis");
                            String issues_message = jsonObject_medical.getString("issues_message");
                            String theraphy_goal = jsonObject_medical.getString("theraphy_goal");


                        }

                        //---------------------------------------------------------------------------

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onPersonalMedicalFailure() {

            }
        });
    }


    public static boolean isTimeWith_in_Interval(String valueToCheck, String endTime, String startTime) {
        boolean isBetween = false;
        try {
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(endTime);

            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(startTime);

            Date d = new SimpleDateFormat("HH:mm:ss").parse(valueToCheck);

            if (time1.after(d) && time2.before(d)) {
                isBetween = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isBetween;
    }

    private Date parseDate(String date) throws Exception {

        try {
            return inputParser.parse(date);
        } catch (ParseException e) {
            return new Date(0);
        }
    }
}
