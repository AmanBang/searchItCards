package com.example.searchitcards.Anime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.searchitcards.R;

public class AnimeShowMore extends AppCompatActivity {
String Url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anime_show_more);


        Intent intent = getIntent();
        Url = intent.getStringExtra("key_for_more");
        Log.i("URLPass",Url);
    }
}