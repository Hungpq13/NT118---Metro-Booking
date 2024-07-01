package com.example.nt118project.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nt118project.AdminSystem.AdminActivity;
import com.example.nt118project.bottomnav.MenuActivity;
import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    EditText inputNumberPhone, inputPassword;
    Button btnSignIn;
    TextView TextSignUp;
    ImageView imgShowPass; // Thêm ImageView
    SharedPreferenceHelper sharedPreferences;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    String roleId;

    boolean isPasswordVisible = false; // Biến để kiểm tra trạng thái hiển thị mật khẩu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = new SharedPreferenceHelper(getApplicationContext());

        // Kiểm tra nếu đã đăng nhập trước đó
        if (sharedPreferences.getLogging()) {
            String roleId = sharedPreferences.getRoleID();
            if ("2".equals(roleId)) {
                Intent intent = new Intent(Login.this, MenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(Login.this, AdminActivity.class);
                startActivity(intent);
                finish();
            }
        }

        inputNumberPhone = findViewById(R.id.inputNumberPhone);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        TextSignUp = findViewById(R.id.textSignUp);
        imgShowPass = findViewById(R.id.imgShowPass); // Ánh xạ ImageView

        TextSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(v -> {
            Authentication.signInWithEmailPassword(inputNumberPhone.getText().toString(), inputPassword.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Boolean isSuccess = task.getResult();
                            if (isSuccess != null && isSuccess) {
                                Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                firebaseFirestore.collection("Users").whereEqualTo("UserId", mAuth.getUid()).get().addOnCompleteListener(task1 -> {
                                    for (DocumentSnapshot document : task1.getResult()) {
                                        sharedPreferences.setUserId(document.getString("UserId"));
                                        sharedPreferences.setUserName(document.getString("Name"));
                                        sharedPreferences.setUserEmail(document.getString("Email"));
                                        sharedPreferences.setUserPhone(document.getString("Phone"));
                                        sharedPreferences.setRoleID(document.getString("Role"));
                                        sharedPreferences.setLogging(true);
                                        Authorization.signInWithRole(document.getString("Role"), getApplicationContext());
                                    }
                                });
                                finish();
                            } else {
                                Log.v("Debug", "Đăng nhập thất bại");
                                Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.v("Debug", "Đăng nhập thất bại: " + task.getException().getMessage());
                            Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Xử lý sự kiện ImageView để hiển thị/ẩn mật khẩu
        imgShowPass.setOnClickListener(view -> {
            if (isPasswordVisible) {
                // Ẩn mật khẩu
                inputPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                imgShowPass.setImageResource(R.drawable.ic_eye_closed); // Đổi ảnh con mắt đóng
            } else {
                // Hiển thị mật khẩu
                inputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                imgShowPass.setImageResource(R.drawable.ic_eye_open); // Đổi ảnh con mắt mở
            }
            isPasswordVisible = !isPasswordVisible; // Đảo ngược trạng thái hiện tại
        });
    }
}
