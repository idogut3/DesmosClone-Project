package com.example.desmosclonejavafirst;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;

public class PlotView extends View {
    private Paint paint;
    private String function;

    public PlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF000000);
        paint.setStrokeWidth(2);
    }

    public void setFunction(String function) {
        this.function = function;
        invalidate(); // Redraw the view
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (function != null) {
            // Draw the polynomial function here
            drawFunction(canvas);
        }
    }

    private void drawFunction(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float[] points = new float[width * 4];
        for (int x = 0; x < width; x++) {
            double y = evaluatePolynomial(function, (x - width / 2) / 20.0);
            points[4 * x] = x;
            points[4 * x + 1] = height / 2;
            points[4 * x + 2] = x;
            points[4 * x + 3] = (float) (height / 2 - y * 20);
        }
        canvas.drawLines(points, paint);
    }

    private double evaluatePolynomial(String function, double x) {
        // This method needs to parse and evaluate the polynomial function
        // For simplicity, let's assume it's of the form "a*x^n + b*x^(n-1) + ... + c"
        // You might need to write a more robust parser here
        double result = 0.0;
        function = function.replace(" ", "");
        String[] terms = function.split("\\+");
        for (String term : terms) {
            String[] parts = term.split("\\*x\\^");
            if (parts.length == 2) {
                double coefficient = Double.parseDouble(parts[0]);
                int power = Integer.parseInt(parts[1]);
                result += coefficient * Math.pow(x, power);
            } else if (term.contains("x")) {
                double coefficient = Double.parseDouble(term.split("\\*x")[0]);
                result += coefficient * x;
            } else {
                result += Double.parseDouble(term);
            }
        }
        return result;
    }
}

