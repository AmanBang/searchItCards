package com.example.searchitcards;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.searchitcards.Anime.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class DashboarduSER extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Name = findViewById(R.id.UserNameDisplay);

        ParseQuery<ParseUser> parseQuery = new ParseQuery<ParseUser>("User");
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                Name.setText(ParseUser.getCurrentUser().getUsername() + "Wellcome to search it");
            }
        });



       BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
     bottomNavigationView.setSelectedItemId(R.id.nav_dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_dashboard:

                        return true;
                    case R.id.nav_movie:
                        startActivity(new Intent(getApplicationContext()
                                , Movies_activity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_news:
                        startActivity(new Intent(getApplicationContext()
                                , Favourites.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_tv:
                        startActivity(new Intent(getApplicationContext()
                                ,TVShows.class));
                        overridePendingTransition(0, 0);
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}