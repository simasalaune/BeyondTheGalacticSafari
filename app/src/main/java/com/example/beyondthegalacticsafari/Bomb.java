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

public class Bomb {

    Random random = new Random();
    public int speed = random.nextInt((int) ((screenY / 42 ) * ScreenRatioInvert));
    int x, y, width, height, randomNum;
    Bitmap bomb;

    Bomb(Resources res, int number) {
        switch(number) {
            case 0: bomb = BitmapFactory.decodeResource(res, R.drawable.bombone);
                break;
            case 1: bomb = BitmapFactory.decodeResource(res, R.drawable.bombtwo);
                break;
        }

        width = bomb.getWidth();
        height = bomb.getHeight();

        width *= 4;
        height *= 4;

        if (ScreenRatio > 0)
            width = (int)(width / ScreenRatio);
        else if (ScreenRatio < 0)
            width = (int)(width * ScreenRatio);

        if(ScreenRatio > 0)
            height = (int)(height / ScreenRatio);
        else if (ScreenRatio < 0)
            height = (int)(height * ScreenRatio);

        bomb = Bitmap.createScaledBitmap(bomb, width, height, false);
        x = width;
        randomNum = ThreadLocalRandom.current().nextInt(2, 3);
        y = -screenY * randomNum;
    }

    public Bitmap getBomb()
    {
        return bomb;
    }

    public Rect getCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }
}
