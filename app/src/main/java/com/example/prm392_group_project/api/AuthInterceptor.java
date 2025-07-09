package com.example.prm392_group_project.api;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private Context context;

    public AuthInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        SharedPreferences prefs = context.getSharedPreferences("AuthPrefs", Context.MODE_PRIVATE);
        String authToken = prefs.getString("AuthToken", null);

        Request.Builder builder = originalRequest.newBuilder();

        if (authToken != null) {
            builder.header("Authorization", "Bearer " + authToken);
        }

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}