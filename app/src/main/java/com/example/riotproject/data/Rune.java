package com.example.riotproject.data;

public class Rune {
    private int id;
    private String key,iconImgPath,name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIconImgPath() {
        return iconImgPath;
    }

    public void setIconImgPath(String iconImgPath) {
        this.iconImgPath = iconImgPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
