package com.example.nt118project.AdminSystem;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

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
                        userList.add(new Member(
                                document.getId(),
                                document.getString("Name"),
                                document.getString("Email"),
                                document.getString("Phone"),
                                document.getString("DoB"),
                                document.getString("Sex"),
                                document.getString("Password")
                        ));
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
        EditText txtDoB = view.findViewById(R.id.txtDob);
        Spinner sexSpinner = view.findViewById(R.id.spinnerGender);
        EditText etUserPhone = view.findViewById(R.id.etUserPhone);

        txtDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtDoB);
            }
        });

        builder.setView(view).setTitle("Thêm Người Dùng").setPositiveButton("Thêm", (dialog, which) -> {
            String id = UUID.randomUUID().toString();
            String name = etUserName.getText().toString().trim();
            String email = etUserEmail.getText().toString().trim();
            String DoB = txtDoB.getText().toString().trim();
            String sex = sexSpinner.getSelectedItem().toString().trim();
            String password = etUserPassword.getText().toString().trim();
            String phone = etUserPhone.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty() && !DoB.isEmpty() && !sex.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                addUserToFirestore(id , name, email, phone, DoB, sex, password);
            } else {
                Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void addUserToFirestore(String userid , String name, String email, String phone, String DoB, String sex, String password) {
        Map<String, Object> data = new HashMap<>();

        data.put("Name", name);
        data.put("Email", email);
        data.put("Phone", phone);
        data.put("DoB", DoB);
        data.put("Sex", sex);
        data.put("Password", password);
        data.put("Role", "2");

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userUid = task.getResult().getUser().getUid();
                data.put("UserId", userUid);

                firebaseFirestore.collection("Users").add(data).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        Toast.makeText(MemberPageActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                        userList.add(new Member(userUid, name, email, phone, DoB, sex, password));
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
        EditText txtDoB = view.findViewById(R.id.txtDob);
        Spinner sexSpinner = view.findViewById(R.id.spinnerGender);
        EditText etUserPhone = view.findViewById(R.id.etUserPhone);
        TextView etUserId = view.findViewById(R.id.etUserId); // ID field

        Member user = userList.get(position);
        etUserName.setText(user.getName());
        etUserEmail.setText(user.getEmail());
        etUserPassword.setText(user.getPassword());
        txtDoB.setText(user.getDoB());
        etUserPhone.setText(user.getPhone());
        etUserId.setText(user.getId()); // Set the ID field as non-editable

        // Set ID field to be non-editable
        etUserId.setEnabled(false);

        String sex = user.getSex();
        if (sex != null && !sex.isEmpty()) {
            int sexPosition = sex.equalsIgnoreCase("Nam") ? 0 : 1;
            sexSpinner.setSelection(sexPosition);
        }

        txtDoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(txtDoB);
            }
        });

        builder.setView(view).setTitle("Sửa Người Dùng").setPositiveButton("Lưu", (dialog, which) -> {
            String name = etUserName.getText().toString().trim();
            String email = etUserEmail.getText().toString().trim();
            String DoB = txtDoB.getText().toString().trim();
            String selectedSex = sexSpinner.getSelectedItem().toString().trim();
            String password = etUserPassword.getText().toString().trim();
            String phone = etUserPhone.getText().toString().trim();
            if (!name.isEmpty() && !email.isEmpty() && !DoB.isEmpty() && !selectedSex.isEmpty() && !password.isEmpty() && !phone.isEmpty()) {
                updateUserInFirestore(user.getId(), name, email, phone, DoB, selectedSex, password);
            } else {
                Toast.makeText(MemberPageActivity.this, "Vui lòng nhập đủ thông tin.", Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("Hủy", null);

        builder.create().show();
    }

    private void updateUserInFirestore(String userId, String name, String email, String phone, String DoB, String sex, String password) {
        Map<String, Object> data = new HashMap<>();
        data.put("UserId", userId);
        data.put("Name", name);
        data.put("Email", email);
        data.put("Phone", phone);
        data.put("DoB", DoB);
        data.put("Sex", sex);
        data.put("Password", password);

        firebaseFirestore.collection("Users").document(userId).update(data).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MemberPageActivity.this, "Cập nhật thông tin người dùng thành công", Toast.LENGTH_SHORT).show();
                fetchUsersFromFirestore();
            } else {
                Toast.makeText(MemberPageActivity.this, "Lỗi khi cập nhật thông tin người dùng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteUserDialog(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa Người Dùng").setMessage("Bạn có chắc chắn muốn xóa người dùng này không?")
                .setPositiveButton("Xóa", (dialog, which) -> deleteUserFromFirestore(userList.get(position).getId()))
                .setNegativeButton("Hủy", null).create().show();
    }

    private void deleteUserFromFirestore(String userId) {
        firebaseFirestore.collection("Users").document(userId).delete().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(MemberPageActivity.this, "Xóa người dùng thành công", Toast.LENGTH_SHORT).show();
                fetchUsersFromFirestore();
            } else {
                Toast.makeText(MemberPageActivity.this, "Lỗi khi xóa người dùng: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog(EditText txtDoB) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Calculate the minimum date (2 years before the current date)
        final Calendar minCalendar = Calendar.getInstance();
        minCalendar.set(Calendar.YEAR, year - 2);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MemberPageActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    calendar.set(year1, monthOfYear, dayOfMonth);
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    txtDoB.setText(sdf.format(calendar.getTime()));
                }, year, month, day);

        // Set the minimum date
        datePickerDialog.getDatePicker().setMaxDate(minCalendar.getTimeInMillis());

        datePickerDialog.show();
    }

}
