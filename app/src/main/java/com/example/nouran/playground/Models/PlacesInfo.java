package com.example.nouran.playground.Models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nouran on 4/16/2018.
 */

public class PlacesInfo {
    private String name;
    private String Attributions;
    private String Address;
    private String id;
    private String phonenumper;
    private Uri website;
    private LatLng latLng;
    private float rating;

    public PlacesInfo(String name, String attributions, String address, String id, String phonenumper, Uri website, LatLng latLng, float rating) {
        this.name = name;
        Attributions = attributions;
        Address = address;
        this.id = id;
        this.phonenumper = phonenumper;
        this.website = website;
        this.latLng = latLng;
        this.rating = rating;
    }


    public PlacesInfo() {
    }

    @Override
    public String toString() {
        return "PlacesInfo{" +
                "name='" + name + '\'' +
                ", Attributions='" + Attributions + '\'' +
                ", Address='" + Address + '\'' +
                ", id='" + id + '\'' +
                ", phonenumper='" + phonenumper + '\'' +
                ", website=" + website +
                ", latLng=" + latLng +
                ", rating=" + rating +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttributions() {
        return Attributions;
    }

    public void setAttributions(String attributions) {
        Attributions = attributions;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhonenumper() {
        return phonenumper;
    }

    public void setPhonenumper(String phonenumper) {
        this.phonenumper = phonenumper;
    }

    public Uri getWebsite() {
        return website;
    }

    public void setWebsite(Uri website) {
        this.website = website;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
