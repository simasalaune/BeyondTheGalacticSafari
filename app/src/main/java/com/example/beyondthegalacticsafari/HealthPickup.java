package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class HealthPickup {

    public int speed = 10 * screenY/42;
    int x, y = -screenY, width, height;
    Bitmap healthPickup;

    HealthPickup(Resources res, int number) {
        switch(number) {
            case 0: healthPickup = BitmapFactory.decodeResource(res, R.drawable.dramblys);
                break;
            case 1: healthPickup = BitmapFactory.decodeResource(res, R.drawable.liutas);
                break;
            case 2: healthPickup = BitmapFactory.decodeResource(res, R.drawable.kroko);
                break;
            case 3: healthPickup = BitmapFactory.decodeResource(res, R.drawable.zirafa);
                break;
        }

        width = healthPickup.getWidth();
        height = healthPickup.getHeight();

        width *= 3;
        height *= 3;

        if (ScreenRatio > 0)
            width = (int)(width / ScreenRatio);
        else if (ScreenRatio < 0)
            width = (int)(width * ScreenRatio);

        if(ScreenRatio > 0)
            height = (int)(height / ScreenRatio);
        else if (ScreenRatio < 0)
            height = (int)(height * ScreenRatio);

        healthPickup = Bitmap.createScaledBitmap(healthPickup, width, height, false);
        x = width;
    }

    public Bitmap getHealthPickup()
    {
        return healthPickup;
    }

    public Rect getCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }
}
