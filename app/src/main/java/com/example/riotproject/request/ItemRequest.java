package com.example.riotproject.request;

import android.os.AsyncTask;
import android.util.Log;

import com.example.riotproject.data.Item;
import com.example.riotproject.data.Items;
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

public class ItemRequest extends AsyncTask<String ,Void,String> {
    String clientKey = "#########################";

    private String str, receiveMsg;

    @Override
    protected String doInBackground(String... params) {
        URL url = null;
        try {
            url = new URL("http://ddragon.leagueoflegends.com/cdn/9.13.1/data/ko_KR/item.json");

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

            Map<String , Item> map = new HashMap<>();
            while (iter.hasNext()) {

                String key = iter.next();

                Object value = data.get(key);

                JSONObject jsonObj =  new JSONObject(value.toString());
                Item item = new Item();
                item.setKey(key);
                item.setName(jsonObj.getString("name"));
                item.setImagePath(jsonObj.getJSONObject("image").getString("full"));

                map.put(key,item);


            }

            Items.getInstance().setItemMap(map);
            Log.d("itemTest" ,Items.getInstance().getItemMap().get("1001").getName() );
        }catch (JSONException e){
            e.printStackTrace();
        }


    }

}
