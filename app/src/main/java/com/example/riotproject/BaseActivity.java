package com.example.riotproject;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        LoadingApp.getInstance().progressON(this, null);
    }

    public void progressON(String message) {
        LoadingApp.getInstance().progressON(this, message);
    }

    public void progressOFF() {
        LoadingApp.getInstance().progressOFF();
    }

}

