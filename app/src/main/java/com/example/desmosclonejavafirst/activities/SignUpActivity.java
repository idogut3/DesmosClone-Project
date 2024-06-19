package com.example.desmosclonejavafirst.activities;


import static com.example.desmosclonejavafirst.validations.app_validations_for_permissions.AppPermissionsValidation.validateCameraAppPermission;
import static com.example.desmosclonejavafirst.validations.database_validations.DataBaseValidation.passedAllDataBaseValidations;
import static com.example.desmosclonejavafirst.validations.text_validations.TextValidation.passedAllTextValidationsForSignUp;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    private EditText firstNameET, lastNameET, usernameET, emailET, passwordET, confirmPasswordET;
    private ImageView imageProfilePicView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.firstNameET = findViewById(R.id.firstName);
        this.lastNameET = findViewById(R.id.lastName);
        this.usernameET = findViewById(R.id.username);
        this.emailET = findViewById(R.id.email);
        this.passwordET = findViewById(R.id.password);
        this.confirmPasswordET = findViewById(R.id.confirmPassword);
        this.imageProfilePicView = findViewById(R.id.imageProfilePicView);



        Button signupButton = findViewById(R.id.buttonSignUp);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        imageProfilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCameraAppPermission(SignUpActivity.this);
                pickImage();
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean passedAllValidations;

                LinkedList<CredentialAttribute> credentials = new LinkedList<>(Arrays.asList(new CredentialAttribute("First name", firstNameET), new CredentialAttribute("Last name", lastNameET), new CredentialAttribute("username", usernameET), new CredentialAttribute("email", emailET), new CredentialAttribute("password", passwordET), new CredentialAttribute("confirm password", confirmPasswordET)));

                String password = passwordET.getText().toString().trim();
                String confirmPassword = confirmPasswordET.getText().toString().trim();

                boolean passedAllTextValidations = passedAllTextValidationsForSignUp(credentials, SignUpActivity.this, password, confirmPassword);
                boolean passedAllDataBaseValidations = passedAllDataBaseValidations();

                passedAllValidations = passedAllTextValidations && passedAllDataBaseValidations;

                if (passedAllValidations) {
                    String firstName = firstNameET.getText().toString().trim();
                    String lastName = lastNameET.getText().toString().trim();
                    String username = usernameET.getText().toString().trim();
                    String email = emailET.getText().toString().trim();

                    User user = new User(username, password, firstName, lastName, email);
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
                } else {
                    Toast.makeText(SignUpActivity.this, "There is a problem with your input, can't sign up", Toast.LENGTH_SHORT).show();
                }
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

    private void uploadImageToDataBase(Uri file) {
        storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
        storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SignUpActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
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


                    databaseReference.child(userId).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                uploadImageToDataBase(imageUri);
                                iSignUpCallback.onSignUpSuccess();
                                Toast.makeText(context.getApplicationContext(), "You signed up successfully yay", Toast.LENGTH_SHORT).show();
                            } else {
                                iSignUpCallback.onSignUpFailure("Failed to save user data");
                            }
                        }
                    });
                } else {
                    iSignUpCallback.onSignUpFailure("Authentication failed");
                    Log.e("tag", "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }
}


