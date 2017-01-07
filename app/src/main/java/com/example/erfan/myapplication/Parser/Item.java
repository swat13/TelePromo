package com.example.erfan.myapplication.Parser;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by erfan on 1/5/2017.
 */

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title;
    private String mainString;
    private int image;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMainString() {
        return mainString;
    }

    public void setMainString(String mainString) {
        this.mainString = mainString;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
