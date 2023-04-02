package com.example.beyondthegalacticsafari;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.webkit.ConsoleMessage;
import android.widget.ImageView;

import androidx.core.content.res.ResourcesCompat;

import java.io.Console;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {

    //ImageButton imageButton;
    Bitmap rocket, rock, background;
    Context context;
    Handler handler;
    final long UPDATE_MILLIS = 30;
    Runnable runnable;
    Paint healthPaint = new Paint();
    int life = 3;
    static int dWidth, dHeight;
    Random random;
    int rocketX, rocketY, rockX, rockY;
    int oldX, oldRocketX;
    ArrayList<Explosion> explosions;
    int X[] = new int[5];
    int Y[] = new int[5];
    Rect can;
    Rect image;

    public GameView(Context context) {
        super(context);
        //assign variables
        this.context = context;
      background = BitmapFactory.decodeResource(getResources(), R.drawable.game_background);
      image =  new Rect(0, 0, background.getWidth(), background.getHeight());
        rocket = BitmapFactory.decodeResource(getResources(), R.drawable.main_ship___base___full_health);


        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
        rock = BitmapFactory.decodeResource(getResources(), R.drawable.rock1);
        healthPaint.setColor(Color.GREEN);
        random = new Random();
        rockY = -dHeight - 200;
        rocketX = dWidth / 2 - rocket.getWidth() / 2;
        rocketY = dHeight - 100 - rocket.getHeight();
        explosions = new ArrayList<>();
        X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
        X[1] = random.nextInt(GameView.dWidth - rock.getWidth());
        X[2] = random.nextInt(GameView.dWidth - rock.getWidth());
        X[3] = random.nextInt(GameView.dWidth - rock.getWidth());
        X[4] = random.nextInt(GameView.dWidth - rock.getWidth());

        Y[0] = -(GameView.dHeight) - 10;
        Y[1] = -(GameView.dHeight) - 10;
        Y[2] = -(GameView.dHeight) - 10;
        Y[3] = -(GameView.dHeight) - 10;
        Y[4] = -(GameView.dHeight) - 10;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        can = new Rect(0,0, canvas.getWidth(), canvas.getHeight());
        canvas.drawBitmap(background, can , image, null);
        canvas.drawBitmap(rocket, rocketX, rocketY, null);
        canvas.drawBitmap(rock, X[0], Y[0], null);
        canvas.drawBitmap(rock, X[1], Y[1], null);
        canvas.drawBitmap(rock, X[2], Y[2], null);
        canvas.drawBitmap(rock, X[3], Y[3], null);
        canvas.drawBitmap(rock, X[4], Y[4], null);
        Y[0] += 35 + random.nextInt(90);
        Y[1] += 35 + random.nextInt(30);
        Y[2] += 35 + random.nextInt(180);
        Y[3] += 35 + random.nextInt(30);
        Y[4] += 35 + random.nextInt(10);
        if (Y[0] + rock.getHeight() >= dHeight) {
            X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
            Y[0] = -dHeight;
        }
        if (Y[1] + rock.getHeight() >= dHeight) {
            X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
            Y[1] = -dHeight;
        }
        if (Y[2] + rock.getHeight() >= dHeight) {
            X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
            Y[2] = -dHeight;
        }
        if (Y[3] + rock.getHeight() >= dHeight) {
            X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
            Y[3] = -dHeight;
        }
        if (Y[4] + rock.getHeight() >= dHeight) {
            X[0] = random.nextInt(GameView.dWidth - rock.getWidth());
            Y[4] = -dHeight;
        }
        for (int i = 0; i < X.length; i++) {
            if (X[i] + rock.getWidth() >= rocketX
                    && X[i] <= rocketX + rocket.getWidth()
                    && Y[i] + rock.getWidth() >= rocketY
                    && Y[i] + rock.getWidth() <= rocketY + rocket.getHeight()) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
            handler.postDelayed(runnable, UPDATE_MILLIS);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= rocketY) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = (int) event.getX();
                oldRocketX = rocketX;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newRocketX = oldRocketX - shift;
                if (newRocketX <= 0)
                    rocketX = 0;
                else if (newRocketX >= dWidth - rocket.getWidth())
                    rocketX = dWidth - rocket.getWidth();
                else
                    rocketX = (int) newRocketX;
            }
        }
        return true;
    }
}
