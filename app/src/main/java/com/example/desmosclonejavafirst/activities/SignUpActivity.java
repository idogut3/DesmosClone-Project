package com.example.desmosclonejavafirst.activities;

//import static com.example.desmosclonejavafirst.security.HashingFunctions.encryptPasswordInSHA1;
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


/**
 * SignUpActivity handles user registration including capturing user details,
 * uploading a profile picture, and saving data to Firebase.
 */
public class SignUpActivity extends AppCompatActivity {


    // Constants
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String DEFAULT_PROFILE_PICTURE_URL = "https://example.com/default_profile_pic.png"; // Replace with your default image URL

    // Member variables
    private Uri imageUri; // URI of the selected profile image
    private String userUUID; // Unique user identifier (for his picture)

    // UI components

    private EditText firstNameET, lastNameET, usernameET, emailET, passwordET, confirmPasswordET;
    private ImageView imageProfilePicView;
    private FirebaseAuth mAuth;  // Firebase authentication instance
    private FirebaseDatabase database; // Firebase database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup); // Set the content view to the signup layout

        initUI(); // Initialize UI components
        initFirebase();  // Initialize Firebase components

        // Set up the image picker for profile picture selection

        imageProfilePicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateCameraAppPermission(SignUpActivity.this);
                pickImage(); // Launch the image picker
            }
        });

        // Set up the signup button with click listener
        Button signupButton = findViewById(R.id.buttonSignUp);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) { // Validate input fields
                    signUpUser(); // Proceed with signup if inputs are valid
                } else { //There is a problem with the input
                    Toast.makeText(SignUpActivity.this, "There is a problem with your input, can't sign up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Initialize UI components.
     */
    private void initUI() {
        firstNameET = findViewById(R.id.firstName);
        lastNameET = findViewById(R.id.lastName);
        usernameET = findViewById(R.id.username);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        confirmPasswordET = findViewById(R.id.confirmPassword);
        imageProfilePicView = findViewById(R.id.imageProfilePicView);
    }

    /**
     * Initialize Firebase components.
     */
    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    /**
     * Validate user inputs for signup.
     *
     * @return true if inputs are valid, false otherwise.
     */
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

    /**
     * Sign up a new user with the given details.
     */

    private void signUpUser() {
        String firstName = firstNameET.getText().toString().trim();
        String lastName = lastNameET.getText().toString().trim();
        String username = usernameET.getText().toString().trim();
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        userUUID = UUID.randomUUID().toString();  // Generate a unique user ID

//        String hashedPassword = encryptPasswordInSHA1(password);
        User user = new User(username, password, firstName, lastName, email, null);

        signUpANewUser(user, database, mAuth, SignUpActivity.this, new ISignUpCallback() {
            @Override
            public void onSignUpSuccess() {
                // Navigate to the main activity upon successful signup
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

    /**
     * Launches an image picker for selecting a profile picture.
     */
    public void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /**
     * Handle the result of the image picker activity.
     *
     * @param requestCode The request code identifying the request.
     * @param resultCode The result code indicating the success or failure of the request.
     * @param data The data returned by the image picker activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData(); // Get the selected image URI
            Glide.with(this).load(imageUri).into(imageProfilePicView);
        }
    }

    /**
     * Upload the selected image to Firebase Storage and save the URL to the database.
     *
     * @param file The URI of the file to upload.
     * @param randomUUID The unique ID for the user.
     * @param userId The Firebase user ID.
     */
    private void uploadImageToDatabaseAndSaveUrl(Uri file, String randomUUID, String userId) {
        if (file == null) {
            saveImageUrlToDatabase(userId, DEFAULT_PROFILE_PICTURE_URL);
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images").child(randomUUID);
        storageReference.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        if (uri != null) {
                            String imageUrl = uri.toString();
                            setImageUri(imageUri);
                            saveImageUrlToDatabase(userId, imageUrl); // Save the image URL to the database
                            Toast.makeText(SignUpActivity.this, "Image Uploaded and URL saved!", Toast.LENGTH_SHORT).show();
                        } else {
                            saveImageUrlToDatabase(userId, DEFAULT_PROFILE_PICTURE_URL);
                        }
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

    /**
     * Save the image URL to the user's profile in the database.
     *
     * @param userId The Firebase user ID.
     * @param imageUrl The URL of the uploaded image.
     */
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

    /**
     * Handle user sign up process, including Firebase authentication and data storage.
     *
     * @param user The user object containing user details.
     * @param database The Firebase database instance.
     * @param mAuth The Firebase authentication instance.
     * @param context The context in which the method is called.
     * @param iSignUpCallback The callback interface for handling sign up success or failure.
     */
    public void signUpANewUser(User user, FirebaseDatabase database, FirebaseAuth mAuth, Context context, ISignUpCallback iSignUpCallback) {
        String userEmail = user.getEmail();
        String userPassword = user.getPassword();

        mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                    DatabaseReference databaseReference = database.getReference().child("users");

                    if (imageUri == null) {
                        user.setImageUrl(DEFAULT_PROFILE_PICTURE_URL);
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
                        uploadImageToDatabaseAndSaveUrl(imageUri, userUUID, userId);
                        String imageUrl = getImageUrl();
                        user.setImageUrl(imageUrl);

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
                    }
                } else {
                    iSignUpCallback.onSignUpFailure("Authentication failed");
                    Log.e("SignUpActivity", "Authentication failed: " + Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });
    }

    /**
     * Get the URL of the selected image.
     *
     * @return The image URL.
     */
    public String getImageUrl() {
        return imageUri != null ? imageUri.toString() : DEFAULT_PROFILE_PICTURE_URL;
    }

    /**
     * Set the URI of the selected image.
     *
     * @param imageUri The URI of the selected image.
     */
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
