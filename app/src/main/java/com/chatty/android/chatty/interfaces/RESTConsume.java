package com.chatty.android.chatty.interfaces;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chatty.android.chatty.content.TokenDTO;
import com.chatty.android.chatty.content.UserDTO;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Alejandro on 28/04/2016.
 */
public class RESTConsume {

    private final String BASE_URL = "http://52.205.251.33:9000/";
    private Gson gson;
    private Retrofit restConsumer;
    private RESTInterface apiService;
    private TokenDTO token;

    private interface RESTInterface {

        @POST("/auth/local")
        Call<TokenDTO> authUser(@Body UserDTO user);
    }

    private static RESTConsume ourInstance = new RESTConsume();

    public static RESTConsume getInstance() {
        return ourInstance;
    }

    private RESTConsume() {
        gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        restConsumer = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = restConsumer.create(RESTInterface.class);


    }

    public void authorizeUser(String email, String password, @Nullable final Callbacks.ChatCallback<String> callback) {
        UserDTO user = new UserDTO(email, password);
        Call<TokenDTO> call = apiService.authUser(user);
        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                token = response.body();

                try {
                    KeyStore.getInstance().setKey(token.getToken());
                    if (callback != null) {
                        callback.onSuccess(token.getToken());
                    }
                }
                catch(NullPointerException e) {
                    if (callback != null) {
                        callback.onFailure("Access denied.");
                    }
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(t.getMessage());
                }
            }
        });
    }


}
