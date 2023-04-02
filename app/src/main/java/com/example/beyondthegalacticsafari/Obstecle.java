package com.example.beyondthegalacticsafari;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Obstecle {
    Bitmap obstecle[] = new Bitmap[3];
    int obstFrame = 0;
    int obstX, obstY, obstVelocity;
    Random random;

    //this is where the obsctecle png are set
    public Obstecle(Context context)
    {
        obstecle[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock1);
        obstecle[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock2);
        obstecle[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.rock3);
        random = new Random();
        resetPosition();
    }

    public Bitmap getObst(int obstFrame)
    {
          return obstecle[obstFrame];
    }

    public int getObstWidth()
    {
        return  obstecle[0].getWidth();
    }

    public int getObstHeight()
    {
        return obstecle[0].getHeight();
    }



    public void resetPosition()
    {
        obstX = random.nextInt(GameView.dWidth - getObstWidth());
        obstY = GameView.dHeight - 10;
        obstVelocity = 35 + random.nextInt(16);
    }
}
