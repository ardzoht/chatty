package com.chatty.android.chatty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailText = (EditText) findViewById(R.id.emailText);
        passwordText = (EditText) findViewById(R.id.passwordText);
        loginButton = (Button) findViewById(R.id.button);
        loadDialog();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!loadingDialog.isShowing()) loadingDialog.show();

                RESTConsume.getInstance().authorizeUser(emailText.getText().toString(), passwordText.getText().toString(), new Callbacks.ChatCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        if(loadingDialog.isShowing()) loadingDialog.dismiss();

                        KeyStore.getInstance().setKey(response);
                        KeyStore.getInstance().setUserId(emailText.getText().toString());
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

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
