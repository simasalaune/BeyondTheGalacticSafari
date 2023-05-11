package com.example.beyondthegalacticsafari;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.media.MediaPlayer;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private FrameLayout game;
    private RelativeLayout GameButtons;
    private MediaPlayer mediaPlayer;
    private boolean isSoundOn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mediaPlayer = MediaPlayer.create(this, R.raw.muzikele);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView = new GameView(this, point.x, point.y);
        game = new FrameLayout(this);
        GameButtons=new RelativeLayout(this);
        RelativeLayout.LayoutParams set = new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        RelativeLayout.LayoutParams prams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        GameButtons.setLayoutParams(prams);

        ImageButton settings = new ImageButton(this);
        settings.setLayoutParams(set);
        settings.setPadding(point.x-point.x/5, point.y/24, 0, 0);
        settings.setBackgroundResource(android.R.color.transparent);
        settings.setImageResource(R.drawable.settings_button);

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
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // Set to true if you want the sound to loop
            mediaPlayer.start();
        }
    }
}