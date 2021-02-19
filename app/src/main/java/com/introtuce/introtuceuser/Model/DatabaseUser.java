package com.introtuce.introtuceuser.Model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class DatabaseUser {
    public String first_name, last_name, gender, dob, country, state, hometown, telephone, phone, ImageUri;

    public DatabaseUser(String first_name, String last_name, String gender, String dob, String country, String state, String hometown, String phone, String telephone, String ImageUri) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.dob = dob;
        this.country = country;
        this.state = state;
        this.hometown = hometown;
        this.phone = phone;
        this.telephone = telephone;
        this.ImageUri = ImageUri;
    }

    public DatabaseUser (){}

    public String getImageUri123() {
        return ImageUri;
    }

    public void setImageUri123(String imageUri) {
        ImageUri = imageUri;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
