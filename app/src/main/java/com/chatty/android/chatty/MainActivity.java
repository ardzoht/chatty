package com.chatty.android.chatty;

import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.chatty.android.chatty.content.Channel;
import com.chatty.android.chatty.content.ChannelAdapter;
import com.chatty.android.chatty.content.DataSource;
import com.pubnub.api.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    Pubnub pubnub;
    ListView channelList;
    ArrayAdapter adapter;
    String currentChannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PubnubInit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        channelList = (ListView) findViewById(R.id.channels);

        initValues();

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

    private void initValues() {
        adapter = new ChannelAdapter<Channel>(this, DataSource.Channels);
        channelList.setAdapter(adapter);
        channelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Channel channelSelected = (Channel)adapter.getItem(position);
                currentChannel = channelSelected.getName();
                Toast.makeText(view.getContext(), currentChannel, Toast.LENGTH_LONG).show();

            }
        });
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
