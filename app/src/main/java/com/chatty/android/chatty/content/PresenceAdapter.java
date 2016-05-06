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
 * Created by Alejandro on 05/05/2016.
 */
public class PresenceAdapter extends RecyclerView.Adapter<PresenceAdapter.Holder> {

    private List<String> users;
    private final Context mContext;

    public PresenceAdapter(List<String> users, Context mContext) {
        this.users = users;
        this.mContext = mContext;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent, false);
        v.getLayoutParams().height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.userTextView.setText(users.get(position).substring(2, users.get(position).length() - 2));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView userTextView;

        public Holder(View itemView) {
            super(itemView);
            userTextView = (TextView) itemView.findViewById(R.id.userTextItem);
        }
    }
}
