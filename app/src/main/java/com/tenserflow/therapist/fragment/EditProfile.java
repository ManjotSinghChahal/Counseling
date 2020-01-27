package com.tenserflow.therapist.fragment;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hbb20.CountryCodePicker;
import com.tenserflow.therapist.Global;
import com.tenserflow.therapist.MainActivity;
import com.tenserflow.therapist.R;
import com.tenserflow.therapist.Utils.DialogHelperClass;
import com.tenserflow.therapist.Utils.ImageViewerActivity;
import com.tenserflow.therapist.Utils.InternetConnectivity;
import com.tenserflow.therapist.Webservice.DeleteProfileImg_Api;
import com.tenserflow.therapist.Webservice.EditProfile_Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfile extends Fragment {


    @BindView(R.id.username_editProfile)
    EditText usernameEditProfile;
    @BindView(R.id.phoneno_editProfile)
    EditText phonenoEditProfile;
    @BindView(R.id.address_editProfile)
    EditText addressEditProfile;
    Unbinder unbinder;
    @BindView(R.id.email_editProfile)
    TextView emailEditProfile;
    @BindView(R.id.rel_editProfile)
    RelativeLayout relEditProfile;

    String name,last_name, phone_no, address, email, user_image;
    @BindView(R.id.rel_circleimage_profile_upper)
    RelativeLayout relCircleimageProfile;
    @BindView(R.id.circleview_nav)
    CircleImageView circleviewNav;


    Uri fileUri;
    private static final String IMAGE_DIRECTORY_NAME = "therapist";
    byte[] byteArray;
    String img_path;
    @BindView(R.id.ccp_edit)
    CountryCodePicker ccpEdit;
    @BindView(R.id.edt_phoneCode_editDetaill)
    EditText edtPhoneCodeEditDetaill;
    String country_code = "";
    @BindView(R.id.rel_camera_icon)
    RelativeLayout relCameraIcon;

    private static final int REQUEST_CAPTURE_IMAGE = 100;
    private static final int REQUEST_SELECT_IMAGE = 101;
    String imageFilePath;
    @BindView(R.id.usernameLast_editProfile)
    EditText usernameLastEditProfile;

    public EditProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        unbinder = ButterKnife.bind(this, view);
        changeTitle();

      /*  //-------------------backpress---------------------------------------------
        ((MainActivity)getActivity()).relBackMainactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            last_name = bundle.getString("last_name");
            phone_no = bundle.getString("phone_no");
            //   address = bundle.getString("address");
            email = bundle.getString("email");
            user_image = bundle.getString("user_image");
            country_code = bundle.getString("country_code");

            usernameEditProfile.setText(name);
            usernameLastEditProfile.setText(last_name);
            phonenoEditProfile.setText(phone_no);
            edtPhoneCodeEditDetaill.setText(country_code);
            //   addressEditProfile.setText(address);
            emailEditProfile.setText(email);
            if (!user_image.equalsIgnoreCase(""))
                //  Glide.with(getActivity()).load(user_image).apply(new RequestOptions()).into(circleviewNav);
                if (!Global.userimage.equalsIgnoreCase(""))
                    Glide.with(getActivity()).load(Global.userimage).apply(new RequestOptions()).into(circleviewNav);
        }


        edtPhoneCodeEditDetaill.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                String input = s.toString();

                if (!s.toString().startsWith("+")) {
                    edtPhoneCodeEditDetaill.setText("+" + s.toString());
                    Selection.setSelection(edtPhoneCodeEditDetaill.getText(), edtPhoneCodeEditDetaill.getText().length());
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {


            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.txtview_send_request)
    public void onSaveClicked() {
        //  edtPhoneCodeEditDetaill


        if (usernameEditProfile.getText().toString().trim().equalsIgnoreCase("")) {
            usernameEditProfile.requestFocus();
            // usernameEditProfile.setError("Please enter username");
            Snackbar snackbar = Snackbar.make(relEditProfile, "Please enter name", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (edtPhoneCodeEditDetaill.getText().toString().trim().equalsIgnoreCase("")) {
            edtPhoneCodeEditDetaill.requestFocus();
            //  phonenoEditProfile.setError("Please enter phone number");
            Snackbar snackbar = Snackbar.make(relEditProfile, "Please enter country code", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (phonenoEditProfile.getText().toString().trim().equalsIgnoreCase("")) {
            phonenoEditProfile.requestFocus();
            //  phonenoEditProfile.setError("Please enter phone number");
            Snackbar snackbar = Snackbar.make(relEditProfile, "Please enter phone number", Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (phonenoEditProfile.getText().toString().trim().length() < 6) {
            phonenoEditProfile.requestFocus();
            //  phonenoEditProfile.setError("Please enter valid phone number");
            Snackbar snackbar = Snackbar.make(relEditProfile, "Please enter valid phone number", Snackbar.LENGTH_LONG);
            snackbar.show();
        } /*else if (addressEditProfile.getText().toString().trim().equalsIgnoreCase("")) {
            addressEditProfile.requestFocus();
            //  addressEditProfile.setError("Please enter address");
            Snackbar snackbar = Snackbar.make(relEditProfile, "Please enter address", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/
       /* else  if (img_path==null){
            Snackbar snackbar = Snackbar.make(getActivity().getWindow().getDecorView(), "Please select picture", Snackbar.LENGTH_LONG);
            snackbar.show();
        }*/
        else {

            if (img_path == null)
                img_path = "";

            // ccpEdit.getSelectedCountryCodeWithPlus()

            Global.hideKeyboard(getActivity());


            if (!InternetConnectivity.isConnected(getActivity())) {
                DialogHelperClass.getConnectionError(getActivity()).show();
            } else {


                EditProfile_Api editProfile = new EditProfile_Api();
                editProfile.edit_profile(getActivity(), usernameEditProfile.getText().toString().trim(),usernameLastEditProfile.getText().toString().trim(), phonenoEditProfile.getText().toString().trim(), "", emailEditProfile.getText().toString().trim(), relEditProfile, img_path, edtPhoneCodeEditDetaill.getText().toString().trim(), byteArray);
            }
        }

    }


    @OnClick({R.id.rel_camera_icon, R.id.rel_circleimage_profile_upper})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rel_camera_icon:
                insertDummyContactWrapper();
                break;
            case R.id.rel_circleimage_profile_upper:
                ImageViewerActivity.start(getActivity(), Global.userimage, Global.KEY_IMAGE_URL);
                break;
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


        if (Global.userimage.equalsIgnoreCase("http://therapy.gangtask.com/public/images/default1.jpg")) {


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
        } else {
            String[] colors = {"Take  photo", "Choose photo", "Remove photo"};
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
                    } else {
                        deleteProfielImg();
                    }
                }
            });
            builder.show();
        }


      /*  FilePickerBuilder.getInstance().setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme)
                .enableCameraSupport(true)
                //  .enableVideoPicker(true)
                //  .enableImagePicker(true)
                .pickPhoto(this);   // in fragment use this*/
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_CAPTURE_IMAGE && resultCode == RESULT_OK) {

            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Uri tempUri = getImageUri(this.getActivity(), imageBitmap);
            img_path = getRealPathFromURI(tempUri);
           Glide.with(this).load(new File(img_path)).apply(new RequestOptions()).into(circleviewNav);*/


            Glide.with(this).load(new File(img_path)).apply(new RequestOptions()).into(circleviewNav);
        } else if (requestCode == REQUEST_SELECT_IMAGE) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), contentURI);
                    Uri tempUri = getImageUri(getActivity(), bitmap);


                    img_path = getRealPathFromURI(tempUri);
                    Glide.with(this).load(new File(img_path)).apply(new RequestOptions()).into(circleviewNav);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }


    }

    public void uriToByteArray(String uri) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError e) {
        }

        byte[] buf = new byte[1024];
        int n;
        try {
            while (-1 != (n = fis.read(buf)))
                baos.write(buf, 0, n);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
        } catch (OutOfMemoryError e) {
        }

        byteArray = baos.toByteArray();
    }

    private void changeTitle() {

        ((MainActivity) getActivity()).userNameMainOptions.setText("Edit Profile");
        ((MainActivity) getActivity()).editbtnMain.setVisibility(View.GONE);
        ((MainActivity) getActivity()).relBackMainactivity.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).relMenuMainactivity.setVisibility(View.GONE);
        ((MainActivity) getActivity()).lockDrawer(true);
    }


    private void deleteProfielImg() {


        if (!InternetConnectivity.isConnected(getActivity())) {
            DialogHelperClass.getConnectionError(getActivity()).show();
        } else {

            DeleteProfileImg_Api delProfileImg_api = new DeleteProfileImg_Api();
            delProfileImg_api.deleteProfielImg(getActivity(), new DeleteProfileImg_Api.DelProfileImgCallBack() {

                @Override
                public void onDelProfileImgStatus(String response) {


                    try {

                        final JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("1")) {

                            String msg = jsonObject.getString("message");
                            Snackbar snackbar = Snackbar.make(relEditProfile, msg, Snackbar.LENGTH_LONG);
                            snackbar.show();
                            Global.userimage = "http://therapy.gangtask.com/public/images/default1.jpg";

                            Glide.with(getActivity()).load(user_image).apply(new RequestOptions()).into(((MainActivity) getActivity()).circleviewNav);
                            Glide.with(getActivity()).load(Global.userimage).apply(new RequestOptions()).into(circleviewNav);
                        } else {
                            String msg = jsonObject.getString("message");
                            Snackbar snackbar = Snackbar.make(relEditProfile, msg, Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDelProfileImgFailure() {

                }


            });
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


    //-------------------------------------------------

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


}