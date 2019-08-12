package com.example.riotproject.request;

public interface AsyncCallback {
    void onSuccss(String result);
    void onFail(Exception e);
}
