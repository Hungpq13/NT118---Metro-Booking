package com.example.nt118project.Personal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nt118project.Auth.SharedPreferenceHelper;
import com.example.nt118project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class information_personal extends AppCompatActivity {

    Button btnPickImage, btnSave;
    ImageView avatarImageView;
    ActivityResultLauncher<Intent> resultLauncher;
    private EditText name, genderEditText, birthdateEditText;
    private Spinner genderSpinner;
    private ImageView editIconHo, editIconGender, editIconBirthdate;
    private boolean isEditingGender = false;
    private boolean isEditing = false;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    SharedPreferenceHelper sharedPreferenceHelper;
    String docId;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        sharedPreferenceHelper = new SharedPreferenceHelper(getApplicationContext());
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
        EditText phoneText = findViewById(R.id.phoneEdt);
        EditText emailText = findViewById(R.id.emailEdt);

        // Khởi tạo Firebase Storage
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        firebaseFirestore.collection("Users").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        name.setText(document.getString("Name"));
                        genderEditText.setText(document.getString("sex"));
                        birthdateEditText.setText(document.getString("DoB"));
                        phoneText.setText(document.getString("Phone"));
                        emailText.setText(document.getString("Email"));
                        docId = document.getId();
                    }
                } else {
                    Log.d("TAG", "onComplete: " + task.getException());
                }
            }
        });

        firebaseFirestore.collection("Users").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        String imageUrl = document.getString("ImageURL");
                        ImageView avatarImageView = findViewById(R.id.avatarImageView);
                        Picasso.get().load(imageUrl).into(avatarImageView);
                    }
                }
            }
        });

        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        // Set up the gender spinner with custom layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        genderSpinner.setAdapter(adapter);

        registerResult();

        editIconHo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditing) {
                    name.setEnabled(true);
                    name.requestFocus();
                    editIconHo.setImageResource(R.drawable.iconsave);
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

    private void registerResult() {
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri imageUri = result.getData().getData();
                    avatarImageView.setImageURI(imageUri);
                    uploadImageToFirebase(imageUri);  // Upload image to Firebase Storage
                } else {
                    Toast.makeText(information_personal.this, "Chưa có hình ảnh nào được chọn", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }

    private void uploadImageToFirebase(Uri imageUri) {
        String fileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference fileReference = storageReference.child("avatars/" + fileName);

        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        HashMap<String, Object> data = new HashMap<>();
                        data.put("ImageURL", imageUrl);

                        firebaseFirestore.collection("Users").whereEqualTo("UserId", sharedPreferenceHelper.getUserId()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot document : task.getResult()) {
                                        String docId = document.getId();
                                        firebaseFirestore.collection("Users").document(docId).update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(information_personal.this, "Upload successful", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(information_personal.this, "Upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(information_personal.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        }, year, month, day);
        datePickerDialog.show();
    }

    private void saveChanges() {
        name.setEnabled(false);
        editIconHo.setImageResource(R.drawable.iconedit);
        isEditing = false;
        Toast.makeText(this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
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
        getSharedPreferences("user_info", MODE_PRIVATE).edit().putString("name", nameValue).putString("gender", genderValue).putString("birthdate", birthdateValue).apply();

        Map<String, Object> userUpdates = new HashMap<>();
        userUpdates.put("Name", nameValue);
        userUpdates.put("sex", genderValue);
        userUpdates.put("DoB", birthdateValue);

        firebaseFirestore.collection("Users").document(docId).update(userUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    sharedPreferenceHelper.setUserName(nameValue);
                    Toast.makeText(getApplicationContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Xử lý lỗi nếu cần
                }
            }
        });

        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
    }
}