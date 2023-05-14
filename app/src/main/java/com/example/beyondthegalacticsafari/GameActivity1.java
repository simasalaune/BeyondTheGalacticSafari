package com.example.beyondthegalacticsafari;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity1 extends AppCompatActivity {
    private GameView1 gameView;
    private FrameLayout game;
    private RelativeLayout GameButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView1(this, point.x, point.y, this);
        game = new FrameLayout(this);
        GameButtons=new RelativeLayout(this);
        LayoutParams set = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        LayoutParams prams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        GameButtons.setLayoutParams(prams);

        Button settings = new Button(this);
        settings.setLayoutParams(set);
        settings.setX(point.x-point.x/3.5f);
        settings.setY(point.y/24);
        settings.setBackgroundResource(R.drawable.settings_button);

//        ImageButton settings = new ImageButton(this);
//        settings.setLayoutParams(set);
//        settings.setPadding(point.x-point.x/5, point.y/24, 0, 0);
//        settings.setBackgroundResource(android.R.color.transparent);
//        settings.setImageResource(R.drawable.settings_button);

        GameButtons.addView(settings);

        game.addView(gameView);
        game.addView(GameButtons);
        setContentView(game);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPause();
                Intent intent = new Intent(getBaseContext(), Pop.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}