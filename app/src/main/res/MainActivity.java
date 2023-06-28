package com.example.myapplication;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private EditText editTextWord;
    private Button buttonCountLetters;
    private LinearLayout layoutCircles;
    private LinearLayout parentLayout;
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize random generator
        random = new Random();
        // Find views by their IDs
        editTextWord = findViewById(R.id.editTextWord);
        buttonCountLetters = findViewById(R.id.buttonCountLetters);
        parentLayout = findViewById(R.id.parentLayout);
        // Set click listener for the button
        buttonCountLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = editTextWord.getText().toString();
                int letterCount = word.length();
                int circleCount = Math.min(letterCount, 5); // Adjust the maximum number of circles here
                // Check if circles are already present and remove them
                if (layoutCircles != null && layoutCircles.getChildCount() > 0) {
                    layoutCircles.removeAllViews();
                }
                for (int i = 0; i < circleCount; i++) {
                    int circleSize = getRandomSize();
                    int borderColor = getRandomColor();
                    int fillColor = getRandomColor();
                    CircleView circleView = new CircleView(MainActivity.this, random);
                    circleView.setCircleSize(circleSize);
                    circleView.setBorderColor(borderColor);
                    circleView.setFillColor(fillColor);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.weight = 1;
                    circleView.setLayoutParams(layoutParams);
                    if (layoutCircles == null) {
                        layoutCircles = new LinearLayout(MainActivity.this);
                        layoutCircles.setOrientation(LinearLayout.HORIZONTAL);
                        parentLayout.addView(layoutCircles);
                    }
                    layoutCircles.addView(circleView);
                }
                String message = "Number of letters: " + letterCount;
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getRandomSize() {
        // Adjust the circle size range as per your requirements
        int minSize = 50;
        int maxSize = 200;
        return random.nextInt(maxSize - minSize) + minSize;
    }
    private int getRandomColor() {
        return Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
