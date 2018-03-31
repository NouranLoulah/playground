package com.example.nouran.playground.Models;

import java.io.Serializable;

/**
 * Created by Nouran on 3/31/2018.
 */

public class galler implements Serializable{
    String id;

    public void setId(String id) {
        this.id = id;
    }

    String img;

    public galler(String img) {
        this.img = img;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
