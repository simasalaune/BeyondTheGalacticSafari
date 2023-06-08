package com.example.myapplication;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

public class CircleView extends View {
    private int circleSize;
    private int borderColor;
    private int fillColor;
    private Random random;

    public CircleView(Context context, Random random) {
        super(context);
        this.random = random;
    }

    public void setCircleSize(int circleSize) {
        this.circleSize = circleSize;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int viewWidth = getWidth();
        int viewHeight = getHeight();

        if (viewWidth <= 0 || viewHeight <= 0) {
            return; // Exit early if the view dimensions are invalid
        }

        int centerX = random.nextInt(viewWidth - circleSize);
        int centerY = random.nextInt(viewHeight - circleSize);
        int radius = circleSize / 2;

        Paint borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(borderColor);
        borderPaint.setStrokeWidth(5);

        Paint fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(fillColor);

        canvas.drawCircle(centerX + radius, centerY + radius, radius, fillPaint);
        canvas.drawCircle(centerX + radius, centerY + radius, radius, borderPaint);
    }

}
