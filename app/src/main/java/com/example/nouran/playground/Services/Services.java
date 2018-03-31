package com.example.nouran.playground.Services;

import com.example.nouran.playground.Models.Model;
import com.example.nouran.playground.Models.galler;
import com.example.nouran.playground.Models.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Nouran on 3/26/2018.
 */

public interface Services {
    @GET("Allstadium")
    Call<List<Model>> playground();


    @GET("StadiumImages/{id}")
    Call<List<galler>> gallery(@Path("id") String id);

    @FormUrlEncoded
    @POST("InsertRegistration")
    Call<login> userSignUp(@Field("user_name") String name,
                           @Field("email") String email,
                           @Field("password") String password,
                           @Field("mobile") String mobile);



    @FormUrlEncoded
    @POST("Login")
    Call<login> userLogIn(@Field("user_name") String user_name,
                          @Field("password") String password);
}