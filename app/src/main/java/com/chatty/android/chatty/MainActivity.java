package com.chatty.android.chatty;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.keystore.KeyNotYetValidException;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.chatty.android.chatty.content.ChannelAdapter;
import com.chatty.android.chatty.content.DataSource;
import com.chatty.android.chatty.utilities.Callbacks;
import com.chatty.android.chatty.utilities.KeyStore;


public class MainActivity extends AppCompatActivity implements Callbacks.CallbackChannel{

    RecyclerView channelList;
    ChannelAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        channelList = (RecyclerView) findViewById(R.id.channels);
        channelList.setHasFixedSize(true);

        initValues(getApplicationContext());

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }

    private void initValues(Context context) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        channelList.setLayoutManager(llm);
        adapter = new ChannelAdapter(DataSource.Channels, MainActivity.this);
        channelList.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            KeyStore.getInstance().setKey("");
           finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void onChannelPressed(String title) {
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("Channel", title);
        startActivity(intent);
    }
}
