package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
import static com.example.beyondthegalacticsafari.GameView.ScreenRatioInvert;
import static com.example.beyondthegalacticsafari.GameView.screenX;
import static com.example.beyondthegalacticsafari.GameView.screenY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Animal {

    public int speed, score = 10;
    int x, y = -screenY, width, height;
    Bitmap animal;
    Random random = new Random();

    Animal(Resources res) {
        animal = BitmapFactory.decodeResource(res, R.drawable.test1);

        width = animal.getWidth();
        height = animal.getHeight();

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

        animal = Bitmap.createScaledBitmap(animal, width, height, false);
        x = random.nextInt(screenX - width);
        speed = (int) (screenY / 92 / ScreenRatioInvert);
    }

    public Bitmap getAnimal()
    {
        return animal;
    }
    public int getX() {return x;}
    public int getY() {return y;}
    public void setX(int a) { x = a;}
    public void setY(int a) { y = a;}

    public Rect getAnimalCollisionShape()
    {
        return new Rect(x, y, x + width, y + height);
    }

}
