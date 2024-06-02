package com.example.desmosclonejavafirst.plot_view.zoom_handler;

import android.view.ScaleGestureDetector;


public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private static final float MIN_SCALE_FACTOR = 0.1f;
    private static final float MAX_SCALE_FACTOR = 10.0f;

    private float scaleFactor = 1.0f;

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        // Get the scale factor from the detector
        float scale = detector.getScaleFactor();

        // Apply the scale factor to the current scaleFactor
        scaleFactor *= scale;

        // Limit the scale factor to a reasonable range
        scaleFactor = Math.max(MIN_SCALE_FACTOR, Math.min(scaleFactor, MAX_SCALE_FACTOR));

        // Return true to indicate that the scale gesture has been handled
        return true;
    }

    // Get the current scale factor
    public float getScaleFactor() {
        return scaleFactor;
    }
}
