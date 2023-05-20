package com.example.beyondthegalacticsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.media.MediaPlayer;



public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean isSoundOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        if (Speed.Speed < 0.5)
        {
            Speed.Speed = 0.5f;
        }
    }


    public void startGame(View view) {

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        /*if (mediaPlayer != null && isPlaying == false) {
            mediaPlayer.setLooping(true); // Set to true if you want the sound to loop
            mediaPlayer.start();
        }
        //else if (mediaPlayer != null || isSoundOn == true) mediaPlayer.stop();*/
    }
    public void openSkinRoom(View view) {
        Intent intent = new Intent(this, SkinRoom.class);
        startActivity(intent);
    }
    public void openOptions(View view) {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }
}
