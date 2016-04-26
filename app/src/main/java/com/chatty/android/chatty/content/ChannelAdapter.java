package com.chatty.android.chatty.content;

import android.animation.LayoutTransition;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chatty.android.chatty.R;

import java.util.List;

/**
 * Created by Alejandro on 25/04/2016.
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.Holder>{


    List<Channel> channels;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.channel_list_item, parent, false);
        v.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.title.setText(channels.get(position).getName());
        holder.description.setText(channels.get(position).getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return channels.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView title;
        TextView description;


        Holder(final View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cardview);
            title = (TextView) itemView.findViewById(R.id.textTitle);
            description = (TextView) itemView.findViewById(R.id.textDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String itemPos = title.getText().toString();
                    Log.d("RecyclerView", "Clicked: " + itemPos);
                }
            });
        }

    }

    public ChannelAdapter(List<Channel> channels) {
        this.channels = channels;
    }

}
