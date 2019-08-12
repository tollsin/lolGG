package com.example.riotproject.request;

import android.os.AsyncTask;
import android.util.Log;

import com.example.riotproject.data.Champion;
import com.example.riotproject.data.ChampionImgPath;
import com.example.riotproject.data.Champions;
import com.example.riotproject.data.Spell;
import com.example.riotproject.data.Spells;

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
import java.util.Map;

public class SpellRequest extends AsyncTask<String,Void,String> {

    String clientKey = "#########################";

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("http://ddragon.leagueoflegends.com/cdn/9.13.1/data/ko_KR/summoner.json");

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

            Map<String , Spell> map = new HashMap<>();
            while (iter.hasNext()) {

                String key = iter.next();

                Object value = data.get(key);

                JSONObject jsonObj =  new JSONObject(value.toString());
                Spell spell = new Spell();

                spell.setId(jsonObj.getString("id"));
                spell.setName(jsonObj.getString("name"));
                String spellKey = jsonObj.getString("key") ;

                spell.setKey(spellKey);
                spell.setImagePath(jsonObj.getJSONObject("image").getString("full"));

                map.put(spellKey,spell);


            }

            Spells.getInstance().setSpellMap(map);
            Log.d("spellTest" , Spells.getInstance().getSpellMap().get("14").getName());
        }catch (JSONException e){
            e.printStackTrace();
        }


    }


}
