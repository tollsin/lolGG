package com.example.riotproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.example.riotproject.request.ItemRequest;
import com.example.riotproject.request.RuenRequest;
import com.example.riotproject.request.SkinRequest;
import com.example.riotproject.request.SpellRequest;
;

public class LodingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView imageView = findViewById(R.id.gif_image);
        GlideDrawableImageViewTarget gifImage = new GlideDrawableImageViewTarget(imageView);
        Glide.with(this).load(R.drawable.img_loading).into(gifImage);
        startApp();
    }

    private void startApp() {
        new SkinRequest().execute();
        new ItemRequest().execute();
        new SpellRequest().execute();
        new RuenRequest().execute();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 10000);
    }
}
