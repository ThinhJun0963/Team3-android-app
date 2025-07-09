// app/src/main/java/com/example/prm392_group_project/utils/TokenManager.java
package com.example.prm392_group_project.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREF_NAME = "AuthPrefs";
    private static final String KEY_AUTH_TOKEN = "AuthToken";
    // KEY_USER_ID không còn được lưu trực tiếp từ response login
    // private static final String KEY_USER_ID = "UserId";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public TokenManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveAuthToken(String authToken) { // Chỉ lưu authToken
        editor.putString(KEY_AUTH_TOKEN, authToken);
        // editor.putLong(KEY_USER_ID, userId); // Bỏ userId
        editor.apply();
    }

    public String getAuthToken() {
        return prefs.getString(KEY_AUTH_TOKEN, null);
    }

    // Phương thức này sẽ không trả về userId trực tiếp từ login response nữa
    // Nếu cần userId, bạn sẽ phải giải mã JWT (phức tạp hơn) hoặc gọi một API khác để lấy profile.
    public Long getUserId() {
        // Trả về một giá trị mặc định hoặc null, hoặc implement logic giải mã JWT nếu cần
        return -1L; // Hoặc ném ngoại lệ nếu userId là bắt buộc
    }

    public void deleteTokens() {
        editor.remove(KEY_AUTH_TOKEN);
        // editor.remove(KEY_USER_ID); // Bỏ userId
        editor.apply();
    }

    public boolean isLoggedIn() {
        return getAuthToken() != null;
    }
}
