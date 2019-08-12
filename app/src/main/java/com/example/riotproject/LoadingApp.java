package com.example.riotproject;

import android.app.Activity;
import android.app.Application;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatDialog;

public class LoadingApp extends Application {
    private String TAG = "LoadingApp";
    private boolean lsLoadingFlag = false;
    private static LoadingApp loadingApp;
    AppCompatDialog progressDialog;
    private static class SingletonHolder{
        private static final LoadingApp INSTANCE = new LoadingApp();
    }

    public static LoadingApp getInstance() {
        return LoadingApp.SingletonHolder.INSTANCE;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        loadingApp = this;
    }
    public void setIsLoadingFlag(boolean flag){
        this.lsLoadingFlag = flag;
    }

    public void progressON(Activity activity, String message) {
        lsLoadingFlag = true;

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {



            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing() && !lsLoadingFlag) {
            progressDialog.dismiss();
        }
    }

}
