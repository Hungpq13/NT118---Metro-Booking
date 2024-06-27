package com.example.nt118project.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;

import com.example.nt118project.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Register extends AppCompatActivity {
    EditText txtEmail,txtUsername,txtPassword,txtConPassword;
    TextView loginRedirectText;
    Button btnSignUp;
    FirebaseDatabase database;
    DatabaseReference reference;
    SharedPreferenceHelper sharedPreferenceHelper;
    private EditText txtDob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        txtEmail = findViewById(R.id.txtEmail);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConPassword = findViewById(R.id.txtConPassword);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtDob = findViewById(R.id.txtDob);

        txtDob.setOnClickListener(view -> showDatePickerDialog());
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String name = txtUsername.getText().toString();
                   String email = txtEmail.getText().toString();
                   String password = txtPassword.getText().toString();
                   DataUser DataUser = new DataUser(name, email, password);

                   Toast.makeText(Register.this, "Bạn đã đăng ký thành công !", Toast.LENGTH_SHORT).show();

                   sharedPreferenceHelper.setUserName(name);
                   sharedPreferenceHelper.setUserEmail(email);
                   sharedPreferenceHelper.setUserPhone("092369462");

                   Authentication.signUpWithEmailPassword(email, password);

                   Intent intent = new Intent(Register.this, Login.class);
                   startActivity(intent);
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
}