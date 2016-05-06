package com.chatty.android.chatty;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chatty.android.chatty.content.MessagesAdapter;
import com.chatty.android.chatty.content.PresenceAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Alejandro on 05/05/2016.
 */
public class PresenceActivity extends AppCompatActivity {

    private List<String> users;
    private RecyclerView userList;
    private PresenceAdapter presenceAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.presence_activity);
        users = new ArrayList<String>(Arrays.asList(getIntent().getStringArrayExtra("Users")));
        userList = (RecyclerView) findViewById(R.id.listUsers);
        setTitle("Presence");
        userList.setHasFixedSize(true);

        initValues(getApplicationContext());

    }

    private void initValues(Context context) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setStackFromEnd(true);
        userList.setLayoutManager(llm);
        presenceAdapter = new PresenceAdapter(users, getApplicationContext());
        userList.setAdapter(presenceAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
