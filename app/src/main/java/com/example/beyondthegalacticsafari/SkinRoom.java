package com.example.beyondthegalacticsafari;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SkinRoom extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_change_skin);
        Button nextButton = findViewById(R.id.next_button);
        //Default skin
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeSkin();
            }
        });}

    private int skinNumber = 1;

    private void changeSkin() {
        skinNumber++;
        if (skinNumber > 4) {
            skinNumber = 1;
        }
        Button nextButton = findViewById(R.id.next_button);
        ImageView skin = findViewById(R.id.skins);
        switch (skinNumber) {
            case 1:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);

                skin.setImageResource(R.drawable.spaceshipone);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshipone);
                break;
            case 2:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);
                skin.setImageResource(R.drawable.spaceshiptwo);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshiptwo);
                break;
            case 3:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);
                skin.setImageResource(R.drawable.spaceshipthree);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshipthree);
                break;
            case 4:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);
                skin.setImageResource(R.drawable.spaceshipfour);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.spaceshipfour);
                break;
        }
    }
    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
