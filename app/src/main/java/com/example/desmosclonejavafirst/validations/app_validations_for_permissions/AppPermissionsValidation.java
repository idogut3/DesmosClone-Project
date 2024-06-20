package com.example.desmosclonejavafirst.validations.app_validations_for_permissions;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.desmosclonejavafirst.validations.Validation;


/**
 * AppPermissionsValidation provides methods to validate and request app permissions, specifically CAMERA and WRITE_EXTERNAL_STORAGE.
 */
public class AppPermissionsValidation extends Validation {


    /**
     * Validates whether the app has CAMERA and WRITE_EXTERNAL_STORAGE permissions.
     * If not, requests these permissions.
     *
     * @param context The context of the calling activity or application.
     */
    public static void validateCameraAppPermission(Context context) {
        if (!hasCameraPermission(context)) {
            requestCameraPermission((Activity) context);
        }
    }


    /**
     * Checks if the app has been granted permission to use the camera and write to external storage.
     *
     * @param context The context of the calling activity or application.
     * @return {@code true} if both CAMERA and WRITE_EXTERNAL_STORAGE permissions are granted, {@code false} otherwise.
     */
    public static boolean hasCameraPermission(Context context) {

        // Check if CAMERA permission is granted
        boolean cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        // Check if WRITE_EXTERNAL_STORAGE permission is granted
        boolean writeExternalStoragePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        // Return true if both permissions are granted, otherwise false

        return cameraPermission && writeExternalStoragePermission;
    }

    private static void requestCameraPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }
}
