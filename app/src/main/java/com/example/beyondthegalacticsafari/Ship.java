package com.example.beyondthegalacticsafari;

import static com.example.beyondthegalacticsafari.GameView.screenRatioX;
import static com.example.beyondthegalacticsafari.GameView.screenRatioY;

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
            ship = BitmapFactory.decodeResource(resources, R.drawable.shipthree);
        } else {
            ship = ShipBitmap.ship;
        }

        width = ship.getWidth();
        height = ship.getHeight();


        width /= 24;
        height /= 24;

        if ((int) screenRatioX != 0) {
            width /= (int) screenRatioX;
        }
        if ((int) screenRatioY != 0) {
            height /= (int) screenRatioY;
        }

        ship = Bitmap.createScaledBitmap(ship, width, height, false);
        x = screenX / 2 - width / 2;
        y = (screenY - 200 - height);
    }

    Bitmap getShip() {
        return ship;
    }

    public Rect getCollisionShape() {
        return new Rect(x, y, x + width-100, y + height-100);
    }
}
