package com.example.nt118project.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.nt118project.R;

public class Register extends AppCompatActivity {
    EditText txtEmail, txtUsername, txtPassword, txtConPassword, phoneTxt;
    TextView loginRedirectText;
    Button btnSignUp;
    Spinner spinnerGender;
    private EditText txtDob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConPassword = findViewById(R.id.txtConPassword);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtDob = findViewById(R.id.txtDob);
        spinnerGender = findViewById(R.id.spinnerGender);
        phoneTxt = findViewById(R.id.txtPhone);

        txtDob.setOnClickListener(view -> showDatePickerDialog());
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtUsername.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String conPassword = txtConPassword.getText().toString().trim();
                String dob = txtDob.getText().toString().trim();
                String gender = spinnerGender.getSelectedItem().toString();
                String phone = phoneTxt.getText().toString().trim();

                // Kiểm tra ràng buộc trường
                if (validateFields(name, email, password, conPassword, dob, phone)) {
                    // Kiểm tra tuổi người dùng có lớn hơn 2 tuổi không
                    if (isAgeValid(dob)) {
                        // Đăng ký người dùng
                        Authentication.signUpWithEmailPassword(name, email, password, dob, gender, phone);

                        Toast.makeText(Register.this, "Bạn đã đăng ký thành công !", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Register.this, Login.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Register.this, "Tuổi của người dùng phải lớn hơn 2 tuổi.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                Register.this,
                (view, year1, month1, dayOfMonth) -> txtDob.setText(dayOfMonth + "/" + (month1 + 1) + "/" + year1),
                year, month, day);

        datePickerDialog.show();
    }

    // Phương thức kiểm tra tuổi của người dùng
    private boolean isAgeValid(String dob) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date birthDate = sdf.parse(dob);
            Calendar dobCalendar = Calendar.getInstance();
            dobCalendar.setTime(birthDate);

            // Lấy ngày hiện tại
            Calendar today = Calendar.getInstance();

            // Tính tuổi
            int age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR);
            if (today.get(Calendar.MONTH) < dobCalendar.get(Calendar.MONTH) ||
                    (today.get(Calendar.MONTH) == dobCalendar.get(Calendar.MONTH) &&
                            today.get(Calendar.DAY_OF_MONTH) < dobCalendar.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }

            // Kiểm tra tuổi có lớn hơn 2 tuổi không
            return age > 2;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức kiểm tra và ràng buộc các trường nhập liệu
    private boolean validateFields(String name, String email, String password, String conPassword, String dob, String phone) {
        if (name.isEmpty()) {
            txtUsername.setError("Vui lòng nhập tên người dùng");
            txtUsername.requestFocus();
            return false;
        }

        if (email.isEmpty()) {
            txtEmail.setError("Vui lòng nhập địa chỉ email");
            txtEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Địa chỉ email không hợp lệ");
            txtEmail.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            txtPassword.setError("Vui lòng nhập mật khẩu");
            txtPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            txtPassword.setError("Mật khẩu phải có ít nhất 6 ký tự");
            txtPassword.requestFocus();
            return false;
        }

        if (!conPassword.equals(password)) {
            txtConPassword.setError("Mật khẩu xác nhận không khớp");
            txtConPassword.requestFocus();
            return false;
        }

        if (dob.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn ngày sinh", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phone.isEmpty()) {
            phoneTxt.setError("Vui lòng nhập số điện thoại");
            phoneTxt.requestFocus();
            return false;
        }

        if (phone.length() != 10 ) {
            phoneTxt.setError("Số điện thoại phải có 10");
            phoneTxt.requestFocus();
            return false;
        }

        return true;
    }
}
