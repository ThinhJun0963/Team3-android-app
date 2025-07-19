// app/src/main/java/com/example/prm392_group_project/api/RetrofitClient.java
package com.example.prm392_group_project.api;

import android.content.Context;

import com.example.prm392_group_project.activities.AdminOrderManagementActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    // Cập nhật BASE_URL từ Swagger API bạn cung cấp
    private static final String BASE_URL = "https://projectprm-production.up.railway.app/";

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .addInterceptor(new AuthInterceptor(context))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static AuthService getAuthService(Context context) {
        return getClient(context).create(AuthService.class);
    }

    public static ProductApi getProductApi(Context context) {
        return getClient(context).create(ProductApi.class);
    }
    public static AdminProductApi getAdminProductApi(Context context) {
        return getClient(context).create(AdminProductApi.class);
    }
    public static AdminCategoryApi getAdminCategoryApi(Context context) {
        return getClient(context).create(AdminCategoryApi.class);
    }
    public static AdminUserApi getAdminUserApi(Context context) {
        return getClient(context).create(AdminUserApi.class);
    }
    public static AdminOrderApi getAdminOrderApi(AdminOrderManagementActivity context) {
        return getClient(context).create(AdminOrderApi.class);
    }
    public static OrderApi getOrderApi(Context context) {
        return getClient(context).create(OrderApi.class);
    }
    public static PaymentApi getPaymentApi(Context context) {
        return getClient(context).create(PaymentApi.class);
    }

}
