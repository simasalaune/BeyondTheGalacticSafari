package com.example.beyondthegalacticsafari;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.ImageButton;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameView extends SurfaceView implements Runnable {
    private SharedPreferences prefs;
    private Thread thread;
    private boolean isPlaying, isGameOver = false, obstacleOn = false, levelWon = false;
    private int  oldX,oldShipX, health, randomNum, score;
    public static int screenX, screenY;
    public static float ScreenRatio, ScreenRatioInvert;
    private  Background background1, background2;
    private Paint textPaint, scoreBackgroundPaint = new Paint(), healthPaint = new Paint();
    private Typeface typeface;
    private Rect scoreBackground, scoreBorder;
    private Ship ship;
    private Obstacle[] obstacles;
    //private Animal[] animals;
    private  Animal animal;
    private  Animal animal2;
    private HealthPickup[] healthPickups;
    private Bomb[] bombs;

    private Random random;


    private Context context;
    public GameView(GameActivity context, int screenX, int screenY, GameActivity activity) {
        super(context);

        //this.activity = activity;

        prefs = activity.getSharedPreferences("game", Context.MODE_PRIVATE);

        healthPaint.setColor(Color.GREEN);
        health = 3;
        score = 50;
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;

        ScreenRatio = screenX/screenY;
        ScreenRatioInvert = screenY/screenX;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
        background2.y = screenY;

        textPaint = new Paint();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.upheavtt);
        }
        textPaint.setTextSize(screenY/18);
        textPaint.setTypeface(typeface);
        textPaint.setColor(context.getColor(R.color.text));
        scoreBackground = new Rect(screenX / 14, screenY/21,
                (int) (screenX/3.5f), screenY/11);
        scoreBorder = new Rect((int) ((screenX / 14)+10*ScreenRatioInvert), (int)((screenY/21)+10*ScreenRatioInvert),
                (int)((screenX/3.5f)+10*ScreenRatioInvert), (int)((screenY/11)+10*ScreenRatioInvert));
        scoreBackgroundPaint.setColor(context.getColor(R.color.backGround));

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

        animal = animal2 = new Animal(getResources());

//        animals = new Animal[3];
//        for (int i = 0; i < animals.length; i++)
//        {
//            Animal animal = new Animal(getResources());
//            animals[i] = animal;
//        }

        random = new Random();
        randomNum = ThreadLocalRandom.current().nextInt(2, 5 + 1);

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

        setBackground(background1, background2);

//        for (Animal animal : animals) {
//            animalMove(animal);
//            animalHit(animal);
//        }
        if (score == 50)
        {
            obstacleOn = false;
            levelWon = true;
            if (ship.y != -screenY) {
                ship.y -= 30 * ScreenRatioInvert;
            }
//            else
//            {
//
////                Intent intent = new Intent(getContext(), GameActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                getContext().startActivity(intent);
//            }
        }
        shipEnter();


        animalMove(animal);
        animalHit(animal);

        animalMove(animal2);
        animalHit(animal2);

        if (obstacleOn == true) {
            for (Obstacle obstacle : obstacles) {
                obstacleMove(obstacle);
                obstacleHit(obstacle);
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
            //Draw Background
            canvas.drawBitmap(background1.background, background1.x, background1.y,textPaint);
            canvas.drawBitmap(background2.background, background2.x, background2.y,textPaint);

            //Draw Health Bar
            if(health == 3) {
                healthPaint.setColor(Color.GREEN);
            }
            if (health == 2){
                healthPaint.setColor(Color.YELLOW);
            } else if (health == 1) {
                 healthPaint.setColor(Color.RED);
            }
            canvas.drawRect(screenX/3, screenY/18, (screenX/3)+(screenX/9)*health,
                    screenY/12, healthPaint);

            //Draw score
            canvas.drawRect(scoreBorder, textPaint);
            canvas.drawRect(scoreBackground, scoreBackgroundPaint);
            canvas.drawText(score+"", screenX / 9, screenY/12, textPaint);

            //Draw obstacles
            for (Obstacle obstacle : obstacles)
                canvas.drawBitmap(obstacle.getObstacle(), obstacle.x, obstacle.y, textPaint);

//            //Draw obstacles
//            for (Animal animal : animals)
//                canvas.drawBitmap(animal.getAnimal(), animal.x, animal.y, paint);

            //Draw animal
            canvas.drawBitmap(animal.getAnimal(), animal.getX(), animal.getY(), textPaint);

            //Draw ship
            canvas.drawBitmap(ship.getShip(), ship.x, ship.y, textPaint);

            if (ship.y < -screenY) {
                getHolder().unlockCanvasAndPost(canvas);
                sleep();
                Intent intent = new Intent(context, GameActivity1.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                return;
            }
          
            //Check game over
            for (HealthPickup healthPickup : healthPickups)
                canvas.drawBitmap(healthPickup.getHealthPickup(), healthPickup.x, healthPickup.y, paint);

            for (Bomb bomb : bombs)
                canvas.drawBitmap(bomb.getBomb(), bomb.x, bomb.y, paint);

            if(isGameOver)
            {
                //save score
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("Score", score);
                editor.apply();

                //call Game Over screen
                isPlaying = false;
                getHolder().unlockCanvasAndPost(canvas);
                sleep();
                Intent intent = new Intent(context, GameOVer.class);
                context.startActivity(intent);
                ((Activity) context).finish();
                return;
            }
            getHolder().unlockCanvasAndPost(canvas);
        }
    }
    //animal movement and coordinate reset
    private void animalMove(Animal animal) {
        animal.setY(animal.getY() + animal.speed);
        if (animal.y + animal.height > screenY + animal.height) {
            animal.setY(-screenY * randomNum);
            animal.setX(random.nextInt(screenX - animal.width));
        }
    }
    //animal hit detection and coordinate reset
    private void animalHit(Animal animal){
        Rect rect = animal.getAnimalCollisionShape();
        if (Rect.intersects(rect, ship.getCollisionShape())) {
            animal.setY(-screenY * randomNum);
            animal.setX(random.nextInt(screenX - animal.width));
            score += animal.score;
        }
    }
    //obstacle movement and coordinate reset
    private void obstacleMove(Obstacle obstacle) {
        obstacle.y += obstacle.speed;
        if (obstacle.y + obstacle.height > screenY + obstacle.height) {
            obstacle.speed = random.nextInt((int) ((screenY / 42 ) * ScreenRatioInvert));
            obstacle.y = -screenY;
            obstacle.x = random.nextInt(screenX - obstacle.width);
        }
    }
    //obstacle hit detection and coordinate reset
    private void obstacleHit(Obstacle obstacle) {
        Rect rect = obstacle.getCollisionShape();
        if (Rect.intersects(rect, ship.getCollisionShape())) {
            health--;
            obstacle.y = -screenY;
            obstacle.x = random.nextInt(screenX - obstacle.width);
        }
        if (health <= 0) {
            isGameOver = true;
        }
    }
    //Background movement
    private void setBackground(Background background1, Background background2)
    {
        background1.y += screenY/82*ScreenRatioInvert;
        background2.y += screenY/82*ScreenRatioInvert;

        if (background1.y + background1.background.getHeight() > screenY+background1.background.getHeight()) {
            background1.y = -screenY;
        }

        if (background2.y + background2.background.getHeight() > screenY+background1.background.getHeight()) {
            background2.y = -screenY;
        }
    }

    private void shipEnter()
    {
        if (levelWon = false) {
            if (ship.y != (screenY - (screenY / 14) - ship.height)) {
                ship.y -= 10 * ScreenRatioInvert;
            } else {
                obstacleOn = true;
            }
        }
    }
        private void sleep(){
        try {
            Thread.sleep(7);
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
