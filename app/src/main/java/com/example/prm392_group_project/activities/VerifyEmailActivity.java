// app/src/main/java/com/example/prm392_group_project/activities/VerifyEmailActivity.java
package com.example.prm392_group_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.example.prm392_group_project.R;
import com.example.prm392_group_project.api.RetrofitClient;
import com.example.prm392_group_project.models.EmailRequest;
import com.example.prm392_group_project.models.ResponseMessage;
import com.example.prm392_group_project.models.VerifyEmailRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailActivity extends AppCompatActivity {

    private EditText etEmail, etOtp;
    private Button btnSendOtp, btnVerify;
    private TextView tvResendOtp;
    private LinearLayout layoutOtpInput;
    private String userEmailFromRegister; // Biến để lưu email nếu được truyền từ màn hình đăng ký

    // Biến cho bộ đếm ngược
    private int countdownSeconds = 60;
    private android.os.Handler handler = new android.os.Handler();
    private Runnable countdownRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_email);

        // Khởi tạo các View
        etEmail = findViewById(R.id.etEmail);
        etOtp = findViewById(R.id.etOtp);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        btnVerify = findViewById(R.id.btnVerify);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        layoutOtpInput = findViewById(R.id.layoutOtpInput);

        // Lấy email từ màn hình đăng ký nếu có
        if (getIntent().hasExtra("registered_email")) {
            userEmailFromRegister = getIntent().getStringExtra("registered_email");
            etEmail.setText(userEmailFromRegister);
            etEmail.setEnabled(false); // Không cho sửa email nếu đã được truyền
            layoutOtpInput.setVisibility(View.VISIBLE); // Hiển thị trường OTP ngay nếu đã có email
            btnSendOtp.setVisibility(View.GONE); // Ẩn nút gửi OTP ban đầu
            showSnackbar("Mã xác thực đã được gửi đến " + userEmailFromRegister);
            startCountdown(); // Bắt đầu đếm ngược ngay
        } else {
            layoutOtpInput.setVisibility(View.GONE); // Ẩn trường OTP ban đầu
            tvResendOtp.setVisibility(View.GONE); // Ẩn nút gửi lại ban đầu
        }

        // Thiết lập lắng nghe sự kiện cho nút "Gửi mã OTP"
        btnSendOtp.setOnClickListener(v -> sendOtpToEmail());
        // Thiết lập lắng nghe sự kiện cho nút "Xác thực OTP"
        btnVerify.setOnClickListener(v -> performEmailVerification());
        // Thiết lập lắng nghe sự kiện cho nút "Gửi lại mã"
        tvResendOtp.setOnClickListener(v -> {
            sendOtpToEmail(); // Gửi lại mã
            startCountdown(); // Bắt đầu lại đếm ngược
        });

        // Tự động nhấn nút xác thực khi đủ OTP (ví dụ cơ bản)
        etOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 6) { // Giả sử OTP 6 chữ số
                    btnVerify.performClick();
                }
            }
        });

        // Khởi tạo Runnable cho đếm ngược
        countdownRunnable = new Runnable() {
            @Override
            public void run() {
                if (countdownSeconds > 0) {
                    countdownSeconds--;
                    tvResendOtp.setText("Gửi lại mã (" + countdownSeconds + "s)");
                    handler.postDelayed(this, 1000);
                } else {
                    tvResendOtp.setText("Không nhận được mã? Gửi lại");
                    tvResendOtp.setEnabled(true); // Kích hoạt nút gửi lại
                }
            }
        };
    }

    // Phương thức để bắt đầu đếm ngược
    private void startCountdown() {
        countdownSeconds = 60; // Đặt lại thời gian đếm ngược
        tvResendOtp.setEnabled(false); // Vô hiệu hóa nút gửi lại trong khi đếm ngược
        handler.removeCallbacks(countdownRunnable); // Đảm bảo không có đếm ngược cũ đang chạy
        handler.post(countdownRunnable); // Bắt đầu đếm ngược
    }

    // Phương thức gửi mã OTP đến email
    private void sendOtpToEmail() {
        String email = etEmail.getText().toString().trim();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showSnackbar("Vui lòng nhập địa chỉ email hợp lệ.");
            return;
        }

        EmailRequest emailRequest = new EmailRequest(email);

        RetrofitClient.getAuthService(this).sendVerifyEmail(emailRequest).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        showSnackbar("Mã xác thực đã được gửi tới email của bạn.");
                        layoutOtpInput.setVisibility(View.VISIBLE); // Hiển thị trường OTP
                        btnSendOtp.setVisibility(View.GONE); // Ẩn nút gửi OTP ban đầu
                        tvResendOtp.setVisibility(View.VISIBLE); // Hiển thị nút gửi lại
                        startCountdown(); // Bắt đầu đếm ngược
                    } else {
                        showSnackbar("Không thể gửi mã xác thực: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Gửi yêu cầu OTP thất bại.";
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
                showSnackbar("Lỗi kết nối khi gửi OTP: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }

    // Phương thức thực hiện xác thực email
    private void performEmailVerification() {
        String email = etEmail.getText().toString().trim();
        String otp = etOtp.getText().toString().trim();

        if (email.isEmpty() || otp.isEmpty()) {
            showSnackbar("Vui lòng nhập email và mã OTP.");
            return;
        }

        VerifyEmailRequest verifyEmailRequest = new VerifyEmailRequest(email, otp);

        RetrofitClient.getAuthService(this).verifyEmail(verifyEmailRequest).enqueue(new Callback<ResponseMessage<String>>() {
            @Override
            public void onResponse(Call<ResponseMessage<String>> call, Response<ResponseMessage<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ResponseMessage<String> responseMessage = response.body();
                    if (responseMessage.isSuccess()) {
                        showSnackbar("Xác thực email thành công! Bạn có thể đăng nhập.");
                        // Dừng đếm ngược khi xác thực thành công
                        handler.removeCallbacks(countdownRunnable);
                        finish(); // Quay lại màn hình đăng nhập
                    } else {
                        showSnackbar("Xác thực thất bại: " + responseMessage.getMessage());
                    }
                } else {
                    String errorMessage = "Xác thực email thất bại. Vui lòng thử lại.";
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

    // Phương thức hiển thị Snackbar
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Ngừng đếm ngược khi Activity bị hủy để tránh rò rỉ bộ nhớ
        handler.removeCallbacks(countdownRunnable);
    }
}
