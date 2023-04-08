package com.example.beyondthegalacticsafari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int  oldX,oldShipX;
    public static int screenX, screenY;
    public static float screenRatioX, screenRatioY;
    private  Background background1, background2;
    private Paint paint;
    private Ship ship;
    private Obstacle[] obstacles;
    private Random random;

    private Context context;
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1440f / screenX;
        screenRatioY = 3120f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.y = screenY;

        paint = new Paint();
        ship = new Ship(screenX, screenY, getResources());

        obstacles = new Obstacle[6];
        for (int i = 0; i < obstacles.length; i++)
        {
            Obstacle obstacle = new Obstacle(getResources());
            obstacles[i] = obstacle;
        }

        random = new Random();

    }
    @Override
    public void run() {
        while (isPlaying)
        {
            update();
            draw();
            sleep();
        }
    }

    private void update() {

        background1.y -= 20;
        background2.y -= 20;

        if (background1.y + background1.background.getHeight() < 0) {
            background1.y = screenY;
        }

        if (background2.y + background2.background.getHeight() < 0) {
            background2.y = screenY;
        }

        for (Obstacle obstacle : obstacles)
        {
             obstacle.y += obstacle.speed;

            if (obstacle.y + obstacle.height > screenY*screenRatioY)
            {
                int bound = (int) (30 * screenRatioY);
                obstacle.speed = random.nextInt(bound + 10);
//                if (obstacle.speed < 10 * screenRatioY)
//                {
//                    obstacle.speed = (int) (10 * screenRatioY);
//                }
                obstacle.y = -screenY;
                obstacle.x = random.nextInt(screenX - obstacle.width);
            }
            if (Rect.intersects(obstacle.getCollisionShape(), ship.getCollisionShape()))
            {
                isGameOver = true;
                return;
            }
        }

    }
    private void draw(){

        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y,paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y,paint);

            for (Obstacle obstacle : obstacles)
                canvas.drawBitmap(obstacle.getObstacle(), obstacle.x, obstacle.y, paint);

            if(isGameOver)
            {
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                sleep();
                Intent intent = new Intent(context, GameOVer.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                return;
            }



            canvas.drawBitmap(ship.getShip(), ship.x, ship.y, paint);

            getHolder().unlockCanvasAndPost(canvas);

        }
    }
        private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }
    public  void pause(){
        try {
            isPlaying = false;
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        if (touchY >= ship.y) {
            int action = event.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                oldX = (int) event.getX();
                oldShipX = ship.x;
            }
            if (action == MotionEvent.ACTION_MOVE) {
                float shift = oldX - touchX;
                float newShipX = oldShipX - shift;
                if (newShipX <= 0)
                    ship.x = 0;
                else if (newShipX >= getWidth() - ship.width)
                    ship.x = getWidth() - ship.width;
                else
                    ship.x = (int) newShipX;
            }
        }
        return true;
    }



}
