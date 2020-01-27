package com.tenserflow.therapist.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

/*   public static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(120, TimeUnit.SECONDS).build();*/

    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3,TimeUnit.MINUTES)
            .build();




    private RetrofitClient() {}


    public static synchronized Retrofit getClient() {
        if (retrofit==null) {

          /*  Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();*/

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://therapy.gangtask.com/api/").client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
