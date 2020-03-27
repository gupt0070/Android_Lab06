package com.example.android_lab06;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    ActionBarDrawerToggle Actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        toolbar = findViewById(R.id.toolbar);
        getActionBar();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        Actionbar = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(Actionbar);
        Actionbar.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.OverflowWriting:
                Toast.makeText(this, "You clicked on the overflow menu above.", Toast.LENGTH_LONG).show();
                break;
            case R.id.facebook:
                Toast.makeText(this, "You clicked on Facebook icon.", Toast.LENGTH_LONG).show();
                break;
            case R.id.instagram:
                Toast.makeText(this, "You clicked on Instagram icon.", Toast.LENGTH_LONG).show();
                break;
            case R.id.twitter:
                Toast.makeText(this, "You clicked on Twitter icon.", Toast.LENGTH_LONG).show();
                break;}
        return true;
    }

    public boolean onNavigationItemSelected( MenuItem givenItem) {
        switch(givenItem.getItemId())
        {
            case R.id.ChatPage:
                Intent ChatPage = new Intent(this, ChatRoomActivity.class);
                startActivity(ChatPage);
                break;
            case R.id.WeatherPage:
                Intent WeatherPage = new Intent(this,WeatherForecast.class);
                startActivity(WeatherPage);
                break;
            case R.id.GoToLogin:
                Intent startPage = new Intent(this,ProfileActivity.class);
                setResult(500,startPage);
                finish();
                break;
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }


}