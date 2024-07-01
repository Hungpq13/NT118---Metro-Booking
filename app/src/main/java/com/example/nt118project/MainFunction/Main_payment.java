package com.example.nt118project.MainFunction;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.example.nt118project.bottomnav.MenuActivity;
import com.example.nt118project.Personal.BookedHistory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main_payment extends AppCompatActivity {
    private Spinner Loaive;
    private Spinner sThanhtoan;
    private TextView Giatien;
    private Button paymentBtn;
    private ImageView back;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    private static final String CHANNEL_ID = "your_channel_id";
    private static int notificationId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paymentt);
        sharedPreferenceHelper = new SharedPreferenceHelper(this);

        EditText emailEdt = findViewById(R.id.hienEmail);
        EditText nameEdt = findViewById(R.id.hienName);
        EditText phoneEdt = findViewById(R.id.hienSDT);
        paymentBtn = findViewById(R.id.btn_payment);

        emailEdt.setText(sharedPreferenceHelper.getUserEmail());
        nameEdt.setText(sharedPreferenceHelper.getUserName());
        phoneEdt.setText(sharedPreferenceHelper.getUserPhone());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }

        Loaive = findViewById(R.id.sLoaive);
        sThanhtoan = findViewById(R.id.sThanhtoan);
        Giatien = findViewById(R.id.Giatien);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_payment.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        ArrayList<String> arrayLoaiVe = new ArrayList<>();
        arrayLoaiVe.add("Chọn loại vé");
        arrayLoaiVe.add("Vé đi");
        arrayLoaiVe.add("Vé về");
        arrayLoaiVe.add("Vé 2 chiều");

        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod("Thanh toán momo", R.drawable.momo)); // replace with actual icon
        paymentMethods.add(new PaymentMethod("Thanh toán ngân hàng", R.drawable.iconwallet)); // replace with actual icon

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayLoaiVe);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Loaive.setAdapter(arrayAdapter);

        CustomSpinnerAdapter paymentAdapter = new CustomSpinnerAdapter(this, paymentMethods);
        sThanhtoan.setAdapter(paymentAdapter);

        Loaive.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);
                if (selectedItem.equals("Vé đi") || selectedItem.equals("Vé về")) {
                    Giatien.setText("20.000 VND");
                } else if (selectedItem.equals("Vé 2 chiều")) {
                    Giatien.setText("40.000 VND");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ticketType = Loaive.getSelectedItem().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String date = dateFormat.format(new Date());

                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String time = timeFormat.format(new Date());

                if (ticketType.equals("Chọn loại vé")) {
                    Toast.makeText(Main_payment.this, "Vui lòng chọn loại vé", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, Object> data = new HashMap<>();
                data.put("UserId", sharedPreferenceHelper.getUserId());
                data.put("buyerName", sharedPreferenceHelper.getUserName());
                data.put("date", date);
                data.put("time", time);
                data.put("turn", ticketType);

                firebaseFirestore.collection("Ticket").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Main_payment.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            showToastWithDelay("Bạn có 1 thông báo mới", 2000); // Hiển thị sau 2 giây


                            // Lưu thông báo vào Notifications collection trên Firestore
                            String notificationTitle = "Bạn đã mua vé thành công";
                            String notificationMessage = "Click vào để xem lịch sử đặt vé";
                            saveNotificationToFirestore(notificationTitle, notificationMessage, date, time, sharedPreferenceHelper.getUserId());

                            // Hiển thị thông báo
                            createAndShowNotification(notificationTitle, notificationMessage);

                            finish();
                        } else {
                            Toast.makeText(Main_payment.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "Your Channel Name";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, channelName, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void createAndShowNotification(String title, String message) {
        // Tạo intent để mở BookedHistoryActivity
        Intent intent = new Intent(this, BookedHistory.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Tạo notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.iconbell)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent) // Đặt PendingIntent vào notification
                .setAutoCancel(true); // Tự động huỷ notification khi click vào

        // Hiển thị head-up notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());
    }

    private void saveNotificationToFirestore(String title, String message, String date, String time, String userId) {
        Map<String, Object> notification = new HashMap<>();
        notification.put("Title", title);
        notification.put("Message", message);
        notification.put("Date", date);
        notification.put("Time", time);
        notification.put("UserId", userId);

        firebaseFirestore.collection("Notifications").add(notification)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            // Successfully saved notification to Firestore
                            String documentId = task.getResult().getId();
                            // You can handle additional logic here if needed
                        } else {
                            // Failed to save notification to Firestore
                            Toast.makeText(Main_payment.this, "Lỗi khi lưu thông báo vào Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void showToastWithDelay(String message, int delay) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(Main_payment.this, message, Toast.LENGTH_SHORT).show();
            }
        }, delay);
    }
}
