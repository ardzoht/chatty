package com.chatty.android.chatty.content;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chatty.android.chatty.R;

import java.util.List;

/**
 * Created by Alejandro on 04/05/2016.
 */
public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.Holder> {

    private List<String> messages;

    public MessagesAdapter(List<String> messages) {

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
        holder.messageText.setText(messages.get(position));

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

