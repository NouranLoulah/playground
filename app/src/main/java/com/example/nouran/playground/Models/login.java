package com.example.nouran.playground.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Nouran on 3/26/2018.
 */

public class login implements Serializable {

    private String id;
    @SerializedName("user_name")
    private String user_name;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("success")
    private int success;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public login(String id) {
        this.id = id;
    }


    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }


    public login(String user_name, String email, String password, String mobile) {
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
    }

    public login(int success) {
        this.success = success;
    }
}
