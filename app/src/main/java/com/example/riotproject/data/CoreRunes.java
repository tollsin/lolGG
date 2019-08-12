package com.example.riotproject.data;

import java.util.Map;

public class CoreRunes {
    private Map<String,Rune> runeMap;
    private String name;
    private String iconImgPath;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<String, Rune> getRuneMap() {
        return runeMap;
    }

    public void setRuneMap(Map<String, Rune> runeMap) {
        this.runeMap = runeMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconImgPath() {
        return iconImgPath;
    }

    public void setIconImgPath(String iconImgPath) {
        this.iconImgPath = iconImgPath;
    }
}
