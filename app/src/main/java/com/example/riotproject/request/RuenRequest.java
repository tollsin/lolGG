package com.example.riotproject.request;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.riotproject.data.CoreRunes;
import com.example.riotproject.data.Rune;
import com.example.riotproject.data.Runes;
import com.example.riotproject.data.Spell;
import com.example.riotproject.data.Spells;

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
import java.util.Map;

public class RuenRequest extends AsyncTask<String,Void,String> {
    String clientKey = "#########################";

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("http://ddragon.leagueoflegends.com/cdn/9.13.1/data/ko_KR/runesReforged.json");

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

            JSONArray jsonArray = new JSONArray(s);

            Map<String,CoreRunes>coreRunesMap = new HashMap<>();
            for(int i = 0;i<jsonArray.length();i++){
                CoreRunes coreRunes = new CoreRunes();
                JSONObject coreRuneObj = jsonArray.getJSONObject(i);
                int coreRuneId =coreRuneObj.getInt("id");
                coreRunes.setId(coreRuneId);
                coreRunes.setName(coreRuneObj.getString("name"));
                coreRunes.setIconImgPath(coreRuneObj.getString("icon"));
                Map<String, Rune>runeMap = new HashMap<>();
                JSONArray runes = coreRuneObj.getJSONArray("slots");

                for(int j =0 ;j<runes.length();j++){
                    JSONObject runeObj = runes.getJSONObject(j);
                    JSONArray runes1 = runeObj.getJSONArray("runes");

                    for(int k =0;k<runes1.length();k++){
                        Rune rune = new Rune();
                        JSONObject obj = runes1.getJSONObject(k);
                        int runeId = obj.getInt("id");
                        rune.setId(runeId);
                        rune.setKey(obj.getString("key"));
                        rune.setName(obj.getString("name"));
                        rune.setIconImgPath(obj.getString("icon"));
                        runeMap.put(String.valueOf(runeId),rune);
                    }
                    coreRunes.setRuneMap(runeMap);
                    coreRunesMap.put(String.valueOf(coreRuneId),coreRunes);
                }

            }

            Runes.getInstance().setCoreRunesMap(coreRunesMap);

            Log.d("RuneTest",Runes.getInstance().getCoreRunesMap().get("8300").getName());
            Log.d("RuneTest",Runes.getInstance().getCoreRunesMap().get("8300").getRuneMap().get("8352").getName() + "name");



        }catch (JSONException e){
            e.printStackTrace();
        }


    }


}
