package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.ScreenRatio;
import static com.example.beyondthegalacticsafari.GameView.screenY;
//import static com.example.beyondthegalacticsafari.GameView.screenRatioX;
//import static com.example.beyondthegalacticsafari.GameView.screenRatioY;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Ship {

    int x, y, width, height;
    Bitmap ship;

    Ship(int screenX, int screenY, Resources resources) {
        if (ShipBitmap.ship == null) {
            ship = BitmapFactory.decodeResource(resources, R.drawable.spaceshipone);
        } else {
            ship = ShipBitmap.ship;
        }

        width = ship.getWidth();
        height = ship.getHeight();

        width /= 28;
        height /= 28;

        if (ScreenRatio > 0)
            width = (int)(width / ScreenRatio);
        else if (ScreenRatio < 0)
            width = (int)(width * ScreenRatio);

        if(ScreenRatio > 0)
            height = (int)(height / ScreenRatio);
        else if (ScreenRatio < 0)
            height = (int)(height * ScreenRatio);

        ship = Bitmap.createScaledBitmap(ship, width, height, false);
        x = screenX / 2 - width / 2;
        y = screenY;
    }

    public void reset() {y = screenY;}
    public int getY() {return y;}
    public void setY(int a) {y = a;}

    Bitmap getShip() {
        return ship;
    }

    public Rect getCollisionShape() {
        return new Rect(x, y, x + width-100, y + height-100);
    }
}
