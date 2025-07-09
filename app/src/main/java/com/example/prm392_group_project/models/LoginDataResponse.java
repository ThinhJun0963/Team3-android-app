package com.example.prm392_group_project.models;

import com.google.gson.annotations.SerializedName;

public class LoginDataResponse {
    @SerializedName("token")
    private String token;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    // Các trường khác như userId, username, email, refreshToken không có trong response login hiện tại
    // Nếu sau này backend trả về, bạn có thể thêm lại vào đây.
}
