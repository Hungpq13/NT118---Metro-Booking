package com.example.nt118project.Auth;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.nt118project.bottomnav.MenuActivity;
import com.example.nt118project.R;

public class Login extends AppCompatActivity {
    EditText inputNumberPhone, inputPassword;
    Button btnSignIn;
    TextView TextSignUp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputNumberPhone = findViewById(R.id.inputNumberPhone);
        inputPassword = findViewById(R.id.inputPassword);
        btnSignIn = findViewById(R.id.btnSignIn);
        TextSignUp = findViewById(R.id.textSignUp);


        TextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view)
            { Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }});
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, MenuActivity.class );
                startActivity(intent);
            }
        });
//          btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!validateUsername() | !validatePassword()) {
//                } else {
//                    checkUser();
//                }
//            }
//        });
//
//    }
//    public Boolean validateUsername() {
//        String val =  inputNumberPhone.getText().toString();
//        if (val.isEmpty()) {
//            inputNumberPhone.setError("Số điện thoại không được bỏ trống");
//            return false;
//        } else {
//            inputNumberPhone.setError(null);
//            return true;
//        }
//    }
//    public Boolean validatePassword(){
//        String val =  inputPassword.getText().toString();
//        if (val.isEmpty()) {
//            inputPassword.setError("Mật khẩu không được bỏ trống");
//            return false;
//        } else {
//            inputPassword.setError(null);
//            return true;
//        }
//    }
//    public void checkUser(){
//        String userUsername =   inputNumberPhone.getText().toString().trim();
//        String userPassword =   inputNumberPhone.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
//        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);
//        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    inputNumberPhone.setError(null);
//                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);
//                    if (passwordFromDB.equals(userPassword)) {
//                        inputNumberPhone.setError(null);
//                        String nameFromDB = snapshot.child(userUsername).child("name").getValue(String.class);
//                        String phoneFromDB = snapshot.child(userUsername).child("phone").getValue(String.class);
//                        Intent intent = new Intent(Login.this, AdminActivity.class);
//                        intent.putExtra("name", nameFromDB);
//                        intent.putExtra("phone", phoneFromDB);
//                        intent.putExtra("password", passwordFromDB);
//                        startActivity(intent);
//                    } else {
//                        inputPassword.setError("Mật khẩu không hợp lệ");
//                        inputPassword.requestFocus();
//                    }
//                } else {
//                    inputNumberPhone.setError("Tài khoản không tồn tại");
//                    inputNumberPhone.requestFocus();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
    }
}