package com.example.riotproject.request;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.os.AsyncTask;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

public class AccountIdRequest extends AsyncTask<String , Void,String> {

    String api_key = "RGAPI-911d425e-2410-49f1-98f9-410f89f44479";
    String url_ ="https://kr.api.riotgames.com/";

    ProgressDialog asyncDialog ;


    ContentValues values;
    private String str, receiveMsg;

    FragmentActivity activity;
    public AccountIdRequest(ContentValues values, FragmentActivity activity) {
        this.values = values;
        this.activity = activity;


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
        super.onPreExecute();


    }

    @Override
    protected void onPostExecute(String s) {

        super.onPostExecute(s);


        /*Log.d("AccoutidRequest",s + "내용 데스네 ");
        if(s.equals(null)){
            return;
        }
        Summoner summoner = new Summoner();
        try {
            JSONObject jsonObject = new JSONObject(s);
            summoner.setProfileIconId(jsonObject.getInt("profileIconId"));
            summoner.setAccountId(jsonObject.getString("accountId"));
            summoner.setName(jsonObject.getString("name"));
            summoner.setPuuid(jsonObject.getString("puuid"));
            summoner.setSummonerLevel(jsonObject.getLong("summonerLevel"));
            summoner.setId(jsonObject.getString("id"));
            summoner.setRevisionDate(jsonObject.getLong("revisionDate"));
        }catch (JSONException e){
            e.printStackTrace();
        }*/
    }
}
