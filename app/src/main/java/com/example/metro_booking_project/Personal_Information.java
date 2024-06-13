package com.example.metro_booking_project;
import java.util.Calendar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.DatePicker;
import android.app.DatePickerDialog;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.content.Intent;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import android.net.Uri;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.ActivityResult;








public class Personal_Information extends AppCompatActivity {

    Button btnPickImage;
    ImageView avatarImageView;

    ImageView btnPickDate;

    ActivityResultLauncher<Intent> resultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

    });
        btnPickImage = findViewById(R.id.btnPickImage);
        avatarImageView = findViewById(R.id.avatarImageView);
        registerResult();
        btnPickImage.setOnClickListener(view -> pickImage());
        btnPickDate =findViewById(R.id.btnPickDate);
        setupClickListener();

    }
    private void setupClickListener() {
        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                        // Process the selected date (e.g., display it)
                        String selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        Toast.makeText(Personal_Information.this, "" + selectedDate, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }
    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            Uri imageUri = result.getData().getData();
                            avatarImageView.setImageURI(imageUri);
                        }catch (Exception e) {
                            Toast.makeText(Personal_Information.this,"No Image Selected",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }




}