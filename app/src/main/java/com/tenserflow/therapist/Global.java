package com.tenserflow.therapist;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.tenserflow.therapist.Utils.ImageViewerActivity;
import com.tenserflow.therapist.Utils.MySpannable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Admin on 12/12/2018.
 */

public class Global {

    public static final java.lang.String KEY_IMAGE_URL ="th.image.url.view" ;
    public static final java.lang.String KEY_VIDEO_URL ="th.video.url.view" ;
    public static final java.lang.String KEY_IMAGE_PATH ="th.image.path.view" ;
    public static String user_id="";
 public static String token="";
 public static String user_name="";
 public static String last_name="";
 public static String userimage="";
 public static String user_email="";
 public static String user_address="";
 public static String user_phone_no="";
 public static String profileApiHit="false";
 public static String enterWayVar="";

 public static String CANCELLATION_POLICY = "";
 public static String PRIVACY_POLICY = "";
 public static String TERMS_CONDITIONS = "";
 public static String INFORMED_CONSENT = "";



    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


    }

    public static void softKeyboardToggle(Activity activity)
        {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }




    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static MultipartBody.Part convertImageTomultipart(String path, String name) {
        Log.e("ImagePath",path);
        File file = new File(path);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData(name, file.getName(), requestFile);

        return body;
    }

    public static void changeFragment(Context context, Fragment fragment,String addTobackStack)
    {
        if (addTobackStack.equalsIgnoreCase("save"))
        {
            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment)
                    .addToBackStack(null)
                    .commit();
        }
        else
        {
            FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container,fragment)
                    //    .addToBackStack(null)
                    .commit();
        }


    }

       public static RequestOptions getGlideOptions() {
        RequestOptions requestOptions = new RequestOptions();
//        requestOptions.transform(new CircleCrop());
        requestOptions.skipMemoryCache(true);
        requestOptions.fitCenter();
      //  requestOptions.placeholder(R.drawable.default_image);
      //  requestOptions.error(R.drawable.default_image);
        return requestOptions;
    }

    public static void hideTitleBar(Activity cnxt) {
        cnxt.requestWindowFeature(Window.FEATURE_NO_TITLE);
        cnxt.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }


    public static String capitalize(final String line) {

        String res;
        if (line==null || line.equalsIgnoreCase(""))
        {
            res="";
        }
       else  if(line.length()>=1)
        {
          res =  Character.toUpperCase(line.charAt(0)) + line.substring(1);
        }
        else
        {
            res = String.valueOf(Character.toUpperCase(line.charAt(0))) ;
        }

        return res;
    }


    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);

                int lineEndIndex;
                String text;

                if (tv.getLineCount()>2)
                {




                if (maxLine == 0) {
                    lineEndIndex = tv.getLayout().getLineEnd(0);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                } else {
                    lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                }

                tv.setText(text);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                tv.setText(
                        addClickablePartTextViewResizable(/*Html.fromHtml(*/tv.getText().toString(), tv, lineEndIndex, expandText,
                                viewMore), TextView.BufferType.SPANNABLE);

                }
                else
                {

                }

            }
        });

    }



    private static SpannableStringBuilder addClickablePartTextViewResizable(final String strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {


            ssb.setSpan(new MySpannable(false) {
                @Override
                public void onClick(View widget) {
                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "See Less", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "..See More", true);
                    }
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }


 /*   ProgressBack PB = new ProgressBack();
        PB.execute("");

    private void downloadFile(String fileURL, String fileName) {
        Log.e("Error....", "kjhgfdfd");
        try {
            String rootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "DCIM";
            File rootFile = new File(rootDir);
            rootFile.mkdir();
            URL url = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(rootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (IOException e) {
            Log.e("Error....", e.toString());
        }

    }
    class ProgressBack extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... arg0) {
            downloadFile("http://therapy.gangtask.com/public/videos/video%201.1.3gp", "Sample.mp4");

            return null;
        }

        protected void onPostExecute(Boolean result) {


        }



    }
*/

}
