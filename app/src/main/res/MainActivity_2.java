package com.example.sampletask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText textInput;
    TextView result;

    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        textInput = findViewById(R.id.inputText);
        result = findViewById(R.id.kiekraidziu);


        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongViewCast")
            @Override
            public void onClick(View view) {
                char letters[] = textInput.getText().toString().toCharArray();
                result.setText("Įvestame tekste iš viso yra " + letters.length + " raidžių/-ės");
                drawView = findViewById(R.id.view4);

                setFigure(0, false);
            }
        });
    }

    public void setFigure(final int figure, final boolean fillFlag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                char letters[] = textInput.getText().toString().toCharArray();
                drawView.setFigure(0, letters.length);
                drawView.invalidate();
            }
        });
    }
}