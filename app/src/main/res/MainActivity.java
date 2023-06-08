package com.example.egzaminas;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String PREF_CIRCLE_COUNT = "circleCount";
    private static final String PREF_CIRCLE_SIZE = "circleSize";
    private static final String PREF_BORDER_COLOR = "borderColor";
    private static final String PREF_FILL_COLOR = "fillColor";

    private EditText editTextWord;
    private Button buttonCountLetters;
    private LinearLayout layoutCircles;
    private LinearLayout parentLayout;
    private Random random;

    private int circleCount;
    private int circleSize;
    private int borderColor;
    private int fillColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize random generator
        random = new Random();

        // Find views by their IDs
        editTextWord = findViewById(R.id.editText);
        buttonCountLetters = findViewById(R.id.button);
        parentLayout = findViewById(R.id.circleContainer);

        Button buttonSave = findViewById(R.id.buttonSave);
        Button buttonLoad = findViewById(R.id.buttonLoad);

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
                    circleSize = getRandomSize();
                    borderColor = getRandomColor();
                    fillColor = getRandomColor();

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

                saveDrawingParameters();
            }
        });
        retrieveDrawingParameters();
        setupDrawing();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveParametersToFile();
            }
        });

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadParametersFromFile();
                setupDrawing(); // Call setupDrawing() to apply the loaded parameters
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

    private void saveDrawingParameters() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(PREF_CIRCLE_COUNT, circleCount);
        editor.putInt(PREF_CIRCLE_SIZE, circleSize);
        editor.putInt(PREF_BORDER_COLOR, borderColor);
        editor.putInt(PREF_FILL_COLOR, fillColor);
        editor.apply();
    }

    private void retrieveDrawingParameters() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        circleCount = prefs.getInt(PREF_CIRCLE_COUNT, 0);
        circleSize = prefs.getInt(PREF_CIRCLE_SIZE, 0);
        borderColor = prefs.getInt(PREF_BORDER_COLOR, 0);
        fillColor = prefs.getInt(PREF_FILL_COLOR, 0);
    }
    private void setupDrawing() {
        // Clear existing circles, if present
        if (layoutCircles != null && layoutCircles.getChildCount() > 0) {
            layoutCircles.removeAllViews();
        }

        // Draw circles with saved parameters
        for (int i = 0; i < circleCount; i++) {
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
    }

    private void saveParametersToFile() {
        // Create a JSONObject to store the parameters
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("circleCount", circleCount);
            parameters.put("circleSize", circleSize);
            parameters.put("borderColor", borderColor);
            parameters.put("fillColor", fillColor);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Convert the JSONObject to a string
        String parametersString = parameters.toString();

        // Create a file name for the parameters file
        String fileName = "parameters.txt";

        // Save the parameters to a file in external storage
        try {
            File file = new File(getExternalFilesDir(null), fileName);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(parametersString);
            fileWriter.close();
            Toast.makeText(MainActivity.this, "Parameters saved to file", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadParametersFromFile() {
        // Create a file name for the parameters file
        String fileName = "parameters.txt";

        // Load the parameters from the file
        try {
            File file = new File(getExternalFilesDir(null), fileName);
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // Convert the string to a JSONObject
            JSONObject parameters = new JSONObject(stringBuilder.toString());

            // Retrieve the parameter values from the JSONObject
            circleCount = parameters.optInt("circleCount", 0);
            circleSize = parameters.optInt("circleSize", 0);
            borderColor = parameters.optInt("borderColor", 0);
            fillColor = parameters.optInt("fillColor", 0);

            bufferedReader.close();
            Toast.makeText(MainActivity.this, "Parameters loaded from file", Toast.LENGTH_SHORT).show();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
