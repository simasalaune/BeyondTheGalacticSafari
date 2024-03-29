package com.example.beyondthegalacticsafari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
public class OptionsActivity extends AppCompatActivity {

    private static boolean soundEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_options);

        // Retrieve sound setting from SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        soundEnabled = preferences.getBoolean("sound_enabled", true);

        Button soundButton = findViewById(R.id.sound_button);
        Button difficultyButton = findViewById(R.id.difficulty_button);

        updateSoundButton();

        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOnOffSound();
            }
        });
        difficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDifficulty();
            }
        });
    }

    private void turnOnOffSound() {
        soundEnabled = !soundEnabled;
        updateSoundButton();

        // Save sound setting to SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("sound_enabled", soundEnabled);
        editor.apply();
    }

    private void updateSoundButton() {
        Button soundButton = findViewById(R.id.sound_button);
        if (soundEnabled) {
            soundButton.setText("Sound Off");
            soundButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.nosound, 0, 0, 0);
        } else {
            soundButton.setText("Sound On");
            soundButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.yessound, 0, 0, 0);
        }
    }

    private int difficultyLevel = 1; // 1 = easy, 2 = medium, 3 = hard

    private void changeDifficulty() {
        difficultyLevel++;
        if (difficultyLevel > 3) {
            difficultyLevel = 1;
        }
        Button difficultyButton = findViewById(R.id.difficulty_button);
        switch (difficultyLevel) {
            case 1:
                difficultyButton.setText("Easy");
                difficultyButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.easy, 0, 0, 0);
                // TODO: set game difficulty to easy
                Speed.Speed = 0.5f;
                break;
            case 2:
                difficultyButton.setText("Medium");
                difficultyButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.medium, 0, 0, 0);
                // TODO: set game difficulty to medium
                Speed.Speed = 1;
                break;
            case 3:
                difficultyButton.setText("Hard");
                difficultyButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.hard, 0, 0, 0);
                // TODO: set game difficulty to hard
                Speed.Speed = 1.5f;
                break;
        }
    }

    public void back(View view) {
        finish();
    }
}
