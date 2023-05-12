package com.example.beyondthegalacticsafari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.Random;

public class GameView extends SurfaceView implements Runnable {

    boolean temp;
    int isColiding;
    private Thread thread;
    private boolean isPlaying, isGameOver = false;
    private int  oldX,oldShipX, health;
    public static int screenX, screenY;
    public static float ScreenRatio, screenRatioInvert;
    private  Background background1, background2;
    private Paint paint;
    Paint healthPaint = new Paint();
    private Ship ship;
    private Obstacle[] obstacles;
    private HealthPickup[] healthPickups;
    private Bomb[] bombs;
    private Random random;

    private Context context;
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        temp = false;
        isColiding = 0;
        healthPaint.setColor(Color.GREEN);
        health = 3;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;

        ScreenRatio = screenX/screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.y = screenY;

        paint = new Paint();
        ship = new Ship(screenX, screenY, getResources());

        obstacles = new Obstacle[6];
        healthPickups = new HealthPickup[4];
        bombs = new Bomb[2];
        for (int i = 0; i < obstacles.length; i++)
        {
            Obstacle obstacle = new Obstacle(getResources());
            obstacles[i] = obstacle;
        }
        for (int i = 0; i < healthPickups.length; i++)
        {
            HealthPickup healthPickup = new HealthPickup(getResources(), i);
            healthPickups[i] = healthPickup;
        }
        for (int i = 0; i < bombs.length; i++)
        {
            Bomb bomb = new Bomb(getResources(), i);
            bombs[i] = bomb;
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

        background1.y += screenY/42;
        background2.y += screenY/42;

        if (background1.y + background1.background.getHeight() > screenY+background1.background.getHeight()) {
            background1.y = -screenY;
        }

        if (background2.y + background2.background.getHeight() > screenY+background1.background.getHeight()) {
            background2.y = -screenY;
        }

        for (Obstacle obstacle : obstacles) {
            obstacle.y += obstacle.speed;

            if (obstacle.y + obstacle.height > screenY + obstacle.height) {
                obstacle.speed = random.nextInt(screenY/42 + screenY/84);
                obstacle.y = -screenY;
                obstacle.x = random.nextInt(screenX - obstacle.width);
            }
            Rect rect = obstacle.getCollisionShape();
            if (Rect.intersects(rect, ship.getCollisionShape())) {
                health--;
                obstacle.y = -screenY;
            }
            if (health <= 0) {
                isGameOver = true;
                return;
            }
        }
        for (HealthPickup healthPickup : healthPickups) {
            healthPickup.y += healthPickup.speed;

            if (healthPickup.y + healthPickup.height > screenY + healthPickup.height) {
                healthPickup.speed = random.nextInt(screenY/42 + screenY/84);
                healthPickup.y = -screenY;
                healthPickup.x = random.nextInt(screenX - healthPickup.width);
            }
            Rect rect = healthPickup.getCollisionShape();
            if (Rect.intersects(rect, ship.getCollisionShape())) {
                if(health < 3) {
                    health++;
                }
                healthPickup.y = -screenY;
            }
        }

        for (Bomb bomb : bombs) {
            bomb.y += bomb.speed;

            if (bomb.y + bomb.height > screenY + bomb.height) {
                bomb.speed = random.nextInt(screenY/42 + screenY/84);
                bomb.y = -screenY;
                bomb.x = random.nextInt(screenX - bomb.width);
            }
            Rect rect = bomb.getCollisionShape();
            if (Rect.intersects(rect, ship.getCollisionShape())) {
                health -= 10;
                bomb.y = -screenY;
            }
        }
    }
    private void draw(){

        if (getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background, background1.x, background1.y,paint);
            canvas.drawBitmap(background2.background, background2.x, background2.y,paint);

            if(health == 3) {
                healthPaint.setColor(Color.GREEN);
            }
            if (health == 2){
                healthPaint.setColor(Color.YELLOW);
            } else if (health == 1) {
                 healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(screenX/3, screenY/18, (screenX/3)+(screenX/9)*health, screenY/12, healthPaint);

            for (Obstacle obstacle : obstacles)
                canvas.drawBitmap(obstacle.getObstacle(), obstacle.x, obstacle.y, paint);

            for (HealthPickup healthPickup : healthPickups)
                canvas.drawBitmap(healthPickup.getHealthPickup(), healthPickup.x, healthPickup.y, paint);

            for (Bomb bomb : bombs)
                canvas.drawBitmap(bomb.getBomb(), bomb.x, bomb.y, paint);

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
