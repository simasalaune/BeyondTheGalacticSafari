package com.example.beyondthegalacticsafari;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class GameOVer extends AppCompatActivity {

    Button optionsButton;
    Button resumeButton;
    SharedPreferences prefs;
    TextView textView1, textView2,  textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        prefs = getSharedPreferences("game", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        resumeButton = (Button) findViewById(R.id.button2);
        textView1 = (TextView) findViewById(R.id.TextView1);
        textView2 = (TextView) findViewById(R.id.TextView2);
        textView3 = (TextView) findViewById(R.id.TextView3);

       setScore:
       if (prefs.getInt("highscore", 0) < prefs.getInt("Score", 0))
        {
            editor.putInt("Score2", prefs.getInt("Score1", 0));
            editor.putInt("Score1", prefs.getInt("highscore", 0));
            editor.putInt("highscore", prefs.getInt("Score", 0));
            editor.apply();
            break setScore;
        } else if (prefs.getInt("highscore", 0) == prefs.getInt("Score", 0)) {
           break setScore;
       }  else if  (prefs.getInt("Score1", 0) < prefs.getInt("Score", 0))
       {
           editor.putInt("Score2", prefs.getInt("Score1", 0));
           editor.putInt("Score1", prefs.getInt("Score", 0));
           editor.apply();
           break setScore;
       } else if  (prefs.getInt("Score1", 0) == prefs.getInt("Score", 0))
       {
           break setScore;
       }
       else if (prefs.getInt("Score2", 0) < prefs.getInt("Score", 0))
       {
           editor.putInt("Score2", prefs.getInt("Score", 0));
           editor.apply();
       }

        textView1.setText(1 + ":" + " " + prefs.getInt("highscore", 0));
            textView2.setText(2 + ":" + " " + prefs.getInt("Score1", 0));
            textView3.setText(3 + ":" + " " + prefs.getInt("Score2", 0));

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), GameActivity.class);
                startActivity(intent);
            }
        });
        optionsButton = (Button) findViewById(R.id.button3);

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}