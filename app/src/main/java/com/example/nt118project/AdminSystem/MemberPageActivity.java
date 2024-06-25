package com.example.nt118project.AdminSystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.MainFunction.MapsActivity;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MemberPageActivity extends AppCompatActivity implements MemberAdapter.OnUserClickListener {

    private RecyclerView recyclerViewUsers;
    private MemberAdapter userAdapter;
    private List<Member> userList;
    private Button btnAddUser;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);

        back = findViewById(R.id.back);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userAdapter = new MemberAdapter(userList, this);
        recyclerViewUsers.setAdapter(userAdapter);

        // Lấy danh sách người dùng từ Firestore
        fetchUsersFromFirestore();

        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUserDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberPageActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchUsersFromFirestore() {
        firebaseFirestore.collection("Users").whereEqualTo("Role", "2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userList.clear();
                    for (DocumentSnapshot document : task.getResult()) {
                        userList.add(new Member(document.getId(), document.getString("Name"), document.getString("Email"), document.getString("Password")));
                    }
                    userAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MemberPageActivity.this, "Lỗi khi tải dữ liệu từ Firestore", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onUserClick(int position) {
        showEditUserDialog(position);
    }

    @Override
    public void onUserLongClick(int position) {
        showDeleteUserDialog(position);
    }

    private void showAddUserDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);

        EditText etUserName = view.findViewById(R.id.etUserName);
        EditText etUserEmail = view.findViewById(R.id.etUserEmail);
        EditText etUserPassword = view.findViewById(R.id.etUserPassword);

        builder.setView(view).setTitle("Thêm Người Dùng").setPositiveButton("Thêm", (dialog, which) -> {
            String name = etUserName.getText().toString().trim();
            String email = etUserEmail.getText().toString().trim();
            String password = etUserPassword.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty()) {
                addUserToFirestore(name, email, password);
            } else {
                Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void addUserToFirestore(String name, String email, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        data.put("Email", email);
        data.put("Password", password);
        data.put("Role", "2");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userUid = task.getResult().getUser().getUid();
                data.put("UserUid", userUid);

                firebaseFirestore.collection("Users").add(data).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(MemberPageActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                        userList.add(new Member(userUid, name, email, password));
                        userAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MemberPageActivity.this, "Lỗi khi thêm người dùng: " + task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(MemberPageActivity.this, "Lỗi khi đăng ký tài khoản: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void showEditUserDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_user, null);

        EditText etUserName = view.findViewById(R.id.etUserName);
        EditText etUserEmail = view.findViewById(R.id.etUserEmail);
        EditText etUserPassword = view.findViewById(R.id.etUserPassword);

        Member user = userList.get(position);
        etUserName.setText(user.getName());
        etUserEmail.setText(user.getEmail());
        etUserPassword.setText(user.getPassword());

        builder.setView(view).setTitle("Sửa Người Dùng").setPositiveButton("Lưu", (dialog, which) -> {
            String name = etUserName.getText().toString().trim();
            String email = etUserEmail.getText().toString().trim();
            String password = etUserPassword.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty()) {
                Member updatedUser = new Member(user.getId(), name, email, password);
                updateUserInFirestore(user.getPassword(), user.getEmail(), position, updatedUser);
            } else {
                Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void updateUserInFirestore(String oldPassword, String email, int position, Member updatedUser) {
        mAuth.signInWithEmailAndPassword(email, oldPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> reauthTask) {
                        if (reauthTask.isSuccessful()) {
                            // Update password
                            user.updatePassword(updatedUser.getPassword()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> passwordUpdateTask) {
                                    if (passwordUpdateTask.isSuccessful()) {

                                        Map<String, String> data = new HashMap<>();
                                        data.put("Name", updatedUser.getName());
                                        data.put("Email", email);
                                        data.put("Password", updatedUser.getPassword());

                                        firebaseFirestore.collection("Users").document(userList.get(position).getId()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> firestoreUpdateTask) {
                                                if (firestoreUpdateTask.isSuccessful()) {
                                                    mAuth.signOut();
                                                    Toast.makeText(MemberPageActivity.this, "Cập nhật người dùng thành công cho người dùng", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(MemberPageActivity.this, "Lỗi khi cập nhật người dùng trên Firestore", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(MemberPageActivity.this, "Lỗi khi cập nhật mật khẩu", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MemberPageActivity.this, "Sửa mật khẩu không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }


    private void showDeleteUserDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Người Dùng").setMessage("Bạn có chắc chắn muốn xóa người dùng này?").setPositiveButton("Xóa", (dialog, which) -> {
            deleteUserFromFirestore(position);
        }).setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void deleteUserFromFirestore(int position) {
        mAuth.signInWithEmailAndPassword(userList.get(position).getEmail(), userList.get(position).getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();
                AuthCredential credential = EmailAuthProvider.getCredential(userList.get(position).getEmail(), userList.get(position).getPassword());
                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> reauthTask) {
                        if (reauthTask.isSuccessful()) {
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> passwordUpdateTask) {
                                    if (passwordUpdateTask.isSuccessful()) {
                                        firebaseFirestore.collection("Users").document(userList.get(position).getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @SuppressLint("NotifyDataSetChanged")
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(MemberPageActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                                                    userList.remove(position);
                                                    userAdapter.notifyDataSetChanged();
                                                } else {
                                                    Toast.makeText(MemberPageActivity.this, "Lỗi khi xóa người dùng", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(MemberPageActivity.this, "Lỗi khi xoá người dùng", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(MemberPageActivity.this, "Xoá người dùng không thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
