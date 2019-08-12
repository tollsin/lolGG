package com.example.riotproject.request;

import com.example.riotproject.data.Summoner;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {
    public void parser(String resultJson) {
        Summoner summoner = new Summoner();
        try {
            JSONObject jsonObject = new JSONObject(resultJson);
            summoner.setProfileIconId(jsonObject.getInt("profileIconId"));
            summoner.setAccountId(jsonObject.getString("accountId"));
            summoner.setName(jsonObject.getString("name"));
            summoner.setPuuid(jsonObject.getString("puuid"));
            summoner.setSummonerLevel(jsonObject.getLong("summonerLevel"));
            summoner.setId(jsonObject.getString("id"));
            summoner.setRevisionDate(jsonObject.getLong("revisionDate"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
