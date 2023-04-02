package com.example.beyondthegalacticsafari;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {

    Bitmap explosion[] = new Bitmap[9];
    int explFrame = 0;
    int explX, explY;

    public Explosion(Context context)
    {
        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex4);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex5);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex6);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex7);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex8);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex9);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex10);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex11);
        explosion[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ex12);
    }

    public Bitmap getExplosion(int explFrame)
    {
        return  explosion[explFrame];
    }

}
