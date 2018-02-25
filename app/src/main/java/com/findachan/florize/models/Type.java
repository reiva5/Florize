package com.findachan.florize.models;

/**
 * Created by Finda on 20/02/2018.
 */

public class Type {
    private String title;
    private String info;
    private final int imageResource;

    public Type(String title, String info,  int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }
}
