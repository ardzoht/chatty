package com.chatty.android.chatty;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.chatty.android.chatty.R;
import com.chatty.android.chatty.content.MessagesAdapter;
import com.chatty.android.chatty.utilities.KeyStore;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alejandro on 04/05/2016.
 */
public class ChatActivity extends AppCompatActivity {

    private EditText messageText;
    private Button sendButton;
    private RecyclerView messagesList;
    private Pubnub pubnub;
    private String channelSelected;
    private List<JSONObject> messages;
    private MessagesAdapter messageAdapter;
    private ToggleButton toggleImportant;
    private Button presenceButton;
    private View status;
    private int currentStatus = 0;
    private SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";
    private Button filter;
    private int filterStatus = 0;

    @Nullable
    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);


        messageText = (EditText) findViewById(R.id.messageText);
        sendButton = (Button) findViewById(R.id.button2);
        messagesList = (RecyclerView) findViewById(R.id.listMessages);
        toggleImportant = (ToggleButton) findViewById(R.id.importantToggle);
        presenceButton = (Button) findViewById(R.id.button3);
        status = (View) findViewById(R.id.view);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        filter = (Button) findViewById(R.id.button4);

        messagesList.setHasFixedSize(true);
        toggleImportant.setTextOff("Normal");
        toggleImportant.setTextOn("Important");

        initValues(getApplicationContext());

        channelSelected = getIntent().getStringExtra("Channel");
        ActionBar titleBar = getSupportActionBar();
        if (titleBar != null) {
            titleBar.setTitle(channelSelected);
        }

        PubnubInit();

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterStatus == 0) {
                    filter.setText("Unfilter");
                    filterStatus = 1;
                    List<JSONObject> messagesFiltered = new ArrayList<JSONObject>();
                    for (JSONObject message : messages) {
                        try {
                            if (message.getString("important").equals("Important")) {
                                messagesFiltered.add(message);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    messageAdapter = new MessagesAdapter(messagesFiltered, getApplicationContext());
                    messagesList.setAdapter(messageAdapter);
                } else {
                    filter.setText("Filterpr");
                    filterStatus = 0;
                    messageAdapter = new MessagesAdapter(messages, getApplicationContext());
                    messagesList.setAdapter(messageAdapter);
                }
            }
        });

        status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GradientDrawable bgShape = (GradientDrawable) status.getBackground();
                JSONObject statusJson = new JSONObject();
                if(currentStatus == 0) {
                    bgShape.setColor(Color.YELLOW);
                    currentStatus = 1;
                    try {
                        statusJson.put("status", "away");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pubnub.setState(channelSelected, sharedpreferences.getString(Email, ""), statusJson, new Callback() {
                        @Override
                        public void successCallback(String channel, Object message) {
                            super.successCallback(channel, message);
                        }
                    });
                }
                else {
                    bgShape.setColor(Color.GREEN);
                    currentStatus = 0;
                    try {
                        statusJson.put("status", "available");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    pubnub.setState(channelSelected, sharedpreferences.getString(Email, ""), statusJson, new Callback() {
                        @Override
                        public void successCallback(String channel, Object message) {
                            super.successCallback(channel, message);
                        }
                    });
                }
            }
        });

        presenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pubnub.hereNow(channelSelected, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        try {
                            JSONObject response = (JSONObject) message;
                            JSONArray users = (JSONArray) response.get("uuids");
                            String[] arrayUsers = new String[users.length()];
                            for(int i = 0;i<users.length();i++) {
                                arrayUsers[i] =  users.getString(i);
                            }
                            Intent i = new Intent(ChatActivity.this, PresenceActivity.class);
                            i.putExtra("Users", arrayUsers);
                            startActivity(i);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        super.errorCallback(channel, error);
                        Log.d("Error", error.toString());
                    }
                });
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isImportant = toggleImportant.isChecked() ? "Important" : "Normal";
                JSONObject array = new JSONObject();
                try {
                    array.put("message", messageText.getText().toString());
                    array.put("important", isImportant);
                    array.put("uuid", sharedpreferences.getString(Email, ""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (messageText.getText().toString().trim().length() != 0) {
                    pubnub.publish(channelSelected, array, new Callback() {
                        @Override
                        public void successCallback(String channel, final Object message) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    messageText.setText("");
                                    messagesList.scrollToPosition(messages.size()-1);
                                }
                            });
                        }

                        @Override
                        public void errorCallback(String channel, PubnubError error) {
                            Log.d("Error", "can't send message");
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        JSONObject statusJson = new JSONObject();
        try {
            statusJson.put("status", "disconnected");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pubnub.setState(channelSelected, sharedpreferences.getString(Email, ""), statusJson, new Callback() {
            @Override
            public void successCallback(String channel, Object message) {
                super.successCallback(channel, message);
            }
        });
    }

    private void initValues(Context context) {
        messages = new ArrayList<>();
        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setStackFromEnd(true);
        messagesList.setLayoutManager(llm);
        messageAdapter = new MessagesAdapter(messages, getApplicationContext());
        messagesList.setAdapter(messageAdapter);
    }
    private void notificate(String title, String message){
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.circle)
                        .setContentTitle(title)
                        .setContentText(message);
        mBuilder.setSound(alarmSound);
        int mNotificationId = 001;
        NotificationManager mNotifyMgr =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    private void PubnubInit() {
        pubnub = new Pubnub("pub-c-44383ebf-88ef-4845-b1c4-95d5efd0d3bf", "sub-c-c3f6e856-0802-11e6-a5b5-0619f8945a4f");
        pubnub.setUUID(sharedpreferences.getString(Email, ""));
        try {
            pubnub.subscribe(channelSelected, new Callback() {
                @Override
                public void successCallback(String channel, final Object message) {
                    messages.add(messages.size(),(JSONObject) message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            messageAdapter.notifyItemInserted(messages.size());
                            try {
                                if(((JSONObject) message).getString("important").equals("Important"))
                                {
                                    if(!((JSONObject) message).getString("uuid").equals(sharedpreferences.getString(Email, "")))
                                        notificate("Important Message", ((JSONObject) message).getString("message"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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
            pubnub.presence(channelSelected, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    JSONObject res = (JSONObject) message;
                    try {
                        if(!res.getString("uuid").equals(sharedpreferences.getString(Email, ""))) {
                            if (res.getJSONObject("data").getString("status").equals("available"))
                                notificate("A user has connected", res.getString("uuid"));
                            else if (res.getJSONObject("data").getString("status").equals("away"))
                                notificate("A user is away", res.getString("uuid"));
                            else if (res.getJSONObject("data").getString("status").equals("disconnected"))
                                notificate("A user has disconnected", res.getString("uuid"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } {

                    }
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                    Log.d("Error", "Couldn't connect to Presence");
                }
            });
        }
        catch(PubnubException e) {
            System.out.println(e.toString());
        }
    }
}
