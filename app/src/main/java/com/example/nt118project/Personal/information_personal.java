package com.example.nt118project.Personal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.R;
import com.example.nt118project.bottomnav.PersonalFragment;

import java.util.Calendar;

public class information_personal extends AppCompatActivity {

    Button btnPickImage , btnSave ;
    ImageView avatarImageView;
    ActivityResultLauncher<Intent> resultLauncher;
    private EditText name, genderEditText, birthdateEditText;
    private Spinner genderSpinner;
    private ImageView editIconHo, editIconGender, editIconBirthdate;
    private boolean isEditingGender = false;
    private boolean isEditing = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_information_personal);
        btnSave = findViewById(R.id.btnSave);
        btnPickImage = findViewById(R.id.btnPickImage);
        avatarImageView = findViewById(R.id.avatarImageView);
        editIconHo = findViewById(R.id.editIconHo);
        name = findViewById(R.id.name);
        genderEditText = findViewById(R.id.genderEditText);
        genderSpinner = findViewById(R.id.genderSpinner);
        editIconGender = findViewById(R.id.editIconGender);
        birthdateEditText = findViewById(R.id.birthdateEditText);
        editIconBirthdate = findViewById(R.id.editIconBirthdate);

        // Set up the gender spinner with custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        genderSpinner.setAdapter(adapter);

        registerResult();

        btnPickImage.setOnClickListener(view -> pickImage());

        editIconHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditing) {
                    name.setEnabled(true);
                    name.requestFocus();
                    editIconHo.setImageResource(R.drawable.iconsave); // Change to save icon
                    isEditing = true;
                } else {
                    saveChanges();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesAll();
                startActivity(new Intent(information_personal.this, PersonalFragment.class));
            }
        });

        editIconGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditingGender) {
                    genderEditText.setVisibility(View.GONE);
                    genderSpinner.setVisibility(View.VISIBLE);
                    editIconGender.setImageResource(R.drawable.iconsave); // Change to save icon
                    isEditingGender = true;
                } else {
                    saveGender();
                }
            }
        });

        editIconBirthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
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
                        } catch (Exception e) {
                            Toast.makeText(information_personal.this, "Chưa có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void saveGender() {
        String selectedGender = genderSpinner.getSelectedItem().toString();
        genderEditText.setText(selectedGender);
        genderEditText.setVisibility(View.VISIBLE);
        genderSpinner.setVisibility(View.GONE);
        editIconGender.setImageResource(R.drawable.iconedit); // Change back to edit icon
        isEditingGender = false;
        Toast.makeText(this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
        // Save the changes to your database or shared preferences
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        Calendar currentDate = Calendar.getInstance();
                        int age = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR);

                        if (currentDate.get(Calendar.DAY_OF_YEAR) < selectedDate.get(Calendar.DAY_OF_YEAR)) {
                            age--;
                        }

                        if (age >= 2) {
                            String dateString = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                            birthdateEditText.setText(dateString);
                            Toast.makeText(information_personal.this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                            // Save the changes to your database or shared preferences
                        } else {
                            Toast.makeText(information_personal.this, "Tuổi của bạn phải lớn hơn 2", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void saveChanges() {
        name.setEnabled(false);
        editIconHo.setImageResource(R.drawable.iconedit); // Change back to edit icon
        isEditing = false;
        Toast.makeText(this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
        // Save the changes to your database or shared preferences
    }
    private void saveChangesAll() {
        // Save name
        name.setEnabled(false);
        String nameValue = name.getText().toString();

        // Save gender
        String genderValue = genderEditText.getText().toString();

        // Save birthdate
        String birthdateValue = birthdateEditText.getText().toString();

        // Save these values to database or shared preferences
        // Example: Save to shared preferences
        getSharedPreferences("user_info", MODE_PRIVATE)
                .edit()
                .putString("name", nameValue)
                .putString("gender", genderValue)
                .putString("birthdate", birthdateValue)
                .apply();
        Toast.makeText(this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
    }

}
