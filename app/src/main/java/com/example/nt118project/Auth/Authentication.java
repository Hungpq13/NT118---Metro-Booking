package com.example.nt118project.Auth;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Authentication {

    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private SharedPreferenceHelper sharedPreference;

    static public void signUpWithEmailPassword(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Authentication", "signInWithEmail:success");

                    Map<String, Object> data = new HashMap<>();
                    data.put("UserId", mAuth.getCurrentUser().getUid());
                    mFirestore.collection("Users").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Log.d("Authentication", "signInWithEmail:successStoreUserID");
                            } else {
                                Log.w("Authentication", "signInWithEmail:failureStoreUserID", task.getException());
                            }
                        }
                    });
                } else {
                    Log.w("Authentication", "signInWithEmail:failure", task.getException());
                }
            }
        });
    }

    static public Task<Boolean> signInWithEmailPassword(String email, String password) {
        TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Authentication", "signInWithEmail:successSignIn");

                    taskCompletionSource.setResult(true);
                } else {
                    Log.w("Authentication", "signInWithEmail:failureSignIn", task.getException());
                    taskCompletionSource.setResult(false);
                }
            }
        });

        return taskCompletionSource.getTask();
    }
}
