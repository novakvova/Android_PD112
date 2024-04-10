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
//        String url = "https://content1.rozetka.com.ua/goods/images/big/343033787.jpg";
//        String url = "http://10.0.2.2:5101/images/1.jpg";
        String url = "https://pd112.itstep.click/images/1.jpg";
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().override(400))
                .into(ivAvatar);

    }
}