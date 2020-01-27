package com.tenserflow.therapist.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.CounteryModel;
import com.tenserflow.therapist.Utils.CustomSpinner;
import com.tenserflow.therapist.Utils.CustomSpinnerState;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Utils.Payment;
import com.tenserflow.therapist.Utils.ProgressDialog;
import com.tenserflow.therapist.Webservice.MedicalDetail_Api;
import com.tenserflow.therapist.Webservice.PersonalDetail_Api;
import com.tenserflow.therapist.Webservice.PersonalMedical_Api;
import com.tenserflow.therapist.adapter.PagerAdapterPM;
import com.tenserflow.therapist.model.Detail.DetailListing;
import com.tenserflow.therapist.model.GetPersonalMedical.GetPersonalMedicalDetail;
import com.tenserflow.therapist.model.MedicalDetail.MedicalDetail;
import com.tenserflow.therapist.model.PersonalDetail.PersonalDetail;
import com.tenserflow.therapist.model.PersonalMedicalDetail.PersonalMedical;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutClient extends Fragment /*implements OnStatePickerListener, OnCountryPickerListener, OnCityPickerListener*/ {


    Unbinder unbinder;
    @BindView(R.id.tabLayout_aboutClient)
    TabLayout tabLayoutAboutClient;
    @BindView(R.id.view_pager_aboutClient)
    ViewPager viewPagerAboutClient;
    @BindView(R.id.edt_name_client)
    EditText edtNameClient;
    @BindView(R.id.edt_name_address)
    EditText edtNameAddress;
    @BindView(R.id.edt_name_city)
    EditText edtNameCity;
    @BindView(R.id.edt_name_state)
    TextView edtNameState;
    @BindView(R.id.txt_name_dob)
    TextView txtviewNameDob;
    @BindView(R.id.edt_name_age)
    EditText edtNameAge;
    @BindView(R.id.edt_name_email)
    EditText edtNameEmail;
    @BindView(R.id.edt_cellPhone)
    EditText edtCellPhone;
    @BindView(R.id.edt_homePhone)
    EditText edtHomePhone;
    @BindView(R.id.edt_preferrredContac)
    EditText edtPreferrredContac;
    @BindView(R.id.edt_fatherName)
    EditText edtFatherName;
    @BindView(R.id.edt_mentalHealth)
    EditText edtMentalHealth;
    @BindView(R.id.edt_motherName)
    EditText edtMotherName;
    @BindView(R.id.edt_medicalDiagonosis)
    EditText edtMedicalDiagonosis;
    @BindView(R.id.rel_Top_aboutClient)
    RelativeLayout relTopAboutClient;
    @BindView(R.id.edt_phoneCode_cellPhone)
    EditText edt_phoneCode_cellPhone;
    @BindView(R.id.edt_phoneCode_homePhone)
    EditText edt_phoneCode_homePhone;

    String date = "";
    @BindView(R.id.edt_mentalHealthM)
    EditText edtMentalHealthM;
    @BindView(R.id.edt_allIssues)
    EditText edtAllIssues;
    @BindView(R.id.edt_therapyGoals)
    EditText edtTherapyGoals;
    @BindView(R.id.edt_phoneCode_preferredContact)
    EditText edtPhoneCodePreferredContact;
    @BindView(R.id.scrollview_aboutClient)
    ScrollView scrollviewAboutClient;


    public static int countryID, stateID;
    @BindView(R.id.txtview_name_country)
    TextView txtviewNameCountry;
    @BindView(R.id.spinnerSate)
    Spinner spinnerSate;
    String therapy_id = "", subCatId = "";


    private CounteryModel object;
    ArrayList<String> countrylist = new ArrayList<>();
    ArrayList<String> statelist = new ArrayList<>();
    String state_str = "", country_str = "";
    private int dragThreshold = 10;
    int downX = 0;
    int downY = 0;


    CustomSpinner customAdapter;

    Spinner spinnerCountry;
    CustomSpinner Customspinner;
    CustomSpinnerState CustomspinnerState;
    boolean stateStatus = false;
    private String TAG = AboutClient.class.getSimpleName();

    int ageCal=0;

    public AboutClient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_client, container, false);
        unbinder = ButterKnife.bind(this, view);

        stateStatus = false;

        changeTitle();



        //----------------------------------------------------

        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        countrylist.add("Select Country");
        Customspinner = new CustomSpinner(getActivity(), countrylist);
        spinnerCountry.setAdapter(Customspinner);


        statelist.add("Select State");
        CustomspinnerState = new CustomSpinnerState(getActivity(), statelist);
        spinnerSate.setAdapter(CustomspinnerState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("therapy_id")) {
            therapy_id = bundle.getString("therapy_id");
            subCatId = bundle.getString("subCatId");
            // getDetailApi();
        }



        //------------------------tab layout----------------------------------------------------------
        tabLayoutAboutClient.addTab(tabLayoutAboutClient.newTab().setText("Personal"));
        tabLayoutAboutClient.addTab(tabLayoutAboutClient.newTab().setText("Medical"));

        final PagerAdapterPM adapter = new PagerAdapterPM(getActivity());
        viewPagerAboutClient.setAdapter(adapter);
        tabLayoutAboutClient.setupWithViewPager(viewPagerAboutClient);
        tabLayoutAboutClient.getTabAt(0);
        viewPagerAboutClient.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    Log.e(TAG, "onPageSelected: first");
                    viewPagerAboutClient.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen._440sdp)));

                } else {
                    Log.e(TAG, "onPageSelected: 2nd");
                    viewPagerAboutClient.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getActivity().getResources().getDimension(R.dimen._590sdp)));
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });




        //-----------------------------country code picker-------------------------------

        countryStatePicker();


        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                country_str = countrylist.get(i);

                getstateList(countrylist.get(i));

                if (!country_str.equalsIgnoreCase("Select Country"))
                {
                    try {

                        getCountryCode(country_str);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                state_str = statelist.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getDetailApi();



        //---------------------------country code picker-------------------------

        edt_phoneCode_cellPhone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (!s.toString().startsWith("+")) {
                    edt_phoneCode_cellPhone.setText("+" + s.toString());
                    Selection.setSelection(edt_phoneCode_cellPhone.getText(), edt_phoneCode_cellPhone.getText().length());
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });

        edt_phoneCode_homePhone.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (!s.toString().startsWith("+")) {
                    edt_phoneCode_homePhone.setText("+" + s.toString());
                    Selection.setSelection(edt_phoneCode_homePhone.getText(), edt_phoneCode_homePhone.getText().length());
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });


        edtPhoneCodePreferredContact.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (!s.toString().startsWith("+")) {
                    edtPhoneCodePreferredContact.setText("+" + s.toString());
                    Selection.setSelection(edtPhoneCodePreferredContact.getText(), edtPhoneCodePreferredContact.getText().length());
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });


        //-----------------------------------------------------------------------



        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tabLayout_aboutClient)
    public void onViewClicked() {
    }


    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("About Client");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).lockDrawer(true);
    }

    @OnClick({R.id.rel_lay_savePersonal, R.id.rel_lay_saveMedical, R.id.txt_name_dob, R.id.rel_name_country, R.id.rel_name_state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_lay_savePersonal:

                int age = 0;
                try {
                    age = Integer.parseInt(edtNameAge.getText().toString().trim());
                } catch (Exception e) {

                }


                if (date.equalsIgnoreCase("")) {
                    date = txtviewNameDob.getText().toString().trim();
                }


                if (edtNameClient.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Name", Snackbar.LENGTH_SHORT).show();

                else if (country_str.equalsIgnoreCase("") || country_str.equalsIgnoreCase("Select Country"))
                    Snackbar.make(relTopAboutClient, "Please select Country", Snackbar.LENGTH_SHORT).show();
                else if (state_str.equalsIgnoreCase("") || state_str.equalsIgnoreCase("Select State"))
                    Snackbar.make(relTopAboutClient, "Please select State", Snackbar.LENGTH_SHORT).show();
                else if (edtNameCity.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter City", Snackbar.LENGTH_SHORT).show();
                else if (edtNameAddress.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Address", Snackbar.LENGTH_SHORT).show();

                else if (edtNameState.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter State", Snackbar.LENGTH_SHORT).show();
                else if (date.equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please select Date of Birth", Snackbar.LENGTH_SHORT).show();
                else if (edtNameAge.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Age", Snackbar.LENGTH_SHORT).show();
                else if (age > 105 || age == 0)
                    Snackbar.make(relTopAboutClient, "Please enter valid Age", Snackbar.LENGTH_SHORT).show();

                else if (edtNameEmail.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Email", Snackbar.LENGTH_SHORT).show();
                else if (!Global.isValidEmail(edtNameEmail.getText().toString().trim()))
                    Snackbar.make(relTopAboutClient, "Please enter valid email", Snackbar.LENGTH_SHORT).show();


                else if (edt_phoneCode_cellPhone.getText().toString().trim().equalsIgnoreCase("") || edt_phoneCode_cellPhone.getText().toString().trim().equalsIgnoreCase("+"))
                    Snackbar.make(relTopAboutClient, "Please enter Country Code of Cell Phone", Snackbar.LENGTH_SHORT).show();
                else if (edtCellPhone.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Cell Phone", Snackbar.LENGTH_SHORT).show();
                else if (edtCellPhone.getText().toString().trim().length() < 6 || edtCellPhone.getText().toString().trim().length() > 15)
                    Snackbar.make(relTopAboutClient, "Please enter valid Cell Phone", Snackbar.LENGTH_SHORT).show();

                else if (edt_phoneCode_homePhone.getText().toString().trim().equalsIgnoreCase("") || edt_phoneCode_homePhone.getText().toString().trim().equalsIgnoreCase("+"))
                    Snackbar.make(relTopAboutClient, "Please enter Country Code of Home Phone", Snackbar.LENGTH_SHORT).show();
                else if (edtHomePhone.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Home Phone", Snackbar.LENGTH_SHORT).show();
                else if (edtHomePhone.getText().toString().trim().length() < 6 || edtHomePhone.getText().toString().trim().length() > 15)
                    Snackbar.make(relTopAboutClient, "Please enter valid Home Phone", Snackbar.LENGTH_SHORT).show();


                else {

                    if (!InternetConnectivity.isConnected(getActivity())) {
                        DialogHelperClass.getConnectionError(getActivity()).show();
                    } else {

                        ProgressDialog.getInstance().show(getActivity());

                        PersonalDetail_Api personalDetail_api = new PersonalDetail_Api();
                        personalDetail_api.personalDetail(getActivity(), subCatId, therapy_id, edtNameClient.getText().toString().trim(), edtNameAddress.getText().toString().trim(), edtNameCity.getText().toString().trim(), state_str, date, String.valueOf(age), edtNameEmail.getText().toString().trim(), edt_phoneCode_cellPhone.getText().toString().trim(), edtCellPhone.getText().toString().trim(), edt_phoneCode_homePhone.getText().toString().trim(), edtHomePhone.getText().toString().trim(), edt_phoneCode_cellPhone.getText().toString().trim(), edt_phoneCode_homePhone.getText().toString().trim(), country_str, new PersonalDetail_Api.GetPersonalDetailCallBack() {
                            @Override
                            public void onGetPersonalDetailStatus(PersonalDetail jsonObject) {

                                ProgressDialog.getInstance().dismiss();

                                try {

                                Snackbar.make(relTopAboutClient, jsonObject.getMessage(), Snackbar.LENGTH_SHORT).show();
                              //  getActivity().onBackPressed();

                                }catch (Exception e){

                                }
                            }

                            @Override
                            public void onGetPersonalDetailFailure() {
                                DialogHelperClass.getSomethingError(getActivity()).show();
                            }
                        });
                    }
                }
                break;
            case R.id.rel_lay_saveMedical:


                if (edtPreferrredContac.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Preferred Contact", Snackbar.LENGTH_SHORT).show();
                else if (edtPreferrredContac.getText().toString().trim().length() < 6)
                    Snackbar.make(relTopAboutClient, "Please enter Valid Preferred method of Contact", Snackbar.LENGTH_SHORT).show();
                else if (edtPhoneCodePreferredContact.getText().toString().trim().equalsIgnoreCase("") || edtPhoneCodePreferredContact.getText().toString().trim().equalsIgnoreCase("+"))
                    Snackbar.make(relTopAboutClient, "Please enter Country Code", Snackbar.LENGTH_SHORT).show();
                else if (edtFatherName.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Father Name", Snackbar.LENGTH_SHORT).show();
                else if (edtMentalHealth.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Father Mental Health Issue", Snackbar.LENGTH_SHORT).show();
                else if (edtMotherName.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Mother Name", Snackbar.LENGTH_SHORT).show();
                else if (edtMentalHealthM.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Mother Mental Health Issue", Snackbar.LENGTH_SHORT).show();
                else if (edtMedicalDiagonosis.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Medical Diagnosis", Snackbar.LENGTH_SHORT).show();
                else if (edtAllIssues.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter All Issues", Snackbar.LENGTH_SHORT).show();
                else if (edtTherapyGoals.getText().toString().trim().equalsIgnoreCase(""))
                    Snackbar.make(relTopAboutClient, "Please enter Therapy Goals", Snackbar.LENGTH_SHORT).show();
                else {

                    if (!InternetConnectivity.isConnected(getActivity())) {
                        DialogHelperClass.getConnectionError(getActivity()).show();
                    } else {

                        ProgressDialog.getInstance().show(getActivity());

                        MedicalDetail_Api medicalDetail = new MedicalDetail_Api();
                        medicalDetail.medicalDetail(getActivity(), subCatId, therapy_id, edtPreferrredContac.getText().toString().trim(), edtFatherName.getText().toString().trim(), edtMentalHealth.getText().toString().trim(), edtMotherName.getText().toString().trim(), edtMentalHealthM.getText().toString().trim(), edtMedicalDiagonosis.getText().toString().trim(), edtAllIssues.getText().toString().trim(), edtPhoneCodePreferredContact.getText().toString().trim(), edtTherapyGoals.getText().toString().trim(), new MedicalDetail_Api.GetMedicalDetailCallBack() {
                            @Override
                            public void onGetMedicalDetailStatus(MedicalDetail jsonObject) {

                                ProgressDialog.getInstance().dismiss();

                                try {


                                Snackbar.make(relTopAboutClient, jsonObject.getMessage(), Snackbar.LENGTH_SHORT).show();
                              //  getActivity().onBackPressed();
                                }catch (Exception e){

                                }


                            }

                            @Override
                            public void onGetMedicalDetailFailure() {

                                ProgressDialog.getInstance().dismiss();
                                DialogHelperClass.getSomethingError(getActivity()).show();
                            }
                        });


                    }
                }


                break;

            case R.id.txt_name_dob:
                Calendar c = Calendar.getInstance();

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                txtviewNameDob.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                txtviewNameDob.setTextColor(Color.parseColor("#1a1b1b"));
                                date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;


                                try {
                                    ageCal =   getAge(year,(monthOfYear+1),dayOfMonth);

                                    if (ageCal<18)
                                    {
                                        Snackbar.make(relTopAboutClient,"Age should be atleast 18 years",Snackbar.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        edtNameAge.setText(String.valueOf(ageCal));
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


                break;

            case R.id.rel_name_country:
                break;

            case R.id.rel_name_state:
                break;


        }
    }




    private void countryStatePicker() {


        String countriesJson = "{\n" +
                "  \"countries\": [\n" +
                "    {\n" +
                "      \"country\": \"Afghanistan\",\n" +
                "      \"states\": [\"Badakhshan\", \"Badghis\", \"Baghlan\", \"Balkh\", \"Bamian\", \"Daykondi\", \"Farah\", \"Faryab\", \"Ghazni\", \"Ghowr\", \"Helmand\", \"Herat\", \"Jowzjan\", \"Kabul\", \"Kandahar\", \"Kapisa\", \"Khost\", \"Konar\", \"Kondoz\", \"Laghman\", \"Lowgar\", \"Nangarhar\", \"Nimruz\", \"Nurestan\", \"Oruzgan\", \"Paktia\", \"Paktika\", \"Panjshir\", \"Parvan\", \"Samangan\", \"Sar-e Pol\", \"Takhar\", \"Vardak\", \"Zabol\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Albania\",\n" +
                "      \"states\": [\"Berat\", \"Dibres\", \"Durres\", \"Elbasan\", \"Fier\", \"Gjirokastre\", \"Korce\", \"Kukes\", \"Lezhe\", \"Shkoder\", \"Tirane\", \"Vlore\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Algeria\",\n" +
                "      \"states\": [\"Adrar\", \"Ain Defla\", \"Ain Temouchent\", \"Alger\", \"Annaba\", \"Batna\", \"Bechar\", \"Bejaia\", \"Biskra\", \"Blida\", \"Bordj Bou Arreridj\", \"Bouira\", \"Boumerdes\", \"Chlef\", \"Constantine\", \"Djelfa\", \"El Bayadh\", \"El Oued\", \"El Tarf\", \"Ghardaia\", \"Guelma\", \"Illizi\", \"Jijel\", \"Khenchela\", \"Laghouat\", \"Muaskar\", \"Medea\", \"Mila\", \"Mostaganem\", \"M'Sila\", \"Naama\", \"Oran\", \"Ouargla\", \"Oum el Bouaghi\", \"Relizane\", \"Saida\", \"Setif\", \"Sidi Bel Abbes\", \"Skikda\", \"Souk Ahras\", \"Tamanghasset\", \"Tebessa\", \"Tiaret\", \"Tindouf\", \"Tipaza\", \"Tissemsilt\", \"Tizi Ouzou\", \"Tlemcen\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Andorra\",\n" +
                "      \"states\": [\"Andorra la Vella\", \"Canillo\", \"Encamp\", \"Escaldes-Engordany\", \"La Massana\", \"Ordino\", \"Sant Julia de Loria\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Angola\",\n" +
                "      \"states\": [\"Bengo\", \"Benguela\", \"Bie\", \"Cabinda\", \"Cuando Cubango\", \"Cuanza Norte\", \"Cuanza Sul\", \"Cunene\", \"Huambo\", \"Huila\", \"Luanda\", \"Lunda Norte\", \"Lunda Sul\", \"Malanje\", \"Moxico\", \"Namibe\", \"Uige\", \"Zaire\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Antarctica\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Antigua and Barbuda\",\n" +
                "      \"states\": [\"Barbuda\", \"Redonda\", \"Saint George\", \"Saint John\", \"Saint Mary\", \"Saint Paul\", \"Saint Peter\", \"Saint Philip\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Argentina\",\n" +
                "      \"states\": [\"Buenos Aires\", \"Buenos Aires Capital\", \"Catamarca\", \"Chaco\", \"Chubut\", \"Cordoba\", \"Corrientes\", \"Entre Rios\", \"Formosa\", \"Jujuy\", \"La Pampa\", \"La Rioja\", \"Mendoza\", \"Misiones\", \"Neuquen\", \"Rio Negro\", \"Salta\", \"San Juan\", \"San Luis\", \"Santa Cruz\", \"Santa Fe\", \"Santiago del Estero\", \"Tierra del Fuego\", \"Tucuman\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Armenia\",\n" +
                "      \"states\": [\"Aragatsotn\", \"Ararat\", \"Armavir\", \"Geghark'unik'\", \"Kotayk'\", \"Lorri\", \"Shirak\", \"Syunik'\", \"Tavush\", \"Vayots' Dzor\", \"Yerevan\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Australia\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Austria\",\n" +
                "      \"states\": [\"Burgenland\", \"Kaernten\", \"Niederoesterreich\", \"Oberoesterreich\", \"Salzburg\", \"Steiermark\", \"Tirol\", \"Vorarlberg\", \"Wien\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Azerbaijan\",\n" +
                "      \"states\": [\"Abseron Rayonu\", \"Agcabadi Rayonu\", \"Agdam Rayonu\", \"Agdas Rayonu\", \"Agstafa Rayonu\", \"Agsu Rayonu\", \"Astara Rayonu\", \"Balakan Rayonu\", \"Barda Rayonu\", \"Beylaqan Rayonu\", \"Bilasuvar Rayonu\", \"Cabrayil Rayonu\", \"Calilabad Rayonu\", \"Daskasan Rayonu\", \"Davaci Rayonu\", \"Fuzuli Rayonu\", \"Gadabay Rayonu\", \"Goranboy Rayonu\", \"Goycay Rayonu\", \"Haciqabul Rayonu\", \"Imisli Rayonu\", \"Ismayilli Rayonu\", \"Kalbacar Rayonu\", \"Kurdamir Rayonu\", \"Lacin Rayonu\", \"Lankaran Rayonu\", \"Lerik Rayonu\", \"Masalli Rayonu\", \"Neftcala Rayonu\", \"Oguz Rayonu\", \"Qabala Rayonu\", \"Qax Rayonu\", \"Qazax Rayonu\", \"Qobustan Rayonu\", \"Quba Rayonu\", \"Qubadli Rayonu\", \"Qusar Rayonu\", \"Saatli Rayonu\", \"Sabirabad Rayonu\", \"Saki Rayonu\", \"Salyan Rayonu\", \"Samaxi Rayonu\", \"Samkir Rayonu\", \"Samux Rayonu\", \"Siyazan Rayonu\", \"Susa Rayonu\", \"Tartar Rayonu\", \"Tovuz Rayonu\", \"Ucar Rayonu\", \"Xacmaz Rayonu\", \"Xanlar Rayonu\", \"Xizi Rayonu\", \"Xocali Rayonu\", \"Xocavand Rayonu\", \"Yardimli Rayonu\", \"Yevlax Rayonu\", \"Zangilan Rayonu\", \"Zaqatala Rayonu\", \"Zardab Rayonu\", \"Ali Bayramli Sahari\", \"Baki Sahari\", \"Ganca Sahari\", \"Lankaran Sahari\", \"Mingacevir Sahari\", \"Naftalan Sahari\", \"Saki Sahari\", \"Sumqayit Sahari\", \"Susa Sahari\", \"Xankandi Sahari\", \"Yevlax Sahari\", \"Naxcivan Muxtar\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bahamas\",\n" +
                "      \"states\": [\"Acklins and Crooked Islands\", \"Bimini\", \"Cat Island\", \"Exuma\", \"Freeport\", \"Fresh Creek\", \"Governor's Harbour\", \"Green Turtle Cay\", \"Harbour Island\", \"High Rock\", \"Inagua\", \"Kemps Bay\", \"Long Island\", \"Marsh Harbour\", \"Mayaguana\", \"New Providence\", \"Nichollstown and Berry Islands\", \"Ragged Island\", \"Rock Sound\", \"Sandy Point\", \"San Salvador and Rum Cay\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bahrain\",\n" +
                "      \"states\": [\"Al Hadd\", \"Al Manamah\", \"Al Mintaqah al Gharbiyah\", \"Al Mintaqah al Wusta\", \"Al Mintaqah ash Shamaliyah\", \"Al Muharraq\", \"Ar Rifa' wa al Mintaqah al Janubiyah\", \"Jidd Hafs\", \"Madinat Hamad\", \"Madinat 'Isa\", \"Juzur Hawar\", \"Sitrah\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bangladesh\",\n" +
                "      \"states\": [\"Barisal\", \"Chittagong\", \"Dhaka\", \"Khulna\", \"Rajshahi\", \"Sylhet\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Barbados\",\n" +
                "      \"states\": [\"Christ Church\", \"Saint Andrew\", \"Saint George\", \"Saint James\", \"Saint John\", \"Saint Joseph\", \"Saint Lucy\", \"Saint Michael\", \"Saint Peter\", \"Saint Philip\", \"Saint Thomas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Belarus\",\n" +
                "      \"states\": [\"Brest\", \"Homyel\", \"Horad Minsk\", \"Hrodna\", \"Mahilyow\", \"Minsk\", \"Vitsyebsk\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Belgium\",\n" +
                "      \"states\": [\"Antwerpen\", \"Brabant Wallon\", \"Brussels\", \"Flanders\", \"Hainaut\", \"Liege\", \"Limburg\", \"Luxembourg\", \"Namur\", \"Oost-Vlaanderen\", \"Vlaams-Brabant\", \"Wallonia\", \"West-Vlaanderen\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Belize\",\n" +
                "      \"states\": [\"Belize\", \"Cayo\", \"Corozal\", \"Orange Walk\", \"Stann Creek\", \"Toledo\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Benin\",\n" +
                "      \"states\": [\"Alibori\", \"Atakora\", \"Atlantique\", \"Borgou\", \"Collines\", \"Donga\", \"Kouffo\", \"Littoral\", \"Mono\", \"Oueme\", \"Plateau\", \"Zou\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bermuda\",\n" +
                "      \"states\": [\"Devonshire\", \"Hamilton\", \"Hamilton\", \"Paget\", \"Pembroke\", \"Saint George\", \"Saint George's\", \"Sandys\", \"Smith's\", \"Southampton\", \"Warwick\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bhutan\",\n" +
                "      \"states\": [\"Bumthang\", \"Chukha\", \"Dagana\", \"Gasa\", \"Haa\", \"Lhuntse\", \"Mongar\", \"Paro\", \"Pemagatshel\", \"Punakha\", \"Samdrup Jongkhar\", \"Samtse\", \"Sarpang\", \"Thimphu\", \"Trashigang\", \"Trashiyangste\", \"Trongsa\", \"Tsirang\", \"Wangdue Phodrang\", \"Zhemgang\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bolivia\",\n" +
                "      \"states\": [\"Chuquisaca\", \"Cochabamba\", \"Beni\", \"La Paz\", \"Oruro\", \"Pando\", \"Potosi\", \"Santa Cruz\", \"Tarija\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bosnia and Herzegovina\",\n" +
                "      \"states\": [\"Una-Sana [Federation]\", \"Posavina [Federation]\", \"Tuzla [Federation]\", \"Zenica-Doboj [Federation]\", \"Bosnian Podrinje [Federation]\", \"Central Bosnia [Federation]\", \"Herzegovina-Neretva [Federation]\", \"West Herzegovina [Federation]\", \"Sarajevo [Federation]\", \" West Bosnia [Federation]\", \"Banja Luka [RS]\", \"Bijeljina [RS]\", \"Doboj [RS]\", \"Fo?a [RS]\", \"Sarajevo-Romanija [RS]\", \"Trebinje [RS]\", \"Vlasenica [RS]\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Botswana\",\n" +
                "      \"states\": [\"Central\", \"Ghanzi\", \"Kgalagadi\", \"Kgatleng\", \"Kweneng\", \"North East\", \"North West\", \"South East\", \"Southern\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Brazil\",\n" +
                "      \"states\": [\"Acre\", \"Alagoas\", \"Amapa\", \"Amazonas\", \"Bahia\", \"Ceara\", \"Distrito Federal\", \"Espirito Santo\", \"Goias\", \"Maranhao\", \"Mato Grosso\", \"Mato Grosso do Sul\", \"Minas Gerais\", \"Para\", \"Paraiba\", \"Parana\", \"Pernambuco\", \"Piaui\", \"Rio de Janeiro\", \"Rio Grande do Norte\", \"Rio Grande do Sul\", \"Rondonia\", \"Roraima\", \"Santa Catarina\", \"Sao Paulo\", \"Sergipe\", \"Tocantins\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Brunei\",\n" +
                "      \"states\": [\"Belait\", \"Brunei and Muara\", \"Temburong\", \"Tutong\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Bulgaria\",\n" +
                "      \"states\": [\"Blagoevgrad\", \"Burgas\", \"Dobrich\", \"Gabrovo\", \"Khaskovo\", \"Kurdzhali\", \"Kyustendil\", \"Lovech\", \"Montana\", \"Pazardzhik\", \"Pernik\", \"Pleven\", \"Plovdiv\", \"Razgrad\", \"Ruse\", \"Shumen\", \"Silistra\", \"Sliven\", \"Smolyan\", \"Sofiya\", \"Sofiya-Grad\", \"Stara Zagora\", \"Turgovishte\", \"Varna\", \"Veliko Turnovo\", \"Vidin\", \"Vratsa\", \"Yambol\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Burkina Faso\",\n" +
                "      \"states\": [\"Bale\", \"Bam\", \"Banwa\", \"Bazega\", \"Bougouriba\", \"Boulgou\", \"Boulkiemde\", \"Comoe\", \"Ganzourgou\", \"Gnagna\", \"Gourma\", \"Houet\", \"Ioba\", \"Kadiogo\", \"Kenedougou\", \"Komondjari\", \"Kompienga\", \"Kossi\", \"Koulpelogo\", \"Kouritenga\", \"Kourweogo\", \"Leraba\", \"Loroum\", \"Mouhoun\", \"Namentenga\", \"Nahouri\", \"Nayala\", \"Noumbiel\", \"Oubritenga\", \"Oudalan\", \"Passore\", \"Poni\", \"Sanguie\", \"Sanmatenga\", \"Seno\", \"Sissili\", \"Soum\", \"Sourou\", \"Tapoa\", \"Tuy\", \"Yagha\", \"Yatenga\", \"Ziro\", \"Zondoma\", \"Zoundweogo\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Burma\",\n" +
                "      \"states\": [\"Ayeyarwady\", \"Bago\", \"Magway\", \"Mandalay\", \"Sagaing\", \"Tanintharyi\", \"Yangon\", \"Chin State\", \"Kachin State\", \"Kayin State\", \"Kayah State\", \"Mon State\", \"Rakhine State\", \"Shan State\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Burundi\",\n" +
                "      \"states\": [\"Bubanza\", \"Bujumbura Mairie\", \"Bujumbura Rural\", \"Bururi\", \"Cankuzo\", \"Cibitoke\", \"Gitega\", \"Karuzi\", \"Kayanza\", \"Kirundo\", \"Makamba\", \"Muramvya\", \"Muyinga\", \"Mwaro\", \"Ngozi\", \"Rutana\", \"Ruyigi\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cambodia\",\n" +
                "      \"states\": [\"Banteay Mean Chey\", \"Batdambang\", \"Kampong Cham\", \"Kampong Chhnang\", \"Kampong Spoe\", \"Kampong Thum\", \"Kampot\", \"Kandal\", \"Koh Kong\", \"Kracheh\", \"Mondol Kiri\", \"Otdar Mean Chey\", \"Pouthisat\", \"Preah Vihear\", \"Prey Veng\", \"Rotanakir\", \"Siem Reab\", \"Stoeng Treng\", \"Svay Rieng\", \"Takao\", \"Keb\", \"Pailin\", \"Phnom Penh\", \"Preah Seihanu\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cameroon\",\n" +
                "      \"states\": [\"Adamaoua\", \"Centre\", \"Est\", \"Extreme-Nord\", \"Littoral\", \"Nord\", \"Nord-Ouest\", \"Ouest\", \"Sud\", \"Sud-Ouest\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Canada\",\n" +
                "      \"states\": [\"Alberta\", \"British Columbia\", \"Manitoba\", \"New Brunswick\", \"Newfoundland and Labrador\", \"Northwest Territories\", \"Nova Scotia\", \"Nunavut\", \"Ontario\", \"Prince Edward Island\", \"Quebec\", \"Saskatchewan\", \"Yukon Territory\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cape Verde\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Central African Republic\",\n" +
                "      \"states\": [\"Bamingui-Bangoran\", \"Bangui\", \"Basse-Kotto\", \"Haute-Kotto\", \"Haut-Mbomou\", \"Kemo\", \"Lobaye\", \"Mambere-Kadei\", \"Mbomou\", \"Nana-Grebizi\", \"Nana-Mambere\", \"Ombella-Mpoko\", \"Ouaka\", \"Ouham\", \"Ouham-Pende\", \"Sangha-Mbaere\", \"Vakaga\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Chad\",\n" +
                "      \"states\": [\"Batha\", \"Biltine\", \"Borkou-Ennedi-Tibesti\", \"Chari-Baguirmi\", \"Guéra\", \"Kanem\", \"Lac\", \"Logone Occidental\", \"Logone Oriental\", \"Mayo-Kebbi\", \"Moyen-Chari\", \"Ouaddaï\", \"Salamat\", \"Tandjile\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Chile\",\n" +
                "      \"states\": [\"Aysen\", \"Antofagasta\", \"Araucania\", \"Atacama\", \"Bio-Bio\", \"Coquimbo\", \"O'Higgins\", \"Los Lagos\", \"Magallanes y la Antartica Chilena\", \"Maule\", \"Santiago Region Metropolitana\", \"Tarapaca\", \"Valparaiso\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"China\",\n" +
                "      \"states\": [\"Anhui\", \"Fujian\", \"Gansu\", \"Guangdong\", \"Guizhou\", \"Hainan\", \"Hebei\", \"Heilongjiang\", \"Henan\", \"Hubei\", \"Hunan\", \"Jiangsu\", \"Jiangxi\", \"Jilin\", \"Liaoning\", \"Qinghai\", \"Shaanxi\", \"Shandong\", \"Shanxi\", \"Sichuan\", \"Yunnan\", \"Zhejiang\", \"Guangxi\", \"Nei Mongol\", \"Ningxia\", \"Xinjiang\", \"Xizang (Tibet)\", \"Beijing\", \"Chongqing\", \"Shanghai\", \"Tianjin\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Colombia\",\n" +
                "      \"states\": [\"Amazonas\", \"Antioquia\", \"Arauca\", \"Atlantico\", \"Bogota District Capital\", \"Bolivar\", \"Boyaca\", \"Caldas\", \"Caqueta\", \"Casanare\", \"Cauca\", \"Cesar\", \"Choco\", \"Cordoba\", \"Cundinamarca\", \"Guainia\", \"Guaviare\", \"Huila\", \"La Guajira\", \"Magdalena\", \"Meta\", \"Narino\", \"Norte de Santander\", \"Putumayo\", \"Quindio\", \"Risaralda\", \"San Andres & Providencia\", \"Santander\", \"Sucre\", \"Tolima\", \"Valle del Cauca\", \"Vaupes\", \"Vichada\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Comoros\",\n" +
                "      \"states\": [\"Grande Comore (Njazidja)\", \"Anjouan (Nzwani)\", \"Moheli (Mwali)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Congo, Democratic Republic\",\n" +
                "      \"states\": [\"Bandundu\", \"Bas-Congo\", \"Equateur\", \"Kasai-Occidental\", \"Kasai-Oriental\", \"Katanga\", \"Kinshasa\", \"Maniema\", \"Nord-Kivu\", \"Orientale\", \"Sud-Kivu\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Congo, Republic of the\",\n" +
                "      \"states\": [\"Bouenza\", \"Brazzaville\", \"Cuvette\", \"Cuvette-Ouest\", \"Kouilou\", \"Lekoumou\", \"Likouala\", \"Niari\", \"Plateaux\", \"Pool\", \"Sangha\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Costa Rica\",\n" +
                "      \"states\": [\"Alajuela\", \"Cartago\", \"Guanacaste\", \"Heredia\", \"Limon\", \"Puntarenas\", \"San Jose\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cote d'Ivoire\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Croatia\",\n" +
                "      \"states\": [\"Bjelovarsko-Bilogorska\", \"Brodsko-Posavska\", \"Dubrovacko-Neretvanska\", \"Istarska\", \"Karlovacka\", \"Koprivnicko-Krizevacka\", \"Krapinsko-Zagorska\", \"Licko-Senjska\", \"Medimurska\", \"Osjecko-Baranjska\", \"Pozesko-Slavonska\", \"Primorsko-Goranska\", \"Sibensko-Kninska\", \"Sisacko-Moslavacka\", \"Splitsko-Dalmatinska\", \"Varazdinska\", \"Viroviticko-Podravska\", \"Vukovarsko-Srijemska\", \"Zadarska\", \"Zagreb\", \"Zagrebacka\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cuba\",\n" +
                "      \"states\": [\"Camaguey\", \"Ciego de Avila\", \"Cienfuegos\", \"Ciudad de La Habana\", \"Granma\", \"Guantanamo\", \"Holguin\", \"Isla de la Juventud\", \"La Habana\", \"Las Tunas\", \"Matanzas\", \"Pinar del Rio\", \"Sancti Spiritus\", \"Santiago de Cuba\", \"Villa Clara\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Cyprus\",\n" +
                "      \"states\": [\"Famagusta\", \"Kyrenia\", \"Larnaca\", \"Limassol\", \"Nicosia\", \"Paphos\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Czech Republic\",\n" +
                "      \"states\": [\"Jihocesky Kraj\", \"Jihomoravsky Kraj\", \"Karlovarsky Kraj\", \"Kralovehradecky Kraj\", \"Liberecky Kraj\", \"Moravskoslezsky Kraj\", \"Olomoucky Kraj\", \"Pardubicky Kraj\", \"Plzensky Kraj\", \"Praha\", \"Stredocesky Kraj\", \"Ustecky Kraj\", \"Vysocina\", \"Zlinsky Kraj\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Denmark\",\n" +
                "      \"states\": [\"Arhus\", \"Bornholm\", \"Frederiksberg\", \"Frederiksborg\", \"Fyn\", \"Kobenhavn\", \"Kobenhavns\", \"Nordjylland\", \"Ribe\", \"Ringkobing\", \"Roskilde\", \"Sonderjylland\", \"Storstrom\", \"Vejle\", \"Vestsjalland\", \"Viborg\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Djibouti\",\n" +
                "      \"states\": [\"Ali Sabih\", \"Dikhil\", \"Djibouti\", \"Obock\", \"Tadjoura\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Dominica\",\n" +
                "      \"states\": [\"Saint Andrew\", \"Saint David\", \"Saint George\", \"Saint John\", \"Saint Joseph\", \"Saint Luke\", \"Saint Mark\", \"Saint Patrick\", \"Saint Paul\", \"Saint Peter\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Dominican Republic\",\n" +
                "      \"states\": [\"Azua\", \"Baoruco\", \"Barahona\", \"Dajabon\", \"Distrito Nacional\", \"Duarte\", \"Elias Pina\", \"El Seibo\", \"Espaillat\", \"Hato Mayor\", \"Independencia\", \"La Altagracia\", \"La Romana\", \"La Vega\", \"Maria Trinidad Sanchez\", \"Monsenor Nouel\", \"Monte Cristi\", \"Monte Plata\", \"Pedernales\", \"Peravia\", \"Puerto Plata\", \"Salcedo\", \"Samana\", \"Sanchez Ramirez\", \"San Cristobal\", \"San Jose de Ocoa\", \"San Juan\", \"San Pedro de Macoris\", \"Santiago\", \"Santiago Rodriguez\", \"Santo Domingo\", \"Valverde\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"East Timor\",\n" +
                "      \"states\": [\"Aileu\", \"Ainaro\", \"Baucau\", \"Bobonaro\", \"Cova-Lima\", \"Dili\", \"Ermera\", \"Lautem\", \"Liquica\", \"Manatuto\", \"Manufahi\", \"Oecussi\", \"Viqueque\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Ecuador\",\n" +
                "      \"states\": [\"Azuay\", \"Bolivar\", \"Canar\", \"Carchi\", \"Chimborazo\", \"Cotopaxi\", \"El Oro\", \"Esmeraldas\", \"Galapagos\", \"Guayas\", \"Imbabura\", \"Loja\", \"Los Rios\", \"Manabi\", \"Morona-Santiago\", \"Napo\", \"Orellana\", \"Pastaza\", \"Pichincha\", \"Sucumbios\", \"Tungurahua\", \"Zamora-Chinchipe\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Egypt\",\n" +
                "      \"states\": [\"Ad Daqahliyah\", \"Al Bahr al Ahmar\", \"Al Buhayrah\", \"Al Fayyum\", \"Al Gharbiyah\", \"Al Iskandariyah\", \"Al Isma'iliyah\", \"Al Jizah\", \"Al Minufiyah\", \"Al Minya\", \"Al Qahirah\", \"Al Qalyubiyah\", \"Al Wadi al Jadid\", \"Ash Sharqiyah\", \"As Suways\", \"Aswan\", \"Asyut\", \"Bani Suwayf\", \"Bur Sa'id\", \"Dumyat\", \"Janub Sina'\", \"Kafr ash Shaykh\", \"Matruh\", \"Qina\", \"Shamal Sina'\", \"Suhaj\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"El Salvador\",\n" +
                "      \"states\": [\"Ahuachapan\", \"Cabanas\", \"Chalatenango\", \"Cuscatlan\", \"La Libertad\", \"La Paz\", \"La Union\", \"Morazan\", \"San Miguel\", \"San Salvador\", \"Santa Ana\", \"San Vicente\", \"Sonsonate\", \"Usulutan\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Equatorial Guinea\",\n" +
                "      \"states\": [\"Annobon\", \"Bioko Norte\", \"Bioko Sur\", \"Centro Sur\", \"Kie-Ntem\", \"Litoral\", \"Wele-Nzas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Eritrea\",\n" +
                "      \"states\": [\"Anseba\", \"Debub\", \"Debubawi K'eyih Bahri\", \"Gash Barka\", \"Ma'akel\", \"Semenawi Keyih Bahri\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Estonia\",\n" +
                "      \"states\": [\"Harjumaa (Tallinn)\", \"Hiiumaa (Kardla)\", \"Ida-Virumaa (Johvi)\", \"Jarvamaa (Paide)\", \"Jogevamaa (Jogeva)\", \"Laanemaa (Haapsalu)\", \"Laane-Virumaa (Rakvere)\", \"Parnumaa (Parnu)\", \"Polvamaa (Polva)\", \"Raplamaa (Rapla)\", \"Saaremaa (Kuressaare)\", \"Tartumaa (Tartu)\", \"Valgamaa (Valga)\", \"Viljandimaa (Viljandi)\", \"Vorumaa (Voru)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Ethiopia\",\n" +
                "      \"states\": [\"Addis Ababa\", \"Afar\", \"Amhara\", \"Binshangul Gumuz\", \"Dire Dawa\", \"Gambela Hizboch\", \"Harari\", \"Oromia\", \"Somali\", \"Tigray\", \"Southern Nations, Nationalities, and Peoples Region\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Fiji\",\n" +
                "      \"states\": [\"Central (Suva)\", \"Eastern (Levuka)\", \"Northern (Labasa)\", \"Rotuma\", \"Western (Lautoka)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Finland\",\n" +
                "      \"states\": [\"Aland\", \"Etela-Suomen Laani\", \"Ita-Suomen Laani\", \"Lansi-Suomen Laani\", \"Lappi\", \"Oulun Laani\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"France\",\n" +
                "      \"states\": [\"Alsace\", \"Aquitaine\", \"Auvergne\", \"Basse-Normandie\", \"Bourgogne\", \"Bretagne\", \"Centre\", \"Champagne-Ardenne\", \"Corse\", \"Franche-Comte\", \"Haute-Normandie\", \"Ile-de-France\", \"Languedoc-Roussillon\", \"Limousin\", \"Lorraine\", \"Midi-Pyrenees\", \"Nord-Pas-de-Calais\", \"Pays de la Loire\", \"Picardie\", \"Poitou-Charentes\", \"Provence-Alpes-Cote d'Azur\", \"Rhone-Alpes\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Gabon\",\n" +
                "      \"states\": [\"Estuaire\", \"Haut-Ogooue\", \"Moyen-Ogooue\", \"Ngounie\", \"Nyanga\", \"Ogooue-Ivindo\", \"Ogooue-Lolo\", \"Ogooue-Maritime\", \"Woleu-Ntem\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Gambia\",\n" +
                "      \"states\": [\"Banjul\", \"Central River\", \"Lower River\", \"North Bank\", \"Upper River\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Georgia\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Germany\",\n" +
                "      \"states\": [\"Baden-Wuerttemberg\", \"Bayern\", \"Berlin\", \"Brandenburg\", \"Bremen\", \"Hamburg\", \"Hessen\", \"Mecklenburg-Vorpommern\", \"Niedersachsen\", \"Nordrhein-Westfalen\", \"Rheinland-Pfalz\", \"Saarland\", \"Sachsen\", \"Sachsen-Anhalt\", \"Schleswig-Holstein\", \"Thueringen\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Ghana\",\n" +
                "      \"states\": [\"Ashanti\", \"Brong-Ahafo\", \"Central\", \"Eastern\", \"Greater Accra\", \"Northern\", \"Upper East\", \"Upper West\", \"Volta\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Greece\",\n" +
                "      \"states\": [\"Agion Oros\", \"Achaia\", \"Aitolia kai Akarmania\", \"Argolis\", \"Arkadia\", \"Arta\", \"Attiki\", \"Chalkidiki\", \"Chanion\", \"Chios\", \"Dodekanisos\", \"Drama\", \"Evros\", \"Evrytania\", \"Evvoia\", \"Florina\", \"Fokidos\", \"Fthiotis\", \"Grevena\", \"Ileia\", \"Imathia\", \"Ioannina\", \"Irakleion\", \"Karditsa\", \"Kastoria\", \"Kavala\", \"Kefallinia\", \"Kerkyra\", \"Kilkis\", \"Korinthia\", \"Kozani\", \"Kyklades\", \"Lakonia\", \"Larisa\", \"Lasithi\", \"Lefkas\", \"Lesvos\", \"Magnisia\", \"Messinia\", \"Pella\", \"Pieria\", \"Preveza\", \"Rethynnis\", \"Rodopi\", \"Samos\", \"Serrai\", \"Thesprotia\", \"Thessaloniki\", \"Trikala\", \"Voiotia\", \"Xanthi\", \"Zakynthos\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Greenland\",\n" +
                "      \"states\": [\"Avannaa (Nordgronland)\", \"Tunu (Ostgronland)\", \"Kitaa (Vestgronland)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Grenada\",\n" +
                "      \"states\": [\"Carriacou and Petit Martinique\", \"Saint Andrew\", \"Saint David\", \"Saint George\", \"Saint John\", \"Saint Mark\", \"Saint Patrick\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Guatemala\",\n" +
                "      \"states\": [\"Alta Verapaz\", \"Baja Verapaz\", \"Chimaltenango\", \"Chiquimula\", \"El Progreso\", \"Escuintla\", \"Guatemala\", \"Huehuetenango\", \"Izabal\", \"Jalapa\", \"Jutiapa\", \"Peten\", \"Quetzaltenango\", \"Quiche\", \"Retalhuleu\", \"Sacatepequez\", \"San Marcos\", \"Santa Rosa\", \"Solola\", \"Suchitepequez\", \"Totonicapan\", \"Zacapa\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Guinea\",\n" +
                "      \"states\": [\"Beyla\", \"Boffa\", \"Boke\", \"Conakry\", \"Coyah\", \"Dabola\", \"Dalaba\", \"Dinguiraye\", \"Dubreka\", \"Faranah\", \"Forecariah\", \"Fria\", \"Gaoual\", \"Gueckedou\", \"Kankan\", \"Kerouane\", \"Kindia\", \"Kissidougou\", \"Koubia\", \"Koundara\", \"Kouroussa\", \"Labe\", \"Lelouma\", \"Lola\", \"Macenta\", \"Mali\", \"Mamou\", \"Mandiana\", \"Nzerekore\", \"Pita\", \"Siguiri\", \"Telimele\", \"Tougue\", \"Yomou\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Guinea-Bissau\",\n" +
                "      \"states\": [\"Bafata\", \"Biombo\", \"Bissau\", \"Bolama\", \"Cacheu\", \"Gabu\", \"Oio\", \"Quinara\", \"Tombali\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Guyana\",\n" +
                "      \"states\": [\"Barima-Waini\", \"Cuyuni-Mazaruni\", \"Demerara-Mahaica\", \"East Berbice-Corentyne\", \"Essequibo Islands-West Demerara\", \"Mahaica-Berbice\", \"Pomeroon-Supenaam\", \"Potaro-Siparuni\", \"Upper Demerara-Berbice\", \"Upper Takutu-Upper Essequibo\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Haiti\",\n" +
                "      \"states\": [\"Artibonite\", \"Centre\", \"Grand 'Anse\", \"Nord\", \"Nord-Est\", \"Nord-Ouest\", \"Ouest\", \"Sud\", \"Sud-Est\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Honduras\",\n" +
                "      \"states\": [\"Atlantida\", \"Choluteca\", \"Colon\", \"Comayagua\", \"Copan\", \"Cortes\", \"El Paraiso\", \"Francisco Morazan\", \"Gracias a Dios\", \"Intibuca\", \"Islas de la Bahia\", \"La Paz\", \"Lempira\", \"Ocotepeque\", \"Olancho\", \"Santa Barbara\", \"Valle\", \"Yoro\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Hong Kong\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Hungary\",\n" +
                "      \"states\": [\"Bacs-Kiskun\", \"Baranya\", \"Bekes\", \"Borsod-Abauj-Zemplen\", \"Csongrad\", \"Fejer\", \"Gyor-Moson-Sopron\", \"Hajdu-Bihar\", \"Heves\", \"Jasz-Nagykun-Szolnok\", \"Komarom-Esztergom\", \"Nograd\", \"Pest\", \"Somogy\", \"Szabolcs-Szatmar-Bereg\", \"Tolna\", \"Vas\", \"Veszprem\", \"Zala\", \"Bekescsaba\", \"Debrecen\", \"Dunaujvaros\", \"Eger\", \"Gyor\", \"Hodmezovasarhely\", \"Kaposvar\", \"Kecskemet\", \"Miskolc\", \"Nagykanizsa\", \"Nyiregyhaza\", \"Pecs\", \"Sopron\", \"Szeged\", \"Szekesfehervar\", \"Szolnok\", \"Szombathely\", \"Tatabanya\", \"Veszprem\", \"Zalaegerszeg\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Iceland\",\n" +
                "      \"states\": [\"Austurland\", \"Hofudhborgarsvaedhi\", \"Nordhurland Eystra\", \"Nordhurland Vestra\", \"Sudhurland\", \"Sudhurnes\", \"Vestfirdhir\", \"Vesturland\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"India\",\n" +
                "      \"states\": [\"Andaman and Nicobar Islands\", \"Andhra Pradesh\", \"Arunachal Pradesh\", \"Assam\", \"Bihar\", \"Chandigarh\", \"Chhattisgarh\", \"Dadra and Nagar Haveli\", \"Daman and Diu\", \"Delhi\", \"Goa\", \"Gujarat\", \"Haryana\", \"Himachal Pradesh\", \"Jammu and Kashmir\", \"Jharkhand\", \"Karnataka\", \"Kerala\", \"Lakshadweep\", \"Madhya Pradesh\", \"Maharashtra\", \"Manipur\", \"Meghalaya\", \"Mizoram\", \"Nagaland\", \"Orissa\", \"Pondicherry\", \"Punjab\", \"Rajasthan\", \"Sikkim\", \"Tamil Nadu\", \"Tripura\", \"Uttaranchal\", \"Uttar Pradesh\", \"West Bengal\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Indonesia\",\n" +
                "      \"states\": [\"Aceh\", \"Bali\", \"Banten\", \"Bengkulu\", \"Gorontalo\", \"Irian Jaya Barat\", \"Jakarta Raya\", \"Jambi\", \"Jawa Barat\", \"Jawa Tengah\", \"Jawa Timur\", \"Kalimantan Barat\", \"Kalimantan Selatan\", \"Kalimantan Tengah\", \"Kalimantan Timur\", \"Kepulauan Bangka Belitung\", \"Kepulauan Riau\", \"Lampung\", \"Maluku\", \"Maluku Utara\", \"Nusa Tenggara Barat\", \"Nusa Tenggara Timur\", \"Papua\", \"Riau\", \"Sulawesi Barat\", \"Sulawesi Selatan\", \"Sulawesi Tengah\", \"Sulawesi Tenggara\", \"Sulawesi Utara\", \"Sumatera Barat\", \"Sumatera Selatan\", \"Sumatera Utara\", \"Yogyakarta\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Iran\",\n" +
                "      \"states\": [\"Ardabil\", \"Azarbayjan-e Gharbi\", \"Azarbayjan-e Sharqi\", \"Bushehr\", \"Chahar Mahall va Bakhtiari\", \"Esfahan\", \"Fars\", \"Gilan\", \"Golestan\", \"Hamadan\", \"Hormozgan\", \"Ilam\", \"Kerman\", \"Kermanshah\", \"Khorasan-e Janubi\", \"Khorasan-e Razavi\", \"Khorasan-e Shemali\", \"Khuzestan\", \"Kohgiluyeh va Buyer Ahmad\", \"Kordestan\", \"Lorestan\", \"Markazi\", \"Mazandaran\", \"Qazvin\", \"Qom\", \"Semnan\", \"Sistan va Baluchestan\", \"Tehran\", \"Yazd\", \"Zanjan\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Iraq\",\n" +
                "      \"states\": [\"Al Anbar\", \"Al Basrah\", \"Al Muthanna\", \"Al Qadisiyah\", \"An Najaf\", \"Arbil\", \"As Sulaymaniyah\", \"At Ta'mim\", \"Babil\", \"Baghdad\", \"Dahuk\", \"Dhi Qar\", \"Diyala\", \"Karbala'\", \"Maysan\", \"Ninawa\", \"Salah ad Din\", \"Wasit\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Ireland\",\n" +
                "      \"states\": [\"Carlow\", \"Cavan\", \"Clare\", \"Cork\", \"Donegal\", \"Dublin\", \"Galway\", \"Kerry\", \"Kildare\", \"Kilkenny\", \"Laois\", \"Leitrim\", \"Limerick\", \"Longford\", \"Louth\", \"Mayo\", \"Meath\", \"Monaghan\", \"Offaly\", \"Roscommon\", \"Sligo\", \"Tipperary\", \"Waterford\", \"Westmeath\", \"Wexford\", \"Wicklow\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Israel\",\n" +
                "      \"states\": [\"Central\", \"Haifa\", \"Jerusalem\", \"Northern\", \"Southern\", \"Tel Aviv\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Italy\",\n" +
                "      \"states\": [\"Abruzzo\", \"Basilicata\", \"Calabria\", \"Campania\", \"Emilia-Romagna\", \"Friuli-Venezia Giulia\", \"Lazio\", \"Liguria\", \"Lombardia\", \"Marche\", \"Molise\", \"Piemonte\", \"Puglia\", \"Sardegna\", \"Sicilia\", \"Toscana\", \"Trentino-Alto Adige\", \"Umbria\", \"Valle d'Aosta\", \"Veneto\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Jamaica\",\n" +
                "      \"states\": [\"Clarendon\", \"Hanover\", \"Kingston\", \"Manchester\", \"Portland\", \"Saint Andrew\", \"Saint Ann\", \"Saint Catherine\", \"Saint Elizabeth\", \"Saint James\", \"Saint Mary\", \"Saint Thomas\", \"Trelawny\", \"Westmoreland\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Japan\",\n" +
                "      \"states\": [\"Aichi\", \"Akita\", \"Aomori\", \"Chiba\", \"Ehime\", \"Fukui\", \"Fukuoka\", \"Fukushima\", \"Gifu\", \"Gumma\", \"Hiroshima\", \"Hokkaido\", \"Hyogo\", \"Ibaraki\", \"Ishikawa\", \"Iwate\", \"Kagawa\", \"Kagoshima\", \"Kanagawa\", \"Kochi\", \"Kumamoto\", \"Kyoto\", \"Mie\", \"Miyagi\", \"Miyazaki\", \"Nagano\", \"Nagasaki\", \"Nara\", \"Niigata\", \"Oita\", \"Okayama\", \"Okinawa\", \"Osaka\", \"Saga\", \"Saitama\", \"Shiga\", \"Shimane\", \"Shizuoka\", \"Tochigi\", \"Tokushima\", \"Tokyo\", \"Tottori\", \"Toyama\", \"Wakayama\", \"Yamagata\", \"Yamaguchi\", \"Yamanashi\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Jordan\",\n" +
                "      \"states\": [\"Ajlun\", \"Al 'Aqabah\", \"Al Balqa'\", \"Al Karak\", \"Al Mafraq\", \"'Amman\", \"At Tafilah\", \"Az Zarqa'\", \"Irbid\", \"Jarash\", \"Ma'an\", \"Madaba\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Kazakhstan\",\n" +
                "      \"states\": [\"Almaty Oblysy\", \"Almaty Qalasy\", \"Aqmola Oblysy\", \"Aqtobe Oblysy\", \"Astana Qalasy\", \"Atyrau Oblysy\", \"Batys Qazaqstan Oblysy\", \"Bayqongyr Qalasy\", \"Mangghystau Oblysy\", \"Ongtustik Qazaqstan Oblysy\", \"Pavlodar Oblysy\", \"Qaraghandy Oblysy\", \"Qostanay Oblysy\", \"Qyzylorda Oblysy\", \"Shyghys Qazaqstan Oblysy\", \"Soltustik Qazaqstan Oblysy\", \"Zhambyl Oblysy\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Kenya\",\n" +
                "      \"states\": [\"Central\", \"Coast\", \"Eastern\", \"Nairobi Area\", \"North Eastern\", \"Nyanza\", \"Rift Valley\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Kiribati\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Korea North\",\n" +
                "      \"states\": [\"Chagang\", \"North Hamgyong\", \"South Hamgyong\", \"North Hwanghae\", \"South Hwanghae\", \"Kangwon\", \"North P'yongan\", \"South P'yongan\", \"Yanggang\", \"Kaesong\", \"Najin\", \"Namp'o\", \"Pyongyang\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Korea South\",\n" +
                "      \"states\": [\"Seoul\", \"Busan City\", \"Daegu City\", \"Incheon City\", \"Gwangju City\", \"Daejeon City\", \"Ulsan\", \"Gyeonggi Province\", \"Gangwon Province\", \"North Chungcheong Province\", \"South Chungcheong Province\", \"North Jeolla Province\", \"South Jeolla Province\", \"North Gyeongsang Province\", \"South Gyeongsang Province\", \"Jeju\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Kuwait\",\n" +
                "      \"states\": [\"Al Ahmadi\", \"Al Farwaniyah\", \"Al Asimah\", \"Al Jahra\", \"Hawalli\", \"Mubarak Al-Kabeer\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Kyrgyzstan\",\n" +
                "      \"states\": [\"Batken Oblasty\", \"Bishkek Shaary\", \"Chuy Oblasty\", \"Jalal-Abad Oblasty\", \"Naryn Oblasty\", \"Osh Oblasty\", \"Talas Oblasty\", \"Ysyk-Kol Oblasty\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Laos\",\n" +
                "      \"states\": [\"Attapu\", \"Bokeo\", \"Bolikhamxai\", \"Champasak\", \"Houaphan\", \"Khammouan\", \"Louangnamtha\", \"Louangphrabang\", \"Oudomxai\", \"Phongsali\", \"Salavan\", \"Savannakhet\", \"Viangchan\", \"Viangchan\", \"Xaignabouli\", \"Xaisomboun\", \"Xekong\", \"Xiangkhoang\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Latvia\",\n" +
                "      \"states\": [\"Aizkraukles Rajons\", \"Aluksnes Rajons\", \"Balvu Rajons\", \"Bauskas Rajons\", \"Cesu Rajons\", \"Daugavpils\", \"Daugavpils Rajons\", \"Dobeles Rajons\", \"Gulbenes Rajons\", \"Jekabpils Rajons\", \"Jelgava\", \"Jelgavas Rajons\", \"Jurmala\", \"Kraslavas Rajons\", \"Kuldigas Rajons\", \"Liepaja\", \"Liepajas Rajons\", \"Limbazu Rajons\", \"Ludzas Rajons\", \"Madonas Rajons\", \"Ogres Rajons\", \"Preilu Rajons\", \"Rezekne\", \"Rezeknes Rajons\", \"Riga\", \"Rigas Rajons\", \"Saldus Rajons\", \"Talsu Rajons\", \"Tukuma Rajons\", \"Valkas Rajons\", \"Valmieras Rajons\", \"Ventspils\", \"Ventspils Rajons\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Lebanon\",\n" +
                "      \"states\": [\"Beyrouth\", \"Beqaa\", \"Liban-Nord\", \"Liban-Sud\", \"Mont-Liban\", \"Nabatiye\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Lesotho\",\n" +
                "      \"states\": [\"Berea\", \"Butha-Buthe\", \"Leribe\", \"Mafeteng\", \"Maseru\", \"Mohale's Hoek\", \"Mokhotlong\", \"Qacha's Nek\", \"Quthing\", \"Thaba-Tseka\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Liberia\",\n" +
                "      \"states\": [\"Bomi\", \"Bong\", \"Gbarpolu\", \"Grand Bassa\", \"Grand Cape Mount\", \"Grand Gedeh\", \"Grand Kru\", \"Lofa\", \"Margibi\", \"Maryland\", \"Montserrado\", \"Nimba\", \"River Cess\", \"River Gee\", \"Sinoe\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Libya\",\n" +
                "      \"states\": [\"Ajdabiya\", \"Al 'Aziziyah\", \"Al Fatih\", \"Al Jabal al Akhdar\", \"Al Jufrah\", \"Al Khums\", \"Al Kufrah\", \"An Nuqat al Khams\", \"Ash Shati'\", \"Awbari\", \"Az Zawiyah\", \"Banghazi\", \"Darnah\", \"Ghadamis\", \"Gharyan\", \"Misratah\", \"Murzuq\", \"Sabha\", \"Sawfajjin\", \"Surt\", \"Tarabulus\", \"Tarhunah\", \"Tubruq\", \"Yafran\", \"Zlitan\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Liechtenstein\",\n" +
                "      \"states\": [\"Balzers\", \"Eschen\", \"Gamprin\", \"Mauren\", \"Planken\", \"Ruggell\", \"Schaan\", \"Schellenberg\", \"Triesen\", \"Triesenberg\", \"Vaduz\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Lithuania\",\n" +
                "      \"states\": [\"Alytaus\", \"Kauno\", \"Klaipedos\", \"Marijampoles\", \"Panevezio\", \"Siauliu\", \"Taurages\", \"Telsiu\", \"Utenos\", \"Vilniaus\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Luxembourg\",\n" +
                "      \"states\": [\"Diekirch\", \"Grevenmacher\", \"Luxembourg\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Macedonia\",\n" +
                "      \"states\": [\"Aerodrom\", \"Aracinovo\", \"Berovo\", \"Bitola\", \"Bogdanci\", \"Bogovinje\", \"Bosilovo\", \"Brvenica\", \"Butel\", \"Cair\", \"Caska\", \"Centar\", \"Centar Zupa\", \"Cesinovo\", \"Cucer-Sandevo\", \"Debar\", \"Debartsa\", \"Delcevo\", \"Demir Hisar\", \"Demir Kapija\", \"Dojran\", \"Dolneni\", \"Drugovo\", \"Gazi Baba\", \"Gevgelija\", \"Gjorce Petrov\", \"Gostivar\", \"Gradsko\", \"Ilinden\", \"Jegunovce\", \"Karbinci\", \"Karpos\", \"Kavadarci\", \"Kicevo\", \"Kisela Voda\", \"Kocani\", \"Konce\", \"Kratovo\", \"Kriva Palanka\", \"Krivogastani\", \"Krusevo\", \"Kumanovo\", \"Lipkovo\", \"Lozovo\", \"Makedonska Kamenica\", \"Makedonski Brod\", \"Mavrovo i Rastusa\", \"Mogila\", \"Negotino\", \"Novaci\", \"Novo Selo\", \"Ohrid\", \"Oslomej\", \"Pehcevo\", \"Petrovec\", \"Plasnica\", \"Prilep\", \"Probistip\", \"Radovis\", \"Rankovce\", \"Resen\", \"Rosoman\", \"Saraj\", \"Skopje\", \"Sopiste\", \"Staro Nagoricane\", \"Stip\", \"Struga\", \"Strumica\", \"Studenicani\", \"Suto Orizari\", \"Sveti Nikole\", \"Tearce\", \"Tetovo\", \"Valandovo\", \"Vasilevo\", \"Veles\", \"Vevcani\", \"Vinica\", \"Vranestica\", \"Vrapciste\", \"Zajas\", \"Zelenikovo\", \"Zelino\", \"Zrnovci\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Madagascar\",\n" +
                "      \"states\": [\"Antananarivo\", \"Antsiranana\", \"Fianarantsoa\", \"Mahajanga\", \"Toamasina\", \"Toliara\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Malawi\",\n" +
                "      \"states\": [\"Balaka\", \"Blantyre\", \"Chikwawa\", \"Chiradzulu\", \"Chitipa\", \"Dedza\", \"Dowa\", \"Karonga\", \"Kasungu\", \"Likoma\", \"Lilongwe\", \"Machinga\", \"Mangochi\", \"Mchinji\", \"Mulanje\", \"Mwanza\", \"Mzimba\", \"Ntcheu\", \"Nkhata Bay\", \"Nkhotakota\", \"Nsanje\", \"Ntchisi\", \"Phalombe\", \"Rumphi\", \"Salima\", \"Thyolo\", \"Zomba\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Malaysia\",\n" +
                "      \"states\": [\"Johor\", \"Kedah\", \"Kelantan\", \"Kuala Lumpur\", \"Labuan\", \"Malacca\", \"Negeri Sembilan\", \"Pahang\", \"Perak\", \"Perlis\", \"Penang\", \"Sabah\", \"Sarawak\", \"Selangor\", \"Terengganu\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Maldives\",\n" +
                "      \"states\": [\"Alifu\", \"Baa\", \"Dhaalu\", \"Faafu\", \"Gaafu Alifu\", \"Gaafu Dhaalu\", \"Gnaviyani\", \"Haa Alifu\", \"Haa Dhaalu\", \"Kaafu\", \"Laamu\", \"Lhaviyani\", \"Maale\", \"Meemu\", \"Noonu\", \"Raa\", \"Seenu\", \"Shaviyani\", \"Thaa\", \"Vaavu\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mali\",\n" +
                "      \"states\": [\"Bamako (Capital)\", \"Gao\", \"Kayes\", \"Kidal\", \"Koulikoro\", \"Mopti\", \"Segou\", \"Sikasso\", \"Tombouctou\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Malta\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Marshall Islands\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mauritania\",\n" +
                "      \"states\": [\"Adrar\", \"Assaba\", \"Brakna\", \"Dakhlet Nouadhibou\", \"Gorgol\", \"Guidimaka\", \"Hodh Ech Chargui\", \"Hodh El Gharbi\", \"Inchiri\", \"Nouakchott\", \"Tagant\", \"Tiris Zemmour\", \"Trarza\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mauritius\",\n" +
                "      \"states\": [\"Agalega Islands\", \"Black River\", \"Cargados Carajos Shoals\", \"Flacq\", \"Grand Port\", \"Moka\", \"Pamplemousses\", \"Plaines Wilhems\", \"Port Louis\", \"Riviere du Rempart\", \"Rodrigues\", \"Savanne\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mexico\",\n" +
                "      \"states\": [\"Aguascalientes\", \"Baja California\", \"Baja California Sur\", \"Campeche\", \"Chiapas\", \"Chihuahua\", \"Coahuila de Zaragoza\", \"Colima\", \"Distrito Federal\", \"Durango\", \"Guanajuato\", \"Guerrero\", \"Hidalgo\", \"Jalisco\", \"Mexico\", \"Michoacan de Ocampo\", \"Morelos\", \"Nayarit\", \"Nuevo Leon\", \"Oaxaca\", \"Puebla\", \"Queretaro de Arteaga\", \"Quintana Roo\", \"San Luis Potosi\", \"Sinaloa\", \"Sonora\", \"Tabasco\", \"Tamaulipas\", \"Tlaxcala\", \"Veracruz-Llave\", \"Yucatan\", \"Zacatecas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Micronesia\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Moldova\",\n" +
                "      \"states\": [\"Anenii Noi\", \"Basarabeasca\", \"Briceni\", \"Cahul\", \"Cantemir\", \"Calarasi\", \"Causeni\", \"Cimislia\", \"Criuleni\", \"Donduseni\", \"Drochia\", \"Dubasari\", \"Edinet\", \"Falesti\", \"Floresti\", \"Glodeni\", \"Hincesti\", \"Ialoveni\", \"Leova\", \"Nisporeni\", \"Ocnita\", \"Orhei\", \"Rezina\", \"Riscani\", \"Singerei\", \"Soldanesti\", \"Soroca\", \"Stefan-Voda\", \"Straseni\", \"Taraclia\", \"Telenesti\", \"Ungheni\", \"Balti\", \"Bender\", \"Chisinau\", \"Gagauzia\", \"Stinga Nistrului\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mongolia\",\n" +
                "      \"states\": [\"Arhangay\", \"Bayanhongor\", \"Bayan-Olgiy\", \"Bulgan\", \"Darhan Uul\", \"Dornod\", \"Dornogovi\", \"Dundgovi\", \"Dzavhan\", \"Govi-Altay\", \"Govi-Sumber\", \"Hentiy\", \"Hovd\", \"Hovsgol\", \"Omnogovi\", \"Orhon\", \"Ovorhangay\", \"Selenge\", \"Suhbaatar\", \"Tov\", \"Ulaanbaatar\", \"Uvs\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Morocco\",\n" +
                "      \"states\": [\"Agadir\", \"Al Hoceima\", \"Azilal\", \"Beni Mellal\", \"Ben Slimane\", \"Boulemane\", \"Casablanca\", \"Chaouen\", \"El Jadida\", \"El Kelaa des Sraghna\", \"Er Rachidia\", \"Essaouira\", \"Fes\", \"Figuig\", \"Guelmim\", \"Ifrane\", \"Kenitra\", \"Khemisset\", \"Khenifra\", \"Khouribga\", \"Laayoune\", \"Larache\", \"Marrakech\", \"Meknes\", \"Nador\", \"Ouarzazate\", \"Oujda\", \"Rabat-Sale\", \"Safi\", \"Settat\", \"Sidi Kacem\", \"Tangier\", \"Tan-Tan\", \"Taounate\", \"Taroudannt\", \"Tata\", \"Taza\", \"Tetouan\", \"Tiznit\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Monaco\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Mozambique\",\n" +
                "      \"states\": [\"Cabo Delgado\", \"Gaza\", \"Inhambane\", \"Manica\", \"Maputo\", \"Cidade de Maputo\", \"Nampula\", \"Niassa\", \"Sofala\", \"Tete\", \"Zambezia\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Namibia\",\n" +
                "      \"states\": [\"Caprivi\", \"Erongo\", \"Hardap\", \"Karas\", \"Khomas\", \"Kunene\", \"Ohangwena\", \"Okavango\", \"Omaheke\", \"Omusati\", \"Oshana\", \"Oshikoto\", \"Otjozondjupa\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Nauru\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Nepal\",\n" +
                "      \"states\": [\"Bagmati\", \"Bheri\", \"Dhawalagiri\", \"Gandaki\", \"Janakpur\", \"Karnali\", \"Kosi\", \"Lumbini\", \"Mahakali\", \"Mechi\", \"Narayani\", \"Rapti\", \"Sagarmatha\", \"Seti\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Netherlands\",\n" +
                "      \"states\": [\"Drenthe\", \"Flevoland\", \"Friesland\", \"Gelderland\", \"Groningen\", \"Limburg\", \"Noord-Brabant\", \"Noord-Holland\", \"Overijssel\", \"Utrecht\", \"Zeeland\", \"Zuid-Holland\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"New Zealand\",\n" +
                "      \"states\": [\"Auckland\", \"Bay of Plenty\", \"Canterbury\", \"Chatham Islands\", \"Gisborne\", \"Hawke's Bay\", \"Manawatu-Wanganui\", \"Marlborough\", \"Nelson\", \"Northland\", \"Otago\", \"Southland\", \"Taranaki\", \"Tasman\", \"Waikato\", \"Wellington\", \"West Coast\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Nicaragua\",\n" +
                "      \"states\": [\"Atlantico Norte\", \"Atlantico Sur\", \"Boaco\", \"Carazo\", \"Chinandega\", \"Chontales\", \"Esteli\", \"Granada\", \"Jinotega\", \"Leon\", \"Madriz\", \"Managua\", \"Masaya\", \"Matagalpa\", \"Nueva Segovia\", \"Rio San Juan\", \"Rivas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Niger\",\n" +
                "      \"states\": [\"Agadez\", \"Diffa\", \"Dosso\", \"Maradi\", \"Niamey\", \"Tahoua\", \"Tillaberi\", \"Zinder\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Nigeria\",\n" +
                "      \"states\": [\"Abia\", \"Abuja Federal Capital\", \"Adamawa\", \"Akwa Ibom\", \"Anambra\", \"Bauchi\", \"Bayelsa\", \"Benue\", \"Borno\", \"Cross River\", \"Delta\", \"Ebonyi\", \"Edo\", \"Ekiti\", \"Enugu\", \"Gombe\", \"Imo\", \"Jigawa\", \"Kaduna\", \"Kano\", \"Katsina\", \"Kebbi\", \"Kogi\", \"Kwara\", \"Lagos\", \"Nassarawa\", \"Niger\", \"Ogun\", \"Ondo\", \"Osun\", \"Oyo\", \"Plateau\", \"Rivers\", \"Sokoto\", \"Taraba\", \"Yobe\", \"Zamfara\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Norway\",\n" +
                "      \"states\": [\"Akershus\", \"Aust-Agder\", \"Buskerud\", \"Finnmark\", \"Hedmark\", \"Hordaland\", \"More og Romsdal\", \"Nordland\", \"Nord-Trondelag\", \"Oppland\", \"Oslo\", \"Ostfold\", \"Rogaland\", \"Sogn og Fjordane\", \"Sor-Trondelag\", \"Telemark\", \"Troms\", \"Vest-Agder\", \"Vestfold\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Oman\",\n" +
                "      \"states\": [\"Ad Dakhiliyah\", \"Al Batinah\", \"Al Wusta\", \"Ash Sharqiyah\", \"Az Zahirah\", \"Masqat\", \"Musandam\", \"Dhofar\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Pakistan\",\n" +
                "      \"states\": [\"Balochistan\", \"North-West Frontier Province\", \"Punjab\", \"Sindh\", \"Islamabad Capital Territory\", \"Federally Administered Tribal Areas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Panama\",\n" +
                "      \"states\": [\"Bocas del Toro\", \"Chiriqui\", \"Cocle\", \"Colon\", \"Darien\", \"Herrera\", \"Los Santos\", \"Panama\", \"San Blas\", \"Veraguas\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Papua New Guinea\",\n" +
                "      \"states\": [\"Bougainville\", \"Central\", \"Chimbu\", \"Eastern Highlands\", \"East New Britain\", \"East Sepik\", \"Enga\", \"Gulf\", \"Madang\", \"Manus\", \"Milne Bay\", \"Morobe\", \"National Capital\", \"New Ireland\", \"Northern\", \"Sandaun\", \"Southern Highlands\", \"Western\", \"Western Highlands\", \"West New Britain\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Paraguay\",\n" +
                "      \"states\": [\"Alto Paraguay\", \"Alto Parana\", \"Amambay\", \"Asuncion\", \"Boqueron\", \"Caaguazu\", \"Caazapa\", \"Canindeyu\", \"Central\", \"Concepcion\", \"Cordillera\", \"Guaira\", \"Itapua\", \"Misiones\", \"Neembucu\", \"Paraguari\", \"Presidente Hayes\", \"San Pedro\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Peru\",\n" +
                "      \"states\": [\"Amazonas\", \"Ancash\", \"Apurimac\", \"Arequipa\", \"Ayacucho\", \"Cajamarca\", \"Callao\", \"Cusco\", \"Huancavelica\", \"Huanuco\", \"Ica\", \"Junin\", \"La Libertad\", \"Lambayeque\", \"Lima\", \"Loreto\", \"Madre de Dios\", \"Moquegua\", \"Pasco\", \"Piura\", \"Puno\", \"San Martin\", \"Tacna\", \"Tumbes\", \"Ucayali\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Philippines\",\n" +
                "      \"states\": [\"Abra\", \"Agusan del Norte\", \"Agusan del Sur\", \"Aklan\", \"Albay\", \"Antique\", \"Apayao\", \"Aurora\", \"Basilan\", \"Bataan\", \"Batanes\", \"Batangas\", \"Biliran\", \"Benguet\", \"Bohol\", \"Bukidnon\", \"Bulacan\", \"Cagayan\", \"Camarines Norte\", \"Camarines Sur\", \"Camiguin\", \"Capiz\", \"Catanduanes\", \"Cavite\", \"Cebu\", \"Compostela\", \"Davao del Norte\", \"Davao del Sur\", \"Davao Oriental\", \"Eastern Samar\", \"Guimaras\", \"Ifugao\", \"Ilocos Norte\", \"Ilocos Sur\", \"Iloilo\", \"Isabela\", \"Kalinga\", \"Laguna\", \"Lanao del Norte\", \"Lanao del Sur\", \"La Union\", \"Leyte\", \"Maguindanao\", \"Marinduque\", \"Masbate\", \"Mindoro Occidental\", \"Mindoro Oriental\", \"Misamis Occidental\", \"Misamis Oriental\", \"Mountain Province\", \"Negros Occidental\", \"Negros Oriental\", \"North Cotabato\", \"Northern Samar\", \"Nueva Ecija\", \"Nueva Vizcaya\", \"Palawan\", \"Pampanga\", \"Pangasinan\", \"Quezon\", \"Quirino\", \"Rizal\", \"Romblon\", \"Samar\", \"Sarangani\", \"Siquijor\", \"Sorsogon\", \"South Cotabato\", \"Southern Leyte\", \"Sultan Kudarat\", \"Sulu\", \"Surigao del Norte\", \"Surigao del Sur\", \"Tarlac\", \"Tawi-Tawi\", \"Zambales\", \"Zamboanga del Norte\", \"Zamboanga del Sur\", \"Zamboanga Sibugay\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Poland\",\n" +
                "      \"states\": [\"Greater Poland (Wielkopolskie)\", \"Kuyavian-Pomeranian (Kujawsko-Pomorskie)\", \"Lesser Poland (Malopolskie)\", \"Lodz (Lodzkie)\", \"Lower Silesian (Dolnoslaskie)\", \"Lublin (Lubelskie)\", \"Lubusz (Lubuskie)\", \"Masovian (Mazowieckie)\", \"Opole (Opolskie)\", \"Podlasie (Podlaskie)\", \"Pomeranian (Pomorskie)\", \"Silesian (Slaskie)\", \"Subcarpathian (Podkarpackie)\", \"Swietokrzyskie (Swietokrzyskie)\", \"Warmian-Masurian (Warminsko-Mazurskie)\", \"West Pomeranian (Zachodniopomorskie)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Portugal\",\n" +
                "      \"states\": [\"Aveiro\", \"Acores\", \"Beja\", \"Braga\", \"Braganca\", \"Castelo Branco\", \"Coimbra\", \"Evora\", \"Faro\", \"Guarda\", \"Leiria\", \"Lisboa\", \"Madeira\", \"Portalegre\", \"Porto\", \"Santarem\", \"Setubal\", \"Viana do Castelo\", \"Vila Real\", \"Viseu\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Qatar\",\n" +
                "      \"states\": [\"Ad Dawhah\", \"Al Ghuwayriyah\", \"Al Jumayliyah\", \"Al Khawr\", \"Al Wakrah\", \"Ar Rayyan\", \"Jarayan al Batinah\", \"Madinat ash Shamal\", \"Umm Sa'id\", \"Umm Salal\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Romania\",\n" +
                "      \"states\": [\"Alba\", \"Arad\", \"Arges\", \"Bacau\", \"Bihor\", \"Bistrita-Nasaud\", \"Botosani\", \"Braila\", \"Brasov\", \"Bucuresti\", \"Buzau\", \"Calarasi\", \"Caras-Severin\", \"Cluj\", \"Constanta\", \"Covasna\", \"Dimbovita\", \"Dolj\", \"Galati\", \"Gorj\", \"Giurgiu\", \"Harghita\", \"Hunedoara\", \"Ialomita\", \"Iasi\", \"Ilfov\", \"Maramures\", \"Mehedinti\", \"Mures\", \"Neamt\", \"Olt\", \"Prahova\", \"Salaj\", \"Satu Mare\", \"Sibiu\", \"Suceava\", \"Teleorman\", \"Timis\", \"Tulcea\", \"Vaslui\", \"Vilcea\", \"Vrancea\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Russia\",\n" +
                "      \"states\": [\"Amur\", \"Arkhangel'sk\", \"Astrakhan'\", \"Belgorod\", \"Bryansk\", \"Chelyabinsk\", \"Chita\", \"Irkutsk\", \"Ivanovo\", \"Kaliningrad\", \"Kaluga\", \"Kamchatka\", \"Kemerovo\", \"Kirov\", \"Kostroma\", \"Kurgan\", \"Kursk\", \"Leningrad\", \"Lipetsk\", \"Magadan\", \"Moscow\", \"Murmansk\", \"Nizhniy Novgorod\", \"Novgorod\", \"Novosibirsk\", \"Omsk\", \"Orenburg\", \"Orel\", \"Penza\", \"Perm'\", \"Pskov\", \"Rostov\", \"Ryazan'\", \"Sakhalin\", \"Samara\", \"Saratov\", \"Smolensk\", \"Sverdlovsk\", \"Tambov\", \"Tomsk\", \"Tula\", \"Tver'\", \"Tyumen'\", \"Ul'yanovsk\", \"Vladimir\", \"Volgograd\", \"Vologda\", \"Voronezh\", \"Yaroslavl'\", \"Adygeya\", \"Altay\", \"Bashkortostan\", \"Buryatiya\", \"Chechnya\", \"Chuvashiya\", \"Dagestan\", \"Ingushetiya\", \"Kabardino-Balkariya\", \"Kalmykiya\", \"Karachayevo-Cherkesiya\", \"Kareliya\", \"Khakasiya\", \"Komi\", \"Mariy-El\", \"Mordoviya\", \"Sakha\", \"North Ossetia\", \"Tatarstan\", \"Tyva\", \"Udmurtiya\", \"Aga Buryat\", \"Chukotka\", \"Evenk\", \"Khanty-Mansi\", \"Komi-Permyak\", \"Koryak\", \"Nenets\", \"Taymyr\", \"Ust'-Orda Buryat\", \"Yamalo-Nenets\", \"Altay\", \"Khabarovsk\", \"Krasnodar\", \"Krasnoyarsk\", \"Primorskiy\", \"Stavropol'\", \"Moscow\", \"St. Petersburg\", \"Yevrey\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Rwanda\",\n" +
                "      \"states\": [\"Butare\", \"Byumba\", \"Cyangugu\", \"Gikongoro\", \"Gisenyi\", \"Gitarama\", \"Kibungo\", \"Kibuye\", \"Kigali Rurale\", \"Kigali-ville\", \"Umutara\", \"Ruhengeri\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Samoa\",\n" +
                "      \"states\": [\"A'ana\", \"Aiga-i-le-Tai\", \"Atua\", \"Fa'asaleleaga\", \"Gaga'emauga\", \"Gagaifomauga\", \"Palauli\", \"Satupa'itea\", \"Tuamasaga\", \"Va'a-o-Fonoti\", \"Vaisigano\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"San Marino\",\n" +
                "      \"states\": [\"Acquaviva\", \"Borgo Maggiore\", \"Chiesanuova\", \"Domagnano\", \"Faetano\", \"Fiorentino\", \"Montegiardino\", \"San Marino Citta\", \"Serravalle\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Sao Tome\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Saudi Arabia\",\n" +
                "      \"states\": [\"Al Bahah\", \"Al Hudud ash Shamaliyah\", \"Al Jawf\", \"Al Madinah\", \"Al Qasim\", \"Ar Riyad\", \"Ash Sharqiyah\", \"'Asir\", \"Ha'il\", \"Jizan\", \"Makkah\", \"Najran\", \"Tabuk\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Senegal\",\n" +
                "      \"states\": [\"Dakar\", \"Diourbel\", \"Fatick\", \"Kaolack\", \"Kolda\", \"Louga\", \"Matam\", \"Saint-Louis\", \"Tambacounda\", \"Thies\", \"Ziguinchor\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Serbia and Montenegro\",\n" +
                "      \"states\": [\"Kosovo\", \"Montenegro\", \"Serbia\", \"Vojvodina\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Seychelles\",\n" +
                "      \"states\": [\"Anse aux Pins\", \"Anse Boileau\", \"Anse Etoile\", \"Anse Louis\", \"Anse Royale\", \"Baie Lazare\", \"Baie Sainte Anne\", \"Beau Vallon\", \"Bel Air\", \"Bel Ombre\", \"Cascade\", \"Glacis\", \"Grand' Anse\", \"Grand' Anse\", \"La Digue\", \"La Riviere Anglaise\", \"Mont Buxton\", \"Mont Fleuri\", \"Plaisance\", \"Pointe La Rue\", \"Port Glaud\", \"Saint Louis\", \"Takamaka\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Sierra Leone\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Singapore\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Slovakia\",\n" +
                "      \"states\": [\"Banskobystricky\", \"Bratislavsky\", \"Kosicky\", \"Nitriansky\", \"Presovsky\", \"Trenciansky\", \"Trnavsky\", \"Zilinsky\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Slovenia\",\n" +
                "      \"states\": [\"Ajdovscina\", \"Beltinci\", \"Benedikt\", \"Bistrica ob Sotli\", \"Bled\", \"Bloke\", \"Bohinj\", \"Borovnica\", \"Bovec\", \"Braslovce\", \"Brda\", \"Brezice\", \"Brezovica\", \"Cankova\", \"Celje\", \"Cerklje na Gorenjskem\", \"Cerknica\", \"Cerkno\", \"Cerkvenjak\", \"Crensovci\", \"Crna na Koroskem\", \"Crnomelj\", \"Destrnik\", \"Divaca\", \"Dobje\", \"Dobrepolje\", \"Dobrna\", \"Dobrova-Horjul-Polhov Gradec\", \"Dobrovnik-Dobronak\", \"Dolenjske Toplice\", \"Dol pri Ljubljani\", \"Domzale\", \"Dornava\", \"Dravograd\", \"Duplek\", \"Gorenja Vas-Poljane\", \"Gorisnica\", \"Gornja Radgona\", \"Gornji Grad\", \"Gornji Petrovci\", \"Grad\", \"Grosuplje\", \"Hajdina\", \"Hoce-Slivnica\", \"Hodos-Hodos\", \"Horjul\", \"Hrastnik\", \"Hrpelje-Kozina\", \"Idrija\", \"Ig\", \"Ilirska Bistrica\", \"Ivancna Gorica\", \"Izola-Isola\", \"Jesenice\", \"Jezersko\", \"Jursinci\", \"Kamnik\", \"Kanal\", \"Kidricevo\", \"Kobarid\", \"Kobilje\", \"Kocevje\", \"Komen\", \"Komenda\", \"Koper-Capodistria\", \"Kostel\", \"Kozje\", \"Kranj\", \"Kranjska Gora\", \"Krizevci\", \"Krsko\", \"Kungota\", \"Kuzma\", \"Lasko\", \"Lenart\", \"Lendava-Lendva\", \"Litija\", \"Ljubljana\", \"Ljubno\", \"Ljutomer\", \"Logatec\", \"Loska Dolina\", \"Loski Potok\", \"Lovrenc na Pohorju\", \"Luce\", \"Lukovica\", \"Majsperk\", \"Maribor\", \"Markovci\", \"Medvode\", \"Menges\", \"Metlika\", \"Mezica\", \"Miklavz na Dravskem Polju\", \"Miren-Kostanjevica\", \"Mirna Pec\", \"Mislinja\", \"Moravce\", \"Moravske Toplice\", \"Mozirje\", \"Murska Sobota\", \"Muta\", \"Naklo\", \"Nazarje\", \"Nova Gorica\", \"Novo Mesto\", \"Odranci\", \"Oplotnica\", \"Ormoz\", \"Osilnica\", \"Pesnica\", \"Piran-Pirano\", \"Pivka\", \"Podcetrtek\", \"Podlehnik\", \"Podvelka\", \"Polzela\", \"Postojna\", \"Prebold\", \"Preddvor\", \"Prevalje\", \"Ptuj\", \"Puconci\", \"Race-Fram\", \"Radece\", \"Radenci\", \"Radlje ob Dravi\", \"Radovljica\", \"Ravne na Koroskem\", \"Razkrizje\", \"Ribnica\", \"Ribnica na Pohorju\", \"Rogasovci\", \"Rogaska Slatina\", \"Rogatec\", \"Ruse\", \"Salovci\", \"Selnica ob Dravi\", \"Semic\", \"Sempeter-Vrtojba\", \"Sencur\", \"Sentilj\", \"Sentjernej\", \"Sentjur pri Celju\", \"Sevnica\", \"Sezana\", \"Skocjan\", \"Skofja Loka\", \"Skofljica\", \"Slovenj Gradec\", \"Slovenska Bistrica\", \"Slovenske Konjice\", \"Smarje pri Jelsah\", \"Smartno ob Paki\", \"Smartno pri Litiji\", \"Sodrazica\", \"Solcava\", \"Sostanj\", \"Starse\", \"Store\", \"Sveta Ana\", \"Sveti Andraz v Slovenskih Goricah\", \"Sveti Jurij\", \"Tabor\", \"Tisina\", \"Tolmin\", \"Trbovlje\", \"Trebnje\", \"Trnovska Vas\", \"Trzic\", \"Trzin\", \"Turnisce\", \"Velenje\", \"Velika Polana\", \"Velike Lasce\", \"Verzej\", \"Videm\", \"Vipava\", \"Vitanje\", \"Vodice\", \"Vojnik\", \"Vransko\", \"Vrhnika\", \"Vuzenica\", \"Zagorje ob Savi\", \"Zalec\", \"Zavrc\", \"Zelezniki\", \"Zetale\", \"Ziri\", \"Zirovnica\", \"Zuzemberk\", \"Zrece\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Solomon Islands\",\n" +
                "      \"states\": [\"Central\", \"Choiseul\", \"Guadalcanal\", \"Honiara\", \"Isabel\", \"Makira\", \"Malaita\", \"Rennell and Bellona\", \"Temotu\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Somalia\",\n" +
                "      \"states\": [\"Awdal\", \"Bakool\", \"Banaadir\", \"Bari\", \"Bay\", \"Galguduud\", \"Gedo\", \"Hiiraan\", \"Jubbada Dhexe\", \"Jubbada Hoose\", \"Mudug\", \"Nugaal\", \"Sanaag\", \"Shabeellaha Dhexe\", \"Shabeellaha Hoose\", \"Sool\", \"Togdheer\", \"Woqooyi Galbeed\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"South Africa\",\n" +
                "      \"states\": [\"Eastern Cape\", \"Free State\", \"Gauteng\", \"KwaZulu-Natal\", \"Limpopo\", \"Mpumalanga\", \"North-West\", \"Northern Cape\", \"Western Cape\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Spain\",\n" +
                "      \"states\": [\"Andalucia\", \"Aragon\", \"Asturias\", \"Baleares\", \"Ceuta\", \"Canarias\", \"Cantabria\", \"Castilla-La Mancha\", \"Castilla y Leon\", \"Cataluna\", \"Comunidad Valenciana\", \"Extremadura\", \"Galicia\", \"La Rioja\", \"Madrid\", \"Melilla\", \"Murcia\", \"Navarra\", \"Pais Vasco\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Sri Lanka\",\n" +
                "      \"states\": [\"Central\", \"North Central\", \"North Eastern\", \"North Western\", \"Sabaragamuwa\", \"Southern\", \"Uva\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Sudan\",\n" +
                "      \"states\": [\"A'ali an Nil\", \"Al Bahr al Ahmar\", \"Al Buhayrat\", \"Al Jazirah\", \"Al Khartum\", \"Al Qadarif\", \"Al Wahdah\", \"An Nil al Abyad\", \"An Nil al Azraq\", \"Ash Shamaliyah\", \"Bahr al Jabal\", \"Gharb al Istiwa'iyah\", \"Gharb Bahr al Ghazal\", \"Gharb Darfur\", \"Gharb Kurdufan\", \"Janub Darfur\", \"Janub Kurdufan\", \"Junqali\", \"Kassala\", \"Nahr an Nil\", \"Shamal Bahr al Ghazal\", \"Shamal Darfur\", \"Shamal Kurdufan\", \"Sharq al Istiwa'iyah\", \"Sinnar\", \"Warab\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Suriname\",\n" +
                "      \"states\": [\"Brokopondo\", \"Commewijne\", \"Coronie\", \"Marowijne\", \"Nickerie\", \"Para\", \"Paramaribo\", \"Saramacca\", \"Sipaliwini\", \"Wanica\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Swaziland\",\n" +
                "      \"states\": [\"Hhohho\", \"Lubombo\", \"Manzini\", \"Shiselweni\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Sweden\",\n" +
                "      \"states\": [\"Blekinge\", \"Dalarnas\", \"Gavleborgs\", \"Gotlands\", \"Hallands\", \"Jamtlands\", \"Jonkopings\", \"Kalmar\", \"Kronobergs\", \"Norrbottens\", \"Orebro\", \"Ostergotlands\", \"Skane\", \"Sodermanlands\", \"Stockholms\", \"Uppsala\", \"Varmlands\", \"Vasterbottens\", \"Vasternorrlands\", \"Vastmanlands\", \"Vastra Gotalands\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Switzerland\",\n" +
                "      \"states\": [\"Aargau\", \"Appenzell Ausser-Rhoden\", \"Appenzell Inner-Rhoden\", \"Basel-Landschaft\", \"Basel-Stadt\", \"Bern\", \"Fribourg\", \"Geneve\", \"Glarus\", \"Graubunden\", \"Jura\", \"Luzern\", \"Neuchatel\", \"Nidwalden\", \"Obwalden\", \"Sankt Gallen\", \"Schaffhausen\", \"Schwyz\", \"Solothurn\", \"Thurgau\", \"Ticino\", \"Uri\", \"Valais\", \"Vaud\", \"Zug\", \"Zurich\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Syria\",\n" +
                "      \"states\": [\"Al Hasakah\", \"Al Ladhiqiyah\", \"Al Qunaytirah\", \"Ar Raqqah\", \"As Suwayda'\", \"Dar'a\", \"Dayr az Zawr\", \"Dimashq\", \"Halab\", \"Hamah\", \"Hims\", \"Idlib\", \"Rif Dimashq\", \"Tartus\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Taiwan\",\n" +
                "      \"states\": [\"Chang-hua\", \"Chia-i\", \"Hsin-chu\", \"Hua-lien\", \"I-lan\", \"Kao-hsiung\", \"Kin-men\", \"Lien-chiang\", \"Miao-li\", \"Nan-t'ou\", \"P'eng-hu\", \"P'ing-tung\", \"T'ai-chung\", \"T'ai-nan\", \"T'ai-pei\", \"T'ai-tung\", \"T'ao-yuan\", \"Yun-lin\", \"Chia-i\", \"Chi-lung\", \"Hsin-chu\", \"T'ai-chung\", \"T'ai-nan\", \"Kao-hsiung city\", \"T'ai-pei city\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Tajikistan\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Tanzania\",\n" +
                "      \"states\": [\"Arusha\", \"Dar es Salaam\", \"Dodoma\", \"Iringa\", \"Kagera\", \"Kigoma\", \"Kilimanjaro\", \"Lindi\", \"Manyara\", \"Mara\", \"Mbeya\", \"Morogoro\", \"Mtwara\", \"Mwanza\", \"Pemba North\", \"Pemba South\", \"Pwani\", \"Rukwa\", \"Ruvuma\", \"Shinyanga\", \"Singida\", \"Tabora\", \"Tanga\", \"Zanzibar Central/South\", \"Zanzibar North\", \"Zanzibar Urban/West\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Thailand\",\n" +
                "      \"states\": [\"Amnat Charoen\", \"Ang Thong\", \"Buriram\", \"Chachoengsao\", \"Chai Nat\", \"Chaiyaphum\", \"Chanthaburi\", \"Chiang Mai\", \"Chiang Rai\", \"Chon Buri\", \"Chumphon\", \"Kalasin\", \"Kamphaeng Phet\", \"Kanchanaburi\", \"Khon Kaen\", \"Krabi\", \"Krung Thep Mahanakhon\", \"Lampang\", \"Lamphun\", \"Loei\", \"Lop Buri\", \"Mae Hong Son\", \"Maha Sarakham\", \"Mukdahan\", \"Nakhon Nayok\", \"Nakhon Pathom\", \"Nakhon Phanom\", \"Nakhon Ratchasima\", \"Nakhon Sawan\", \"Nakhon Si Thammarat\", \"Nan\", \"Narathiwat\", \"Nong Bua Lamphu\", \"Nong Khai\", \"Nonthaburi\", \"Pathum Thani\", \"Pattani\", \"Phangnga\", \"Phatthalung\", \"Phayao\", \"Phetchabun\", \"Phetchaburi\", \"Phichit\", \"Phitsanulok\", \"Phra Nakhon Si Ayutthaya\", \"Phrae\", \"Phuket\", \"Prachin Buri\", \"Prachuap Khiri Khan\", \"Ranong\", \"Ratchaburi\", \"Rayong\", \"Roi Et\", \"Sa Kaeo\", \"Sakon Nakhon\", \"Samut Prakan\", \"Samut Sakhon\", \"Samut Songkhram\", \"Sara Buri\", \"Satun\", \"Sing Buri\", \"Sisaket\", \"Songkhla\", \"Sukhothai\", \"Suphan Buri\", \"Surat Thani\", \"Surin\", \"Tak\", \"Trang\", \"Trat\", \"Ubon Ratchathani\", \"Udon Thani\", \"Uthai Thani\", \"Uttaradit\", \"Yala\", \"Yasothon\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Togo\",\n" +
                "      \"states\": [\"Kara\", \"Plateaux\", \"Savanes\", \"Centrale\", \"Maritime\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Tonga\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Trinidad and Tobago\",\n" +
                "      \"states\": [\"Couva\", \"Diego Martin\", \"Mayaro\", \"Penal\", \"Princes Town\", \"Sangre Grande\", \"San Juan\", \"Siparia\", \"Tunapuna\", \"Port-of-Spain\", \"San Fernando\", \"Arima\", \"Point Fortin\", \"Chaguanas\", \"Tobago\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Tunisia\",\n" +
                "      \"states\": [\"Ariana (Aryanah)\", \"Beja (Bajah)\", \"Ben Arous (Bin 'Arus)\", \"Bizerte (Banzart)\", \"Gabes (Qabis)\", \"Gafsa (Qafsah)\", \"Jendouba (Jundubah)\", \"Kairouan (Al Qayrawan)\", \"Kasserine (Al Qasrayn)\", \"Kebili (Qibili)\", \"Kef (Al Kaf)\", \"Mahdia (Al Mahdiyah)\", \"Manouba (Manubah)\", \"Medenine (Madanin)\", \"Monastir (Al Munastir)\", \"Nabeul (Nabul)\", \"Sfax (Safaqis)\", \"Sidi Bou Zid (Sidi Bu Zayd)\", \"Siliana (Silyanah)\", \"Sousse (Susah)\", \"Tataouine (Tatawin)\", \"Tozeur (Tawzar)\", \"Tunis\", \"Zaghouan (Zaghwan)\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Turkey\",\n" +
                "      \"states\": [\"Adana\", \"Adiyaman\", \"Afyonkarahisar\", \"Agri\", \"Aksaray\", \"Amasya\", \"Ankara\", \"Antalya\", \"Ardahan\", \"Artvin\", \"Aydin\", \"Balikesir\", \"Bartin\", \"Batman\", \"Bayburt\", \"Bilecik\", \"Bingol\", \"Bitlis\", \"Bolu\", \"Burdur\", \"Bursa\", \"Canakkale\", \"Cankiri\", \"Corum\", \"Denizli\", \"Diyarbakir\", \"Duzce\", \"Edirne\", \"Elazig\", \"Erzincan\", \"Erzurum\", \"Eskisehir\", \"Gaziantep\", \"Giresun\", \"Gumushane\", \"Hakkari\", \"Hatay\", \"Igdir\", \"Isparta\", \"Istanbul\", \"Izmir\", \"Kahramanmaras\", \"Karabuk\", \"Karaman\", \"Kars\", \"Kastamonu\", \"Kayseri\", \"Kilis\", \"Kirikkale\", \"Kirklareli\", \"Kirsehir\", \"Kocaeli\", \"Konya\", \"Kutahya\", \"Malatya\", \"Manisa\", \"Mardin\", \"Mersin\", \"Mugla\", \"Mus\", \"Nevsehir\", \"Nigde\", \"Ordu\", \"Osmaniye\", \"Rize\", \"Sakarya\", \"Samsun\", \"Sanliurfa\", \"Siirt\", \"Sinop\", \"Sirnak\", \"Sivas\", \"Tekirdag\", \"Tokat\", \"Trabzon\", \"Tunceli\", \"Usak\", \"Van\", \"Yalova\", \"Yozgat\", \"Zonguldak\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Turkmenistan\",\n" +
                "      \"states\": [\"Ahal Welayaty (Ashgabat)\", \"Balkan Welayaty (Balkanabat)\", \"Dashoguz Welayaty\", \"Lebap Welayaty (Turkmenabat)\", \"Mary Welayaty\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Uganda\",\n" +
                "      \"states\": [\"Adjumani\", \"Apac\", \"Arua\", \"Bugiri\", \"Bundibugyo\", \"Bushenyi\", \"Busia\", \"Gulu\", \"Hoima\", \"Iganga\", \"Jinja\", \"Kabale\", \"Kabarole\", \"Kaberamaido\", \"Kalangala\", \"Kampala\", \"Kamuli\", \"Kamwenge\", \"Kanungu\", \"Kapchorwa\", \"Kasese\", \"Katakwi\", \"Kayunga\", \"Kibale\", \"Kiboga\", \"Kisoro\", \"Kitgum\", \"Kotido\", \"Kumi\", \"Kyenjojo\", \"Lira\", \"Luwero\", \"Masaka\", \"Masindi\", \"Mayuge\", \"Mbale\", \"Mbarara\", \"Moroto\", \"Moyo\", \"Mpigi\", \"Mubende\", \"Mukono\", \"Nakapiripirit\", \"Nakasongola\", \"Nebbi\", \"Ntungamo\", \"Pader\", \"Pallisa\", \"Rakai\", \"Rukungiri\", \"Sembabule\", \"Sironko\", \"Soroti\", \"Tororo\", \"Wakiso\", \"Yumbe\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Ukraine\",\n" +
                "      \"states\": [\"Cherkasy\", \"Chernihiv\", \"Chernivtsi\", \"Crimea\", \"Dnipropetrovs'k\", \"Donets'k\", \"Ivano-Frankivs'k\", \"Kharkiv\", \"Kherson\", \"Khmel'nyts'kyy\", \"Kirovohrad\", \"Kiev\", \"Kyyiv\", \"Luhans'k\", \"L'viv\", \"Mykolayiv\", \"Odesa\", \"Poltava\", \"Rivne\", \"Sevastopol'\", \"Sumy\", \"Ternopil'\", \"Vinnytsya\", \"Volyn'\", \"Zakarpattya\", \"Zaporizhzhya\", \"Zhytomyr\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"United Arab Emirates\",\n" +
                "      \"states\": [\"Abu Dhabi\", \"'Ajman\", \"Al Fujayrah\", \"Sharjah\", \"Dubai\", \"Ra's al Khaymah\", \"Umm al Qaywayn\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"United Kingdom\",\n" +
                "      \"states\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"United States\",\n" +
                "      \"states\": [\"Alabama\", \"Alaska\", \"Arizona\", \"Arkansas\", \"California\", \"Colorado\", \"Connecticut\", \"Delaware\", \"District of Columbia\", \"Florida\", \"Georgia\", \"Hawaii\", \"Idaho\", \"Illinois\", \"Indiana\", \"Iowa\", \"Kansas\", \"Kentucky\", \"Louisiana\", \"Maine\", \"Maryland\", \"Massachusetts\", \"Michigan\", \"Minnesota\", \"Mississippi\", \"Missouri\", \"Montana\", \"Nebraska\", \"Nevada\", \"New Hampshire\", \"New Jersey\", \"New Mexico\", \"New York\", \"North Carolina\", \"North Dakota\", \"Ohio\", \"Oklahoma\", \"Oregon\", \"Pennsylvania\", \"Rhode Island\", \"South Carolina\", \"South Dakota\", \"Tennessee\", \"Texas\", \"Utah\", \"Vermont\", \"Virginia\", \"Washington\", \"West Virginia\", \"Wisconsin\", \"Wyoming\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Uruguay\",\n" +
                "      \"states\": [\"Artigas\", \"Canelones\", \"Cerro Largo\", \"Colonia\", \"Durazno\", \"Flores\", \"Florida\", \"Lavalleja\", \"Maldonado\", \"Montevideo\", \"Paysandu\", \"Rio Negro\", \"Rivera\", \"Rocha\", \"Salto\", \"San Jose\", \"Soriano\", \"Tacuarembo\", \"Treinta y Tres\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Uzbekistan\",\n" +
                "      \"states\": [\"Andijon Viloyati\", \"Buxoro Viloyati\", \"Farg'ona Viloyati\", \"Jizzax Viloyati\", \"Namangan Viloyati\", \"Navoiy Viloyati\", \"Qashqadaryo Viloyati\", \"Qaraqalpog'iston Respublikasi\", \"Samarqand Viloyati\", \"Sirdaryo Viloyati\", \"Surxondaryo Viloyati\", \"Toshkent Shahri\", \"Toshkent Viloyati\", \"Xorazm Viloyati\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Vanuatu\",\n" +
                "      \"states\": [\"Malampa\", \"Penama\", \"Sanma\", \"Shefa\", \"Tafea\", \"Torba\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Venezuela\",\n" +
                "      \"states\": [\"Amazonas\", \"Anzoategui\", \"Apure\", \"Aragua\", \"Barinas\", \"Bolivar\", \"Carabobo\", \"Cojedes\", \"Delta Amacuro\", \"Dependencias Federales\", \"Distrito Federal\", \"Falcon\", \"Guarico\", \"Lara\", \"Merida\", \"Miranda\", \"Monagas\", \"Nueva Esparta\", \"Portuguesa\", \"Sucre\", \"Tachira\", \"Trujillo\", \"Vargas\", \"Yaracuy\", \"Zulia\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Vietnam\",\n" +
                "      \"states\": [\"An Giang\", \"Bac Giang\", \"Bac Kan\", \"Bac Lieu\", \"Bac Ninh\", \"Ba Ria-Vung Tau\", \"Ben Tre\", \"Binh Dinh\", \"Binh Duong\", \"Binh Phuoc\", \"Binh Thuan\", \"Ca Mau\", \"Cao Bang\", \"Dac Lak\", \"Dac Nong\", \"Dien Bien\", \"Dong Nai\", \"Dong Thap\", \"Gia Lai\", \"Ha Giang\", \"Hai Duong\", \"Ha Nam\", \"Ha Tay\", \"Ha Tinh\", \"Hau Giang\", \"Hoa Binh\", \"Hung Yen\", \"Khanh Hoa\", \"Kien Giang\", \"Kon Tum\", \"Lai Chau\", \"Lam Dong\", \"Lang Son\", \"Lao Cai\", \"Long An\", \"Nam Dinh\", \"Nghe An\", \"Ninh Binh\", \"Ninh Thuan\", \"Phu Tho\", \"Phu Yen\", \"Quang Binh\", \"Quang Nam\", \"Quang Ngai\", \"Quang Ninh\", \"Quang Tri\", \"Soc Trang\", \"Son La\", \"Tay Ninh\", \"Thai Binh\", \"Thai Nguyen\", \"Thanh Hoa\", \"Thua Thien-Hue\", \"Tien Giang\", \"Tra Vinh\", \"Tuyen Quang\", \"Vinh Long\", \"Vinh Phuc\", \"Yen Bai\", \"Can Tho\", \"Da Nang\", \"Hai Phong\", \"Hanoi\", \"Ho Chi Minh\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Yemen\",\n" +
                "      \"states\": [\"Abyan\", \"'Adan\", \"Ad Dali'\", \"Al Bayda'\", \"Al Hudaydah\", \"Al Jawf\", \"Al Mahrah\", \"Al Mahwit\", \"'Amran\", \"Dhamar\", \"Hadramawt\", \"Hajjah\", \"Ibb\", \"Lahij\", \"Ma'rib\", \"Sa'dah\", \"San'a'\", \"Shabwah\", \"Ta'izz\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Zambia\",\n" +
                "      \"states\": [\"Central\", \"Copperbelt\", \"Eastern\", \"Luapula\", \"Lusaka\", \"Northern\", \"North-Western\", \"Southern\", \"Western\"]\n" +
                "    },\n" +
                "    {\n" +
                "      \"country\": \"Zimbabwe\",\n" +
                "      \"states\": [\"Bulawayo\", \"Harare\", \"Manicaland\", \"Mashonaland Central\", \"Mashonaland East\", \"Mashonaland West\", \"Masvingo\", \"Matabeleland North\", \"Matabeleland South\", \"Midlands\"]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        //  JSONObject jsonObject = new JSONObject(countriesJson);
        //  JsonParser parser = new JsonParser();
        //  JsonElement mJson = parser.parse(countriesJson);
        Gson gson = new Gson();

        object = gson.fromJson(countriesJson, CounteryModel.class);

        countrylist.clear();

        countrylist.add("Select Country");
        for (int i = 0; i < object.getCountries().size(); i++) {
            countrylist.add(object.getCountries().get(i).getCountry());
        }
        Customspinner.notifyDataSetChanged();


    }

    /* private List<String> getCountryList(){
         countrylist.clear();
         for(com.tenserflow.therapist.Utils.Country country:object.getCountries()){
             countrylist.add(country.getCountry());
         }

         return countrylist;
     }*/
    private void getstateList(String countryName) {
        //  List<String> statelist = new ArrayList<>();
        statelist.clear();
        statelist.add("Select State");
        for (com.tenserflow.therapist.Utils.Country country : object.getCountries()) {
            if (country.getCountry().toString().equalsIgnoreCase(countryName)) {
                statelist.addAll(country.getStates());

            }
        }

        //  Log.e("efcdecedcrdvc","  fff");
        CustomspinnerState.notifyDataSetChanged();
        spinnerSate.setSelection(0);


    }

    //  return statelist;}


    private void getDetailApi() {


        PersonalMedical_Api personalMedical_api = new PersonalMedical_Api();
        personalMedical_api.medical_personal(getActivity(), therapy_id, new PersonalMedical_Api.PersonalMedicalCallBack() {
            @Override
            public void onPersonalMedicalStatus(String res) {

                //   Snackbar.make(relTopAboutClient,response.getMessage(),Snackbar.LENGTH_SHORT).show();

               /* if (response.getStatus().equalsIgnoreCase("1")) {

                    String user_Name = response.getPersonalDetails().getName();
                    String user_CountryName = response.getPersonalDetails().getCountryName();
                    String user_State = response.getPersonalDetails().getState();
                    String user_City = response.getPersonalDetails().getCity();
                    String user_Address = response.getPersonalDetails().getAddress();
                    String user_Dob = response.getPersonalDetails().getDob();
                    String user_Age = response.getPersonalDetails().getAge();
                    String user_Email = response.getPersonalDetails().getEmail();
                    String user_CountryCodeC = response.getPersonalDetails().getCountryCodeC();
                    String user_CellPhone = response.getPersonalDetails().getCellPhone();
                    String user_CountryCodeH = response.getPersonalDetails().getCountryCodeH();
                    String user_HomePhone = response.getPersonalDetails().getHomePhone();

                   *//* Log.e("Dvwrasbvd",user_Name);
                    Log.e("Dvwrasbvd",response.getMedicalDetails().getDiagonosis());
*//*

                    String user_ContactNumber = response.getMedicalDetails().getContactNumber();
                    String user_CountryCode = response.getMedicalDetails().getCountryCode();

                    String user_FatherNam = response.getMedicalDetails().getFatherName();
                    String user_FatherMentalHealthIssue = response.getMedicalDetails().getFatherMentalHealthIssue();
                    String user_MotherName = response.getMedicalDetails().getMotherName();
                    String user_MotherMentalHealthIssue = response.getMedicalDetails().getMotherMentalHealthIssue();
                    String user_Diagonosis = response.getMedicalDetails().getDiagonosis();
                    String user_IssuesMessage = response.getMedicalDetails().getIssuesMessage();
                    String user_TheraphyGoal = response.getMedicalDetails().getTheraphyGoal();




                    try {


                    if (user_Name != null && !user_Name.equalsIgnoreCase(""))
                        edtNameClient.setText(user_Name);

                    if (user_City != null && !user_City.equalsIgnoreCase(""))
                        edtNameCity.setText(user_City);

                    if (user_Address != null && !user_Address.equalsIgnoreCase(""))
                        edtNameAddress.setText(user_Address);

                    if (user_Dob != null && !user_Dob.equalsIgnoreCase(""))
                        txtviewNameDob.setText(user_Dob);

                    if (user_Age != null && !user_Age.equalsIgnoreCase(""))
                        edtNameAge.setText(user_Age);

                    if (user_Email != null && !user_Email.equalsIgnoreCase(""))
                        edtNameEmail.setText(user_Email);

                    if (user_CellPhone != null && !user_CellPhone.equalsIgnoreCase(""))
                        edtCellPhone.setText(user_CellPhone);

                    if (user_HomePhone != null && !user_HomePhone.equalsIgnoreCase(""))
                        edtHomePhone.setText(user_HomePhone);

                    if (user_CountryCodeC != null && !user_CountryCodeC.equalsIgnoreCase(""))
                        edt_phoneCode_cellPhone.setText(user_CountryCodeC);

                    if (user_CountryCodeH != null && !user_CountryCodeH.equalsIgnoreCase(""))
                        edt_phoneCode_homePhone.setText(user_CountryCodeH);


                    if (user_CountryCode != null && !user_CountryCode.equalsIgnoreCase(""))
                        edtPhoneCodePreferredContact.setText(user_CountryCode);

                    if (user_ContactNumber != null && !user_ContactNumber.equalsIgnoreCase(""))
                        edtPreferrredContac.setText(user_ContactNumber);

                    if (user_FatherNam != null && !user_FatherNam.equalsIgnoreCase(""))
                        edtFatherName.setText(user_FatherNam);

                    if (user_FatherMentalHealthIssue != null && !user_FatherMentalHealthIssue.equalsIgnoreCase(""))
                        edtMentalHealth.setText(user_FatherMentalHealthIssue);

                    if (user_MotherName != null && !user_MotherName.equalsIgnoreCase(""))
                        edtMotherName.setText(user_MotherName);

                    if (user_MotherMentalHealthIssue != null && !user_MotherMentalHealthIssue.equalsIgnoreCase(""))
                        edtMentalHealthM.setText(user_MotherMentalHealthIssue);

                    if (user_Diagonosis != null && !user_Diagonosis.equalsIgnoreCase(""))
                        edtMedicalDiagonosis.setText(user_Diagonosis);

                    if (user_IssuesMessage != null && !user_IssuesMessage.equalsIgnoreCase(""))
                        edtAllIssues.setText(user_IssuesMessage);

                    if (user_TheraphyGoal != null && !user_TheraphyGoal.equalsIgnoreCase(""))
                        edtTherapyGoals.setText(user_TheraphyGoal);

                    if (user_CountryName != null && !user_CountryName.equalsIgnoreCase("")) {
                        setCountry(user_CountryName, user_State);


                    }

                    }catch (Exception e){

                    }
                }
*/




                try {

                    JSONObject jsonObject = new JSONObject(res);
                    String  status = jsonObject.getString("status");
                    Log.e("ResponseProfile",jsonObject.toString());


                    if (status.equalsIgnoreCase("1")) {


                        //------------------personal details------------------------------------
                        Object object = jsonObject.get("Personal_details");
                        if (object instanceof JSONArray) {
                            JSONArray jsonArray_personal = jsonObject.getJSONArray("Personal_details");


                        } else {
                            JSONObject jsonObject_personal = jsonObject.getJSONObject("Personal_details");

                            String personal_status  = jsonObject_personal.getString("personal_status");
                            String user_id  = jsonObject_personal.getString("user_id");
                            String sub_cat  = jsonObject_personal.getString("sub_cat");
                            String therapy_id  = jsonObject_personal.getString("therapy_id");
                            String name = jsonObject_personal.getString("name");
                            String address  = jsonObject_personal.getString("address");
                            String city  = jsonObject_personal.getString("city");
                            String state  = jsonObject_personal.getString("state");
                            String dob  = jsonObject_personal.getString("dob");
                            String age  = jsonObject_personal.getString("age");
                            String email  = jsonObject_personal.getString("email");
                            String country_name  = jsonObject_personal.getString("country_name");
                            String country_code_C  = jsonObject_personal.getString("country_code_C");
                            String cell_phone  = jsonObject_personal.getString("cell_phone");
                            String country_code_H  = jsonObject_personal.getString("country_code_H");
                            String home_phone  = jsonObject_personal.getString("home_phone");




                            try {


                                if (name != null && !name.equalsIgnoreCase(""))
                                    edtNameClient.setText(name);

                                if (city != null && !city.equalsIgnoreCase(""))
                                    edtNameCity.setText(city);

                                if (address != null && !address.equalsIgnoreCase(""))
                                    edtNameAddress.setText(address);

                                if (dob != null && !dob.equalsIgnoreCase(""))
                                    txtviewNameDob.setText(dob);

                                if (age != null && !age.equalsIgnoreCase(""))
                                    edtNameAge.setText(age);

                                if (email != null && !email.equalsIgnoreCase(""))
                                    edtNameEmail.setText(email);

                                if (cell_phone != null && !cell_phone.equalsIgnoreCase(""))
                                    edtCellPhone.setText(cell_phone);

                                if (home_phone != null && !home_phone.equalsIgnoreCase(""))
                                    edtHomePhone.setText(home_phone);

                                if (country_code_C != null && !country_code_C.equalsIgnoreCase(""))
                                    edt_phoneCode_cellPhone.setText(country_code_C);

                                if (country_code_H != null && !country_code_H.equalsIgnoreCase(""))
                                    edt_phoneCode_homePhone.setText(country_code_H);

                                if (country_name != null && !country_name.equalsIgnoreCase(""))
                                    setCountry(country_name, state);




                            }catch (Exception e){

                            }



                        }



                        //----------------medical details-----------------------------------------

                        Object object1 = jsonObject.get("Medical_details");
                        if (object1 instanceof JSONArray) {
                            JSONArray jsonArray_medical = jsonObject.getJSONArray("Medical_details");


                        } else {
                            JSONObject jsonObject_medical = jsonObject.getJSONObject("Medical_details");
                            String medical_status  = jsonObject_medical.getString("medical_status");
                            String user_id  = jsonObject_medical.getString("user_id");
                            String sub_cat  = jsonObject_medical.getString("sub_cat");
                            String therapy_id  = jsonObject_medical.getString("therapy_id");
                            String country_code  = jsonObject_medical.getString("country_code");
                            String contact_number  = jsonObject_medical.getString("contact_number");
                            String father_name  = jsonObject_medical.getString("father_name");
                            String mother_name  = jsonObject_medical.getString("mother_name");
                            String Father_Mental_health_issue  = jsonObject_medical.getString("Father_Mental_health_issue");
                            String Mother_Mental_health_issue  = jsonObject_medical.getString("Mother_Mental_health_issue");
                            String any_medical  = jsonObject_medical.getString("any_medical");
                            String diagonosis  = jsonObject_medical.getString("diagonosis");
                            String issues_message  = jsonObject_medical.getString("issues_message");
                            String theraphy_goal  = jsonObject_medical.getString("theraphy_goal");


                            try {

                            if (country_code != null && !country_code.equalsIgnoreCase(""))
                                edtPhoneCodePreferredContact.setText(country_code);

                            if (contact_number != null && !contact_number.equalsIgnoreCase(""))
                                edtPreferrredContac.setText(contact_number);

                            if (father_name != null && !father_name.equalsIgnoreCase(""))
                                edtFatherName.setText(father_name);

                            if (Father_Mental_health_issue != null && !Father_Mental_health_issue.equalsIgnoreCase(""))
                                edtMentalHealth.setText(Father_Mental_health_issue);

                            if (mother_name != null && !mother_name.equalsIgnoreCase(""))
                                edtMotherName.setText(mother_name);

                            if (Mother_Mental_health_issue != null && !Mother_Mental_health_issue.equalsIgnoreCase(""))
                                edtMentalHealthM.setText(Mother_Mental_health_issue);

                            if (diagonosis != null && !diagonosis.equalsIgnoreCase(""))
                                edtMedicalDiagonosis.setText(diagonosis);

                            if (issues_message != null && !issues_message.equalsIgnoreCase(""))
                                edtAllIssues.setText(issues_message);

                            if (theraphy_goal != null && !theraphy_goal.equalsIgnoreCase(""))
                                edtTherapyGoals.setText(theraphy_goal);



                            }catch (Exception e){

                        }



                        }


                        //---------------------------------------------------------------------------

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("ErrorProfile",e.getMessage());
                }




            }

            @Override
            public void onPersonalMedicalFailure() {

            }
        });

    }

    private void setState(String user_State_) {


        if (statelist.size() != 0) {


            for (int c = 0; c < statelist.size(); c++) {

                if (user_State_.equalsIgnoreCase(String.valueOf(statelist.get(c)))) {

                    try {
                        spinnerSate.setSelection(c);
                    }catch (Exception e){

                    }

                    break;
                }
            }

        }

    }

    private void getstateListAfterApiHit(String countryName, String stateName) {

        statelist.clear();
        statelist.add("Select State");
        for (com.tenserflow.therapist.Utils.Country country : object.getCountries()) {
            if (country.getCountry().toString().equalsIgnoreCase(countryName)) {
                statelist.addAll(country.getStates());

            }
        }

        setState(stateName);

        //  CustomspinnerState.notifyDataSetChanged();
        // spinnerSate.setSelection(0);


    }

    private void setCountry(final String user_CountryName_, final String state) {


        if (countrylist.size() != 0) {


            for (int c = 0; c < countrylist.size(); c++) {

                if (user_CountryName_.equalsIgnoreCase(String.valueOf(countrylist.get(c)))) {

                    spinnerCountry.setSelection(c);
                    break;
                }
            }

        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getstateListAfterApiHit(user_CountryName_, state);

            }
        }, 1000);

    }


    //------------calculate age-------------------------------------------------
    public int getAge (int _year, int _month, int _day) throws Exception {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        int a = year - _year;

       if (month < _month)
            a--;
        else if (month == _month) {
            if (day < _day)
                a--;
        }

        return a;

    }

    public void  getCountryCode(String countryName) throws Exception {
        // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            // Map all country names and codes in key - value pairs.
            countryMap.put(name, code);
        }
        // Get the country code for the given country name using the map.
        // Here you will need some validation or better yet
        // a list of countries to give to user to choose from.
        String countryCode = countryMap.get(countryName); // "NL" for Netherlands.
        String phCode = "+"+getCountryZipCode(countryCode);
        edt_phoneCode_cellPhone.setText(phCode);
        edt_phoneCode_homePhone.setText(phCode);
        edtPhoneCodePreferredContact.setText(phCode);


    }

    public String getCountryZipCode(String countryCode) throws Exception{
        String CountryID="";
        String CountryZipCode="";

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        //getNetworkCountryIso
        CountryID= manager.getSimCountryIso().toUpperCase();

        String[] rl=this.getResources().getStringArray(R.array.CountryCodes);
        for(int i=0;i<rl.length;i++){
            String[] g=rl[i].split(",");

            if(g[1].trim().equals(countryCode)){
                CountryZipCode=g[0];
                break;
            }
        }
        return CountryZipCode;
    }

}
