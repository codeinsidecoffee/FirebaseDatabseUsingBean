package com.mrlonewolfer.firebasedatabseusingbean.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserBean {
    String fname,email,lname,mobile,userId;

    public UserBean(String userId,String fname,String lname,String email, String mobile) {
        this.fname = fname;
        this.email = email;
        this.lname = lname;
        this.mobile = mobile;
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "fname='" + fname + '\'' +
                ", email='" + email + '\'' +
                ", lname='" + lname + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
