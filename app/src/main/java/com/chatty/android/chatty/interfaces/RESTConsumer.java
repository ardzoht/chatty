package com.chatty.android.chatty.interfaces;

import com.chatty.android.chatty.content.TokenDTO;
import com.chatty.android.chatty.content.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Alejandro on 28/04/2016.
 */
public interface RESTConsumer {

    @POST("/auth/local")
    Call<TokenDTO> authUser(@Body UserDTO user);
}
