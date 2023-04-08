package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
//import static com.example.beyondthegalacticsafari.GameView.screenRatioX;
//import static com.example.beyondthegalacticsafari.GameView.screenRatioY;
import static com.example.beyondthegalacticsafari.GameView.screenRatioInvert;
import static com.example.beyondthegalacticsafari.GameView.screenX;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Obstacle {

    public int speed = (int) (60 * screenRatioInvert);
    int x, y = (int) (-screenY * screenRatioInvert), width, height;
    Bitmap obstacle;

    Obstacle (Resources res) {
        obstacle = BitmapFactory.decodeResource(res, R.drawable.rock1);

        width = obstacle.getWidth();
        height = obstacle.getHeight();

        width *= 2;
        height *= 2;

        if (ScreenRatio > 0)
            width = (int)(width / ScreenRatio);
        else if (ScreenRatio < 0)
            width = (int)(width * ScreenRatio);

        if(ScreenRatio > 0)
            height = (int)(height / ScreenRatio);
        else if (ScreenRatio < 0)
            height = (int)(height * ScreenRatio);

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
