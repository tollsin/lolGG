package com.example.riotproject.data;

import java.util.Map;

public class Champion {
    private String id;
    private String key;
    private String name;
    private ChampionImgPath imagePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChampionImgPath getImagePath() {
        return imagePath;
    }

    public void setImagePath(ChampionImgPath imagePath) {
        this.imagePath = imagePath;
    }
}
