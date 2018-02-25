package com.findachan.florize.models;

/**
 * Created by Finda on 21/02/2018.
 */

public class Bouquet {
    private String name;
    private int category;
    private int price;
    private String desc;
    private String url;
    private String id;

    public Bouquet() {
    }

    public Bouquet(String name, int category, int price, String desc, String url, String id) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.desc = desc;
        this.url = url;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.category = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId(){
        return id;
    }
}
