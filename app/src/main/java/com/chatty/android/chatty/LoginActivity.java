package com.chatty.android.chatty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.chatty.android.chatty.content.Channel;
import com.chatty.android.chatty.interfaces.RESTConsume;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;

/**
 * Created by Alejandro on 28/04/2016.
 */
public class LoginActivity extends Activity {

    private EditText passwordText;
    private EditText emailText;
    private Button loginButton;
    private ProgressDialog loadingDialog;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";
    private WebView webView;
    private ProgressDialog bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login_activity);

                                                                                                    /*
        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.button);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        loadDialog();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!loadingDialog.isShowing()) loadingDialog.show();

                RESTConsume.getInstance().authorizeUser(emailText.getText().toString(), passwordText.getText().toString(), new Callbacks.ChatCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        if(loadingDialog.isShowing()) loadingDialog.dismiss();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        KeyStore.getInstance().setKey(response);
                        KeyStore.getInstance().setUserId(emailText.getText().toString());
                        editor.putString(Email, emailText.getText().toString());
                        editor.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(String error) {

                        if(loadingDialog.isShowing()) loadingDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Access denied", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
                                                                                                                            */
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();/*
        if(!sharedpreferences.getString(Email, "").equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }*/
        Channel newChannel = new Channel("Test Channel", "Testing", LoginActivity.this, LoginActivity.this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadDialog() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loadingDialog.setMessage("Loading. Please wait...");
        loadingDialog.setIndeterminate(true);
        loadingDialog.setCanceledOnTouchOutside(false);
    }
}

