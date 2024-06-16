package com.example.nt118project.AdminSystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.MainFunction.MapsActivity;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;

import java.util.ArrayList;
import java.util.List;

public class MemberPageActivity extends AppCompatActivity implements MemberAdapter.OnUserClickListener {

    private RecyclerView recyclerViewUsers;
    private MemberAdapter userAdapter;
    private List<Member> userList;
    private Button btnAddUser;
    ImageView back ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_page);

        back = findViewById(R.id.back);
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        userList = new ArrayList<>();
        userList.add(new Member("Nguyen Van A", "nguyenvana@example.com","123456"));
        userList.add(new Member("Le Thi B", "lethib@example.com", "123"));
        userList.add(new Member("Tran Van C", "tranvanc@example.com", "1"));

        userAdapter = new MemberAdapter(userList, (MemberAdapter.OnUserClickListener) this);
        recyclerViewUsers.setAdapter(userAdapter);

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

        builder.setView(view)
                .setTitle("Thêm Người Dùng")
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = etUserName.getText().toString().trim();
                    String email = etUserEmail.getText().toString().trim();
                    String password = etUserPassword.getText().toString().trim();
                    if (!name.isEmpty() && !email.isEmpty()) {
                        Member newUser = new Member(name, email , password);
                        userAdapter.addUser(newUser);
                    } else {
                        Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
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

        builder.setView(view)
                .setTitle("Sửa Người Dùng")
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String name = etUserName.getText().toString().trim();
                    String email = etUserEmail.getText().toString().trim();
                    String password = etUserPassword.getText().toString().trim();
                    if (!name.isEmpty() && !email.isEmpty()) {
                        Member updatedUser = new Member(name, email, password);
                        userAdapter.updateUser(position, updatedUser);
                    } else {
                        Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void showDeleteUserDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Người Dùng")
                .setMessage("Bạn có chắc chắn muốn xóa người dùng này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    userAdapter.removeUser(position);
                })
                .setNegativeButton("Hủy", null);

        builder.create().show();
    }
}
