package com.example.riotproject.request;

import android.os.AsyncTask;
import android.util.Log;

import com.example.riotproject.data.Champion;
import com.example.riotproject.data.ChampionImgPath;
import com.example.riotproject.data.Champions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class SkinRequest extends AsyncTask<String, Void, String> {
    String clientKey = "#########################";

    private String str, receiveMsg;




    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("http://ddragon.leagueoflegends.com/cdn/9.13.1/data/ko_KR/champion.json");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("x-waple-authorization", clientKey);

            if (conn.getResponseCode() == conn.HTTP_OK) {
                InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuffer buffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                receiveMsg = buffer.toString();
                Log.i("receiveMsg : ", receiveMsg);

                reader.close();
            } else {
                Log.i("통신 결과", conn.getResponseCode() + "에러");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveMsg;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            JSONObject data = new JSONObject(new JSONObject(s).optString("data"));

            Iterator<String> iter =data.keys();

            Map<String ,Champion > map = new HashMap<>();
            while (iter.hasNext()){

                String key = iter.next();

                Object value = data.get(key);

                JSONObject jsonObj =  new JSONObject(value.toString());
                String key1 = jsonObj.getString("key");

                String name = jsonObj.getString("name");
                String id = jsonObj.getString("id");

                JSONObject imgObj= jsonObj.getJSONObject("image");

                ChampionImgPath imgPath = new ChampionImgPath();
                imgPath.setFull(imgObj.getString("full"));
                imgPath.setSprite(imgObj.getString("sprite"));
                imgPath.setGroup(imgObj.getString("group"));

                Champion champion = new Champion();
                champion.setId(id);
                champion.setName(name);
                champion.setKey(key1);
                champion.setImagePath(imgPath);

                map.put(key1 ,champion);

            }
            Log.d("반복문 종료",map.get("32").getName());
            Champions.getInstance().setChampionMap(map);
        }catch (JSONException e){
            e.printStackTrace();
        }


    }
}
