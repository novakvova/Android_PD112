package com.example.sim;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivAvatar = findViewById(R.id.ivAvatar);
        String url = "https://content1.rozetka.com.ua/goods/images/big/343033787.jpg";
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(400))
                .into(ivAvatar);

    }
}