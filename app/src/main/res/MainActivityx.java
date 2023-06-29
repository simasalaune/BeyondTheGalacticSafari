package com.example.myapplicationgg;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private LinearLayout squaresContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        squaresContainer = findViewById(R.id.squaresContainer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editText.getText().toString().trim();
                int numLetters = word.length();
                createSquares(numLetters);
            }
        });
    }

    private void createSquares(int numSquares) {
        squaresContainer.removeAllViews();

        for (int i = 0; i < numSquares; i++) {
            View square = new View(this);
            int size = getRandomSize();
            int color = getRandomColor();

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            params.setMargins(8, 8, 8, 8);
            square.setLayoutParams(params);
            square.setBackgroundColor(color);

            squaresContainer.addView(square);
        }
    }

    private int getRandomSize() {
        Random random = new Random();
        int minSize = 50;
        int maxSize = 150;
        return random.nextInt(maxSize - minSize + 1) + minSize;
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
