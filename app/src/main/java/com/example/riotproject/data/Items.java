package com.example.riotproject.data;

import java.util.Map;

public class Items {

    private Map<String,Item> itemMap;

    private static class SingletonHolder{
        private static final Items INSTANCE = new Items();
    }

    public static Items getInstance() {
        return Items.SingletonHolder.INSTANCE;
    }

    public Map<String, Item> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<String, Item> itemMap) {
        this.itemMap = itemMap;
    }
}
