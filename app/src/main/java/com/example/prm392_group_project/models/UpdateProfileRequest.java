package com.example.prm392_group_project.models;

import com.google.gson.annotations.SerializedName;

public class UpdateProfileRequest {
    @SerializedName("name")
    private String name;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("avatar")
    private String avatar; // URL or base64 string
    @SerializedName("gender")
    private String gender;

    public UpdateProfileRequest(String name, String phoneNumber, String avatar, String gender) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
        this.gender = gender;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
}