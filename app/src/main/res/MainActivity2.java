package com.example.myapplicationyyj;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private CanvasView canvasView;
    private Button buttonChangeSize;
    private Button buttonChangeColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasView = findViewById(R.id.canvasView);
        buttonChangeSize = findViewById(R.id.buttonChangeSize);
        buttonChangeColor = findViewById(R.id.buttonChangeColor);

        buttonChangeSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeStripeSizes();
            }
        });

        buttonChangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.changeStripeColors();
            }
        });
        canvasView.changeStripeSizes();
    }
}
