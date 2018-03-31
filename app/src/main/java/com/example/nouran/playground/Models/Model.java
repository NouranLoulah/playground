package com.example.nouran.playground.Models;

import java.io.Serializable;

/**
 * Created by Nouran on 3/25/2018.
 */

public class Model implements Serializable {

    String playground_id_pk;
    String playground_name;
    String playground_cost;
    String playground_capacity;
    String playground_phone;
    String playground_city_id_fk;
    String playground_governorate_id_fk;
    String playground_address;
    String playground_evaluate;
    double playground_google_lng;
    double playground_google_lat;
    String playground_info;
    String playground_descrip;
    String user_id_fk;
    String approved;
    String date_s;
    String image_name;
    String playground_offer;

    public String getPlayground_id_pk() {
        return playground_id_pk;
    }

    public void setPlayground_id_pk(String playground_id_pk) {
        this.playground_id_pk = playground_id_pk;
    }

    public String getPlayground_name() {
        return playground_name;
    }

    public void setPlayground_name(String playground_name) {
        this.playground_name = playground_name;
    }

    public String getPlayground_cost() {
        return playground_cost;
    }

    public void setPlayground_cost(String playground_cost) {
        this.playground_cost = playground_cost;
    }

    public String getPlayground_capacity() {
        return playground_capacity;
    }

    public void setPlayground_capacity(String playground_capacity) {
        this.playground_capacity = playground_capacity;
    }

    public String getPlayground_phone() {
        return playground_phone;
    }

    public void setPlayground_phone(String playground_phone) {
        this.playground_phone = playground_phone;
    }

    public String getPlayground_city_id_fk() {
        return playground_city_id_fk;
    }

    public void setPlayground_city_id_fk(String playground_city_id_fk) {
        this.playground_city_id_fk = playground_city_id_fk;
    }

    public String getPlayground_governorate_id_fk() {
        return playground_governorate_id_fk;
    }

    public void setPlayground_governorate_id_fk(String playground_governorate_id_fk) {
        this.playground_governorate_id_fk = playground_governorate_id_fk;
    }

    public String getPlayground_address() {
        return playground_address;
    }

    public void setPlayground_address(String playground_address) {
        this.playground_address = playground_address;
    }

    public String getPlayground_evaluate() {
        return playground_evaluate;
    }

    public void setPlayground_evaluate(String playground_evaluate) {
        this.playground_evaluate = playground_evaluate;
    }

    public double getPlayground_google_lng() {
        return playground_google_lng;
    }

    public void setPlayground_google_lng(double playground_google_lng) {
        this.playground_google_lng = playground_google_lng;
    }

    public double getPlayground_google_lat() {
        return playground_google_lat;
    }

    public void setPlayground_google_lat(double playground_google_lat) {
        this.playground_google_lat = playground_google_lat;
    }

    public String getPlayground_info() {
        return playground_info;
    }

    public void setPlayground_info(String playground_info) {
        this.playground_info = playground_info;
    }

    public String getPlayground_descrip() {
        return playground_descrip;
    }

    public void setPlayground_descrip(String playground_descrip) {
        this.playground_descrip = playground_descrip;
    }

    public String getUser_id_fk() {
        return user_id_fk;
    }

    public void setUser_id_fk(String user_id_fk) {
        this.user_id_fk = user_id_fk;
    }

    public String getApproved() {
        return approved;
    }

    public void setApproved(String approved) {
        this.approved = approved;
    }

    public String getDate_s() {
        return date_s;
    }

    public void setDate_s(String date_s) {
        this.date_s = date_s;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getPlayground_offer() {
        return playground_offer;
    }

    public void setPlayground_offer(String playground_offer) {
        this.playground_offer = playground_offer;
    }



    public Model(String playground_id_pk, String playground_name, String playground_cost, String playground_capacity, String playground_phone, String playground_city_id_fk, String playground_governorate_id_fk, String playground_address, String playground_evaluate, double playground_google_lng, double playground_google_lat, String playground_info, String playground_descrip, String user_id_fk, String approved, String date_s, String image_name, String playground_offer) {
        this.playground_id_pk = playground_id_pk;
        this.playground_name = playground_name;
        this.playground_cost = playground_cost;
        this.playground_capacity = playground_capacity;
        this.playground_phone = playground_phone;
        this.playground_city_id_fk = playground_city_id_fk;
        this.playground_governorate_id_fk = playground_governorate_id_fk;
        this.playground_address = playground_address;
        this.playground_evaluate = playground_evaluate;
        this.playground_google_lng = playground_google_lng;
        this.playground_google_lat = playground_google_lat;
        this.playground_info = playground_info;
        this.playground_descrip = playground_descrip;
        this.user_id_fk = user_id_fk;
        this.approved = approved;
        this.date_s = date_s;
        this.image_name = image_name;
        this.playground_offer = playground_offer;
    }






}
