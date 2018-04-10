package com.example.nouran.playground.Models;

import retrofit2.http.Field;

/**
 * Created by Nouran on 4/2/2018.
 */

public class MSG {

    Integer time_reservation;
    String user_id;
    String date_reservation;
    String playground_id ;
    private  Integer success;

    public MSG(Integer time_reservation, String user_id, String date_reservation, String playground_id) {
        this.time_reservation = time_reservation;
        this.user_id = user_id;
        this.date_reservation = date_reservation;
        this.playground_id = playground_id;
    }

    public MSG(Integer success) {
        this.success = success;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getTime_reservation() {
        return time_reservation;
    }

    public void setTime_reservation(Integer time_reservation) {
        this.time_reservation = time_reservation;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_reservation() {
        return date_reservation;
    }

    public void setDate_reservation(String date_reservation) {
        this.date_reservation = date_reservation;
    }

    public String getPlayground_id() {
        return playground_id;
    }

    public void setPlayground_id(String playground_id) {
        this.playground_id = playground_id;
    }


}
