package com.chatty.android.chatty.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Alejandro on 28/04/2016.
 */
public class KeyStore {
    private String token;
    private String userId;
    private List<String> favorites = new ArrayList<>();

    private static KeyStore ourInstance = new KeyStore();

    public static KeyStore getInstance() {
        return ourInstance;
    }

    private KeyStore() {
    }


    public void setKey(String key) {
        token = key;
    }

    public String getKey() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public void addFavorite(String user) {
        favorites.add(user);
    }

}
