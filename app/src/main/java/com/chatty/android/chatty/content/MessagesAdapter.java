package com.chatty.android.chatty.content;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * Created by Alejandro on 04/05/2016.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {

    private List<JSONArray> messages;

    public MessagesAdapter(List<JSONArray> messages) {

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
            holder.messageText.setText(messages.get(position).getString(0));
            /*if(messages.get(position).getString(1).equals(KeyStore.getInstance().getUserId())) {
                holder.messageText.setBackgroundColor(0xff1f901f);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView messageText;

        Holder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.messageTextItem);
        }
    }
}

