package com.example.desmosclonejavafirst.plot_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.desmosclonejavafirst.plot_view.zoom_handler.ScaleListener;


/**
 * Custom view for plotting polynomial functions and enabling pinch-to-zoom functionality.
 */
public class PlotView extends View {
    private Paint paint;
    private String function;

    // Variables for pinch-to-zoom
    private ScaleGestureDetector scaleGestureDetector;
    private ScaleListener scaleListener;

    private PointF pivotPoint;  // To store the pivot point for scaling

    private boolean hasErrorShown = false;


    /**
     * Constructor for PlotView.
     *
     * @param context Context of the view.
     * @param attrs   Attributes set from XML.
     */
    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes the view by setting up paint, scale gesture detector, and pivot point.
     */
    private void init() {
        paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setStrokeWidth(2);
        scaleListener = new ScaleListener();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), scaleListener);
        pivotPoint = new PointF();
    }

    /**
     * Sets the polynomial function to be plotted and invalidates the view to trigger redraw.
     *
     * @param function Polynomial function in string format.
     */
    public void setFunction(String function) {
        this.function = function;
        hasErrorShown = false; // Reset the error flag when a new function is set
        invalidate(); // Redraw the view
    }

    /**
     * Draws the polynomial function on the canvas.
     *
     * @param canvas Canvas on which the function is drawn.
     */
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // Apply zoom transformation
        canvas.save();
        canvas.scale(scaleListener.getScaleFactor(), scaleListener.getScaleFactor(), pivotPoint.x, pivotPoint.y);

        // Draw axes and function
        drawAxes(canvas);
        if (function != null && !function.isEmpty()) {
            // Draw the polynomial function here
            try {
                //  Block of code to try
                drawFunction(canvas);
                hasErrorShown = false;
            } catch (Exception e) {
                // Handle function parsing error
                if (!hasErrorShown) {
                    Toast.makeText(getContext(), "Please enter your polynomial by the form ax^n + bx^(n-1) ...", Toast.LENGTH_SHORT).show();
                    hasErrorShown = true;
                }
            }


        }
        canvas.restore();
    }

    /**
     * Draws the polynomial function on the canvas.
     *
     * @param canvas Canvas on which the function is drawn.
     */
    private void drawFunction(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        // Center of the canvas
        int centerX = width / 2;
        int centerY = height / 2;

        // List to store points
        float[] points = new float[(width + 1) * 4];
        int pointIndex = 0;

        // Adjust the scale to fit the function in the view
        double scaleX = 0.05; // Scale for x-axis
        double scaleY = 0.05; // Scale for y-axis

        for (int i = 0; i < width; i++) {
            // Calculating what is the x point we want to calculate now
            // (taking into consideration the size of the View element in the activity's xml)
            double x = (i - centerX) * scaleX;
            double y = evaluatePolynomial(function, x);

            // Setting in the array of points for each x point in the interval his place on the View element in arr[i]
            // And in arr[i+1] his y place according to the correct place in the view element
            points[pointIndex++] = i;
            points[pointIndex++] = (float) (centerY - y / scaleY);

            if (pointIndex >= points.length) break;
        }

        paint.setColor(0xFF0000FF); // Blue color for the function

        // Drawing the function
        for (int i = 0; i < pointIndex - 2; i += 2) {
            canvas.drawLine(points[i], points[i + 1], points[i + 2], points[i + 3], paint);
        }
    }

    /**
     * Draws X and Y axes on the canvas.
     *
     * @param canvas Canvas on which the axes are drawn.
     */
    private void drawAxes(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();

        paint.setColor(0xFF888888); // Gray color for the axes

        // Draw X-axis (horizontal line)
        canvas.drawLine(0, height / 2, width, height / 2, paint);

        // Draw Y-axis (vertical line)
        canvas.drawLine(width / 2, 0, width / 2, height, paint);
    }

    /**
     * Evaluates the polynomial function at a given x value.
     *
     * @param function Polynomial function in string format.
     * @param x        Value at which the function is evaluated.
     * @return Result of the function evaluation at x.
     */

    private static double evaluatePolynomial(String function, double x) {
        double result = 0.0;
        function = function.replace(" ", "").replace("-", "+-");

        // Split the function into terms
        String[] terms = function.split("\\+");

        for (String term : terms) {
            if (term.isEmpty()) continue;

            double coefficient = 1.0;
            int power = 0;

            if (term.contains("x^")) {
                // Split the term into coefficient and power parts
                String[] parts = term.split("x\\^");
                if (!parts[0].isEmpty() && !parts[0].equals("-")) {
                    coefficient = Double.parseDouble(parts[0]);
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                }
                power = Integer.parseInt(parts[1]);
            } else if (term.contains("x")) {
                // Split the term into coefficient part
                String[] parts = term.split("x");
                if (!parts[0].isEmpty() && !parts[0].equals("-")) {
                    coefficient = Double.parseDouble(parts[0]);
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                }
                power = 1;
            } else {
                // Term is a constant
                coefficient = Double.parseDouble(term);
            }

            result += coefficient * Math.pow(x, power);
        }
        return result;
    }


    /**
     * Handles touch events on the view for pinch-to-zoom functionality.
     *
     * @param event The motion event.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Update the pivot point for scaling
        pivotPoint.set(event.getX(), event.getY());
        // Let the ScaleGestureDetector handle all the touch events
        scaleGestureDetector.onTouchEvent(event);
        invalidate(); // Redraw the view to reflect scale changes
        return true;
    }


}
