package com.chatty.android.chatty;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.android.chatty.content.Channel;
import com.chatty.android.chatty.content.ChannelAdapter;
import com.chatty.android.chatty.content.DataSource;
import com.chatty.android.chatty.interfaces.RESTConsume;
import com.chatty.android.chatty.utilities.KeyStore;
import com.pubnub.api.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    Pubnub pubnub;
    RecyclerView channelList;
    ChannelAdapter adapter;
    String currentChannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PubnubInit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        channelList = (RecyclerView) findViewById(R.id.channels);
        channelList.setHasFixedSize(true);

        initValues(getApplicationContext());

        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pubnub.publish("mainChannel", "What up", new Callback() {
                        @Override
                        public void successCallback(String channel, Object message) {
                            System.out.println("Sent: " + message.toString());
                        }
                        public void errorCallback(String channel, PubnubError error) {
                            System.out.println("Error: " + error.toString());
                        }
                    });
                }
            });
        }
    }

    private void initValues(Context context) {
        LinearLayoutManager llm = new LinearLayoutManager(context);
        channelList.setLayoutManager(llm);
        adapter = new ChannelAdapter(DataSource.Channels);
        channelList.setAdapter(adapter);
    }

    private void PubnubInit() {
        pubnub = new Pubnub("pub-c-44383ebf-88ef-4845-b1c4-95d5efd0d3bf", "sub-c-c3f6e856-0802-11e6-a5b5-0619f8945a4f");

        try {
            pubnub.subscribe("mainChannel", new Callback() {
                @Override
                public void successCallback(String channel, final Object message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                }

                @Override
                public void connectCallback(String channel, Object message) {
                    super.connectCallback(channel, message);
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    super.reconnectCallback(channel, message);
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    System.out.print("DISCONNECT " + channel + " : " + message.getClass() + " : " + message.toString() );
                }
            });
        }
        catch(PubnubException e) {
            System.out.println(e.toString());
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
