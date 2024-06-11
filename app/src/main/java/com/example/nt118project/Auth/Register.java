package com.example.nt118project.Auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String name = txtUsername.getText().toString();
                   String email = txtEmail.getText().toString();
                   String password = txtPassword.getText().toString();
                   DataUser DataUser = new DataUser(name, email, password);

                   Toast.makeText(Register.this, "Bạn đã đăng ký thành công !", Toast.LENGTH_SHORT).show();

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
}