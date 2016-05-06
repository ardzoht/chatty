package com.chatty.android.chatty.content;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chatty.android.chatty.R;
import com.chatty.android.chatty.utilities.KeyStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Alejandro on 04/05/2016.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {

    private List<JSONObject> messages;
    private final String REGEX = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private final Context mContext;

    public MessagesAdapter(List<JSONObject> messages, Context context) {
        this.mContext = context;
        this.messages = messages;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        v.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        try {
            String messageReceived = messages.get(position).getString("message");
            if(isURL(messageReceived)) {
                holder.messageText.setPaintFlags(holder.messageText.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                holder.messageText.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            }
            holder.messageText.setText(messageReceived);
            if(messages.get(position).getString("important").equals("Important")) {
                holder.messageText.setBackgroundColor(mContext.getResources().getColor(R.color.importantColor));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private boolean isURL(String message) {
        Pattern patt = Pattern.compile(REGEX);
        Matcher match = patt.matcher(message);
        return match.matches();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView messageText;

        Holder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.messageTextItem);
            messageText.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if(isURL(messageText.getText().toString())) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(messageText.getText().toString()));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        }
    }
}

