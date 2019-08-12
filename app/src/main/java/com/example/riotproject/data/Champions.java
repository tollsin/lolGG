package com.example.riotproject.data;

import java.util.Map;

public class Champions {
    private Map<String ,Champion> championMap;

    private static class SingletonHolder{
        private static final Champions INSTANCE = new Champions();
    }

    public static Champions getInstance() {
        return Champions.SingletonHolder.INSTANCE;
    }

    public Map<String, Champion> getChampionMap() {
        return championMap;
    }

    public void setChampionMap(Map<String, Champion> championMap) {
        this.championMap = championMap;
    }
}
