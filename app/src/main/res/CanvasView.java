package com.example.myapplicationyyj;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CanvasView extends View {
    private List<Integer> stripeSizes;
    private List<Paint> stripePaints;

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);

        stripeSizes = new ArrayList<>();
        stripePaints = new ArrayList<>();

        // Initialize the paint objects for each color
        stripePaints.add(new Paint());
        stripePaints.add(new Paint());
        stripePaints.add(new Paint());

        stripePaints.get(0).setColor(Color.YELLOW);
        stripePaints.get(1).setColor(Color.GREEN);
        stripePaints.get(2).setColor(Color.RED);

        resetStripeSizes();
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        // Call changeStripeSizes() to initialize stripe sizes when the view size is determined
        changeStripeSizes();
    }
    private void resetStripeSizes() {
        stripeSizes.clear();
        stripeSizes.add(0);
        stripeSizes.add(0);
        stripeSizes.add(0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Calculate the size of each stripe
        int stripeHeight = height / 3;

        // Draw the flag stripes
        canvas.drawRect(0, 0, width, stripeSizes.get(0), stripePaints.get(0));
        canvas.drawRect(0, stripeSizes.get(0), width, stripeSizes.get(1), stripePaints.get(1));
        canvas.drawRect(0, stripeSizes.get(1), width, stripeSizes.get(2), stripePaints.get(2));

        invalidate(); // Redraw the view
    }



    public void changeStripeColors() {
        Random random = new Random();

        // Generate random colors for each stripe
        for (int i = 0; i < stripePaints.size(); i++) {
            int color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            stripePaints.get(i).setColor(color);
        }

        invalidate(); // Redraw the view
    }

    public void changeStripeSizes() {
        Random random = new Random();

        // Generate random sizes for each stripe
        int height = getHeight();
        int stripe1 = random.nextInt(height + 1);  // Add +1 to ensure positive bound
        int stripe2 = random.nextInt(height - stripe1 + 1);  // Add +1 to ensure positive bound
        int stripe3 = height - stripe1 - stripe2;

        stripeSizes.set(0, stripe1);
        stripeSizes.set(1, stripe1 + stripe2);
        stripeSizes.set(2, stripe1 + stripe2 + stripe3);

        invalidate(); // Redraw the view
    }

}
