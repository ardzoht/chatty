package com.chatty.android.chatty;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chatty.android.chatty.content.MessagesAdapter;
import com.chatty.android.chatty.content.PresenceAdapter;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alejandro on 05/05/2016.
 */
public class ContactListActivity extends Activity {

    private List<String> users;
    private RecyclerView userList;
    private PresenceAdapter contactAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presence_activity);
        users = KeyStore.getInstance().getFavorites();
        userList = (RecyclerView) findViewById(R.id.listUsers);
        setTitle("Contact List");
        userList.setHasFixedSize(true);

        initValues(getApplicationContext());

    }

    private void initValues(Context context) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setStackFromEnd(true);
        userList.setLayoutManager(llm);
        contactAdapter = new PresenceAdapter(users, getApplicationContext(), ContactListActivity.this);
        userList.setAdapter(contactAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
