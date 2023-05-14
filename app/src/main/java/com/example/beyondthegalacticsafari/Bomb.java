package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bomb {

    public int speed = 25 * screenY/42;
    int x, y = -screenY, width, height;
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
