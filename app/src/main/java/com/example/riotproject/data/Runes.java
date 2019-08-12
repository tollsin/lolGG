package com.example.riotproject.data;

import java.util.Map;

public class Runes {
    Map<String,CoreRunes> coreRunesMap;


    private static class SingletonHolder{
        private static final Runes INSTANCE = new Runes();
    }

    public static Runes getInstance() {
        return Runes.SingletonHolder.INSTANCE;
    }


    public Map<String, CoreRunes> getCoreRunesMap() {
        return coreRunesMap;
    }

    public void setCoreRunesMap(Map<String, CoreRunes> coreRunesMap) {
        this.coreRunesMap = coreRunesMap;
    }
}
