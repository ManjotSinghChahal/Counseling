package com.tenserflow.therapist.Utils;

public class FileProvider {



   /* Intent pictureIntent = new Intent(
            MediaStore.ACTION_IMAGE_CAPTURE);
                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
        //Create a file to store the image
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        if (photoFile != null) {


            Uri photoURI = FileProvider.getUriForFile(MainActivity.this, getPackageName()+".provider", photoFile);
            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                    photoURI);



            grantUriPermission(getPackageName(), photoURI,
                    //     grantUriPermission("com.example.vll.fileprovider", photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION
            );



            startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
        }
    }*/





/*
    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",        // suffix
                storageDir      // directory
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.e("Vsddvb ", imageFilePath);

            Glide.with(this).load(imageFilePath).into(imageView);

            //   imageView.setImageBitmap(BitmapFactory.decodeFile(imageFilePath));

        }
    }
*/

/*


           <provider
    android:name="android.support.v4.content.FileProvider"
    android:authorities="${applicationId}.provider"
    android:exported="false"
    android:grantUriPermissions="true">
            <meta-data
    android:name="android.support.FILE_PROVIDER_PATHS"
    android:resource="@xml/file_list"></meta-data>
        </provider>
*/







/*
    <?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <paths xmlns:android="http://schemas.android.com/apk/res/android">
        <external-path name="my_images"
    path="Android/data/com.tenserflow.fileprovider/files/Pictures" />
    </paths>
</PreferenceScreen>*/




}
