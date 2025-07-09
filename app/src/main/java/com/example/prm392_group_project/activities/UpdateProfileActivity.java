// app/src/main/java/com/example/prm392_group_project/activities/UpdateProfileActivity.java
package com.example.prm392_group_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.models.UpdateProfileRequest;
import com.example.prm392_group_project.utils.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText etName, etPhoneNumber, etAvatarUrl;
    private RadioGroup rgGender;
    private Button btnUpdate;
    private ImageView ivAvatar;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        tokenManager = new TokenManager(this);

        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etAvatarUrl = findViewById(R.id.etAvatarUrl);
        rgGender = findViewById(R.id.rgGender);
        btnUpdate = findViewById(R.id.btnUpdate);
        ivAvatar = findViewById(R.id.ivAvatar);

        // TODO: Load existing user data to pre-fill fields
        // Bạn sẽ cần một API để lấy thông tin profile hiện tại của người dùng
        // Sau đó điền vào các EditText và chọn RadioButton
        // Ví dụ:
        // fetchUserProfile();

        btnUpdate.setOnClickListener(v -> performUpdateProfile());
    }

    // private void fetchUserProfile() {
    //     // Gọi API để lấy thông tin profile của người dùng
    //     // Sau khi nhận được response, điền dữ liệu vào UI
    //     // Ví dụ:
    //     // etName.setText(userProfile.getName());
    //     // etPhoneNumber.setText(userProfile.getPhoneNumber());
    //     // etAvatarUrl.setText(userProfile.getAvatar());
    //     // setGenderRadio(userProfile.getGender());
    // }

    private void performUpdateProfile() {
        String name = etName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String avatar = etAvatarUrl.getText().toString().trim();
        String gender = getSelectedGender();

        if (name.isEmpty() || phoneNumber.isEmpty() || gender.isEmpty()) {
            showSnackbar("Vui lòng điền đầy đủ thông tin bắt buộc.");
            return;
        }

        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest(name, phoneNumber, avatar, gender);

        RetrofitClient.getAuthService(this).updateProfile(updateProfileRequest).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        showSnackbar("Cập nhật hồ sơ thành công!");
                        finish(); // Quay lại màn hình trước
                    } else {
                        showSnackbar("Cập nhật thất bại: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Cập nhật hồ sơ thất bại.";
                    try {
                        if (response.errorBody() != null) {
                            errorMessage = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    showSnackbar(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ResponseMessage<String>> call, Throwable t) {
                showSnackbar("Lỗi kết nối: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    private String getSelectedGender() {
        int selectedId = rgGender.getCheckedRadioButtonId();
        if (selectedId == R.id.rbMale) return "MALE";
        if (selectedId == R.id.rbFemale) return "FEMALE";
        if (selectedId == R.id.rbOther) return "OTHER";
        return "";
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    private void setGenderRadio(String gender) {
        if (gender != null) {
            switch (gender.toUpperCase()) {
                case "MALE":
                    rgGender.check(R.id.rbMale);
                    break;
                case "FEMALE":
                    rgGender.check(R.id.rbFemale);
                    break;
                case "OTHER":
                    rgGender.check(R.id.rbOther);
                    break;
            }
        }
    }
}
