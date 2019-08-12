package com.example.riotproject.request;

import android.content.ContentValues;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class RequestHttpURLConnection {
    ContentValues _params;
    String _url;

    public String request(String _url , ContentValues _params,String method){
        this._params = _params;
        this._url = _url;

        if(method.equals("post"))
           return PostReqeust();
        else
            return GetReqeust();

    }

    public String GetReqeust (){
        HttpURLConnection urlConn = null;


        StringBuffer sbParams = new StringBuffer();

        if(_params == null){
            sbParams.append("");
        }else{
            boolean isAnd = false;
            String key;
            String value;
            for(Map.Entry<String,Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();
                if (isAnd) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=").append(value);

                if (!isAnd) {
                    if (_params.size() >= 2) {
                        isAnd = true;
                    }
                }
            }
        }
        try {

            URL url = new URL(_url + "?" + sbParams.toString());
            Log.d("test",url.toString());
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");



            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d("test","애러 :" + urlConn.getResponseCode());
                if(urlConn.getResponseCode() == 404){
                    return String.valueOf(404);
                }else
                    return "애러";
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));


            String line;
            String page = "";


            while ((line = reader.readLine()) != null){
                page += line;
            }
            Log.d("test",page);
            return page;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }



    public String PostReqeust(){
        HttpURLConnection urlConn = null;


        StringBuffer sbParams = new StringBuffer();

        if(_params == null){
            sbParams.append("");
        }else{
            boolean isAnd = false;
            String key;
            String value;
            for(Map.Entry<String,Object> parameter : _params.valueSet()) {
                key = parameter.getKey();
                value = parameter.getValue().toString();
                if (isAnd) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=").append(value);

                if (!isAnd) {
                    if (_params.size() >= 2) {
                        isAnd = true;
                    }
                }
            }
        }
        try {

            URL url = new URL(_url) ;
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setRequestProperty("Accept-Charset", "UTF-8");
            urlConn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            String strParams = sbParams.toString();
            OutputStream os = urlConn.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            if (urlConn.getResponseCode() != HttpURLConnection.HTTP_OK){
                Log.d("test","애러 :" + urlConn.getResponseCode());
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));


            String line;
            String page = "";


            while ((line = reader.readLine()) != null){
                page += line;
            }

            return page;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConn != null)
                urlConn.disconnect();
        }
        return null;
    }


}
