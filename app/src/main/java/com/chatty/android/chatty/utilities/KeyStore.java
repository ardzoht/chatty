package com.chatty.android.chatty.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;


/**
 * Created by Alejandro on 28/04/2016.
 */
public class KeyStore {
    private String token;

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
}
