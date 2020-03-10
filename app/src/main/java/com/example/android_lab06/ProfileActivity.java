package com.example.android_lab06;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton takePictureBtn;
    Button goToChatBtn, goToToolbarBtn, goToWeatherBtn;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // get the intent that got us here
        Intent loginPage = getIntent();

        String emailTyped = loginPage.getStringExtra("emailTyped");

        //Put the string that was sent from FirstActivity into the edit text:
        EditText enterText = findViewById(R.id.EmailEditText);
        enterText.setText(emailTyped);

        takePictureBtn = findViewById(R.id.ImageButton);
        takePictureBtn.setOnClickListener(c -> {


            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        });

        goToChatBtn = findViewById(R.id.GoToChatBtn);
        goToChatBtn.setOnClickListener(c -> {
            Intent goToChatPage = new Intent(ProfileActivity.this, ChatRoomActivity.class);

            startActivityForResult(goToChatPage, 345);

        });

        //goToToolbarBtn = findViewById(R.id.GoToToolbarPage);
        // goToToolbarBtn.setOnClickListener(c -> {
        // Intent goToMenuPage = new Intent(ProfileActivity.this, TestToolbar.class);

        // startActivityForResult(goToMenuPage, 123);}        );

        goToWeatherBtn = findViewById(R.id.GoToWeatherPage);
        goToWeatherBtn.setOnClickListener(c -> {
            Intent goToMenuPage = new Intent(ProfileActivity.this, WeatherForecast.class);

            startActivityForResult(goToMenuPage, 234);

        });

        Log.d(ACTIVITY_NAME, "In function: onCreate()");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            takePictureBtn.setImageBitmap(imageBitmap);
        }
        Log.d(ACTIVITY_NAME, "In function: onActivityResult()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(ACTIVITY_NAME, "In function: onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(ACTIVITY_NAME, "In function: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(ACTIVITY_NAME, "In function: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(ACTIVITY_NAME, "In function: onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(ACTIVITY_NAME, "In function: onDestroy()");
    }
}