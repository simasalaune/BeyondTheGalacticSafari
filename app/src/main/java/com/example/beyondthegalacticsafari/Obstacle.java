package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.screenRatioX;
import static com.example.beyondthegalacticsafari.GameView.screenRatioY;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Obstacle {

    public int speed = 30;
    int x, y = (int) (-screenY * screenRatioY), width, height;
    Bitmap obstacle;

    Obstacle (Resources res) {
        obstacle = BitmapFactory.decodeResource(res, R.drawable.rock1);

        width = obstacle.getWidth();
        height = obstacle.getHeight();

        width *= 3;
        height *= 3;

        if ((int) screenRatioX != 0) {
            width /= (int) screenRatioX;
        }
        if ((int) screenRatioY != 0) {
            height /= (int) screenRatioY;
        }

        obstacle = Bitmap.createScaledBitmap(obstacle, width, height, false);
        x = width;
    }

    public Bitmap getObstacle()
    {
        return obstacle;
    }

    public Rect getCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }

}
