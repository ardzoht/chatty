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
public class PresenceActivity extends Activity implements Callbacks.CallbackFavorite{

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
        presenceAdapter = new PresenceAdapter(users, getApplicationContext(), PresenceActivity.this);
        userList.setAdapter(presenceAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onUserPressed(final String user) {
        new AlertDialog.Builder(this)
                .setTitle("Add to contact list?")
                .setMessage("Are you sure you want to add this user to your list?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        KeyStore.getInstance().addFavorite(user);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    @Override
    public void onUserAlreadyAdded() {
        new AlertDialog.Builder(this)
                .setTitle("Wait")
                .setMessage("This user already is on your contact list")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
