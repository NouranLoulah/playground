package com.example.nouran.playground.Services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Nouran on 3/25/2018.
 */

public class playgroundAPI {

    final static String BASE_URL = "http://clup.alatheertech.com/Api/";
    public static Retrofit retrofit = null;

    public  static Retrofit getclient() {

        if (retrofit == null) {
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                     .addConverterFactory(GsonConverterFactory
                     .create(gson)).build();
        }
        return retrofit;
    }

}
