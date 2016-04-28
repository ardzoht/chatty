package com.chatty.android.chatty.interfaces;

import android.util.Log;

import com.chatty.android.chatty.content.TokenDTO;
import com.chatty.android.chatty.content.UserDTO;
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

    private Gson gson;
    private Retrofit restConsumer;
    private RESTInterface apiService;
    private UserDTO userTest;
    private TokenDTO token;

    private static interface RESTInterface {

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
                .baseUrl("http://52.205.251.33:9000/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        apiService = restConsumer.create(RESTInterface.class);

        // Create testing user
        userTest = new UserDTO("test@example.com", "test");

    }

    public void authorizeUser() {
        Call<TokenDTO> call = apiService.authUser(userTest);
        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                int statusCode = response.code();
                token = response.body();
                Log.d("Success", String.valueOf(statusCode));
                Log.d("Token Received", token.getToken());
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {
                Log.d("Error", "There has been an error bro.");
            }
        });
    }


}
