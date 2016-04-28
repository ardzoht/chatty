package com.chatty.android.chatty.content;


import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;


public class UserDTO {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public UserDTO() {
    }

    /**
     *
     * @param email
     * @param password
     */
    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The password
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     * The password
     */
    public void setPassword(String password) {
        this.password = password;
    }


    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}