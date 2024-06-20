package com.example.desmosclonejavafirst.plot_view.zoom_handler;

import android.view.ScaleGestureDetector;


/**
 * Custom ScaleGestureDetector.SimpleOnScaleGestureListener for handling pinch-to-zoom functionality.
 */
public class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private static final float MIN_SCALE_FACTOR = 0.1f;
    private static final float MAX_SCALE_FACTOR = 10.0f;

    private float scaleFactor = 1.0f;


    /**
     * Called when a scale gesture is detected.
     *
     * @param detector The ScaleGestureDetector that detected the gesture.
     * @return true if the event is consumed, else false.
     */
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

    /**
     * Retrieves the current scale factor.
     *
     * @return The current scale factor.
     */
    public float getScaleFactor() {
        return scaleFactor;
    }
}
