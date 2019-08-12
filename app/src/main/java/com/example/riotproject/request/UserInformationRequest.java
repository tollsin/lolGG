package com.example.riotproject.request;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

public class UserInformationRequest extends AsyncTask<String , Void,String> {

    String url_ ="https://kr.api.riotgames.com/";

    ProgressDialog asyncDialog ;


    ContentValues values;
    private String str, receiveMsg;

    FragmentActivity activity;
    public UserInformationRequest(ContentValues values, FragmentActivity activity) {
        this.values = values;
        this.activity = activity;

        asyncDialog = new ProgressDialog(activity);
    }

    @Override
    protected String doInBackground(String... params) {
      String url = null;
          for(int i=0; i<params.length;i++){
            url_ += params[i];
        }
        Log.d("test",url_);

        RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
        receiveMsg= requestHttpURLConnection.request(url_,values,"get");

        return receiveMsg;
    }

    @Override
    protected void onPreExecute() {

        //LoadingApp.getInstance().progressON(activity,null);
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);


    }
}
