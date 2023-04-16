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

import java.util.Random;

public class Obstacle {

    public int speed = (int) (60 * screenRatioInvert);
    int x, y = (int) (-screenY * screenRatioInvert), width, height;
    Bitmap obstacle;

    Obstacle (Resources res) {
        Bitmap[] obstacleSprites = new Bitmap[4];
        obstacleSprites[0] = BitmapFactory.decodeResource(res, R.drawable.rockone);
        obstacleSprites[1] = BitmapFactory.decodeResource(res, R.drawable.rocktwo);
        obstacleSprites[2] = BitmapFactory.decodeResource(res, R.drawable.rockthree);
        obstacleSprites[3] = BitmapFactory.decodeResource(res, R.drawable.rockfour);

        // select a random sprite
        int spriteIndex = new Random().nextInt(4);
        obstacle = obstacleSprites[spriteIndex];

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