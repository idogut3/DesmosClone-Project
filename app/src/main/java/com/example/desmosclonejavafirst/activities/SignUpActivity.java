package com.example.desmosclonejavafirst.activities;

import static com.example.desmosclonejavafirst.validations.app_validations_for_permissions.AppPermissionsValidation.validateCameraAppPermission;
import static com.example.desmosclonejavafirst.validations.database_validations.DataBaseValidation.passedAllDataBaseValidations;
import static com.example.desmosclonejavafirst.validations.text_validations.TextValidation.passedAllTextValidationsForSignUp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.desmosclonejavafirst.R;
import com.example.desmosclonejavafirst.entities.User;
import com.example.desmosclonejavafirst.entities.entity_handling.ISignUpCallback;
import com.example.desmosclonejavafirst.validations.text_validations.CredentialAttribute;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;
    private String userUUID;

    private EditText firstNameET, lastNameET, usernameET, emailET, passwordET, confirmPasswordET;
    private ImageView imageProfilePicView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        initFirebase();

        imageProfilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCameraAppPermission(SignUpActivity.this);
                pickImage();
            }
        });

        Button signupButton = findViewById(R.id.buttonSignUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    signUpUser();
                } else {
                    Toast.makeText(SignUpActivity.this, "There is a problem with your input, can't sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initUI() {
        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        usernameET = findViewById(R.id.username);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        confirmPasswordET = findViewById(R.id.confirmPassword);
        imageProfilePicView = findViewById(R.id.imageProfilePicView);
    }

    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    private boolean validateInputs() {
        LinkedList<CredentialAttribute> credentials = new LinkedList<>(Arrays.asList(
                new CredentialAttribute("First name", firstNameET),
                new CredentialAttribute("Last name", lastNameET),
                new CredentialAttribute("Username", usernameET),
                new CredentialAttribute("Email", emailET),
                new CredentialAttribute("Password", passwordET),
                new CredentialAttribute("Confirm password", confirmPasswordET)
        ));

        String password = passwordET.getText().toString().trim();
        String confirmPassword = confirmPasswordET.getText().toString().trim();

        boolean textValidationPassed = passedAllTextValidationsForSignUp(credentials, SignUpActivity.this, password, confirmPassword);
        boolean dataBaseValidations = passedAllDataBaseValidations();

        return textValidationPassed && dataBaseValidations;
    }

    private void signUpUser() {
        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String username = usernameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        userUUID = UUID.randomUUID().toString();
        User user = new User(username, password, firstName, lastName, email, null);

        signUpANewUser(user, database, mAuth, SignUpActivity.this, new ISignUpCallback() {
            @Override
            public void onSignUpSuccess() {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSignUpFailure(String errorMessage) {
                Toast.makeText(SignUpActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Glide.with(this).load(imageUri).into(imageProfilePicView);
        }
    }

    private void uploadImageToDatabaseAndSaveUrl(Uri file, String randomUUID, String userId) {
        if (file == null) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images").child(randomUUID);
        storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        setImageUri(imageUri);
                        saveImageUrlToDatabase(userId, imageUrl);
                        Toast.makeText(SignUpActivity.this, "Image Uploaded and URL saved!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Image Upload Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveImageUrlToDatabase(String userId, String imageUrl) {
        DatabaseReference userRef = database.getReference().child("users").child(userId).child("imageUrl");
        userRef.setValue(imageUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (!task.isSuccessful()) {
                    Log.e("SignUpActivity", "Failed to save image URL to database: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    public void signUpANewUser(User user, FirebaseDatabase database, FirebaseAuth mAuth, Context context, ISignUpCallback iSignUpCallback) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DatabaseReference databaseReference = database.getReference().child("users");

                    uploadImageToDatabaseAndSaveUrl(imageUri, userUUID, userId);
                    user.setImageUrl(getImageUrl());

                    databaseReference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                iSignUpCallback.onSignUpSuccess();
                                Toast.makeText(context.getApplicationContext(), "You signed up successfully!", Toast.LENGTH_SHORT).show();
                            } else {
                                iSignUpCallback.onSignUpFailure("Failed to save user data");
                            }
                        }
                    });
                } else {
                    iSignUpCallback.onSignUpFailure("Authentication failed");
                    Log.e("SignUpActivity", "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    public String getImageUrl() {
        return imageUri.toString();
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
