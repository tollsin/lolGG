package com.example.riotproject.data;

import java.util.Map;

public class Spells {
    private Map<String,Spell> spellMap;

    private static class SingletonHolder{
        private static final Spells INSTANCE = new Spells();
    }

    public static Spells getInstance() {
        return Spells.SingletonHolder.INSTANCE;
    }


    public Map<String, Spell> getSpellMap() {
        return spellMap;
    }

    public void setSpellMap(Map<String, Spell> spellMap) {
        this.spellMap = spellMap;
    }
}
