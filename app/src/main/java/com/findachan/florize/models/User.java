package com.findachan.florize.models;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by harumlokawati on 22/02/18.
 */

@IgnoreExtraProperties
public class User {

    public String id;
    public String email;
    public String fullname;
    public String phone;
    public String address;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        this.fullname = "-";
        this.email = "-";
        this.phone = "-";
        this.address = "-";
        this.id = "-";
    }

    public User(String fullname, String email, String phone, String address, String id) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.id = id;
    }
    public boolean isPhoneSet(){
        return (!this.phone.contentEquals("-"));
    }
    public boolean isAddressSet(){
        return (!this.address.contentEquals("-"));
    }
    public boolean isFullnameSet(){
        return (!this.fullname.contentEquals("-"));
    }
    public boolean isIDSet(){
        return (!this.id.contentEquals("-"));
    }
    public boolean isEmailSet(){
        return (!this.email.contentEquals("-"));
    }
}