package com.example.beyondthegalacticsafari;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class Background {

    int x = 0, y = 0;
    Bitmap background;

    Background(int screenX, int screenY, Bitmap bitmap) {
        background = Bitmap.createScaledBitmap(bitmap, screenX,-screenY, false);
    }
}
