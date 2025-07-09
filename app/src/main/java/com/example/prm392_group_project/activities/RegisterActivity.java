// app/src/main/java/com/example/prm392_group_project/activities/RegisterActivity.java
package com.example.prm392_group_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.RegisterRequest;
import com.example.prm392_group_project.models.ResponseMessage;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPassword, etConfirmPassword, etPhoneNumber;
    private RadioGroup rgGender;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);

        btnRegister.setOnClickListener(v -> performRegistration());
        tvLogin.setOnClickListener(v -> finish()); // Quay lại màn hình đăng nhập
    }

    private void performRegistration() {
        String name = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String gender = getSelectedGender();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phoneNumber.isEmpty() || gender.isEmpty()) {
            showSnackbar("Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showSnackbar("Mật khẩu xác nhận không khớp.");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackbar("Email không hợp lệ.");
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(name, email, password, phoneNumber, gender);

        RetrofitClient.getAuthService(this).register(registerRequest).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        showSnackbar("Đăng ký thành công! Vui lòng xác thực email của bạn.");
                        // Chuyển sang màn hình xác thực email
                        Intent intent = new Intent(RegisterActivity.this, VerifyEmailActivity.class);
                        intent.putExtra("registered_email", email); // Truyền email sang màn hình xác thực
                        startActivity(intent);
                        finish();
                    } else {
                        showSnackbar("Đăng ký thất bại: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Đăng ký thất bại. Vui lòng thử lại.";
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
}
