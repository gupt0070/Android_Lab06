package com.example.android_lab06;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton PictureTaken;
    Button ChatButtonHere;
    Button WeatherButtonHere;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent loginPage = getIntent();
        String EmailTextWritten = loginPage.getStringExtra("EMAIL");
        EditText enterText = findViewById(R.id.Email);
        enterText.setText(EmailTextWritten);
        PictureTaken = findViewById(R.id.Image);
        PictureTaken.setOnClickListener(c -> { Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }});

        ChatButtonHere = findViewById(R.id.ChatButton);
        ChatButtonHere.setOnClickListener(c -> {
            Intent goToChatPage = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            startActivityForResult(goToChatPage, 345); });


        WeatherButtonHere = findViewById(R.id.WeatherPage);
        WeatherButtonHere.setOnClickListener(c -> {
            Intent goToMenuPage = new Intent(ProfileActivity.this, WeatherForecast.class);
            startActivityForResult(goToMenuPage, 234); });

        Button TheToolBarButton = findViewById(R.id.ToolbarPage);
        TheToolBarButton.setOnClickListener(c -> {
            Intent ProfilePage = new Intent(ProfileActivity.this, TestToolbar.class);
            startActivityForResult(ProfilePage, 500); });
        Log.d(ACTIVITY_NAME, "In function: onCreate()");}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent ImagesShown) {
        super.onActivityResult(requestCode, resultCode, ImagesShown);
        //step e if not will go to profile Activity//
        { if (requestCode == 500){
            finish();
        } }
        Log.d(ACTIVITY_NAME, "In function: onActivityResult()");}

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