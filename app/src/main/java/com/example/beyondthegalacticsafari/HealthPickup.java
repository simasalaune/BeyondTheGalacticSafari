package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
import static com.example.beyondthegalacticsafari.GameView.ScreenRatioInvert;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HealthPickup {

    Random random = new Random();
    int randomNum;
    public int speed = random.nextInt((int) ((screenY / 42 ) * ScreenRatioInvert));
    int x, y, width, height, score = 1;
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
        randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);
        y = -screenY * randomNum;
    }

    public Bitmap getHealthPickup()
    {
        return healthPickup;
    }

    public Rect getCollisionShape()
    {
        return new Rect(x, y, x + width+100, y + height+100);
    }
}
