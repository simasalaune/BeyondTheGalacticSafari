package com.example.beyondthegalacticsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void startGame(View view) {
        GameView gameView = new GameView(this);
        setContentView(gameView);
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
