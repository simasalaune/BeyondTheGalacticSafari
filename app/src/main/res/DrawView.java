package com.example.sampletask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class DrawView extends View {
    private int figure;
    int n;

    public DrawView(Context context) {
        super(context);
    }

    public DrawView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DrawView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(context, attributeSet, defStyleAttr);
    }
    public void setFigure(int figure, int n)
    {
        this.figure = figure;
        this.n=n;
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
        Paint paint;

        paint = new Paint();
        Random rnd = new Random();


        for (int i=0; i<n; i++) {
            paint.setColor(Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(rnd.nextInt(15));
            float size = (float)rnd.nextInt(300);
            float randX = (float)rnd.nextInt(1000);
            float randY = (float)rnd.nextInt(1000);

            canvas.drawCircle(randX, randY, size, paint);
        }
    }
}
