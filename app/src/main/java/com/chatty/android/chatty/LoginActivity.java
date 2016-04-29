package com.chatty.android.chatty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chatty.android.chatty.interfaces.RESTConsume;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;

/**
 * Created by Alejandro on 28/04/2016.
 */
public class LoginActivity extends AppCompatActivity {

    private EditText passwordText;
    private EditText emailText;
    private Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RESTConsume.getInstance().authorizeUser(emailText.getText().toString(), passwordText.getText().toString(), new Callbacks.ChatCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        KeyStore.getInstance().setKey(response);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d("Error", error);
                    }
                });

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
