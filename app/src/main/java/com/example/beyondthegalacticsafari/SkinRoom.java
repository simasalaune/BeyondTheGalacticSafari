package com.example.beyondthegalacticsafari;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SkinRoom extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        if (skinNumber > 3) {
            skinNumber = 1;
        }
        Button nextButton = findViewById(R.id.next_button);
        ImageView skin = findViewById(R.id.skins);
        switch (skinNumber) {
            case 1:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);

                skin.setImageResource(R.drawable.shipone);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipone);
                break;
            case 2:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);
                skin.setImageResource(R.drawable.shiptwo);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.shiptwo);
                break;
            case 3:
                nextButton.setText("Next");
                nextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.skin, 0, 0, 0);
                skin.setImageResource(R.drawable.shipthree);
                ShipBitmap.ship = BitmapFactory.decodeResource(getResources(), R.drawable.shipthree);
                break;
        }
    }
    public void back(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
